/***
 * @pName Admin
 * @name FToDayMatch
 * @user HongWei
 * @date 2019/1/6
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FToDayMatch {
    /**
     * 比赛ID
     */
    private String ID;

    /**
     * 颜色值
     */
    private String color;

    /**
     * 联赛ID
     */
    private String leagueID;

    /**
     * 类型
     【类型】1:联赛，2:杯赛
     */
    private String kind;

    /**
     * 是否是重要比赛
     【是否是重要比赛】0:一般赛事，1:重要赛事
     */
    private String level;

    /**
     * 所属联赛:
     简体名,繁体名,英文名
     */
    private String league;

    /**
     * 子联赛名称
     */
    private String subLeague;

    /**
     *
     子联赛ID
     */
    private String subLeagueId;

    /**
     * 比赛时间
     */
    private String time;

    /**
     * 开场时间
     【开场时间】上半场时为上半场开场时间,下半场为下半场开场时间
     */
    private String time2;

    /**
     * 主队信息:
     简体名,繁体名,英文名,主队ID
     */
    private String home;

    /**
     * 客队信息:
     简体名,繁体名,英文名,客队ID
     */
    private String away;

    /**
     * 比赛状态
     【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
     */
    private String state;

    /**
     * 主队比分
     【主队比分】常规时间
     */
    private String homeScore;

    /**
     * 客队比分
     【客队比分】常规时间
     */
    private String awayScore;


    /**
     * 主队上半场比分
     */
    private String bc1;

    /**
     * 客队上半场比分
     */
    private String bc2;

    /**
     * 主队红牌
     */
    private String red1;

    /**
     * 客队红牌
     */
    private String red2;

    /**
     * 主队黄牌
     */
    private String yellow1;

    /**
     * 客队黄牌
     */
    private String yellow2;

    /**
     * 主队排名
     */
    private String order1;

    /**
     * 客队排名
     */
    private String order2;

    /**
     * 比赛说明
     */
    private String explain;

    /**
     * 是否中立场
     */
    private String zl;

    /**
     * 电视直播
     */
    private String tv;

    /**
     * 是否有阵容
     */
    private String lineup;

    /**
     * 比赛说明2
     * 【比赛说明2】例：CCTV5 上海体育 北京体育;|2;|4;3|90,2-1;3-3;1,2-1;1-3;2
     含义如下：
     【CCTV5 上海体育 北京体育】事件中的电视台信息。
     【2；】先开球 (1：主，2：客);
     【4;3】角球数 主num;客num
     【90,2-1】90分钟 主得分-客得分
     【3-3】两回合 主得分-客得分
     【1,2-1】1：（1: 120分钟/含常规时间；2: 加时/不含常规时间；3: 加时中/含常规时间），2-1：主得分-客得分
     【1-3】点球 主进球数-客进球数
     【2】胜出（1：主，2：客）
     */
    private String explain2;

    /**
     * 主队角球
     */
    private String Corner1;

    /**
     * 客队角球
     */
    private String Corner2;
}
