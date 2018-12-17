package com.management.admin.entity.db;

import lombok.*;

import javax.persistence.Table;
import java.util.Date;

/**
 * 直播间表
 */
@Table(name = "tb_lives")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Live {
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
    private Date liveDate;
    /**
     * 赛程信息
     */
    private Integer scheduleId;
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
    private Date addDate;
}