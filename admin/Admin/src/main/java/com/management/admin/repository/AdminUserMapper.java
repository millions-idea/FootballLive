package com.management.admin.repository;

import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.PermissionRelation;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.RelationAdminUsers;
import com.management.admin.entity.resp.UserInfo;
import org.apache.ibatis.annotations.*;

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

    @Update("update tb_admin_users set status=2 where user_id=#{userId}")
    /**
     * 删除管理员  Timor 2018年12月17日23:29:22
     * @param  userId
     * @return
     */
    int deleteAdmin(@Param("userId") Integer userId);

    @Update("update tb_admin_users set password=#{newpwd} where phone=#{phone} and password=#{password}")
    /**
     * 修改管理员密码(自己)  Timor 2018年12月17日16:29:22
     * @param  phone
     * @param  password
     * @return
     */
    int updateAdminpassword(@Param("phone") String phone,@Param("password") String password,@Param("newpwd") String newpwd);

    @Update("update tb_admin_users set ${condition} where phone=#{phone}" )
    /**
     * 超级管理员修改密码 Timor 2018年12月17日18:29:22
     * @param  phone
     * @param condition
     * @param password
     * @param status
     * @return
     */
    int updateSupAdmin(@Param("phone") String phone, @Param("condition") String condition, @Param("password") String password, @Param("status") Integer status);


    @Insert("insert into tb_admin_users (phone,password,type,add_date)" +
            "values ( #{adminUsers.phone}, #{adminUsers.password},#{adminUsers.type},#{adminUsers.addDate})")
    /**
     *添加管理员  提莫 2018-12-17 10:22:30
     * @param  adminUsers
     * @return 成功返回true
     */
    @Options(useGeneratedKeys = true, keyProperty = "adminUsers.userId")
    int insertAdmin(@Param("adminUsers") RelationAdminUsers adminUsers);

    @Insert("insert into tb_permission_relations(permission_group_id,user_id)" +
            "values (#{adminUsers.permissionGroupId},#{adminUsers.userId})")
    /**
     *添加管理员权限类型  提莫 2018-12-17 10:22:30
     * @param  users
     * @return 成功返回true
     */
    int insertAdminRelation(@Param("adminUsers") RelationAdminUsers adminUsers);

    @Select("select phone from tb_admin_users where phone=#{phone}")
    /**
     *添加管理员时，验证手机号是否已经存在  提莫 2018-12-17 10:22:30
     * @param  account
     * @return  返回账号
     */
    String checkPhone(String phone);


    @Select("SELECT t1.* FROM tb_admin_users t1 " +
            "WHERE t1.status<2 and  ${condition} GROUP BY t1.user_id ORDER BY t1.add_date DESC LIMIT #{page},${limit}")
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


    @Select("SELECT COUNT(t1.user_id) FROM tb_admin_users t1 " +
            "WHERE t1.status<2 and  ${condition} GROUP BY t1.user_id ORDER BY t1.add_date DESC")
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


    @Select("SELECT type FROM tb_admin_users WHERE user_id=#{userId}")
    /**
     * 查询管理员的type 2018年12月18日00:33:30
     * @param userId
     * @return
     */
    Integer selectType(@Param("userId") Integer userId);


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
    @Select("SELECT * FROM tb_admin_users WHERE phone=#{username} AND status = 0")
    AdminUser selectByUsername(@Param("username") String username);
}
