/***
 * @pName Live
 * @name ChatRoomMapper
 * @user HongWei
 * @date 2019/1/24
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.ChatRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatRoomMapper extends MyMapper<ChatRoom> {
    @Select("SELECT * FROM tb_chat_rooms WHERE live_id=#{liveId}")
    List<ChatRoom> selectByLive(@Param("liveId") Integer liveId);
}
