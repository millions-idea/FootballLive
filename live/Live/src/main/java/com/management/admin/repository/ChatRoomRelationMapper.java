/***
 * @pName Live
 * @name ChatRoomRelationMapper
 * @user HongWei
 * @date 2019/1/24
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.ChatRoomUserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ChatRoomRelationMapper extends MyMapper<ChatRoomUserRelation> {
    /**
     * 是否在黑名单中 DF 2019年1月24日15:24:09
     * @param userId
     * @param liveId
     * @return
     */
    @Select("SELECT * FROM tb_chat_room_user_relations WHERE user_id=#{userId} AND live_id=#{liveId} AND is_black_list=1")
    ChatRoomUserRelation isBlack(@Param("userId") Integer userId, @Param("liveId") Integer liveId);
}
