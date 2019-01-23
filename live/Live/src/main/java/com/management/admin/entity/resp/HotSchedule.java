/***
 * @pName Live
 * @name HotSchedule
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotSchedule {
    private Integer scheduleId;
    private Integer gameId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gameDate;
    private String gameDateString;
    private Integer gameDuration;
    private Integer status;
    private Integer isDelete;

    private String scheduleResult;
    private String scheduleGrade;
    private Integer winTeamId;
    private Integer masterTeamId;
    private Integer targetTeamId;
    private Integer masterRedChess;
    private Integer masterYellowChess;
    private Integer masterCornerKick;
    private Integer targetRedChess;
    private Integer targetYellowChess;
    private Integer targetCornerKick;

    private Integer isHot;
    private Integer cloudId;
    private Integer namiScheduleId;

    /*lives*/
    private Integer liveId;
    private String liveTitle;
    private String sourceUrl;


    /*teams*/
    private String masterTeamIcon;
    private String masterTeamName;
    private String targetTeamIcon;
    private String targetTeamName;

    /*games*/
    private String gameName;
    private String gameIcon;



    /*ext*/
    private String shortGameDate;
    private String shortStatus;
    private String shortButtonName;
    private String shortStyleClassName;

    private Integer categoryId;
    private String categoryName;
    private Boolean isFootball;

    private String gameTime;

    /*informations*/
    private String content;
    private String forecastResult;
    private String forecastGrade;
    private Integer forecastTeamId;

}
