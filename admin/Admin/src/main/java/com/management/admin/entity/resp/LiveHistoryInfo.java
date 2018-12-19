/***
 * @pName Admin
 * @name LiveHistoryDetail
 * @user HongWei
 * @date 2018/12/20
 * @desc
 */
package com.management.admin.entity.resp;


import com.management.admin.entity.db.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveHistoryInfo {
    private Integer historyId;
    private Integer liveId;

    private String liveTitle;
    private String liveDate;

    private Integer gameId;
    private String gameName;
    private Integer status;

    private Team team;
    private Team targetTeam;

    private String teamId;
    private Integer winTeamId;
    private String scheduleResult;
    private String scheduleGrade;

}
