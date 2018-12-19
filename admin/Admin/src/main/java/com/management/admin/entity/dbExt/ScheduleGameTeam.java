package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName ScheduleGameTeam
 * @Description 封装类 Schedule类Game类Team类
 * @Author ZXL01
 * @Date 2018/12/18 22:10
 * Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleGameTeam {

    private Integer scheduleId;

    private Integer gameId;

    private String teamId;

    private Date gameDate;

    private String gameDuration;

    private Integer  status;

    private String scheduleResult;

    /*private Integer primaryId;//主场球队Id

    private Integer secondId;//客场球队ID*/


    private String scheduleGrade;

    private Integer  winTeamId;

    private String teamName;

    private String teamIcon;

    private String gameName;

    private String gameIcon;

    private Integer categoryId;

    private Integer isDelete;

}
