/***
 * @pName Admin
 * @name PermissionMapMapper
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.PermissionMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapMapper extends MyMapper<PermissionMap> {
    /**
     * 查询映射列表--分组id DF 2018年12月16日16:37:25
     * @param groupId
     * @return
     */
    @Select("SELECT * FROM tb_permission_maps WHERE permission_group_id=#{groupId}")
    List<PermissionMap> selectMaps(@Param("groupId") Integer groupId);
}
