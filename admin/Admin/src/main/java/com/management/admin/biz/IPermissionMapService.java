/***
 * @pName Admin
 * @name IPermissionMapService
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.PermissionMap;

import java.util.List;

public interface IPermissionMapService {
    /**
     * 获取权限映射列表--分组id DF 2018年12月16日16:35:23
     * @param groupId
     * @return
     */
    List<PermissionMap> getListByGroupId(Integer groupId);

    /**
     * 获取所有权限映射列表 DF 2018年12月16日16:40:04
     * @return
     */
    List<PermissionMap> getAllList();
}
