package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Integer teamId;

    private Date gameDate;

    private String gameDuration;

    private String status;

}
