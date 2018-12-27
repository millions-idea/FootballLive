package com.management.admin.entity.dbExt;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiveDetail {
    /**
     * 直播间编号
     */
    private Integer liveId;
    /**
     * 直播间标题
     */
    private String liveTitle;
    /**
     * 直播时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date liveDate;

    private String liveDateStr;

    /**
     * 直播状态
     */
    private Integer status;
    /**
     * 分享次数
     */
    private Integer shareCount;
    /**
     * 收藏次数
     */
    private Integer collectCount;
    /**
     * 直播链接
     */
    private String sourceUrl;
    /**
     * 广告编号
     */
    private Integer adId;
    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addDate;
    private String addDateStr;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gameDate;
    private String gameDateStr;

    private String gameDuration;

    private Integer liveStatus;

    private String gameName;

    private String gameIcon;

    private Integer teamId;

    private String teamName;

    private String teamIcon;

    private Integer gameId;

    private String targetUrl;

    private Integer scheduleId;

    private String content;
}