package com.management.admin.biz.impl;

import com.management.admin.biz.ILiveService;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.dbExt.LiveHotDetail;
import com.management.admin.repository.LiveMapper;
import com.management.admin.repository.ScheduleMapper;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LiveServiceImpl implements ILiveService {

    private LiveMapper liveMapper;
    private ScheduleMapper scheduleMapper;

    @Autowired
    public  LiveServiceImpl(LiveMapper liveMapper,ScheduleMapper scheduleMapper){
        this.liveMapper = liveMapper;
        this.scheduleMapper = scheduleMapper;
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
    public Integer deleteLive(Integer liveId) {
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
    public Integer insertLive(LiveDetail liveDetail) {
        Live live = new Live();
        live.setAddDate(new Date());
        live.setLiveDate(liveDetail.getLiveDate());
        live.setLiveTitle(liveDetail.getLiveTitle());
        live.setSourceUrl(liveDetail.getSourceUrl());
        live.setAdId(liveDetail.getAdId());
        // 设置赛事
        live.setScheduleId(scheduleMapper.queryScheduleByGameId(liveDetail.getGameId()).getScheduleId());
        live.setStatus(0);
        live.setShareCount(0);
        live.setCollectCount(0);
        Integer result = liveMapper.insert(live);
        if(result>0){
            return 1;
        }
        return 0;
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
        return liveMapper.selectHotLives();
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
            where += " AND (" + ConditionUtil.like("live_id", condition, true, "t1");
            if (condition.split("-").length == 2){
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
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (beginTime != null && beginTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }

    @Override
    public List<Live> queryAll() {
        return liveMapper.queryAll();
    }
}
