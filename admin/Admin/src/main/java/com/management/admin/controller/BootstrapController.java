/***
 * @pName management
 * @name BootstrapController
 * @user HongWei
 * @date 2018/8/26
 * @desc
 */
package com.management.admin.controller;

import com.google.common.collect.ImmutableMap;
import com.management.admin.annotaion.BindAdminDomain;
import com.management.admin.annotaion.BindDomain;
import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.IUserService;
import com.management.admin.entity.resp.UserInfo;
import com.management.admin.entity.template.Constant;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.utils.MD5Util;
import com.management.admin.utils.TokenUtil;
import com.management.admin.utils.web.CookieUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/")
public class BootstrapController {
    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @BindDomain
    @BindAdminDomain
    @GetMapping(value = {"","management/bootstrap/signin"})
    public String signup(HttpServletRequest request){
        return "bootstrap/index";
    }

    /**
     * 管理员登录验证 DF 2018年12月16日15:49:56
     * @param req
     * @param username 工号
     * @param password  密码
     * @return
     */
    @PostMapping("management/bootstrap/login")
    @ResponseBody
    public JsonResult login(HttpServletRequest req, HttpServletResponse response, String username, String password){
        UserInfo userInfo = userService.staffLogin(username, password);

        if(userInfo == null) return new JsonResult().failingAsString("账号或密码错误");

        // 生成平台令牌
        Map<String, String> fields
                = ImmutableMap.of("phone", username, "userId", userInfo.getUserId() + "");
        String token = TokenUtil.create(fields);
        userInfo.setToken(token);

        // 本地缓存
        CookieUtil.setCookie(req, response, "token", token);

        // 续期
        String key  = "token:" + MD5Util.encrypt16(userInfo.getPhone() + userInfo.getUserId());
        redisTemplate.opsForValue().set(key, token, 30, TimeUnit.DAYS);

        return new JsonResult().successful(userInfo);
    }


    /**
     * 注销登录 DF 2018年12月16日18:02:39
     * @return
     */
    @GetMapping("management/bootstrap//logout")
    @ResponseBody
    @WebLog(section = "User", content = "管理员注销登陆")
    public JsonResult logout(HttpServletRequest req, HttpServletResponse res){
        userService.logout(SessionUtil.getToken(req));
        CookieUtil.deleteCookie(req,res,"token");
        return JsonResult.successful();
    }
}
