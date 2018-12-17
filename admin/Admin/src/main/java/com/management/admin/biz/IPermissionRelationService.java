/***
 * @pName Admin
 * @name IPermissionRelationService
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.dbExt.PermissionRelationDetail;

import java.util.List;

public interface IPermissionRelationService {

    /**
     * 获取权限列表--用户id DF 2018年12月16日16:27:58
     * @param userId
     * @return
     */
    List<PermissionRelationDetail> getListByUserId(Integer userId);
}
