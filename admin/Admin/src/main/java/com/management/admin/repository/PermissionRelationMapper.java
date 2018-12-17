/***
 * @pName Admin
 * @name PermissionRelationMapper
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.PermissionRelation;
import com.management.admin.entity.dbExt.PermissionRelationDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionRelationMapper extends MyMapper<PermissionRelation> {
    /**
     * 查询详情列表 DF 2018年12月16日16:29:53
     * @param userId
     * @return
     */
    @Select("SELECT * FROM `tb_permission_relations` t1 " +
            "LEFT JOIN tb_permission_groups t2 ON t1.permission_group_id = t2.group_id WHERE t1.user_id=#{userId}")
    List<PermissionRelationDetail> selectDetails(@Param("userId") Integer userId);
}
