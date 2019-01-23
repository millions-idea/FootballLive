package com.management.admin.repository;

import com.management.admin.entity.db.ChatRoom;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatRoomMapper extends MyMapper<ChatRoom> {


    @Select("select t1.room_id,t1.chat_room_id,t1.frequency,t2.live_title,t2.live_id "+
            "from tb_chat_rooms t1 left join tb_lives t2 on t1.live_id=t2.live_id"+
            " where ${condition} GROUP BY t1.room_id DESC LIMIT #{page},${limit}")
    List<ChatRoomDetail> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.live_id) from tb_chat_rooms t1 left join tb_lives t2 on t1.live_id=t2.live_id where ${condition}")

    Integer selectLimitCount(@Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("select count(*) from tb_chat_rooms")
    Integer selectRoomCount();

    @Select("select t1.room_id,t1.chat_room_id,t1.frequency,t2.live_title,t2.live_id " +
            "  from tb_chat_rooms t1 left join tb_lives t2 on t1.live_id=t2.live_id where room_id=#{roomId}")
    ChatRoomDetail queryChatRoomById(Integer roomId);


    /**
     * 查询聊天室信息--直播间id  DF 2018年12月20日23:42:02
     * @param liveId
     * @return
     */
    @Select("SELECT * FROM tb_chat_rooms WHERE live_id = #{liveId}")
    List<ChatRoom> selectByLive(@Param("liveId") Integer liveId);

    @Insert("INSERT INTO tb_chat_rooms (live_id, chat_room_id, frequency) VALUES(#{liveId}, #{chatRoomId}, 10) ON DUPLICATE KEY UPDATE " +
            "chat_room_id = #{chatRoomId}, live_id=#{liveId}")
    int insertOrUpdate(ChatRoom chatRoom);

    @Delete("DELETE FROM tb_chat_rooms WHERE live_id=#{liveId}")
   int deleteLive(@Param("liveId") Integer liveId);
}
