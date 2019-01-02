package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

/**
 * 情报信息
 */
@Table(name = "tb_informations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Information {
    /**
     * 情报信息编号
     */
    private Integer isrId;
    /**
     * 赛事编号
     */
    private Integer gameId;
    /**
     * 直播间编号
     */
    private Integer liveId;
    /**
     * 情报内容
     */
    private String content;

    /**
     * 预测比分
     */
    private String forecastGrade;

    /**
     * 预测胜利球队
     */
    private Integer forecastTeamId;

    /**
     * 预测结果
     */
    private String forecastResult;

    private Date addDate;

    private Integer isHot;
}
