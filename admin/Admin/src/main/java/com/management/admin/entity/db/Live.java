package com.management.admin.entity.db;

import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = " SELECT LAST_INSERT_ID()")
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
    private Date editDate;

    public Integer getLiveId() {
        return liveId;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public Date getLiveDate() {
        return liveDate;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public Integer getAdId() {
        return adId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public Date getEditDate() {
        return editDate;
    }
}
