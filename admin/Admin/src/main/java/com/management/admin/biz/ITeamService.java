/***
 * @pName Admin
 * @name ITeamService
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.dbExt.TeamCompetition;
import com.management.admin.entity.dbExt.TeamDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ITeamService {

    /**
     * 获取所有球队 DF 2018年12月17日23:25:20
     * @return
     */
    List<Team> getAllTeams();

    /**
     * 获取球队详细信息列表 DF 2018年12月17日23:25:20
     * @param teamIdList
     * @return
     */
    List<Team> getTeams(String teamIdList);

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    List<TeamCompetition> getTeamLimit(Integer page, String limit);

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    Integer getTeamLimitCount();

    /**
     * 添加球队信息 提莫 2018年12月18日14:50:30
     * @return
     */
    Boolean addTeam(Team team);

    /**
     * 删除球队信息 提莫 2018年12月18日19:33:30
     * @return
     */
    boolean deleteTeam(Integer teamId);


    /**
     * 获取团队信息 DF 2018年12月30日17:51:33
     * @param gameId
     * @return
     */
    TeamDetail getTeam(Integer gameId);
}
