package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomDetail {

    /**
     * 聊天室编号
     */
    private Integer roomId;
    /**
     * 直播间编号
     */
    private Integer liveId;
    /**
     * 聊天室id
     */
    private String charRoomId;
    /**
     * 发言频率
     */
    private Double frequency;
    /**
     * 直播间标题
     */
    private String liveTitle;
    /**
     * 用户数量
     */
    private Integer userCount;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 用户头像
     */
    private String photo;

    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 签名
     */
    private String signature;
}
