/***
 * @pName Live
 * @name ChatRoom
 * @user HongWei
 * @date 2019/1/24
 * @desc
 */
package com.management.admin.entity.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Table(name = "tb_chat_rooms")
@Getter
@Setter
public class ChatRoom {
    private Integer roomId;
    private Integer liveId;
    private String chatRoomId;
    private Double frequency;
}
