/***
 * @pName Admin
 * @name UserMapper
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.User;
import com.management.admin.entity.resp.UserInfo;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends MyMapper<User> {
    

    /**
     * 更新用户基本档案信息 DF 2018年11月29日01:58:53
     * @param user
     * @return
     */
    @Update("UPDATE tb_users SET photo=#{photo}, user_code=#{userCode}, nick_name=#{nickName}, signature=#{signature}"
                + " WHERE user_id=#{userId}")
    int updateBasicInfo(User user);

    /**
     * 重置密码 DF 2018年12月7日02:13:48
     * @param phone
     * @param encryptPassword
     * @return
     */
    @Update("UPDATE tb_users SET password=#{password},edit_date=NOW() WHERE phone=#{phone}")
    int resetPassword(@Param("phone") String phone, @Param("password") String encryptPassword);


    /**
     * 改绑有讯号 DF 2018年12月11日04:06:30
     * @param account
     * @param userCode
     * @return
     */
    @Update("UPDATE tb_users SET edit_date=NOW(), user_code=#{userCode},edit_count=edit_count-1 WHERE phone=#{account} AND edit_count > 0")
    int updateUserCode(@Param("account") String account, @Param("userCode") String userCode);

    /**
     * 更新个人名片 DF 2018年12月11日04:29:20
     * @param phone
     * @param buffer
     * @param nickname
     * @param signature
     * @return
     */
    @Update("UPDATE tb_users SET ${buffer} WHERE phone=#{phone}")
    int updateInfo(@Param("phone") String phone, @Param("buffer") String buffer, @Param("nickname") String nickname,  @Param("signature") String signature);

    /**
     * 更新个人头像 DF 2018年12月11日06:52:27
     * @param phone
     * @param upload
     * @return
     */
    @Update("UPDATE tb_users SET edit_date=NOW(), photo=#{photo} WHERE phone = #{phone}")
    int updatePhoto(@Param("phone") String phone, @Param("photo") String upload);

    /**
     * 查询用户资料 DF 2018年12月11日15:45:15
     * @param accid
     * @return
     */
    @Select("SELECT user_id, phone,  user_code, nick_name, signature FROM tb_users WHERE phone IN(${accid}) ")
    List<UserInfo> selectByAccid(@Param("accid") String accid);



    /**
     * 更新最后一次更新时间 DF 2018年12月16日17:36:45
     * @param userId
     * @return
     */
    @Update("UPDATE tb_users SET edit_date=NOW() WHERE user_id=#{userId} ")
    int updateLastLoginTime(@Param("userId") Integer userId);

    @Select("SELECT t1.* FROM tb_users t1 " +
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
    List<User> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.user_id) FROM tb_users t1\n" +
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
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    @Select("select * from tb_users where phone=#{phone}")
    User selectUserByPhone(String phone);
}
