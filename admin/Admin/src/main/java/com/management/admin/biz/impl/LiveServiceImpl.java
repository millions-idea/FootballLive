package com.management.admin.biz.impl;

import com.management.admin.biz.ILiveService;
import com.management.admin.entity.db.*;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.dbExt.LiveHotDetail;
import com.management.admin.entity.resp.*;
import com.management.admin.entity.template.Constant;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.*;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.IdWorker;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.http.NeteaseImUtil;
import com.management.admin.utils.http.TencentLiveUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LiveServiceImpl implements ILiveService {
    private final Logger logger = LoggerFactory.getLogger(LiveServiceImpl.class);

    private final LiveMapper liveMapper;
    private final ScheduleMapper scheduleMapper;
    private final ChatRoomMapper chatRoomMapper;
    private final ChatRoomUserRelationMapper chatRoomUserRelationMapper;
    private final LiveCollectMapper liveCollectMapper;
    private final LiveHistoryMapper liveHistoryMapper;


    @Autowired
    public LiveServiceImpl(LiveMapper liveMapper, ScheduleMapper scheduleMapper, ChatRoomMapper chatRoomMapper, ChatRoomUserRelationMapper chatRoomUserRelationMapper, LiveCollectMapper liveCollectMapper, LiveHistoryMapper liveHistoryMapper) {
        this.liveMapper = liveMapper;
        this.scheduleMapper = scheduleMapper;
        this.chatRoomMapper = chatRoomMapper;
        this.chatRoomUserRelationMapper = chatRoomUserRelationMapper;
        this.liveCollectMapper = liveCollectMapper;
        this.liveHistoryMapper = liveHistoryMapper;
    }


    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     *
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<LiveDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        List<LiveDetail> list = liveMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return liveMapper.selectLiveCount();
    }

    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     *
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getLimitCount(String condition, Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        return liveMapper.selectLimitCount(state, beginTime, endTime, where);
    }

    /**
     * 获取用户信息--用户id DF 2018年12月16日19:11:09
     *
     * @param liveId
     * @return
     */
    @Override
    public Live getLiveInfo(Integer liveId) {
        return liveMapper.selectByPrimaryKey(liveId);
    }

    @Override
    @Transactional
    public Integer deleteLive(Integer liveId) {
        // 获取此直播间对应的聊天室
        List<ChatRoom> chatRooms = chatRoomUserRelationMapper.queryChatRoomByLiveId(liveId);

        //删除房间与成员关系
        boolean result = chatRoomUserRelationMapper.deleteLive(liveId) > 0;
        if(!result) throw new InfoException("删除房间成员关系失败");

        result = chatRoomMapper.deleteLive(liveId) > 0;
        if(!result) throw new InfoException("删除房间失败");

        // 遍历下的所有聊天室
        chatRooms.forEach(item -> {
            // 同步云信（删除其下所有聊天室）
           removeRemoteRoom(item.getChatRoomId());
        });

        return liveMapper.deleteLive(liveId);
    }

    @Override
    public LiveDetail queryLiveDetails(Integer liveId) {
        return liveMapper.queryLiveDetailByLiveId(liveId);
    }

    /**
     * 添加直播间
     *
     * @param liveDetail
     * @return
     */
    @Override
    @Transactional
    public Boolean insertLive(LiveDetail liveDetail) {
        Live live = new Live();
        if(liveDetail.getLiveId() == null) {
            liveDetail.setLiveId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
        }
        live.setLiveId(liveDetail.getLiveId());
        live.setAddDate(new Date());
        live.setLiveDate(liveDetail.getLiveDate());
        live.setLiveTitle(liveDetail.getLiveTitle());
        live.setSourceUrl(liveDetail.getSourceUrl());
        if (liveDetail.getAdId() == null) liveDetail.setAdId(0);
        live.setAdId(liveDetail.getAdId());
        // 设置赛事
        live.setScheduleId(scheduleMapper.queryScheduleByGameId(liveDetail.getGameId()).getScheduleId());
        live.setStatus(0);
        live.setShareCount(0);
        live.setCollectCount(0);
        Integer result = liveMapper.insertOrUpdate(live);
        if (result > 0) {
            //创建云信聊天室(群组)

            String json = "owner=" + Constant.HotAccId + "&tname=" + liveDetail.getLiveTitle() + "&members=" + JsonUtil.getJson(new String[]{Constant.HotAccId})
                    + "&msg=live&magree=0&joinmode=0";

            String response = NeteaseImUtil.post("nimserver/team/create.action", json);
            NAGroup model = JsonUtil.getModel(response, NAGroup.class);
            if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");

            //数据本地化备份
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setLiveId(live.getLiveId());
            chatRoom.setChatRoomId(model.getTid());
            chatRoom.setFrequency(10D);//10s
            result = chatRoomMapper.insert(chatRoom);
            if (result <= 0) throw new InfoException("备份云端数据失败");

            //建立默认的成员关系
            ChatRoomUserRelation chatRoomUserRelation = new ChatRoomUserRelation();
            chatRoomUserRelation.setLiveId(live.getLiveId());
            chatRoomUserRelation.setRoomId(chatRoom.getRoomId());
            chatRoomUserRelation.setUserId(Constant.HotUserId);
            chatRoomUserRelation.setIsBlackList(0);
            result = chatRoomUserRelationMapper.insert(chatRoomUserRelation);
            if (result <= 0) throw new InfoException("建立默认成员关系失败");
            return true;
        }
        return false;
    }

    /**
     * 修改直播间
     *
     * @param liveDetail
     * @return
     */
    @Override
    @Transactional
    public Integer modifyLive(LiveDetail liveDetail) {
        Live live = new Live();
        live.setLiveId(liveDetail.getLiveId());
        live.setLiveDate(liveDetail.getLiveDate());
        live.setLiveTitle(liveDetail.getLiveTitle());
        live.setSourceUrl(liveDetail.getSourceUrl());
        if (liveDetail.getAdId() == null) liveDetail.setAdId(0);
        live.setAdId(liveDetail.getAdId());
        live.setStatus(0);
        // 设置赛事
        live.setScheduleId(scheduleMapper.queryScheduleByGameId(liveDetail.getGameId()).getScheduleId());
        Integer result = liveMapper.modifyLiveById(live);

        //Integer reslut2 = scheduleMapper.modifyStatusById(liveDetail.getS)
        if (result > 0) {
            Integer reslut2 = scheduleMapper.modifyStatusById(liveDetail.getScheduleId(), liveDetail.getScheduleStatus());
            if (result > 0 && reslut2 > 0) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    /**
     * 获取热门直播信息 DF 2018年12月17日23:42:36
     *
     * @return
     */
    @Override
    public List<LiveHotDetail> getHotLives() {
        List<LiveHotDetail> list = liveMapper.selectHotLives();
        if(list == null || list.size() == 0){
            list = liveMapper.selectTwoLives();
        }
        return list;
    }

    /**
     * 获取直播间详情信息 DF 2018年12月18日15:27:04
     *
     * @param liveId
     * @return
     */
    @Override
    public LiveInfo getLiveDetailInfo(Integer liveId) {
        return liveMapper.selectLiveDetailInfo(liveId);
    }

    /**
     * 添加收藏 DF 2018年12月19日03:59:40
     *
     * @param liveId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Boolean addCollect(Integer liveId, Integer userId) {
        Live live = liveMapper.selectByPrimaryKey(liveId);
        LiveCollect liveCollect = new LiveCollect();
        liveCollect.setLiveId(liveId);
        liveCollect.setUserId(userId);
        liveCollect.setIsCancel(0);
        liveCollect.setAddDate(new Date());
        liveCollect.setScheduleId(live.getScheduleId());
        //更新我的收藏
        boolean result = liveCollectMapper.insertOrUpdate(liveCollect) > 0;
        if (!result) throw new InfoException("收藏失败");

        //更新直播间收藏总数
        result = liveMapper.addCollectCount(liveId) > 0;
        return result;
    }

    /**
     * 取消收藏 DF 2018年12月19日03:59:48
     *
     * @param liveId
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Boolean cancelCollect(Integer liveId, Integer userId) {
        boolean result = liveCollectMapper.cancelCollect(liveId, userId) > 0;
        if (result) {
            result = liveMapper.reduceCollectCount(liveId) > 0;
        }
        return result;
    }

    /**
     * 提取分页条件
     *
     * @return
     */
    private String extractLimitWhere(String condition, Integer isEnable, String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if (condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("live_id", condition, true, "t1");
            if (condition.split("-").length == 2) {
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t1");
                where += " OR " + ConditionUtil.like("schedule_id", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("game_name", condition, true, "t3");
            where += " OR " + ConditionUtil.like("live_title", condition, true, "t1");
            where += " OR " + ConditionUtil.like("team_name", condition, true, "t4") + ")";
        }
        // 取两个日期之间或查询指定日期
        where = extractBetweenTime(beginTime, endTime, where);
        return where.trim();
    }

    /**
     * 提取两个日期之间的条件
     *
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")) {
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        } else if (beginTime != null && beginTime.contains("-")) {
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        } else if (endTime != null && endTime.contains("-")) {
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }

    @Override
    public List<Live> queryAll() {
        return liveMapper.queryAll();
    }

    /**
     * 设置开始直播 DF 2018年12月20日02:51:29
     *
     * @param liveId
     */
    @Override
    public void setBeginLive(Integer liveId) {
        boolean result = liveMapper.updateStatus(liveId, 1) > 0;
        if (!result) logger.info("刷新直播状态失败");
    }

    /**
     * 添加观看历史 DF 2018年12月20日03:22:12
     *
     * @param userId
     * @param liveId
     */
    @Override
    public void addHistory(Integer userId, Integer liveId) {
        LiveHistory liveHistory = new LiveHistory();
        liveHistory.setLiveId(liveId);
        liveHistory.setUserId(userId);
        boolean result = liveHistoryMapper.insertOrUpdate(liveHistory) > 0;
        if (!result) logger.info("添加观看历史失败");
    }

    /**
     * 获取观看历史 DF 2018年12月20日04:15:05
     *
     * @param userId
     * @return
     */
    @Override
    public List<LiveHistoryInfo> getLiveHistoryList(Integer userId) {
        return liveHistoryMapper.getLiveHistoryList(userId);
    }

    /**
     * 清空观看历史 DF 2018年12月20日04:45:25
     *
     * @param userId
     * @return
     */
    @Override
    public boolean cleanHistorys(Integer userId) {
        return liveHistoryMapper.cleanHistorys(userId) > 0;
    }

    /**
     * 获取个人收藏 DF 2018年12月20日04:54:26
     *
     * @param userId
     * @return
     */
    @Override
    public List<LiveCollectInfo> getLiveCollectList(Integer userId) {
        return liveCollectMapper.getLiveCollectList(userId);
    }

    /**
     * 清空个人收藏 DF 2018年12月20日04:58:02
     *
     * @param userId
     * @return
     */
    @Override
    public boolean cleanCollect(Integer userId) {
        return liveCollectMapper.cleanCollect(userId) > 0;
    }

    /**
     * 加入群组 DF 2018年12月20日23:27:13
     *
     * @param phone
     * @param userId
     * @param liveId
     */
    @Override
    @Transactional
    public String addGroup(String phone, Integer userId, Integer liveId) {
        List<ChatRoom> chatRooms = chatRoomMapper.selectByLive(liveId);
        if (chatRooms == null) return "直播间不存在";

        ChatRoomUserRelation chatRoomUserRelation = chatRoomUserRelationMapper.selectRelation(userId, liveId);

        if (chatRoomUserRelation != null && chatRoomUserRelation.getIsBlackList() == 1) return "您已被加入直播间黑名单";

        chatRoomUserRelationMapper.insertRelation(userId, liveId);

        //同步云端数据
        Optional<ChatRoom> first = chatRooms.stream().filter(item -> !item.getChatRoomId().contains("@")).findFirst();
        if(first.isPresent()) {
            String chatRoomId = first.get().getChatRoomId();

            String response = NeteaseImUtil.post("nimserver/team/add.action", "tid=" + chatRoomId + "&owner=" + Constant.HotAccId
                    + "&members=" + JsonUtil.getJson(new String[]{phone}) + "&magree=0" + "&msg=ADD");
            NAGroup model = JsonUtil.getModel(response, NAGroup.class);
            if (model == null) {
                return "同步云端数据失败";
            } else {
                logger.info(response);
                return "SUCCESS";
            }
        }

        logger.info("addGroup_default");
        return "SUCCESS";
    }

    /**
     * 退出群组 DF 2018年12月24日20:22:25
     *
     * @param userId
     * @param accid
     * @param liveId
     * @return
     */
    @Override
    @Transactional
    public String leaveGroup(Integer userId, String accid, Integer liveId) {
        //解除聊天群组成员关系
        List<ChatRoom> chatRooms = chatRoomMapper.selectByLive(liveId);
        if(chatRooms == null || chatRooms.size() == 0) return null;
        AtomicInteger count = new AtomicInteger();
        chatRooms.forEach((ChatRoom item) -> {
            boolean result = chatRoomUserRelationMapper.deleteMember(userId, accid, item.getLiveId()) > 0;
            removeRemoteRoom(item.getChatRoomId());
            if (result) {
                count.getAndIncrement();
            }
        });
        if(count.get() == chatRooms.size()) return "SUCCESS";
        return null;
    }

    /**
     * 查询所有赛事信息
     *
     * @return
     */
    @Override
    public List<LiveDetail> selectScheduleByLive() {
        return liveMapper.selectScheduleByLive();
    }

    /**
     * 批量解散群组直播间
     *
     * @return
     */
    @Override
    @Transactional
    public Integer bulkDissolution() {
        int result = 0;
        List<ChatRoomUserRelation> chatRoomUserRelations = chatRoomUserRelationMapper.selectAll();
        List<ChatRoom> chatRooms = chatRoomMapper.selectAll();
        List<Live> lives = liveMapper.selectAll();

        lives.forEach(item -> {
            chatRoomUserRelationMapper.deleteLive(item.getLiveId());
            chatRoomMapper.deleteLive(item.getLiveId());
            liveMapper.deleteLive(item.getLiveId());

        });

        chatRooms.forEach(item -> {
            removeRemoteRoom(item.getChatRoomId());
        });

        String response = NeteaseImUtil.post("nimserver/team/joinTeams.action",
                "accid=" + Constant.HotAccId);

        GroupAll group = JsonUtil.getModel(response, GroupAll.class);

        if (group.getInfos() != null && group.getInfos().size() > 0) {
            group.getInfos().forEach(item -> {
                String response1 = NeteaseImUtil.post("nimserver/team/remove.action",
                        "tid=" + item.getTid() + "&owner=" + item.getOwner());
                NAGroup model = JsonUtil.getModel(response1, NAGroup.class);
                if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");
            });
        }
        return 0;
    }

    /**
     * 解散第三方平台的直播间 DF 2019年1月22日14:28:55
     * @param chatRoomId
     */
    private void removeRemoteRoom(String chatRoomId) {
        String response = NeteaseImUtil.post("nimserver/team/remove.action",
                "tid=" + chatRoomId + "&owner=" + Constant.HotAccId);
        NAGroup model = JsonUtil.getModel(response, NAGroup.class);
        if (!model.getCode().equals(200)) {
            logger.info("删除网易云端数据失败");
        }

        if(chatRoomId.contains("@")){
            response = TencentLiveUtil.post("group_open_http_svc/destroy_group", "{\"GroupId\": \"" + chatRoomId + "\"}");
            TGroupResp tGroupResp = JsonUtil.getModel(response, TGroupResp.class);
            if(!tGroupResp.getErrorCode().equals(0)){
                logger.info("删除腾讯云端数据失败");
            }
        }

    }


}