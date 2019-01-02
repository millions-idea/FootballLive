package com.management.admin.repository;

import com.management.admin.entity.db.ChatRoom;
import com.management.admin.entity.db.ChatRoomUserRelation;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatRoomUserRelationMapper extends MyMapper<ChatRoomUserRelation> {

    // 分页查询
    @Select("select t2.room_id,t2.frequency,t3.user_id,t3.photo,t3.nick_name,t3.signature " +
            " from tb_chat_room_user_relations t1 left join tb_chat_rooms t2 on t1.room_id=t2.room_id " +
            "left join tb_users t3 on t1.user_id=t3.user_id where t2.room_id=#{roomId} and t1.is_black_list =0 and " +
            " ${condition} GROUP BY t3.user_id ORDER BY t3.add_date DESC LIMIT #{page},${limit}")
    /**
     * 分页查询
     * @param page
     * @param limit
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    List<ChatRoomDetail> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition
            , @Param("roomId") Integer roomId);

    @Select("SELECT COUNT(t1.live_id) from tb_chat_room_user_relations t1 left join tb_chat_rooms t2 on t1.room_id=t2.room_id" +
            " left join tb_users t3 on t1.user_id=t3.user_id where t2.room_id=#{roomId} and t1.is_black_list=0 and ${condition}")
    /**
     * 查询记录
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    Integer selectLimitCount(@Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition
            , @Param("roomId") Integer roomId);

    /**
     * 查询人数
     *
     * @return
     */
    @Select("select count(*) from tb_chat_room_user_relations t1 left join tb_chat_rooms t2 on t1.room_id=t2.room_id " +
            " left join tb_users t3 on t1.user_id=t3.user_id where t2.room_id=#{roomId} and t1.is_black_list=0")
    Integer selectChatRoomUserCount(Integer roomId);

    /**
     * 拉黑
     * @param userId
     * @return
     */
    @Update("update tb_chat_room_user_relations set is_black_list=1 where user_id=#{userId}")
    Integer ShieldingUser(Integer userId);

    /**
     * 根据直播间编号查询相应的聊天室
     * @param liveId
     * @return
     */
    @Select("select * from tb_chat_rooms where live_id=#{liveId}")
    List<ChatRoom> queryChatRoomByLiveId(Integer liveId);

    /**
     * 根据直播间编号查询相应的聊天室
     * @param liveId
     * @return
     */
    @Update("update tb_chat_rooms set is_delete=1 where live_id=#{liveId}")
    Integer deleteChatRoomByLiveId(Integer liveId);
    /**
     * 建立关系
     * @param userId
     * @param liveId
     * @return
     */
    @Insert("INSERT INTO tb_chat_room_user_relations(live_id, room_id, user_id) " +
            "VALUES(#{userId}, (SELECT room_id FROM tb_chat_rooms WHERE live_id = #{liveId} LIMIT 1), #{userId}) ON DUPLICATE KEY UPDATE edit_date=NOW()")
    int insertRelation(@Param("userId") Integer userId, @Param("liveId") Integer liveId);

    /**
     * 查询关系 DF 2018年12月21日00:07:42
     * @param userId
     * @param liveId
     * @return
     */
    @Select("SELECT * FROM tb_chat_room_user_relations WHERE user_id=#{userId} AND live_id=#{liveId}")
    ChatRoomUserRelation selectRelation(@Param("userId") Integer userId, @Param("liveId") Integer liveId);

    /**
     * 删除群组成员关系 DF 2018年12月24日21:52:20
     * @param userId
     * @param roomId
     * @param liveId
     * @return
     */
    @Update("DELETE FROM SELECT * FROM tb_chat_room_user_relations  WHERE user_id = #{userId}  AND room_id = (SELECT room_id FROM tb_chat_rooms WHERE chat_room_id = #{roomId} LIMIT 1) AND live_id = #{liveId}")
    int deleteMember(@Param("userId") Integer userId, @Param("roomId") String roomId, @Param("liveId") Integer liveId);

    @Insert("INSERT INTO tb_chat_room_user_relations (live_id, room_id, user_id, is_black_list, edit_date) " +
            "VALUES(#{liveId}, #{roomId}, #{userId}, #{isBlackList}, NOW()) ON DUPLICATE KEY UPDATE room_id = #{roomId},  live_id = #{liveId}, edit_date=NOW()")
    int insertOrUpdate(ChatRoomUserRelation chatRoomUserRelation);

    @Delete("DELETE FROM tb_chat_room_user_relations WHERE live_id=#{liveId}")
    int deleteLive(@Param("liveId") Integer liveId);
}
