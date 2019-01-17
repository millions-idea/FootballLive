/***
 * @pName Live
 * @name InformationMapper
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.resp.HotInformation;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InformationMapper extends MyMapper<HotInformation> {
    /**
     * 查询热门情报列表 DF 2019年1月10日15:16:54
     * @return
     */
    @Select("SELECT *,   " +
            " t4.team_id AS masterTeamId, t5.team_id AS targetTeamId,   " +
            " t4.team_name AS masterTeamName, t5.team_name AS targetTeamName,   " +
            " t4.team_icon AS masterTeamIcon, t5.team_icon AS targetTeamIcon  " +
            "FROM tb_informations t1  " +
            "LEFT JOIN tb_lives t2 ON t1.live_id = t2.live_id  " +
            "LEFT JOIN tb_schedules t3 ON t2.schedule_id = t3.schedule_id  " +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t3.master_team_id  " +
            "LEFT JOIN tb_teams t5 ON t5.team_id = t3.target_team_id  " +
            "LEFT JOIN tb_games t6 ON t6.game_id = t3.game_id  " +
            "WHERE t1.is_delete = 0 AND (t3.status = 0 OR t3.status = 1) ORDER BY t2.live_date ASC LIMIT 4")
    List<HotInformation> selectTopList();
}
