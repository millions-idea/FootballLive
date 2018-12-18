/***
 * @pName Admin
 * @name LiveGameDetail
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveScheduleDetail {
    /*lives*/
    private Integer liveId;
    private String liveTitle;
    private Date liveDate;
    private String sourceUrl;

    /*schedules*/
    private String teamId;
    private Integer status;

    /*games*/
    private Integer gameId;
    private String gameName;

    private String teamName;
    private String teamIcon;
}
