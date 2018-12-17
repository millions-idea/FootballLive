/***
 * @pName Admin
 * @name UserApiController
 * @user HongWei
 * @date 2018/11/27
 * @desc
 */
package com.management.admin.apiController;

import com.google.common.collect.ImmutableMap;
import com.management.admin.biz.ISmsService;
import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.User;
import com.management.admin.entity.resp.NASignIn;
import com.management.admin.entity.resp.UserInfo;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.entity.template.SessionModel;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.PropertyUtil;
import com.management.admin.utils.RequestUtil;
import com.management.admin.utils.TokenUtil;
import com.management.admin.utils.http.CosUtil;
import com.management.admin.utils.http.NeteaseImUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserApiController {
    @Autowired
    private ISmsService smsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 发送验证码 DF 2018年11月27日00:45:07
     * @param phone 手机号码 15000000000 必填
     * @return 6位纯数字
     */
    @GetMapping("sendSmsCode")
    public JsonResult sendSmsCode(String phone){
        boolean result = smsService.sendRegCode(phone);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }


    /**
     * 发送找回密码短信验证码 DF 2018年12月7日02:05:25
     * @param phone 手机号码 15000000000 必填
     * @return 6位纯数字
     */
    @GetMapping("sendResetPwdSmsCode")
    public JsonResult sendResetPwdSmsCode(String phone){
        boolean result = smsService.sendResetPwdSmsCode(phone);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 注册账号 DF 2018年11月29日00:40:58
     * @param phone 限制11位数字 必填
     * @param password 限制6-32位字母+数字 必填
     * @param smsCode 限制6位字母+数字 必填
     * @return
     */
    @PostMapping("signUp")
    public JsonResult signUp(HttpServletRequest req, HttpServletResponse resp, String phone, String password, String smsCode){
        // 1.参数验证
        if(phone.isEmpty() || phone.length() < 11 || phone.length() > 11) return new JsonResult().failingAsString("手机号码格式有误");
        if(password.isEmpty() || password.length() < 6 || phone.length() > 32) return new JsonResult().failingAsString("密码格式有误");
        if(smsCode.isEmpty() || smsCode.length() != 6) return new JsonResult().failingAsString("短信验证码格式有误");

        // 2.注册数据库、推送网易平台
        String ip = RequestUtil.getIp(req);
        UserInfo userInfo = userService.addUser(phone, password, smsCode, ip);
        Map<String, String> fields
                = ImmutableMap.of("phone", phone, "userId", userInfo.getUserId() + "", "userCode", userInfo.getUserCode());
        String token = TokenUtil.create(fields);
        userInfo.setToken(token);

        // 3.返回结果
        if(userInfo != null) return new JsonResult().successful(userInfo);
        return JsonResult.failing();
    }

    /**
     * 初始化用户信息 DF 2018年11月29日01:43:39
     * @param photo 用户头像 默认constant/defaultProfilePhoto.png 选填
     * @param nickName 用户昵称(同编码) 选填
     * @param signature 个性签名 选填
     * @return
     */
    @PostMapping("initSettings")
    public JsonResult initSettings(HttpServletRequest req, String photo, String nickName, String signature){
        if(photo.isEmpty()) photo = "images/head-default.png";
        SessionModel session = SessionUtil.getSession(req);
        userService.editProfile(session.getUserId(),photo, nickName, signature);
        return JsonResult.successful();
    }

    /**
     * 登录账号 DF 2018年11月29日02:21:44
     * @param phone 手机号码 15000000000 必填
     * @param password 限制6-32位字母+数字 必填
     * @return
     */
    @PostMapping("signIn")
    public JsonResult signIn(HttpServletRequest req, HttpServletResponse resp, String phone, String password){
        User user = userService.login(phone, password);
        if(user == null) return new JsonResult().failingAsString("账号或密码错误");
        UserInfo userInfo = new UserInfo();
        PropertyUtil.clone(user, userInfo);

        // 生成平台令牌
        Map<String, String> fields
                = ImmutableMap.of("phone", phone, "userId", user.getUserId() + "");
        String token = TokenUtil.create(fields);
        userInfo.setToken(token);
        return new JsonResult().successful(userInfo);
    }

    /**
     * 重置密码 DF 2018年12月7日02:08:32
     * @param phone
     * @param smsCode
     * @param password
     * @return
     */
    @PostMapping("resetPassword")
    public JsonResult resetPassword(String phone, String smsCode, String password){
        // 1.参数验证
        if(phone.isEmpty() || phone.length() < 11 || phone.length() > 11) return new JsonResult().failingAsString("手机号码格式有误");
        if(password.isEmpty() || password.length() < 6 || phone.length() > 32) return new JsonResult().failingAsString("密码格式有误");
        if(smsCode.isEmpty() || smsCode.length() != 6) return new JsonResult().failingAsString("短信验证码格式有误");

        // 2.更新数据库
        Boolean result = userService.resetPassword(phone, password, smsCode);

        // 3.返回结果
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 上传头像 DF 2018年12月10日22:13:35
     * @param imageData
     * @return
     */
    @PostMapping("uploadAvatar")
    public JsonResult uploadAvatar(HttpServletRequest req, String imageData) throws Exception {
        SessionModel session = SessionUtil.getSession(req);
        String upload = CosUtil.upload(imageData);
        userService.updatePhoto(session.getPhone(), upload);
        return new JsonResult().successful(upload);
    }

    /**
     * 更新个人名片 DF 2018年12月11日04:25:40
     * @param req
     * @param nickname 昵称 选填
     * @param signature 个性签名 选填
     * @return
     */
    @PostMapping("updateInfo")
    public JsonResult updateInfo(HttpServletRequest req,
                                     @RequestParam(value = "nickname", required = false) String nickname,
                                     @RequestParam(value = "signature", required = false) String signature){
        SessionModel session = SessionUtil.getSession(req);
        boolean result = userService.updateInfo(session.getPhone(), nickname, signature);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 查询用户资料 DF 2018年12月11日15:43:00
     * @param accid
     * @return
     */
    @GetMapping("getUserInfo")
    public JsonArrayResult<UserInfo> getUserInfo(String accid){
        List<UserInfo> userInfos = userService.getUserInfo(accid);
        return new JsonArrayResult<UserInfo>(userInfos);
    }
}
