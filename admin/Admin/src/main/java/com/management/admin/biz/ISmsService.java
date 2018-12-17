/***
 * @pName Admin
 * @name IUserService
 * @user HongWei
 * @date 2018/11/27
 * @desc
 */
package com.management.admin.biz;

public interface ISmsService {
    /**
     * 发送手机用户注册验证码 DF 2018年11月27日00:47:18
     * @param phone 手机号码 15000000000, 15000000001... 1000+
     * @return
     */
    boolean sendRegCode(String phone);

    /**
     * 发送找回密码短信验证码 DF 2018年12月7日02:06:02
     * @param phone 手机号码 15000000000 必填
     * @return
     */
    boolean sendResetPwdSmsCode(String phone);
}
