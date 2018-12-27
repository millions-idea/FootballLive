/***
 * @pName Admin
 * @name GameDetail
 * @user HongWei
 * @date 2018/12/28
 * @desc
 */
package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDetail {

    private Integer gameId;

    private String gameName;

    private String gameIcon;

    private Integer categoryId;

    private Integer isDelete;

    private Date gameDate;
}
