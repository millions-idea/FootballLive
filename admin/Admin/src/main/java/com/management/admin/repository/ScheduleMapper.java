package com.management.admin.repository;

import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.*;
import com.management.admin.repository.extendsMapper.InsertListUpdateMapper;
import com.management.admin.repository.extendsMapper.InsertListUpdateScheduleMapper;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper extends MyMapper<Schedule>, InsertListUpdateScheduleMapper<Schedule> {

    /**
     * 根据赛程查询相应赛事
     * @param gameId
     * @return
     */
    @Select("select * from tb_schedules where game_id=#{gameId} and is_delete=0 LIMIT 1")
    Schedule queryScheduleByGameId(Integer gameId);

    @Update("update tb_schedules set status=#{status} where schedule_id=#{scheduleId} and is_delete=0")
    Integer modifyStatusById(@Param("scheduleId") Integer scheduleId,@Param("status") Integer status);

    /**
     * 查询赛程信息列表 DF 2018年12月18日02:26:40
     * @param condition
     * @return
     */
    @Select("SELECT t1.live_id, t1.live_title, t1.live_date, t1.source_url, t2.*, t3.game_name, t3.category_id, " +
            " t4.team_name AS masterTeamName, t4.team_icon AS masterTeamIcon, " +
            " t5.team_name AS targetTeamName, t5.team_icon AS targetTeamIcon " +
            " FROM tb_lives t1 " +
            "LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t3.game_id = t2.game_id " +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t2.master_team_id " +
            "LEFT JOIN tb_teams t5 ON t5.team_id = t2.target_team_id " +
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



    @Select("SELECT *, t3.team_name AS winTeamName FROM tb_schedules as t1 LEFT JOIN tb_games as t2 on t1.game_id=t2.game_id " +
            "LEFT JOIN tb_teams t3 ON t3.team_id = t1.win_team_id  " +
            "LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id  " +
            " WHERE t1.is_delete=0 AND t2.game_id != -1 AND ${condition}  ORDER BY t1.schedule_id DESC LIMIT #{page},${limit}")
    /**
     * 分页查询 Timor 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @return
     */
    List<ScheduleGameTeam> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.schedule_id) FROM tb_schedules t1 LEFT JOIN tb_teams t2 ON t2.team_id = t1.win_team_id WHERE t1.is_delete=0 AND t2.game_id != -1")
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


    @Update("update tb_schedules set game_id=#{gameId},team_id=#{teamId}, game_date=#{gameDate}, game_duration=#{gameDuration}, " +
            "  status=#{status}, schedule_result=#{scheduleResult}, schedule_grade=#{scheduleGrade}, " +
            " master_team_id=#{masterTeamId}, target_team_id=#{targetTeamId}, " +
            "  win_team_id=#{winTeamId}, is_hot=#{isHot} where schedule_id=#{scheduleId}")
    /**
     * 修改赛程 提莫 2018年12月19日1:40:30
     * @return
     */
    Integer updateSchedule(Schedule schedule);


    @Insert("insert into tb_schedules(game_id,team_id,game_date,game_duration, master_team_id, target_team_id) " +
            "values (#{gameId},#{teamId},#{gameDate},#{gameDuration},#{masterTeamId},#{targetTeamId})")
    /**
     * 添加赛程信息 提莫 2018年12月18日14:50:30
     * @return
     */
    Integer addSchedule(Schedule schedule);


    /**
     * 修改赛程状态为正在直播  提莫 2018年12月27日17:09:30
     * @return
     */
    @Update("update tb_schedule status=1 where schedule_id=#{scheduleId}")
    Integer beingSchedule(Integer scheduleId);

    /**
     * 修改赛程状态为已经结束  提莫 2018年12月27日17:10:30
     * @return
     */
    @Update("update tb_schedule status=2 where schedule_id=#{scheduleId}")
    Integer endSchedule(Integer scheduleId);

    /**
     * 根据情报id查询赛程信息 DF 2018年12月31日01:03:04
     * @param isrId
     * @return
     */
    @Select("SELECT * FROM `tb_informations` t1 " +
            "LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id " +
            "LEFT JOIN tb_schedules t3 ON t3.schedule_id = t2.schedule_id " +
            "WHERE t1.isr_id = #{isrId}")
    ScheduleGameTeam selectScheduleByInfoId(@Param("isrId") Integer isrId);

    @Update("UPDATE tb_informations SET ${condition}" +
            "WHERE isr_id = #{isrId}")
    int updateInformation(@Param("forecastGrade") String scheduleGrade, @Param("forecastResult") String scheduleResult
            , @Param("forecastTeamId") Integer winTeamId, @Param("isrId") Integer liveId , @Param("condition") String condition);

    @Select("SELECT * FROM tb_schedules t1 " +
            "LEFT JOIN tb_lives t2 ON t2.schedule_id = t1.schedule_id " +
            "WHERE t1.is_delete = 0")
    List<ScheduleLiveDetail> selectScheduleList();


    @Update("${sql}")
    void execUpdate(@Param("sql") String sql);

    @Select("SELECT t1.*, t3.game_name, t3.category_id,\n" +
            "t4.team_name AS masterTeamName, t4.team_icon AS masterTeamIcon,\n" +
            "t5.team_name AS targetTeamName, t5.team_icon AS targetTeamIcon\n" +
            "FROM tb_schedules t1\n" +
            "LEFT JOIN tb_games t3 ON t3.game_id = t1.game_id\n" +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t1.master_team_id\n" +
            "LEFT JOIN tb_teams t5 ON t5.team_id = t1.target_team_id\n" +
            "WHERE t1.is_delete = 0 AND t3.is_delete = 0\n" +
            "AND ${condition} " +
            "ORDER BY t1.game_date desc")
    List<LiveScheduleDetail> selectNoStartScheduleList(@Param("gameId") Integer gameId, @Param("categoryId") Integer categoryId,
                                                       @Param("condition") String condition);
}
