/***
 * @pName Admin
 * @name IUserService
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.User;
import com.management.admin.entity.resp.UserInfo;

import java.util.List;

/**
 * 用户业务接口 DF 2018年11月29日00:47:09
 */
public interface IUserService {
    /**
     * 添加新用户 DF 2018年11月29日00:47:48
     * @param phone 1500000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @param smsCode 限制6位字母+数字 必填
     * @param ip
     * @return 事务提交失败会抛出MsgException异常, 成功返回用户信息
     */
    UserInfo addUser(String phone, String password, String smsCode, String ip);

    /**
     * 编辑用户档案 DF 2018年11月29日01:48:55
     * @param uid       用户id
     * @param photo     用户头像 默认constant/defaultProfilePhoto.png 选填
     * @param nickName  用户昵称(同编码) 选填
     * @param signature 个性签名 选填
     */
    void editProfile(Integer uid, String photo, String nickName, String signature);

    /**
     * 登录验证 DF 2018年11月29日02:30:18
     * @param phone 1500000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @return
     */
    User login(String phone, String password);

    /**
     * 员工账号登录验证 DF 2018年12月16日15:52:42
     * @param username 1000000000 必填
     * @param password 限制6-32位字母+数字 MD5(MD5(salt + password)) 必填
     * @return
     */
    UserInfo staffLogin(String username, String password);

    /**
     * 重置密码 DF 2018年12月7日02:09:17
     * @param phone
     * @param password
     * @param smsCode
     * @return
     */
    Boolean resetPassword(String phone, String password, String smsCode);

    /**
     * 更新个人名片 DF 2018年12月11日04:26:20
     * @param phone 手机号 必填
     * @param nickname 昵称 选填
     * @param signature 个性签名 选填
     * @return
     */
    Boolean updateInfo(String phone, String nickname, String signature);

    /**
     * 更新个人头像 DF 2018年12月11日06:51:56
     * @param phone
     * @param upload
     * @return
     */
    Boolean updatePhoto(String phone, String upload);

    /**
     * 查询用户资料 DF 2018年12月11日15:43:48
     * @param accid
     * @return
     */
    List<UserInfo> getUserInfo(String accid);

    /**
     * 员工登录验证--用户名 DF 2018年12月16日16:02:52
     * @param username
     * @return
     */
    User staffLoginByUname(String username);

    /**
     * 注销登录 DF 2018年12月16日17:58:26
     * @param token
     */
    void logout(String token);



    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    List<User> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime);


    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     * @return
     */
    Integer getCount();


    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getLimitCount(String condition, Integer state, String beginTime, String endTime);

    /**
     * 获取用户信息--用户id DF 2018年12月16日19:11:09
     * @param userId
     * @return
     */
    User getUserInfoById(Integer userId);
}
