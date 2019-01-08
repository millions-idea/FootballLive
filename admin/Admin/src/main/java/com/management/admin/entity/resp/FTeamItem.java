/***
 * @pName Admin
 * @name FTeamItem
 * @user HongWei
 * @date 2019/1/5
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FTeamItem {
    /**
     * 球队ID
     */
    private String id;

    /**
     * 所属联赛ID
     */
    private String lsID;

    /**
     * 简体名
     */
    private String g;

    /**
     * 繁体名
     */
    private String b;

    /**
     * 英文名
     */
    private String e;

    /**
     * 球队成立日期
     */
    private String Found;

    /**
     * 所在地
     */
    private String Area;

    /**
     * 球场
     */
    private String gym;

    /**
     * 球场容量
     */
    private String Capacity;

    /**
     * 队标 【队标】http://zq.win007.com/Image/team/{接口返回的图片地址}?win007=sell
     */
    private String Flag;

    /**
     * 地址
     */
    private String addr;

    /**
     * 球队网址
     */
    private String URL;

    /**
     * 主教练
     */
    private String master;
}
