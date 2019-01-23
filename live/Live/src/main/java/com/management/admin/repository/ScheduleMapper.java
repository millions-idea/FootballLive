/***
 * @pName Live
 * @name ScheduleMapper
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.resp.HotSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.LinkedList;
import java.util.List;

@Mapper
public interface ScheduleMapper extends MyMapper<HotSchedule> {
    @Select("(SELECT *,  " +
            "   t2.team_name AS masterTeamName, t3.team_name AS targetTeamName,  " +
            "   t2.team_icon AS masterTeamIcon, t3.team_icon AS targetTeamIcon " +
            "   FROM tb_schedules t1  " +
            "   LEFT JOIN tb_teams t2 ON t1.master_team_id = t2.team_id  " +
            "   LEFT JOIN tb_teams t3 ON t1.target_team_id = t3.team_id " +
            "   LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id " +
            "   LEFT JOIN tb_games t5 ON t1.game_id = t5.game_id " +
            "   LEFT JOIN tb_live_categorys t6 ON t5.category_id = t6.category_id " +
            "   WHERE t1.`status` = 0  AND t1.game_date LIKE '${toDay}%' ORDER BY t1.game_date ASC  LIMIT 100)  " +
            "UNION " +
            "(SELECT *,  " +
            "   t2.team_name AS masterTeamName, t3.team_name AS targetTeamName,  " +
            "   t2.team_icon AS masterTeamIcon, t3.team_icon AS targetTeamIcon " +
            "   FROM tb_schedules t1  " +
            "   LEFT JOIN tb_teams t2 ON t1.master_team_id = t2.team_id  " +
            "   LEFT JOIN tb_teams t3 ON t1.target_team_id = t3.team_id " +
            "   LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id " +
            "   LEFT JOIN tb_games t5 ON t1.game_id = t5.game_id " +
            "   LEFT JOIN tb_live_categorys t6 ON t5.category_id = t6.category_id " +
            "   WHERE t1.`status` = 1 AND t1.game_date LIKE '${toDay}%' ORDER BY t1.game_date ASC LIMIT 100) ")
    /**
     * 查询热门赛程列表 DF 2019年1月10日12:05:46
     * @param count 分页数量(倍数)
     */
    List<HotSchedule> selectTopListLimit(@Param("toDay") String toDay);



    @Select("(SELECT *,  " +
            "   t2.team_name AS masterTeamName, t3.team_name AS targetTeamName,  " +
            "   t2.team_icon AS masterTeamIcon, t3.team_icon AS targetTeamIcon " +
            "   FROM tb_schedules t1  " +
            "   LEFT JOIN tb_teams t2 ON t1.master_team_id = t2.team_id  " +
            "   LEFT JOIN tb_teams t3 ON t1.target_team_id = t3.team_id " +
            "   LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id " +
            "   LEFT JOIN tb_games t5 ON t1.game_id = t5.game_id " +
            "   WHERE t1.`status` = 0  AND t1.game_date LIKE '${toDay}%' ORDER BY t1.game_date ASC  LIMIT 100)  " +
            "UNION " +
            "(SELECT *,  " +
            "   t2.team_name AS masterTeamName, t3.team_name AS targetTeamName,  " +
            "   t2.team_icon AS masterTeamIcon, t3.team_icon AS targetTeamIcon " +
            "   FROM tb_schedules t1  " +
            "   LEFT JOIN tb_teams t2 ON t1.master_team_id = t2.team_id  " +
            "   LEFT JOIN tb_teams t3 ON t1.target_team_id = t3.team_id " +
            "   LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id " +
            "   LEFT JOIN tb_games t5 ON t1.game_id = t5.game_id " +
            "   WHERE t1.`status` = 1 AND t1.game_date LIKE '${toDay}%' ORDER BY t1.game_date ASC LIMIT 100) ")
    /**
     * 查询热门赛程列表 DF 2019年1月10日12:05:46
     * @param count 分页数量(倍数)
     */
    List<HotSchedule> selectTopList(@Param("toDay") String toDay);


    /**
     * 根据command查询热门赛程 DF 2019年1月13日15:54:16
     * @param command
     * @return
     */
    @Select("${command}")
    LinkedList<HotSchedule> selectTopListCommand(@Param("command") String command);


    @Select("(SELECT *,  " +
            "   t2.team_name AS masterTeamName, t3.team_name AS targetTeamName,  " +
            "   t2.team_icon AS masterTeamIcon, t3.team_icon AS targetTeamIcon " +
            "   FROM tb_schedules t1  " +
            "   LEFT JOIN tb_teams t2 ON t1.master_team_id = t2.team_id  " +
            "   LEFT JOIN tb_teams t3 ON t1.target_team_id = t3.team_id " +
            "   LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id " +
            "   LEFT JOIN tb_games t5 ON t1.game_id = t5.game_id " +
            "   LEFT JOIN tb_live_categorys t6 ON t5.category_id = t6.category_id " +
            "   WHERE ${where} ORDER BY t1.game_date ASC) ")
    /**
     * 查询热门赛程列表 DF 2019年1月10日12:05:46
     * @param where
     */
    LinkedList<HotSchedule> selecList(@Param("where") String where);

    @Select("SELECT *, " +
            "t3.team_name AS masterTeamName, t4.team_name AS targetTeamName, " +
            "t3.team_icon AS masterTeamIcon, t4.team_icon AS targetTeamIcon  " +
            "FROM tb_lives t1 " +
            "LEFT JOIN tb_schedules t2 ON t1.schedule_id = t2.schedule_id " +
            "LEFT JOIN tb_teams t3 ON t2.master_team_id = t3.team_id  " +
            "LEFT JOIN tb_teams t4 ON t2.target_team_id = t4.team_id  " +
            "LEFT JOIN tb_games t5 ON t2.game_id = t5.game_id " +
            "LEFT JOIN tb_live_categorys t6 ON t5.category_id = t6.category_id  " +
            "LEFT JOIN tb_informations t7 ON t7.live_id = t1.live_id  " +
            "WHERE t1.live_id = #{liveId}")
    HotSchedule selectLive(@Param("liveId") Integer liveId);
}
