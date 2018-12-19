/***
 * @pName Admin
 * @name TeamMapper
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.dbExt.TeamCompetition;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper extends MyMapper<Team> {
    /**
     * 查询球队详细信息列表 DF 2018年12月17日23:27:00
     * @param teamIdList
     * @return
     */
    @Select("SELECT * FROM tb_teams WHERE team_id IN(${teamId}) AND is_delete = 0 ORDER BY FIELD(team_id,${teamId});")
    List<Team> selectTeams(@Param("teamId") String teamIdList);

<<<<<<< HEAD

    @Select("select * from tb_teams where is_delete =0")
    /**
     * 查询所有球队 Timor 2018年8月30日11:33:22
     */
    List<Team> getTeams();

    @Select("SELECT * FROM tb_teams as t1 LEFT JOIN tb_games as t2 on t1.game_id=t2.game_id " +
            " WHERE t1.is_delete=0  LIMIT #{page},${limit}")
    /**
     * 分页查询 Timor 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @return
     */
    List<TeamCompetition> selectLimit(@Param("page") Integer page, @Param("limit") String limit);

    @Select("SELECT COUNT(team_id) FROM tb_teams ")
    /**
     * 分页查询记录数 提莫 2018年8月30日11:33:30
     * @return
     */
    Integer selectLimitCount();

    @Insert("insert into tb_teams(team_name,team_icon,game_id) values (#{teamName},#{teamIcon},#{gameId})")
    /**
     * 添加球队信息 提莫 2018年12月18日14:50:30
     * @return
     */
    Integer addTeam(Team team);


    @Update("update tb_teams set is_delete=1 where team_id=#{teamId}")
    /**
     * 删除球队信息 提莫 2018年12月18日19:33:30
     * @return
     */
    Integer deleteTeam(@Param("teamId") Integer teamId);
=======
>>>>>>> fbdf85d67e922fcaacc2d2e35b0c64ab52fa9753
}
