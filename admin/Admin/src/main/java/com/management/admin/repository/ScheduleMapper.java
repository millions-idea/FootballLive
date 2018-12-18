package com.management.admin.repository;

import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ScheduleMapper extends MyMapper<Schedule>{

    /**
     * 根据赛程查询相应赛事
     * @param gameId
     * @return
     */
    @Select("select * from tb_schedules where game_id=#{gameId} and is_delete=0")
    Schedule queryScheduleByGameId(Integer gameId);

    @Update("update tb_schedules set status=#{status} where schedule_id=#{scheduleId} and is_delete=0")
    Integer modifyStatusById(@Param("scheduleId") Integer scheduleId,@Param("status") Integer status);

    /**
     * 查询赛事信息列表 DF 2018年12月18日02:26:40
     * @param gameId
     * @param condition
     * @return
     */
    @Select("SELECT t1.live_id, t1.live_title, t1.live_date, t1.source_url, t2.*, t3.game_name FROM tb_lives t1 " +
            "LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t3.game_id = t2.game_id " +
            "WHERE t1.status = 0 AND t2.is_delete = 0 AND t3.is_delete = 0 " +
            "AND ${condition} " +
            "ORDER BY t1.live_date ")
    List<LiveScheduleDetail> selectScheduleDetailList(@Param("gameId") Integer gameId,@Param("condition") String condition);
}
