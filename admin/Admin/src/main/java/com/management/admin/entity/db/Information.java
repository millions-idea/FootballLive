package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

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
}
