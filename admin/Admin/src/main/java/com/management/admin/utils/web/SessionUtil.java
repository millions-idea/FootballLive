/***
 * @pName Admin
 * @name SessionUtil
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.utils.web;

import com.google.common.collect.ImmutableMap;
import com.management.admin.entity.template.SessionModel;
import com.management.admin.exception.MsgException;
import com.management.admin.utils.TokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Web会话辅助工具 DF 2018年11月29日02:06:02
 */
public class SessionUtil {
    /**
     * 获取当前会话--用户信息 DF 2018年11月29日02:06:23
     * @return
     */
    public static SessionModel getSession(HttpServletRequest request){
        // 取出token信息
        String token = request.getParameter("token");
        if(token.isEmpty()) throw new MsgException("缺少令牌");

        // 按照token安全加密规则进行验证, 如果没有通过则抛出异常
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) throw new MsgException("用户来路异常");

        // 安全验证通过后解析出来用户id并返回
        return new SessionModel(Integer.valueOf(map.get("userId")), map.get("phone"), map.get("userCode"));
    }

    /**
     * 获取当前会话--token DF 2018年11月29日02:06:23
     * @return
     */
    public static String getToken(HttpServletRequest request){
        // 取出token信息
        String token = request.getParameter("token");
        if(token == null) token = CookieUtil.getCookieValue(request, "token");
        return token;
    }

    /**
     * 获取当前会话--用户信息 DF 2018年12月11日01:29:21
     * @return
     */
    public static SessionModel getSession(String token){
        // 取出token信息
        if(token.isEmpty()) throw new MsgException("缺少令牌");

        // 按照token安全加密规则进行验证, 如果没有通过则抛出异常
        Map<String, String> map = TokenUtil.validate(token);
        if(map.isEmpty()) throw new MsgException("用户来路异常");

        // 安全验证通过后解析出来用户id并返回
        return new SessionModel(Integer.valueOf(map.get("userId")), map.get("phone"), map.get("userCode"));
    }
}

