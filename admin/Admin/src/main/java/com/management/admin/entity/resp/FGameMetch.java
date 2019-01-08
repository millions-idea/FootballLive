/***
 * @pName Admin
 * @name FGameMetch
 * @user HongWei
 * @date 2019/1/5
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FGameMetch {
    /**
     * 联赛ID
     */
    private String id;

    /**
     * 颜色值
     */
    private String color;

    /**
     * 国语名简称
     */
    private String gb_short;

    /**
     * 繁体名简称
     */
    private String big_short;

    /**
     * 英文名简称
     */
    private String en_short;

    /**
     * 国语名全称
     */
    private String gb;

    /**
     * 繁体名全称
     */
    private String big;

    /**
     * 英文名全称
     */
    private String en;

    /**
     * 类型 【类型】1:联赛，2:杯赛
     */
    private String type;

    /**
     * 子联赛名
     */
    private String subSclass;

    /**
     * 总轮次
     */
    private String sum_round;

    /**
     * 当前轮
     */
    private String curr_round;

    /**
     * 当前赛季
     */
    private String Curr_matchSeason;

    /**
     * 所属国家ID
     */
    private String countryID;

    /**
     * 所属国家名
     */
    private String country;

    /**
     * 所属区域联赛ID 【所属区域联赛ID】0:国际联赛，1:欧洲联赛，2:美洲联赛，3:亚洲联赛，4:大洋洲联赛，5:非洲联赛
     */
    private String areaID;

    /**
     * 所属国家名英文名
     */
    private String countryEn;

    /**
     * 联赛图标 【联赛图标】所有图片地址后面加参数 ?win007=sell
     */
    private String logo;

    /**
     * 国家队图标 【国家队图标】所有图片地址后面加参数 ?win007=sell
     */
    private String countryLogo;
}
