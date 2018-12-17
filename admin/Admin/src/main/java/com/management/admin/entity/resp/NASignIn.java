/***
 * @pName management
 * @name NeteasaSignInResp
 * @user DF
 * @date 2018/9/5
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 云信注册JSON DF 2018年11月29日01:09:46
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NASignIn {
    private Integer code;
    private Info info;

    @Getter
    @Setter
    public class Info{
        private String token;
        private String accId;
        private String name;
    }
}
