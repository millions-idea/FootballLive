/***
 * @pName Admin
 * @name ScheduleServiceImpl
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.*;
import com.management.admin.repository.utils.ConditionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    private final ScheduleMapper scheduleMapper;
    private final InformationMapper informationMapper;
    private final LiveMapper liveMapper;
    private final TeamMapper teamMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper, InformationMapper informationMapper, LiveMapper liveMapper, TeamMapper teamMapper) {
        this.scheduleMapper = scheduleMapper;
        this.informationMapper = informationMapper;
        this.liveMapper = liveMapper;
        this.teamMapper = teamMapper;
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
    public List<ScheduleGameTeam> getScheduleLimit(Integer page, String limit) {
        page = ConditionUtil.extractPageIndex(page, limit);
        List<ScheduleGameTeam> list = scheduleMapper.selectLimit(page, limit);
        return list;
    }

    /**
     * 删除赛程信息 DF 2018-12-17 14:39:562
     * @return
     */
    @Override
    public boolean deleteSchedule(Integer scheduleId) {
        Integer result=scheduleMapper.deleteSchedule(scheduleId);
        if(result>0){
            return true;
        }
        return false;
    }

    /**
     * 修改赛程信息 DF 2018-12-17 14:39:562
     * @return
     */
    @Override
    public boolean updateSchedule(Schedule schedule) {
        Integer result=scheduleMapper.updateSchedule(schedule);
        if(result>0){
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
        result = liveMapper.addLive(live) > 0;
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
}
