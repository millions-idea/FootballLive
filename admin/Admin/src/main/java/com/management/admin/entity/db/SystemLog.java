package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;
@Table(name = "tb_system_logs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SystemLog {

    private Integer logId;

    private Integer userId;

    private String section;

    private String content;

    private Date addDate;
}
