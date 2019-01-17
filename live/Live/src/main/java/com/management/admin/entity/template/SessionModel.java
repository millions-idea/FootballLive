/***
 * @pName Admin
 * @name SessionModel
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.entity.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Web会话模板 DF 2018年11月29日02:14:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionModel {
    private Integer userId;
    private String phone;
    private String userCode;
}
