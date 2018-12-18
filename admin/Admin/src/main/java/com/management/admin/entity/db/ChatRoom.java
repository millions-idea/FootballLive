package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 聊天信息实体类
 */

@Table(name =  "tb_chat_room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoom {

    /**
     * 聊天室主键，编号
     */
    private Integer roomId;

    /**
     * 直播间编号
     */
    private Integer liveId;
    /**
     * 聊天室id
     */
    private Integer chatRoomId;
    /**
     * 发言频率
     */
    private Double frequency;
}
