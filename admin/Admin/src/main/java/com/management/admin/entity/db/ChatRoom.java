/***
 * @pName Admin
 * @name ChatRoom
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_chat_rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = " SELECT LAST_INSERT_ID()")
    private Integer roomId;

    /**
     * 直播间id
     */
    private Integer liveId;

    /**
     * 聊天室 id
     */
    private String chatRoomId;

    /**
     * 发言频率
     */
    private Double frequency;
}
