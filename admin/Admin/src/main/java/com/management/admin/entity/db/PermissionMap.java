/***
 * @pName Admin
 * @name PermissionMap
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

@Table(name = "tb_permission_maps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionMap {
    private Integer mapId;
    private Integer permissionGroupId;
    private String permissionGroupCode;
    private String accessCode;
    private String accessUrl;
}
