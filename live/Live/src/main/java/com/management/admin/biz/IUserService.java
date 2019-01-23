/***
 * @pName Admin
 * @name IUserService
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.User;
import com.management.admin.entity.enums.UserRoleEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户业务接口 DF 2018年11月29日00:47:09
 */
public interface IUserService {

    /**
     * 登录验证 DF 2018年11月29日02:30:18
     * @param phone 1500000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @return
     */
    User login(String phone, String password);

    /**
     * 通过用户名验证 DF 2019年1月23日15:27:23
     * @param username
     * @return
     */
    User loginByUname(String username);

    /**
     * 获取用户档案 DF 2019年1月23日23:23:16
     * @param userId
     * @return
     */
    User getProfile(Integer userId);
}
