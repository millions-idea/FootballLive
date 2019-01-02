/***
 * @pName Admin
 * @name LiveGameDetail
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveScheduleDetail {
    /*lives*/
    private Integer liveId;
    private String liveTitle;
    private Date liveDate;
    private String sourceUrl;
    private Integer categoryId;

    /*schedules*/
    private String teamId;
    private Integer status;
    private Integer masterTeamId;
    private Integer targetTeamId;
    private String masterTeamName;
    private String masterTeamIcon;
    private String targetTeamName;
    private String targetTeamIcon;

    /*games*/
    private Integer gameId;
    private String gameName;

    private String teamName;
    private String teamIcon;

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




    private Integer masterRedChess;
    private Integer masterYellowChess;
    private Integer masterCornerKick;

    private Integer targetRedChess;
    private Integer targetYellowChess;
    private Integer targetCornerKick;

    private Integer cloudId;
}
