/***
 * @pName Admin
 * @name TeamDetail
 * @user HongWei
 * @date 2018/12/30
 * @desc
 */
package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDetail {
    private Integer teamId;
    private String teamName;
    private String teamIcon;
    private Integer gameId;
    private Integer isDelete;
    private String gameName;
    private String gameIcon;
    private Integer categoryId;
}
