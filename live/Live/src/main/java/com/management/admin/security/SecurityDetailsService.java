/***
 * @pName mi-ocr-web-app
 * @name UserDetailsServiceEx
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package com.management.admin.security;


import com.google.common.base.Joiner;
import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.User;
import com.management.admin.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 通过用户名查询此用户是否为合法用户
        User detail = userService.loginByUname(username);

        return new SecurityUserDetails(detail, detail.getPhone(), detail.getPhone(), detail.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }


}
