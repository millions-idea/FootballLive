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

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleGame {
    private Integer liveId;
    private String liveTitle;
    private String liveDate;
    private String sourceUrl;
    private Integer categoryId;

    private Integer gameId;
    private String gameName;
    private Integer status;

    private Team team;
    private Team targetTeam;



    /**
     * 比赛结果(让胜\让平\让负...)
     */
    private String scheduleResult;
    /**
     * 比赛成绩(-1.5/2或2-1这种格式)
     */
    private String scheduleGrade;
    /**
     * 胜利方球队id
     */
    private Integer winTeamId;
    /**
     * 胜利方球队logo
     */
    private String winTeamIcon;
    /**
     * 胜利方球队名称
     */
    private String winTeamName;

    private Integer masterTeamId;
    private Integer targetTeamId;

    private String masterTeamName;
    private String targetTeamName;

    private String masterTeamIcon;
    private String targetTeamIcon;



    private Integer masterRedChess;
    private Integer masterYellowChess;
    private Integer masterCornerKick;

    private Integer targetRedChess;
    private Integer targetYellowChess;
    private Integer targetCornerKick;

    private Integer cloudId;


    /**
     * 预测结果
     */
    private String forecastResult;
    /**
     * 预测比分
     */
    private String forecastGrade;
    /**
     * 预测胜利球队
     */
    private Integer forecastTeamId;


    private Date gameDate;

    private String gameTime;

}
