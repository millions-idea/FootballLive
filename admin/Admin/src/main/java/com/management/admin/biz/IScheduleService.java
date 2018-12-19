/***
 * @pName Admin
 * @name IScheduleService
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
import com.management.admin.entity.dbExt.TeamCompetition;

import java.util.List;

public interface IScheduleService {

    /**
     * 获取赛事信息列表 DF 2018年12月18日02:23:48
     * @param gameId
     * @return
     */
    List<LiveScheduleDetail> getScheduleDetailList(Integer gameId);


    /**
     * 分页加载赛程信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    List<ScheduleGameTeam> getScheduleLimit(Integer page, String limit);

    /**
     * 加载赛程信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    Integer getScheduleLimitCount();

    /**
     * 删除赛程信息 DF 2018年12月17日14:40:233
     * @return
     */
    boolean deleteSchedule(Integer scheduleId);

    /**
     * 添加赛程信息 DF 2018年12月17日14:40:233
     * @return
     */
    boolean addSchedule(Schedule schedule);

    /**
     * 查询赛程信息BY ID   DF 2018年12月17日14:40:233
     * @return
     */
    ScheduleGameTeam selectScheduleById(Integer scheduleId);

}
