/***
 * @pName Admin
 * @name ChatRoomMapper
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRoomMapper extends MyMapper<ChatRoom> {
}
