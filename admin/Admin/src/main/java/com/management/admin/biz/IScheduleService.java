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

import java.util.List;

public interface IScheduleService {

    /**
     * 获取赛事信息列表 DF 2018年12月18日02:23:48
     *
     * @param gameId            赛事id 选填
     * @param liveCategoryId    直播分类id 选填
     * @param date              日期时间 2018-12-20 yyyy:MM:dd 选填
     * @return
     */
    List<LiveScheduleDetail> getScheduleDetailList(Integer gameId, Integer liveCategoryId, String date);


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
     * 修改赛程信息 DF 2018年12月17日14:40:233
     * @return
     */
    boolean updateSchedule(Schedule schedule);


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

    /**
     * 查询情报详情信息列表 DF 2018年12月20日19:05:03
     * @param gameId
     * @param liveCategoryId
     * @param date
     * @return
     */
    List<LiveScheduleDetail> getInformationDetailList(Integer gameId, Integer liveCategoryId, String date);

    /**
     * 修改赛程状态为正在直播  timor 2018年12月27日17:12:233
     * @return
     */
    Integer beingSchedule(Integer scheduleId);

    /**
     * 修改赛程状态为已经结束  timor 2018年12月27日17:12:233
     * @return
     */
    Integer endSchedule(Integer scheduleId);

    /**
     * 根据情报id查询赛程信息 DF 2018年12月31日01:01:56
     * @param isrId
     * @return
     */
    ScheduleGameTeam selectScheduleByInfoId(Integer isrId);

    /**
     * 开通直播间 DF 2019年1月2日04:46:08
     * @param scheduleId
     * @return
     */
    boolean openLive(Integer scheduleId);

    /**
     * 批量开通直播间 DF 2019年1月2日16:48:19
     */
    void openLives();
}
