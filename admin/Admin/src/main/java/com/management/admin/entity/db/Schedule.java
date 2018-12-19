package com.management.admin.entity.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {

    private Integer scheduleId;

    private Integer gameId;

    private String teamId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gameDate;

    private String gameDuration;

    private String status;

    private String scheduleResult;

    private String scheduleGrade;

    private Integer  winTeamId;

    private Integer isDelete;

}
