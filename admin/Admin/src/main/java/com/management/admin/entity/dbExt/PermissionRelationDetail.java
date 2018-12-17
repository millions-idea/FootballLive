/***
 * @pName Admin
 * @name PermissionRelationDetail
 * @user HongWei
 * @date 2018/12/16
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
public class PermissionRelationDetail {
    /*permission relation*/
    private Integer relationId;
    private Integer permissionGroupId;
    private Integer userId;
    private Integer groupId;

    /*permission group*/
    /*private Integer groupId;*/
    private String permissionGroupName;
    private String permissionGroupCode;
}
