/***
 * @pName Admin
 * @name ScheduleServiceImpl
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.db.*;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
import com.management.admin.entity.dbExt.ScheduleLiveDetail;
import com.management.admin.entity.resp.NAGroup;
import com.management.admin.entity.template.Constant;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.*;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.http.NeteaseImUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    private final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
    private final ScheduleMapper scheduleMapper;
    private final InformationMapper informationMapper;
    private final LiveMapper liveMapper;
    private final TeamMapper teamMapper;
    private final ChatRoomMapper chatRoomMapper;
    private final ChatRoomUserRelationMapper chatRoomUserRelationMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper, InformationMapper informationMapper, LiveMapper liveMapper, TeamMapper teamMapper, ChatRoomMapper chatRoomMapper, ChatRoomUserRelationMapper chatRoomUserRelationMapper) {
        this.scheduleMapper = scheduleMapper;
        this.informationMapper = informationMapper;
        this.liveMapper = liveMapper;
        this.teamMapper = teamMapper;
        this.chatRoomMapper = chatRoomMapper;
        this.chatRoomUserRelationMapper = chatRoomUserRelationMapper;
    }

    /**
     * 获取赛事信息列表 DF 2018年12月18日02:23:48
     *
     *
     * @param gameId            赛事id 选填
     * @param liveCategoryId    直播分类id 选填
     * @param date              日期时间 2018-12-20 yyyy:MM:dd 选填
     * @return
     */
    @Override
    public List<LiveScheduleDetail> getScheduleDetailList(Integer gameId, Integer liveCategoryId, String date) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" 1=1 ");

        //按赛事id筛选
        if(gameId != null && gameId > 0){
            buffer.append(" AND t2.game_id=#{gameId}");
        }

        //按直播分类id筛选
        if(liveCategoryId != null && liveCategoryId > 0) {
            buffer.append(" AND t3.category_id=#{categoryId}");
        }

        //按日期筛选
        if(date != null && date.length() > 0) {
            buffer.append(" AND " + ConditionUtil.like2("live_date", date, true, "t1"));
        }

        List<LiveScheduleDetail> liveScheduleDetails = scheduleMapper.selectScheduleDetailList(gameId, liveCategoryId, buffer.toString());

        if(liveScheduleDetails != null && liveScheduleDetails.size() > 0){
            List<LiveScheduleDetail> list = new ArrayList<>();
            //按直播状态区分，已开始、未开始、已结束、比赛时间、距离当前时间最近
            List<LiveScheduleDetail> tempList = liveScheduleDetails.stream().filter(item -> item.getStatus().equals(1)).collect(Collectors.toList());
            list.addAll(tempList);

            tempList = liveScheduleDetails.stream().filter(item -> item.getStatus().equals(0)).collect(Collectors.toList());
            list.addAll(tempList);

            tempList = liveScheduleDetails.stream().filter(item -> item.getStatus().equals(2)).collect(Collectors.toList());
            list.addAll(tempList);

            /*tempList = liveScheduleDetails.stream().filter(item -> list.stream().filter(nItem -> nItem.getLiveId().equals(item.getLiveId())).findFirst().isPresent())
                    .map(item -> item)
                    .sorted(Comparator.comparing(LiveScheduleDetail::getLiveDate).reversed())
                    .collect(Collectors.toList());*/

            tempList = list.stream().filter(item -> !liveScheduleDetails.contains(item)).collect(Collectors.toList());
            list.addAll(tempList);

            return list;
        }

        return liveScheduleDetails;
    }

    /**
     * 加载赛程信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    @Override
    public Integer getScheduleLimitCount() {
        return scheduleMapper.selectLimitCount();
    }

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<ScheduleGameTeam> getScheduleLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition,state,beginTime,endTime);
        List<ScheduleGameTeam> list = scheduleMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 删除赛程信息 DF 2018-12-17 14:39:562
     * @return
     */
    @Override
    @Transactional
    public boolean deleteSchedule(Integer scheduleId) {
        Live live = liveMapper.selectBySchedule(scheduleId);
        if(live == null) throw new InfoException("无法找到关联直播间");

        //删除直播间
        boolean result = liveMapper.deleteLive(live.getLiveId()) > 0;
        if(!result) throw new InfoException("删除直播间关系失败");

        //删除赛程
        result = scheduleMapper.deleteSchedule(scheduleId) > 0;
        if(!result) throw new InfoException("删除赛程关系失败");

        //同步云信
        String response = NeteaseImUtil.post("nimserver/team/remove.action",
                "tid=" + "" + "&owner=" + Constant.HotAccId);
        NAGroup model = JsonUtil.getModel(response, NAGroup.class);
        if (!model.getCode().equals(200)) {
            logger.info("删除云端数据失败");
        }

        return result;
    }

    /**
     * 修改赛程信息 DF 2018-12-17 14:39:562
     * @return
     */
    @Override
    @Transactional
    public boolean updateSchedule(Schedule schedule) {
        boolean result=scheduleMapper.updateSchedule(schedule) > 0;
        if(result){
            //判断本次是开始比赛还是结束比赛, 创建或解散聊天室
            if(schedule.getStatus().equals(1)){
                //创建聊天室
                Live live = liveMapper.selectBySchedule(schedule.getScheduleId());
                if(live == null) throw new InfoException("直播间不存在");

                String response = NeteaseImUtil.post("nimserver/team/create.action",
                        "owner=" + Constant.HotAccId + "&tname=" + live.getLiveTitle() + "&members=" + JsonUtil.getJson(new String[]{Constant.HotAccId})
                                + "&msg=live&magree=0&joinmode=0");
                NAGroup model = JsonUtil.getModel(response, NAGroup.class);
                if (!model.getCode().equals(200)) {
                    logger.info("同步云端数据失败");
                }else{

                    //数据本地化备份
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setLiveId(live.getLiveId());
                    chatRoom.setChatRoomId(model.getTid());
                    chatRoom.setFrequency(10D);//10s
                    result = chatRoomMapper.insertOrUpdate(chatRoom) > 0;
                    if(!result) throw new InfoException("备份云端数据失败");
                    ChatRoom nChatRoom = chatRoomMapper.selectByLive(chatRoom.getLiveId());

                    //建立默认的成员关系
                    ChatRoomUserRelation chatRoomUserRelation = new ChatRoomUserRelation();
                    chatRoomUserRelation.setLiveId(live.getLiveId());
                    chatRoomUserRelation.setRoomId(nChatRoom.getRoomId());
                    chatRoomUserRelation.setUserId(Constant.HotUserId);
                    chatRoomUserRelation.setIsBlackList(0);
                    result = chatRoomUserRelationMapper.insertOrUpdate(chatRoomUserRelation) > 0;
                    if(!result) throw new InfoException("建立默认成员关系失败");

                }

            }
            if(schedule.getStatus().equals(2)){
                //解散聊天室
                Live live = liveMapper.selectBySchedule(schedule.getScheduleId());
                if(live == null) throw new InfoException("直播间不存在");
                ChatRoom chatRoom = chatRoomMapper.selectByLive(live.getLiveId());
                if(chatRoom != null) {
                    String response = NeteaseImUtil.post("nimserver/team/remove.action",
                            "tid=" + chatRoom.getChatRoomId() + "&owner=" + Constant.HotAccId);
                    NAGroup model = JsonUtil.getModel(response, NAGroup.class);
                    if (!model.getCode().equals(200)) {
                        logger.info("删除云端数据失败");
                    }
                }

            }
            return true;
        }
        return false;
    }


    /**
     * 添加赛程信息 DF 2018-12-17 14:39:562
     * @return
     */
    @Override
    @Transactional
    public boolean addSchedule(Schedule schedule) {
        //添加赛事
        schedule.setScheduleGrade("0-0");
        boolean result = scheduleMapper.insertSelective(schedule) > 0;
        if(!result) throw new InfoException("添加赛事失败");

        //查询参赛球队
        List<Team> teams = teamMapper.selectTeams(schedule.getMasterTeamId() + "," + schedule.getTargetTeamId());
        if(teams == null || teams.size() != 2) throw new InfoException("参赛球队设置错误");
        Team masterTeam = teams.get(0);
        Team targetTeam = teams.get(1);

        //添加直播间
        Live live = new Live();
        live.setLiveTitle(masterTeam.getTeamName() + " VS " + targetTeam.getTeamName());
        live.setLiveDate(schedule.getGameDate());
        live.setScheduleId(schedule.getScheduleId());
        live.setSourceUrl("#");
        live.setAddDate(new Date());
        live.setStatus(0);
        result = liveMapper.insertSelective(live) > 0;

        //添加聊天室
        //创建云信聊天室(群组)
        String response = NeteaseImUtil.post("nimserver/team/create.action",
                "owner=" + Constant.HotAccId + "&tname=" + live.getLiveTitle() + "&members=" + JsonUtil.getJson(new String[]{Constant.HotAccId})
                        + "&msg=live&magree=0&joinmode=0");
        NAGroup model = JsonUtil.getModel(response, NAGroup.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");

        //数据本地化备份
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setLiveId(live.getLiveId());
        chatRoom.setChatRoomId(model.getTid());
        chatRoom.setFrequency(10D);//10s
        result = chatRoomMapper.insert(chatRoom) > 0;
        if(!result) throw new InfoException("备份云端数据失败");

        //建立默认的成员关系
        ChatRoomUserRelation chatRoomUserRelation = new ChatRoomUserRelation();
        chatRoomUserRelation.setLiveId(live.getLiveId());
        chatRoomUserRelation.setRoomId(chatRoom.getRoomId());
        chatRoomUserRelation.setUserId(Constant.HotUserId);
        chatRoomUserRelation.setIsBlackList(0);
        result = chatRoomUserRelationMapper.insert(chatRoomUserRelation) > 0;
        if(!result) throw new InfoException("建立默认成员关系失败");
        return result;
    }

    /**
     * 查询赛程BY ID  DF 2018-12-17 14:39:562
     * @return
     */
    @Override
    public ScheduleGameTeam selectScheduleById(Integer scheduleId) {
        ScheduleGameTeam scheduleGameTeam=scheduleMapper.selectScheduleById(scheduleId);
        if(scheduleGameTeam!=null){
            return scheduleGameTeam;
        }
        return null;
    }

    /**
     * 查询情报详情信息列表 DF 2018年12月20日19:05:03
     *
     * @param gameId
     * @param liveCategoryId
     * @param date
     * @return
     */
    @Override
    public List<LiveScheduleDetail> getInformationDetailList(Integer gameId, Integer liveCategoryId, String date) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" 1=1 ");

        //按赛事id筛选
        if(gameId != null && gameId > 0){
            buffer.append(" AND t4.game_id=#{gameId}");
        }

        //按直播分类id筛选
        if(liveCategoryId != null && liveCategoryId > 0) {
            buffer.append(" AND t4.category_id=#{categoryId}");
        }

        //按日期筛选
        if(date != null && date.length() > 0) {
            buffer.append(" AND " + ConditionUtil.like2("live_date", date, true, "t2"));
        }

        List<LiveScheduleDetail> liveScheduleDetails = informationMapper.selectInformationDetailList(gameId, liveCategoryId, buffer.toString());

        return liveScheduleDetails;
    }

    /**
     * 修改赛程状态为正在直播 timor 2018-12-27 17:39:562
     * @return
     */
    @Override
    public Integer beingSchedule(Integer scheduleId) {
        return  scheduleMapper.beingSchedule(scheduleId);
    }

    /**
     * 修改赛程状态为已经结束 timor 2018-12-27 17:39:562
     * @return
     */
    @Override
    public Integer endSchedule(Integer scheduleId) {
        return  scheduleMapper.endSchedule(scheduleId);
    }

    /**
     * 根据情报id查询赛程信息 DF 2018年12月31日01:01:56
     *
     * @param isrId
     * @return
     */
    @Override
    public ScheduleGameTeam selectScheduleByInfoId(Integer isrId) {
        return scheduleMapper.selectScheduleByInfoId(isrId);
    }

    /**
     * 开通直播间 DF 2019年1月2日04:46:08
     *
     * @param scheduleId
     * @return
     */
    @Override
    @Transactional
    public boolean openLive(Integer scheduleId) {
        Schedule schedule = scheduleMapper.selectByPrimaryKey(scheduleId);
        if(schedule == null) throw new InfoException("赛程不存在");

        //查询参赛球队
        List<Team> teams = teamMapper.selectTeams(schedule.getMasterTeamId() + "," + schedule.getTargetTeamId());
        if(teams == null || teams.size() != 2) throw new InfoException("参赛球队设置错误");
        Team masterTeam = teams.get(0);
        Team targetTeam = teams.get(1);

        //添加直播间
        Live live = new Live();
        live.setLiveTitle(masterTeam.getTeamName() + " VS " + targetTeam.getTeamName());
        live.setLiveDate(schedule.getGameDate());
        live.setScheduleId(schedule.getScheduleId());
        live.setSourceUrl("#");
        live.setAddDate(new Date());
        live.setStatus(0);
        boolean result = liveMapper.insertSelective(live) > 0;

        //添加聊天室
        //创建云信聊天室(群组)
        String response = NeteaseImUtil.post("nimserver/team/create.action",
                "owner=" + Constant.HotAccId + "&tname=" + live.getLiveTitle() + "&members=" + JsonUtil.getJson(new String[]{Constant.HotAccId})
                        + "&msg=live&magree=0&joinmode=0");
        NAGroup model = JsonUtil.getModel(response, NAGroup.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");

        //数据本地化备份
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setLiveId(live.getLiveId());
        chatRoom.setChatRoomId(model.getTid());
        chatRoom.setFrequency(10D);//10s
        result = chatRoomMapper.insert(chatRoom) > 0;
        if(!result) throw new InfoException("备份云端数据失败");

        //建立默认的成员关系
        ChatRoomUserRelation chatRoomUserRelation = new ChatRoomUserRelation();
        chatRoomUserRelation.setLiveId(live.getLiveId());
        chatRoomUserRelation.setRoomId(chatRoom.getRoomId());
        chatRoomUserRelation.setUserId(Constant.HotUserId);
        chatRoomUserRelation.setIsBlackList(0);
        result = chatRoomUserRelationMapper.insert(chatRoomUserRelation) > 0;
        if(!result) throw new InfoException("建立默认成员关系失败");
        return result;
    }

    /**
     * 批量开通直播间 DF 2019年1月2日16:48:19
     */
    @Override
    @Transactional
    public void openLives() {
        List<ScheduleLiveDetail> scheduleList = scheduleMapper.selectScheduleList();
        if(scheduleList == null || scheduleList.size() == 0) throw new InfoException("空的赛程列表");

        scheduleList.stream().filter(item -> item.getLiveId() == null).forEach(item -> {
            //查询参赛球队
            List<Team> teams = teamMapper.selectTeams(item.getMasterTeamId() + "," + item.getTargetTeamId());
            if(teams != null && teams.size() == 2) {
                Team masterTeam = teams.get(0);
                Team targetTeam = teams.get(1);

                //添加直播间
                Live live = new Live();
                live.setLiveTitle(masterTeam.getTeamName() + " VS " + targetTeam.getTeamName());
                live.setLiveDate(item.getGameDate());
                live.setScheduleId(item.getScheduleId());
                live.setSourceUrl("#");
                live.setAddDate(new Date());
                live.setStatus(0);
                boolean result = liveMapper.insertSelective(live) > 0;

                //添加聊天室
                //创建云信聊天室(群组)
                String response = NeteaseImUtil.post("nimserver/team/create.action",
                        "owner=" + Constant.HotAccId + "&tname=" + live.getLiveTitle() + "&members=" + JsonUtil.getJson(new String[]{Constant.HotAccId})
                                + "&msg=live&magree=0&joinmode=0");
                NAGroup model = JsonUtil.getModel(response, NAGroup.class);
                if (model.getCode().equals(200)) {
                    //数据本地化备份
                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setLiveId(live.getLiveId());
                    chatRoom.setChatRoomId(model.getTid());
                    chatRoom.setFrequency(10D);//10s
                    result = chatRoomMapper.insert(chatRoom) > 0;
                    if(result) {
                        //建立默认的成员关系
                        ChatRoomUserRelation chatRoomUserRelation = new ChatRoomUserRelation();
                        chatRoomUserRelation.setLiveId(live.getLiveId());
                        chatRoomUserRelation.setRoomId(chatRoom.getRoomId());
                        chatRoomUserRelation.setUserId(Constant.HotUserId);
                        chatRoomUserRelation.setIsBlackList(0);
                        result = chatRoomUserRelationMapper.insert(chatRoomUserRelation) > 0;
                    }
                }
            }


        });
    }
    /**
     * 提取分页条件
     * @return
     */
    private String extractLimitWhere(String condition, Integer isEnable,  String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if(condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("live_id", condition, true, "t4");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("game_date", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("game_name", condition, true, "t2");
            where += " OR " + ConditionUtil.like("game_id", condition, true, "t2");
            where += " OR " + ConditionUtil.like("team_id", condition, true, "t3");
            where += " OR " + ConditionUtil.like("schedule_id", condition, true, "t1");

            where += " OR " + ConditionUtil.like("team_name", condition, true, "t3");
            where += " OR " + ConditionUtil.like("live_title", condition, true, "t4")+ ")";
        }
        // 取两个日期之间或查询指定日期
        where = extractBetweenTime(beginTime, endTime, where);
        return where.trim();
    }
    /**
     * 提取两个日期之间的条件
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")){
            where += " AND t1.game_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (beginTime != null && beginTime.contains("-")){
            where += " AND t1.game_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (endTime != null && endTime.contains("-")){
            where += " AND t1.game_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }
}
