package com.management.admin.entity.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    private Integer scheduleId;

    /**
     * 赛事id
     */
    private Integer gameId;

    /**
     * 球队id(支持多个)
     */
    private String  teamId;

    /**
     * 开始比赛时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date gameDate;

    /**
     * 比赛时长
     */
    private String gameDuration;

    /**
     * 比赛状态(0=未开始, 1=正在直播, 2=已结束)
     */
    private Integer status;

    /**
     * 删除(0=正常 1=删除)
     */
    private Integer isDelete;

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

}
