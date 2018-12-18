package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 聊天室用户关系表
 */

@Table(name =  "tb_chat_room_user_relation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomUserRelation {
    /**
     * 聊天室用户关系编号
     */
    private Integer relationId;
    /**
     * 直播间编号
     */
    private Integer liveId;

    /**
     * 聊天室编号
     */
    private Integer roomId;
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 是否拉黑
     */
    private boolean isBlackList;
}
