/***
 * @pName Admin
 * @name ITeamService
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.Team;

import java.util.List;

public interface ITeamService {
    /**
     * 获取团队详细信息列表 DF 2018年12月17日23:25:20
     * @param teamIdList
     * @return
     */
    List<Team> getTeams(String teamIdList);

}
