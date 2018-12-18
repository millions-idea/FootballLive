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
import com.management.admin.repository.TeamMapper;
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
     * 获取团队详细信息列表 DF 2018年12月17日23:25:20
     *
     * @param teamIdList
     * @return
     */
    @Override
    public List<Team> getTeams(String teamIdList) {
        return teamMapper.selectTeams(teamIdList);
    }

}
