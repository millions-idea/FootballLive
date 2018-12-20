package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

/**
 * 用户反馈实体类
 */
@Table(name =  "tb_user_feedback")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserFeedback {

    /**
     * 反馈编号
     */
    private Integer feedbackId;
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户昵称
     */
    private String userNick;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 反馈时间
     */
    private Date addDate;

    /**
     * 反馈内容
     */
    private String content;
}
