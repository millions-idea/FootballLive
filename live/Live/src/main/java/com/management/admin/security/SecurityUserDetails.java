/***
 * @pName mi-ocr-web-app
 * @name UserDetailsEx
 * @user HongWei
 * @date 2018/7/28
 * @desc
 */
package com.management.admin.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUserDetails extends User {
    private String salt;
    private com.management.admin.entity.db.User detail;

    public com.management.admin.entity.db.User getDetail() {
        return detail;
    }

    public void setDetail(com.management.admin.entity.db.User detail) {
        this.detail = detail;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public SecurityUserDetails(com.management.admin.entity.db.User detail, String username, String salt, String password,
                               Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.detail = detail;
        this.salt = salt;
    }
}
