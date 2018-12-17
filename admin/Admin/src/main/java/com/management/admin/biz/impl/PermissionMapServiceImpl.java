/***
 * @pName Admin
 * @name PermissionMapServiceImpl
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IPermissionMapService;
import com.management.admin.entity.db.PermissionMap;
import com.management.admin.repository.PermissionMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionMapServiceImpl implements IPermissionMapService {
    private final PermissionMapMapper permissionMapMapper;

    @Autowired
    public PermissionMapServiceImpl(PermissionMapMapper permissionMapMapper) {
        this.permissionMapMapper = permissionMapMapper;
    }

    /**
     * 获取权限映射列表--分组id DF 2018年12月16日16:35:23
     *
     * @param groupId
     * @return
     */
    @Override
    public List<PermissionMap> getListByGroupId(Integer groupId) {
        return permissionMapMapper.selectMaps(groupId);
    }

    /**
     * 获取所有权限映射列表 DF 2018年12月16日16:40:04
     *
     * @return
     */
    @Override
    public List<PermissionMap> getAllList() {
        return permissionMapMapper.selectAll();
    }
}
