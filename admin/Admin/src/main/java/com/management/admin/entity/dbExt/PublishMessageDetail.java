package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/***
 * 推送消息包装类
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublishMessageDetail {
    /**
     * 推送消息编号
     */
    private Integer msgId;

    /**
     * 推送消息信息
     */
    private String message;

    /**
     * 添加时间
     */
    private Date addDate;

    /**
     * 推送消息类型
     */
    private boolean type;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 推送消息关系id
     */
    private Integer relationId;
}
