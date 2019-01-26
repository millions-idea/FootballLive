/***
 * @pName Live
 * @name IScheduleService
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.PublishMessage;
import com.management.admin.entity.resp.HotInformation;
import com.management.admin.entity.resp.HotSchedule;
import com.management.admin.entity.resp.ScheduleEntity;

import java.util.List;

public interface IScheduleService {
    /**
     * 获取指定数量的热门赛程 DF 2019年1月10日11:15:31
     * @return
     */
    List<HotSchedule> getTopList();

    /**
     * 获取热门情报列表 DF 2019年1月10日15:12:52
     * @return
     */
    List<HotInformation> getTopInformationList();

    /**
     * 获取最近的赛程列表 DF 2019年1月12日21:48:57
     * @return
     */
    List<HotSchedule> getRecentList();


    /**
     * 查询赛程列表 DF 2019年1月13日06:09:24
     * @param type
     * @param gameId
     * @param date
     * @return
     */
    List<HotSchedule> getScheduleList(Integer type, Integer gameId, String date);

    /**
     * 获取直播间 DF 2019年1月18日19:02:12
     * @param liveId
     * @return
     */
    HotSchedule getLive(Integer liveId);

    List<HotSchedule> getMarkScheduleList(Integer userId);

    List<HotSchedule> getHistoryScheduleList(Integer userId);

    List<PublishMessage> getMessageList(Integer userId);
}
