package com.management.admin.repository;

import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
import com.management.admin.entity.dbExt.TeamCompetition;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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
     * 查询赛程信息列表 DF 2018年12月18日02:26:40
     * @param condition
     * @return
     */
    @Select("SELECT t1.live_id, t1.live_title, t1.live_date, t1.source_url, t2.*, t3.game_name, t3.category_id FROM tb_lives t1 " +
            "LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t3.game_id = t2.game_id " +
            "WHERE t1.status = 0 AND t2.is_delete = 0 AND t3.is_delete = 0 " +
            "AND ${condition} " +
            "ORDER BY t1.live_date desc")
    List<LiveScheduleDetail> selectScheduleDetailList(@Param("gameId") Integer gameId, @Param("categoryId") Integer categoryId,
                                                      @Param("condition") String condition);


    @Select("SELECT * FROM tb_schedules as t1 LEFT JOIN tb_games as t2 on t1.game_id=t2.game_id " +
            "LEFT JOIN tb_teams t3 ON t3.team_id = t1.win_team_id" +
            " WHERE t1.schedule_id=#{scheduleId} and  t1.is_delete=0 ")
    /**
     * 查询赛程BY赛程ID Timor 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @return
     */
     ScheduleGameTeam selectScheduleById(@Param("scheduleId") Integer scheduleId);



    @Select("SELECT * FROM tb_schedules as t1 LEFT JOIN tb_games as t2 on t1.game_id=t2.game_id " +
            "LEFT JOIN tb_teams t3 ON t3.team_id = t1.win_team_id" +
            " WHERE t1.is_delete=0  LIMIT #{page},${limit}")
    /**
     * 分页查询 Timor 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @return
     */
    List<ScheduleGameTeam> selectLimit(@Param("page") Integer page, @Param("limit") String limit);

    @Select("SELECT COUNT(schedule_id) FROM tb_schedules ")
    /**
     * 分页查询记录数 提莫 2018年8月30日11:33:30
     * @return
     */
    Integer selectLimitCount();


    @Update("update tb_schedules set is_delete=1 where schedule_id=#{scheduleId}")
    /**
     * 删除赛程 提莫 2018年12月19日1:40:30
     * @return
     */
    Integer deleteSchedule(@Param("scheduleId") Integer scheduleId);


    @Update("update tb_schedules set game_id=#{gameId},team_id=#{teamId}, game_date=#{gameDate}, game_duration=#{gameDuration}," +
            "  status=#{status}, schedule_result=#{scheduleResult}, schedule_grade=#{scheduleGrade}," +
            "  win_team_id=#{winTeamId} where schedule_id=#{scheduleId}")
    /**
     * 修改赛程 提莫 2018年12月19日1:40:30
     * @return
     */
    Integer updateSchedule(Schedule schedule);


    @Insert("insert into tb_schedules(game_id,team_id,game_date,game_duration) values (#{gameId},#{teamId},#{gameDate},#{gameDuration})")
    /**
     * 添加赛程信息 提莫 2018年12月18日14:50:30
     * @return
     */
    Integer addSchedule(Schedule schedule);


}
