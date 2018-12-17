/***
 * @pName Admin
 * @name ScheduleServiceImpl
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.repository.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    private final ScheduleMapper scheduleMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    /**
     * 获取赛事信息列表 DF 2018年12月18日02:23:48
     *
     * @param gameId
     * @return
     */
    @Override
    public List<LiveScheduleDetail> getScheduleDetailList(Integer gameId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" 1=1 ");
        if(gameId != null && gameId > 0) buffer.append(" AND t2.game_id=#{gameId}");
        List<LiveScheduleDetail> liveScheduleDetails = scheduleMapper.selectScheduleDetailList(gameId, buffer.toString());
        return liveScheduleDetails;
    }
}
