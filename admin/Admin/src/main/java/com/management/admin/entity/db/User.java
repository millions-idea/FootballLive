/***
 * @pName Admin
 * @name User
 * @user HongWei
 * @date 2018/11/29
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户表
 */
@Table(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private Integer userId;
    /**
     * 手机号(用户名)
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String photo;
    /**
     * 昵称(默认=用户编码)
     */
    private String nickName;
    /**
     * 签名
     */
    private String signature;
    /**
     * 注册时间
     */
    private Date addDate;
    /**
     * 最后一次编辑时间
     */
    private Date editDate;
    /**
     * 网易云通信ID(只允许字母、数字、半角下划线_、@、半角点以及半角-组成，不区分大小写，会统一小写处理)
     */
    private String cloudAccid;
    /**
     * 网易云通信ID可以指定登录token值，最大长度128字符， 并更新，如果未指定，会自动生成token，并在创建成功后返回
     */
    private String cloudToken;
    /**
     * 注册ip
     */
    private String ip;
    /**
     * 用户类型（0用户1管理员）
     */
    private Integer type;
}
