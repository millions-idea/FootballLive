/***
 * @pName Admin
 * @name ScheduleServiceImpl
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.db.*;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
import com.management.admin.entity.dbExt.ScheduleLiveDetail;
import com.management.admin.entity.resp.NAGroup;
import com.management.admin.entity.resp.NMAsyncSchedule;
import com.management.admin.entity.resp.NMOdds;
import com.management.admin.entity.template.Constant;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.*;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.http.NamiUtil;
import com.management.admin.utils.http.NeteaseImUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
            buffer.append(" AND t3.game_id=#{gameId}");
        }

        //按直播分类id筛选
        if(liveCategoryId != null && liveCategoryId > 0) {
            buffer.append(" AND t3.category_id=#{categoryId}");
        }

        //按日期筛选
        StringBuffer nBuffer = new StringBuffer();
        nBuffer.append(buffer.toString());
        if(date != null && date.length() > 0) {
            buffer.append(" AND " + ConditionUtil.like2("live_date", date, true, "t1"));
        }else{
            buffer.append(" AND " + ConditionUtil.like2("live_date", DateUtil.getCurrentDate(), true, "t1"));
        }

        List<LiveScheduleDetail> list1 = scheduleMapper.selectScheduleDetailList(gameId, liveCategoryId, buffer.toString());

        if(date != null && date.length() > 0) {
            nBuffer.append(" AND " + ConditionUtil.like2("game_date", date, true, "t1"));
        }else{
            nBuffer.append(" AND " + ConditionUtil.like2("game_date", DateUtil.getCurrentDate(), true, "t1"));
        }
        List<LiveScheduleDetail> list2 = scheduleMapper.selectNoStartScheduleList(gameId, liveCategoryId, nBuffer.toString());

        list2.stream().forEach(item -> {
            boolean isExist = list1.stream().anyMatch(nItem -> nItem.getGameId().equals(item.getGameId()) &&
                    nItem.getMasterTeamId().equals(item.getMasterTeamId())
                    &&
                    nItem.getTargetTeamId().equals(item.getTargetTeamId()));
            if(!isExist){
                list1.add(item);
            }
        });


        list1.sort((g1, g2) -> g1.getGameDate().compareTo(g2.getGameDate()));

        if(list1 != null && list1.size() > 0){
            List<LiveScheduleDetail> list = new ArrayList<>();
            //按直播状态区分，已开始、未开始、已结束、比赛时间、距离当前时间最近
            List<LiveScheduleDetail> tempList = list1.stream().filter(item -> item.getStatus().equals(1) && (item.getSourceUrl() != null && item.getSourceUrl() != "#")).collect(toList());
            list.addAll(tempList);

            tempList = list1.stream().filter(item -> item.getStatus().equals(1) && !(item.getSourceUrl() != null && item.getSourceUrl() != "#")).collect(toList());
            list.addAll(tempList);

            tempList = list1.stream().filter(item -> item.getStatus().equals(0)).collect(toList());
            list.addAll(tempList);

            tempList = list1.stream().filter(item -> item.getStatus().equals(2)).collect(toList());
            list.addAll(tempList);

            /*tempList = liveScheduleDetails.stream().filter(item -> list.stream().filter(nItem -> nItem.getLiveId().equals(item.getLiveId())).findFirst().isPresent())
                    .map(item -> item)
                    .sorted(Comparator.comparing(LiveScheduleDetail::getLiveDate).reversed())
                    .collect(Collectors.toList());*/

            tempList = list.stream().filter(item -> !list1.contains(item)).collect(toList());
            list.addAll(tempList);
            return list;
        }
        return list1;
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
        if(list != null && list.size() > 0){
            List<ScheduleGameTeam> list1 = new ArrayList<>();
            //按直播状态区分， 已开始  、未开始、已结束、比赛时间、距离当前时间最近
            List<ScheduleGameTeam> tempList = list.stream().filter(item -> item.getStatus().equals(1)).collect(toList());
            list1.addAll(tempList);

            tempList = list.stream().filter(item -> item.getStatus().equals(0)).collect(toList());
            list1.addAll(tempList);

            tempList = list.stream().filter(item -> item.getStatus().equals(2)).collect(toList());
            list1.addAll(tempList);

            tempList = list.stream().filter(item -> !list1.contains(item)).collect(toList());
            list1.addAll(tempList);
            return list1;
        }
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

        //删除房间与成员关系
        result = chatRoomUserRelationMapper.deleteLive(live.getLiveId()) > 0;
        if(!result) throw new InfoException("删除房间成员关系失败");

        result = chatRoomMapper.deleteLive(live.getLiveId()) > 0;
        if(!result) throw new InfoException("删除房间失败");

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
        schedule.setEditDate(new Date());
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

                    //删除房间与成员关系
                    result = chatRoomUserRelationMapper.deleteLive(live.getLiveId()) > 0;
                    if(!result) throw new InfoException("删除房间成员关系失败");

                    result = chatRoomMapper.deleteLive(live.getLiveId()) > 0;
                    if(!result) throw new InfoException("删除房间失败");

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
     * 开通直播间并配置直播源 DF 2019年1月4日14:22:39
     *
     * @param scheduleId
     * @return
     */
    @Override
    @Transactional
    public boolean openLive(Integer scheduleId, String sourceUrl) {
        Schedule schedule = scheduleMapper.selectByPrimaryKey(scheduleId);
        if(schedule == null) throw new InfoException("赛程不存在");
        schedule.setEditDate(new Date());
        boolean result = scheduleMapper.updateSchedule(schedule) > 0;
        if(!result) throw new InfoException("更新赛程失败");

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
        live.setSourceUrl(sourceUrl);
        live.setAddDate(new Date());
        live.setStatus(0);
        live.setEditDate(new Date());
        live.setTeamIdList(masterTeam.getTeamId() + "," + targetTeam.getTeamId());
        result = liveMapper.insertOrUpdate(live) > 0;
        if(!result)  throw new InfoException("添加直播间失败");
        Live liveDetail = liveMapper.selectBySchedule(schedule.getScheduleId());

        //添加聊天室
        //创建云信聊天室(群组)
        String response = NeteaseImUtil.post("nimserver/team/create.action",
                "owner=" + Constant.HotAccId + "&tname=" + live.getLiveTitle() + "&members=" + JsonUtil.getJson(new String[]{Constant.HotAccId})
                        + "&msg=live&magree=0&joinmode=0");
        NAGroup model = JsonUtil.getModel(response, NAGroup.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");

        //数据本地化备份
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setLiveId(liveDetail.getLiveId());
        chatRoom.setChatRoomId(model.getTid());
        chatRoom.setFrequency(10D);//10s
        result = chatRoomMapper.insertOrUpdate(chatRoom) > 0;
        if(!result) throw new InfoException("备份云端数据失败");
        ChatRoom chatRoomDetail = chatRoomMapper.selectByLive(liveDetail.getLiveId());


        //建立默认的成员关系
        ChatRoomUserRelation chatRoomUserRelation = new ChatRoomUserRelation();
        chatRoomUserRelation.setLiveId(liveDetail.getLiveId());
        chatRoomUserRelation.setRoomId(chatRoomDetail.getRoomId());
        chatRoomUserRelation.setUserId(Constant.HotUserId);
        chatRoomUserRelation.setIsBlackList(0);
        result = chatRoomUserRelationMapper.insertOrUpdate(chatRoomUserRelation) > 0;
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
                    result = chatRoomMapper.insertOrUpdate(chatRoom) > 0;
                    if(result) {
                        //建立默认的成员关系
                        ChatRoomUserRelation chatRoomUserRelation = new ChatRoomUserRelation();
                        chatRoomUserRelation.setLiveId(live.getLiveId());
                        chatRoomUserRelation.setRoomId(chatRoom.getRoomId());
                        chatRoomUserRelation.setUserId(Constant.HotUserId);
                        chatRoomUserRelation.setIsBlackList(0);
                        result = chatRoomUserRelationMapper.insertOrUpdate(chatRoomUserRelation) > 0;
                    }
                }
            }


        });
    }

    /**
     * 删除所有已结束状态但还有直播间的数据 DF 2019年1月4日21:25:24
     *
     * @return
     */
    @Override
    @Transactional
    public boolean cleanLives() {
        List<ScheduleLiveDetail> list = scheduleMapper.selectCloseStatusList();

        //删除云信数据库
        list.forEach(item -> {
            ChatRoom chatRoom = chatRoomMapper.selectByLive(item.getLiveId());
            if(chatRoom != null){
                String response1 = NeteaseImUtil.post("nimserver/team/remove.action",
                        "tid=" + chatRoom.getChatRoomId() + "&owner=" + Constant.HotAccId);
                NAGroup model = JsonUtil.getModel(response1, NAGroup.class);
                //if (!model.getCode().equals(200)) throw new InfoException("删除云信数据库过程中失败" + item.getLiveId());
            }
        });

        //删除本地数据库
        list.forEach(item -> {
            boolean result = chatRoomUserRelationMapper.deleteLive(item.getLiveId()) > 0;
            //if(!result) throw new InfoException("删除房间成员关系过程中失败" + item.getLiveId());
            result = chatRoomMapper.deleteLive(item.getLiveId()) > 0;
            //if(!result) throw new InfoException("删除房间过程中失败" + item.getLiveId());
            result = liveMapper.deleteLive(item.getLiveId()) > 0;
            //if(!result) throw new InfoException("删除直播间过程中失败" + item.getLiveId());
        });

        return true;
    }

    /**
     * 提取分页条件
     * @return
     */
    private String extractLimitWhere(String condition, Integer state,  String beginTime, String endTime) {
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
        // 只获取一种状态的数据
        if(state != null){
            where += " AND t1.status = " + state;
        }
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



    /**
     * 同步赛程列表 DF 2019年1月2日20:43:23
     */
    @Transactional
    public void asyncScheduleList() {
        String response = NamiUtil.get("sports/football/match/live", null);
        if(response == null) throw new InfoException("同步接口数据失败");
        List<List<Object>> list = new ArrayList<>();
        list = JSON.parseObject(response, List.class);
        List<NMAsyncSchedule> nmAsyncSchedules = new ArrayList<>();

        list.stream().forEach(item -> {
            Integer namiScheduleId = new Integer(item.get(0) + "");
            Integer status = new Integer(item.get(1) + "");
            String masterInlineArray = String.valueOf(item.get(2));
            List masterProperty = JSON.parseObject(masterInlineArray, List.class);

            String targetInlineArray = String.valueOf(item.get(3));
            List targetProperty = JSON.parseObject(targetInlineArray, List.class);

            Integer masterGrade = Integer.valueOf(masterProperty.get(0) + "");
            Integer masterRedChess = Integer.valueOf(masterProperty.get(2)+ "");
            Integer masterYellowChess = Integer.valueOf(masterProperty.get(3)+ "");
            Integer masterCornerKick = Integer.valueOf(masterProperty.get(4)+ "");

            Integer targetGrade = Integer.valueOf(targetProperty.get(0) + "");
            Integer targetRedChess = Integer.valueOf(targetProperty.get(2) + "");
            Integer targetYellowChess = Integer.valueOf(targetProperty.get(3) + "");
            Integer targetCornerKick = Integer.valueOf(targetProperty.get(4) + "");

            NMAsyncSchedule nmAsyncSchedule = new NMAsyncSchedule();
            nmAsyncSchedule.setNamiScheduleId(namiScheduleId);
            nmAsyncSchedule.setStatus(status);
            nmAsyncSchedule.setMasterGrade(masterGrade);
            nmAsyncSchedule.setMasterCornerKick(masterCornerKick);
            nmAsyncSchedule.setMasterRedChess(masterRedChess);
            nmAsyncSchedule.setMasterYellowChess(masterYellowChess);

            nmAsyncSchedule.setTargetCornerKick(targetCornerKick);
            nmAsyncSchedule.setTargetGrade(targetGrade);
            nmAsyncSchedule.setTargetRedChess(targetRedChess);
            nmAsyncSchedule.setTargetYellowChess(targetYellowChess);

            nmAsyncSchedules.add(nmAsyncSchedule);
        });

        StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE tb_schedules SET ");
        buffer.append("schedule_grade = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            String scheduleGrade = item.getMasterGrade() + "-" + item.getTargetGrade();
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + "'" + scheduleGrade + "'");
        });
        buffer.append(" END,");
        buffer.append("master_red_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("master_yellow_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("master_corner_kick = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("target_red_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("target_yellow_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("target_corner_kick = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("status = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            Integer status = item.getStatus();
            if(status.equals(1)){
                status = 0;//未开赛
            }else if(status.intValue() >= 2 && status.intValue() <= 7){
                status = 1;//进行中
            }else if(status.equals(8)){
                status = 2;//已结束
            }else if(status.intValue() >= 9 && status.intValue() <= 13){
                status = 3;//推迟
            }else {
                status = 4;//未知
            }
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + status);
        });
        buffer.append(" END ");
        List<String> collect = nmAsyncSchedules.stream().map(item -> item.getNamiScheduleId() + "").collect(toList());
        buffer.append("WHERE nami_schedule_id IN(" + String.join(",",collect) + ")");
        scheduleMapper.execUpdate(buffer.toString());
        return;
    }

    @Transactional
    public void asyncFeiJingScheduleList() {
        String response = NamiUtil.get("sports/football/match/live", null);
        if(response == null) throw new InfoException("同步接口数据失败");
        List<List<Object>> list = new ArrayList<>();
        list = JSON.parseObject(response, List.class);
        List<NMAsyncSchedule> nmAsyncSchedules = new ArrayList<>();

        list.stream().forEach(item -> {
            Integer namiScheduleId = new Integer(item.get(0) + "");
            Integer status = new Integer(item.get(1) + "");
            String masterInlineArray = String.valueOf(item.get(2));
            List masterProperty = JSON.parseObject(masterInlineArray, List.class);

            String targetInlineArray = String.valueOf(item.get(3));
            List targetProperty = JSON.parseObject(targetInlineArray, List.class);

            Integer masterGrade = Integer.valueOf(masterProperty.get(0) + "");
            Integer masterRedChess = Integer.valueOf(masterProperty.get(2)+ "");
            Integer masterYellowChess = Integer.valueOf(masterProperty.get(3)+ "");
            Integer masterCornerKick = Integer.valueOf(masterProperty.get(4)+ "");

            Integer targetGrade = Integer.valueOf(targetProperty.get(0) + "");
            Integer targetRedChess = Integer.valueOf(targetProperty.get(2) + "");
            Integer targetYellowChess = Integer.valueOf(targetProperty.get(3) + "");
            Integer targetCornerKick = Integer.valueOf(targetProperty.get(4) + "");

            NMAsyncSchedule nmAsyncSchedule = new NMAsyncSchedule();
            nmAsyncSchedule.setNamiScheduleId(namiScheduleId);
            nmAsyncSchedule.setStatus(status);
            nmAsyncSchedule.setMasterGrade(masterGrade);
            nmAsyncSchedule.setMasterCornerKick(masterCornerKick);
            nmAsyncSchedule.setMasterRedChess(masterRedChess);
            nmAsyncSchedule.setMasterYellowChess(masterYellowChess);

            nmAsyncSchedule.setTargetCornerKick(targetCornerKick);
            nmAsyncSchedule.setTargetGrade(targetGrade);
            nmAsyncSchedule.setTargetRedChess(targetRedChess);
            nmAsyncSchedule.setTargetYellowChess(targetYellowChess);

            nmAsyncSchedules.add(nmAsyncSchedule);
        });

        StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE tb_schedules SET ");
        buffer.append("schedule_grade = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            String scheduleGrade = item.getMasterGrade() + "-" + item.getTargetGrade();
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + "'" + scheduleGrade + "'");
        });
        buffer.append(" END,");
        buffer.append("master_red_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("master_yellow_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("master_corner_kick = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("target_red_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("target_yellow_chess = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("target_corner_kick = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + item.getMasterRedChess());
        });
        buffer.append(" END,");
        buffer.append("status = CASE nami_schedule_id ");
        nmAsyncSchedules.forEach(item -> {
            Integer status = item.getStatus();
            if(status.equals(1)){
                status = 0;//未开赛
            }else if(status.intValue() >= 2 && status.intValue() <= 7){
                status = 1;//进行中
            }else if(status.equals(8)){
                status = 2;//已结束
            }else if(status.intValue() >= 9 && status.intValue() <= 13){
                status = 3;//推迟
            }else {
                status = 4;//未知
            }
            buffer.append(" WHEN " + item.getNamiScheduleId() + " THEN " + status);
        });
        buffer.append(" END ");
        List<String> collect = nmAsyncSchedules.stream().map(item -> item.getNamiScheduleId() + "").collect(toList());
        buffer.append("WHERE nami_schedule_id IN(" + String.join(",",collect) + ")");
        scheduleMapper.execUpdate(buffer.toString());
        return;
    }
}
