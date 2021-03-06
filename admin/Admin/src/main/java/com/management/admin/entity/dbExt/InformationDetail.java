package com.management.admin.entity.dbExt;

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
public class InformationDetail {

    private Integer isrId;

    private Integer gameId;

    private Integer liveId;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveDate;
    private String liveDateStr;

    private String liveTitle;

    private Integer scheduleStatus;

    private String gameName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addDate;

    private String addDateStr;

    private String scheduleStatusStr;

    private Integer isHot;

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
    private String winTeamName;
}
