/***
 * @pName Admin
 * @name PermissionRelationServiceImpl
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IPermissionRelationService;
import com.management.admin.entity.dbExt.PermissionRelationDetail;
import com.management.admin.repository.PermissionRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionRelationServiceImpl implements IPermissionRelationService {
    private final PermissionRelationMapper permissionRelationMapper;

    @Autowired
    public PermissionRelationServiceImpl(PermissionRelationMapper permissionRelationMapper) {
        this.permissionRelationMapper = permissionRelationMapper;
    }

    /**
     * 获取权限列表--用户id DF 2018年12月16日16:27:58
     *
     * @param userId
     * @return
     */
    @Override
    public List<PermissionRelationDetail> getListByUserId(Integer userId) {
        return permissionRelationMapper.selectDetails(userId);
    }
}
