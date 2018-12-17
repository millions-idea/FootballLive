/***
 * @pName Admin
 * @name LiveHotDetail
 * @user HongWei
 * @date 2018/12/17
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
public class LiveHotDetail {
    /*lives*/
    private Integer liveId;
    private String liveTitle;
    private Date liveDate;

    /*schedules*/
    private String teamId;

    /*games*/
    private Integer gameId;
    private String gameName;

    private String teamName;
    private String teamIcon;
}
