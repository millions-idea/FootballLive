/***
 * @pName Admin
 * @name LiveInfo
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.entity.resp;

import com.management.admin.entity.db.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveInfo {
    /**
     * 直播id
     */
    private Integer liveId;
    /**
     * 直播时间
     */
    private String liveDate;
    /**
     * 直播标题
     */
    private String liveTitle;
    /**
     * 播放链接
     */
    private String sourceUrl;


    /**
     * 球队id(支持多个)
     */
    private String teamId;
    /**
     * 赛事名称（如中超赛事）
     */
    private String gameName;
    /**
     * 开始比赛时间
     */
    private String gameDate;
    /**
     * 比赛时长
     */
    private Double gameDuration;
    /**
     * 比赛状态(0=未开始, 1=正在直播, 2=已结束)
     */
    private Integer status;


    /**
     * 类型(0=图片广告,1=播放器广告)
     */
    private Integer type;
    /**
     * 播放器广告url
     */
    private String playerAdUrl;
    /**
     * 播放器广告目标url
     */
    private String playerAdTargetUrl;


    /**
     * 全站广告url
     */
    private String adUrl;
    /**
     * 全站广告目标url
     */
    private String adTargetUrl;

    /**
     * 比赛结果(让胜\让平\让负...)
     */
    private String scheduleResult;
    /**
     * 比赛成绩(-1.5/2或2-1这种格式)
     */
    private String scheduleGrade;
    /**
     * 胜利方球队id
     */
    private Integer winTeamId;
    /**
     * 胜利方球队logo
     */
    private String winTeamIcon;
    /**
     * 胜利方球队名称
     */
    private String winTeamName;


    /**
     * 参赛球队信息
     */
    private List<Team> teamList;

    /**
     * 房间id
     */
    private Integer roomId;
    /**
     * 聊天室id
     */
    private String chatRoomId;
    /**
     * 发言频率
     */
    private Double frequency;


    private String  chatRoomErrorMsg;
}
