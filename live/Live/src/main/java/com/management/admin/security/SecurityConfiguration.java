/***
 * @pName mi-ocr-web-app
 * @name SpringSecurityConfiguration
 * @user HongWei
 * @date 2018/7/21
 * @desc security安全信息框架配置类
 */
package com.management.admin.security;


import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.management.admin.entity.db.User;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.StringUtil;
import com.management.admin.utils.TokenUtil;
import com.management.admin.utils.web.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityDetailsService securityDetailsService;

    @Bean
    public Md5PasswordEncoder md5PasswordEncoder(){
        return new SecurityPasswordEncoder();
    }

    @Bean
    public ReflectionSaltSource reflectionSaltSource(){
        ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
        reflectionSaltSource.setUserPropertyToUse("salt");
        return reflectionSaltSource;
    }

    /**
     * 密码加密
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new LoginAuthenticationProvider();
        provider.setSaltSource(reflectionSaltSource());
        provider.setUserDetailsService(securityDetailsService);
        provider.setPasswordEncoder(md5PasswordEncoder());
        return provider;
    }


    /**
     * 保护机制
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/**", "/api/**")
                .permitAll().and();

        http.authorizeRequests()
                .antMatchers("/user/**")
                .hasAnyRole("ADMIN").and();

        http
                .formLogin()
                .usernameParameter("username").passwordParameter("password").permitAll()
                .loginPage("/")  // 登录入口
                .loginProcessingUrl("/api/user/login")    // 登录验证接口
                .permitAll()
                .successForwardUrl("/index")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

                        User user = ((SecurityUserDetails) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal()).getDetail();

                        // 生成平台令牌
                        Map<String, String> fields
                                = ImmutableMap.of("phone", user.getPhone(), "userId", user.getUserId() + "");
                        String token = TokenUtil.create(fields);

                        // 本地缓存
                        CookieUtil.setCookie(httpServletRequest, httpServletResponse, "token", token);

                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        String msg = "SUCCESS";
                        String role = authentication.getAuthorities().stream().findFirst().get().toString();
                        if(role != null && role.length() > 0) {
                            role = role.substring(role.indexOf("_") + 1,  role.length());
                        }
                        out.write("{\"error\":0,\"msg\":\"SUCCESS\",\"role\":\"" + role  + "\"}");
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, "token");
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error\":1,\"msg\":\"ERROR\"}");
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        CookieUtil.deleteCookie(httpServletRequest, httpServletResponse, "token");
                        httpServletResponse.sendRedirect("/");
                    }
                })
                .permitAll();;  // 设置无保护机制的路由或页面

        System.out.println("加载安全配置完成");
    }

    /**
     * 排除静态资源
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/frame/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/images/**")
                .antMatchers("/layui/**")
                .antMatchers("/fonts/**")
                .antMatchers("/noamd-js/**");
    }

}
