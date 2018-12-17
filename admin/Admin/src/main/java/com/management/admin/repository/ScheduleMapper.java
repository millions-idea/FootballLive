package com.management.admin.repository;

import com.management.admin.entity.db.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
