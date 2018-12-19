/***
 * @pName Admin
 * @name TeamServiceImpl
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.ITeamService;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.dbExt.TeamCompetition;
import com.management.admin.repository.TeamMapper;
import com.management.admin.repository.utils.ConditionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements ITeamService {
    private final TeamMapper teamMapper;

    @Autowired
    public TeamServiceImpl(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    /**
     * 获取所有球队 DF 2018年12月17日23:25:20
     *
     * @return
     */
    @Override
    public List<Team> getAllTeams() {
        return teamMapper.getTeams();
    }

    /**
     * 获取团队详细信息列表 DF 2018年12月17日23:25:20
     *
     * @param teamIdList
     * @return
     */
    @Override
    public List<Team> getTeams(String teamIdList) {
        return teamMapper.selectTeams(teamIdList);
    }

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    @Override
    public Integer getTeamLimitCount() {
        return teamMapper.selectLimitCount();
    }

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<TeamCompetition> getTeamLimit(Integer page, String limit) {
        page = ConditionUtil.extractPageIndex(page, limit);
        List<TeamCompetition> list = teamMapper.selectLimit(page, limit);
        return list;
    }

    /**
     * 添加球队信息 提莫 2018年12月18日14:50:30
     * @return
     */
    @Override
    public Boolean addTeam(Team team) {
        Integer  result=teamMapper.addTeam(team);
        if(result>0){
            return true ;
        }
        return false ;
    }


    /**
     * 删除球队信息 提莫 2018年12月18日19:33:30
     * @return
     */
    @Override
    public boolean deleteTeam(Integer teamId) {
        Integer  result=teamMapper.deleteTeam(teamId);
        if(result>0){
            return true ;
        }
        return false ;
    }
}
