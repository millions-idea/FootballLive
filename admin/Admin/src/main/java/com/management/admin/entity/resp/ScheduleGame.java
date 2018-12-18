/***
 * @pName Admin
 * @name HotGame
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.entity.resp;

import com.management.admin.entity.db.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleGame {
    private Integer liveId;
    private String liveTitle;
    private String liveDate;
    private String sourceUrl;

    private Integer gameId;
    private String gameName;
    private Integer status;

    private Team team;
    private Team targetTeam;
}
