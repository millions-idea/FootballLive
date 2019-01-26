/***
 * @pName Live
 * @name ChatRoomRelation
 * @user HongWei
 * @date 2019/1/24
 * @desc
 */
package com.management.admin.entity.db;

import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name ="tb_chat_room_user_relations")
@Getter
@Setter
public class ChatRoomUserRelation {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Integer relationId;
    /**
     * 直播间id
     */
    private Integer liveId;
    /**
     * 房间id
     */
    private Integer roomId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 是否拉黑
     */
    private Integer isBlackList;
}
