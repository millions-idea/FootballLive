/***
 * @pName Admin
 * @name UserMapper
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends MyMapper<User> {
    @Select("SELECT * FROM tb_users WHERE phone=#{username} AND (is_delete IS NULL OR is_delete = 0)")
    User selectByUsername(@Param("username") String username);
}
