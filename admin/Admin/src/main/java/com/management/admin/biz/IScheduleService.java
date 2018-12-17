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

import java.util.List;

public interface IScheduleService {

    /**
     * 获取赛事信息列表 DF 2018年12月18日02:23:48
     * @param gameId
     * @return
     */
    List<LiveScheduleDetail> getScheduleDetailList(Integer gameId);
}
