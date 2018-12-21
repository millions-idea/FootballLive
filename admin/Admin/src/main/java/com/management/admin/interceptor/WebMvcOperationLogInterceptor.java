/***
 * @pName proback
 * @name FinanceAuthenticationInterceptor
 * @user DF
 * @date 2018/8/5
 * @desc
 */
package com.management.admin.interceptor;

import com.management.admin.annotaion.AspectLog;
import com.management.admin.annotaion.Sign;
import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.ISystemLogService;
import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.SystemLog;
import com.management.admin.entity.template.Constant;
import com.management.admin.entity.template.SessionModel;
import com.management.admin.exception.MsgException;
import com.management.admin.utils.Base64Util;
import com.management.admin.utils.RSAUtil;
import com.management.admin.utils.RequestUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/***
 * Web操作日志记录
 */
public class WebMvcOperationLogInterceptor implements HandlerInterceptor {
    /**
     * 控制验签系统开闭
     */
    private final Boolean isOpen;

    @Autowired
    private ISystemLogService systemLogService;

    public WebMvcOperationLogInterceptor(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isOpen){
            // 只拦截method级别的处理器
            if (!(handler instanceof HandlerMethod)) return true;
            // 只拦截token注解过的方法
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 判断接口注解
            WebLog log = method.getAnnotation(WebLog.class);
            if (log != null){
                // 获取
                SessionModel session = SessionUtil.getSession(request);
                SystemLog systemLog = new SystemLog();
                systemLog.setUserId(session.getUserId());
                systemLog.setSection(log.section());
                systemLog.setContent(log.content());
                // 放入数据库
                Integer result = systemLogService.insertSystemLog(systemLog);
                if(result>0){
                    return true;
                }else {
                    return  false;
                }

            }
        }
        return true;
    }



    /**
     * 快速加密
     * @param body
     * @return
     * @throws Exception
     */
    private String getEncrypt(String body) throws Exception {
        // formUid=1&toUid=2&amount=1.9&remark=充值&token=
        byte[] bytes = RSAUtil.encryptByPublicKey(body.getBytes(), Constant.INFO_PUB_KEY);
        return  Base64Util.encode(bytes);
    }

    /**
     * 快速解密
     * @param body
     * @return
     * @throws Exception
     */
    private String getDecrypt(String body) throws Exception {
        byte[] decode = Base64Util.decode(body);
        return new String(RSAUtil.decryptByPrivateKey(decode, Constant.INFO_PRI_KEY), "UTF-8");
    }
}
