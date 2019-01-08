/***
 * @pName Admin
 * @name FSchedules
 * @user HongWei
 * @date 2019/1/5
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FScheduleMetch {
    /**
     * 【是否隐藏】True为该场比赛从[当天即时比分接口]隐藏
     */
    private String hidden;
    /**
     * 黄牌数
     */
    private String yellow;
    /**
     * 子联赛ID
     */
    private String subID;
    /**
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
     * 比赛ID
     */
    private String a;

    /**
     * 颜色值
     */
    private String b;

    /**
     * 联赛/杯赛信息:
        简体名,繁体名,英文名,联赛/杯赛ID,是否是精简版
        【是否是精简版】0:完全版，1:精简版
     */
    private String c;

    /**
     * 比赛时间
     */
    private String d;

    /**
     * 联赛子类型
     */
    private String e;

    /**
     * 【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10:取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
     */
    private String f;

    /**
     * 主队信息:
        简体名,繁体名,英文名,主队ID
     */
    private String h;

    /**
     * 客队信息:
        简体名,繁体名,英文名,客队ID
     */
    private String i;

    /**
     * 主队比分 【主队比分】常规时间
     */
    private String j;

    /**
     * 客队比分 【客队比分】常规时间
     */
    private String k;

    /**
     * 主队半场比分
     */
    private String l;

    /**
     * 客队半场比分
     */
    private String m;

    /**
     * 主队红牌
     */
    private String n;

    /**
     * 客队红牌
     */
    private String o;

    /**
     * 主队排名
     */
    private String p;

    /**
     * 客队排名
     */
    private String q;

    /**
     * 赛事说明 【赛事说明】格式为![CDATA[赛事说明的内容]]
     */
    private String r;

    /**
     * 轮次/分组名 轮次/分组名】例：<s>4/8强/准决赛<s>
     */
    private String s;

    /**
     * 比赛地点
     */
    private String t;

    /**
     * 天气图标
     */
    private String u;

    /**
     * 天气 【天气】1:天晴，2:大致天晴，3:间中有云，4:多云，5:微雨，6:大雨，7:雪，8:雷暴，9:地区性雷暴，10:有雾，11:中雨，12:阴天，13:雷陣雨
     */
    private String v;

    /**
     * 温度
     */
    private String w;

    /**
     * 赛季 【赛季】例：<x>2013-2014</x>
     */
    private String x;

    /**
     * 是否中立场 【是否中立场】True:中立场，False:非中立场
     */
    private String z;
}
