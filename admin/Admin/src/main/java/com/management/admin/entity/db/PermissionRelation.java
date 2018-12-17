/***
 * @pName Admin
 * @name PermissionRelation
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

@Table(name = "tb_permission_relations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRelation {
    private Integer relationId;
    private Integer permissionGroupId;
    private Integer userId;
    private Integer groupId;
}
