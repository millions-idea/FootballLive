/***
 * @pName Admin
 * @name HotGame
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.entity.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.management.admin.entity.db.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotGame {
    private Integer liveId;
    private String liveTitle;
    private String liveDate;

    private Integer gameId;
    private String gameName;

    private Team team;
    private Team targetTeam;


    private Integer masterTeamId;
    private Integer targetTeamId;
    private String masterTeamName;
    private String masterTeamIcon;
    private String targetTeamName;
    private String targetTeamIcon;
}
