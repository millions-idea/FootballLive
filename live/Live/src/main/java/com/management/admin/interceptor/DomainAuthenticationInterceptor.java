/***
 * @pName proback
 * @name FinanceAuthenticationInterceptor
 * @user DF
 * @date 2018/8/5
 * @desc
 */
package com.management.admin.interceptor;

import com.management.admin.annotaion.BindAdminDomain;
import com.management.admin.annotaion.BindDomain;
import com.management.admin.annotaion.Sign;
import com.management.admin.entity.template.Constant;
import com.management.admin.exception.InfoException;
import com.management.admin.exception.MsgException;
import com.management.admin.utils.Base64Util;
import com.management.admin.utils.RSAUtil;
import com.management.admin.utils.RequestUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;

/***
 * 域名拦截器
 */
public class DomainAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String url =  request.getRequestURL().toString().toLowerCase();
        // 只拦截method级别的处理器
        if (!(handler instanceof HandlerMethod)) return true;
        // 只拦截token注解过的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要验签
        BindDomain bindDomain = method.getAnnotation(BindDomain.class);
        BindAdminDomain bindAdminDomain = method.getAnnotation(BindAdminDomain.class);
        if(bindDomain != null){
            String[] arr = Constant.BindDomain.split(",");
            int count = 0;
            for (String u : arr){
                if(url.contains(u)){
                    count ++;
                }
            }
            if(count <= 0){
                if(!Constant.DebugMode){
                    throw new InfoException("来路错误" );
                }
            }
        }

        if(bindAdminDomain != null){
            if(!url.contains(Constant.BindAdminDomain)) {
                if(!Constant.DebugMode){
                    throw new InfoException("控制来路错误" );
                }
            }
        }
        return true;
    }
}
