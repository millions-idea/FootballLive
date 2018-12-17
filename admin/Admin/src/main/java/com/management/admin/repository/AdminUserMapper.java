package com.management.admin.repository;

import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.User;
import com.management.admin.entity.resp.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName AdminUserMapper
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/17 14:38
 * Version 1.0
 **/
@Mapper
public interface AdminUserMapper extends MyMapper<AdminUser> {
    @Select("SELECT t1.* FROM tb_admin_users t1 " +
            "WHERE ${condition} GROUP BY t1.user_id ORDER BY t1.add_date DESC LIMIT #{page},${limit}")
    /**
     * 分页查询 韦德 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    List<AdminUser> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.user_id) FROM tb_admin_users t1\n" +
            "WHERE ${condition}")
    /**
     * 分页查询记录数 韦德 2018年8月30日11:33:30
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    Integer selectLimitCount(@Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);


    /**
     * 查询员工信息 DF 2018年12月16日17:23:54
     * @param username
     * @param newPassword
     * @return
     */
    @Select("SELECT t1.*,t3.permission_group_code AS role FROM tb_admin_users t1 "
            + "LEFT JOIN tb_permission_relations t2 ON t1.user_id = t2.user_id "
            + "LEFT JOIN tb_permission_groups t3 ON t2.permission_group_id = t3.group_id "
            + "WHERE t1.phone=#{username} AND t1.password=#{password} ")
    UserInfo selectStaff(@Param("username") String username,@Param("password") String newPassword);


    /**
     * 查询信息--用户名 DF 2018年12月16日16:06:24
     * @param username
     * @return
     */
    @Select("SELECT * FROM tb_admin_users WHERE phone=#{username}")
    AdminUser selectByUsername(@Param("username") String username);
}
