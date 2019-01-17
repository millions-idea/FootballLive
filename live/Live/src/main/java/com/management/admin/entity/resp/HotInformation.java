/***
 * @pName Live
 * @name HotInformation
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotInformation {
    private Integer isrId;
    private Integer gameId;
    private Integer liveId;
    private String content;
    private String forecastResult;
    private String forecastGrade;
    private Integer forecastTeamId;

    private Integer masterTeamId;
    private String masterTeamIcon;
    private String masterTeamName;

    private Integer targetTeamId;
    private String targetTeamIcon;
    private String targetTeamName;

    private String gameName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gameDate;
    private String shortGameDate;
    private String gameDateString;
}
