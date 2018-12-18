package com.management.admin.repository;

import com.management.admin.entity.db.ChatRoom;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatRoomMapper extends MyMapper<ChatRoom> {


    @Select("select t1.room_id,t1.chat_room_id,t1.frequency,t2.live_title,t2.live_id "+
            "from tb_chat_rooms t1 left join tb_lives t2 on t1.live_id=t2.live_id"+
            " where ${condition} GROUP BY t1.room_id DESC LIMIT #{page},${limit}")
    /**
     * ��ҳ��ѯ Τ�� 2018��8��30��11:33:22
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
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.live_id) from tb_chat_rooms t1 left join tb_lives t2 on t1.live_id=t2.live_id where ${condition}")
    /**
     * ��ҳ��ѯ��¼�� Τ�� 2018��8��30��11:33:30
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    Integer selectLimitCount(@Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    /**
     * ��ѯ�ܼ�¼��
     * @return
     */
    @Select("select count(*) from tb_chat_rooms")
    Integer selectRoomCount();

    /**
     * ���������ұ�Ų�ѯ��Ӧ������������ ���� 2018��12��19��02:53:14
     * @param roomId
     * @return
     */
    @Select("select t1.room_id,t1.chat_room_id,t1.frequency,t2.live_title,t2.live_id " +
            "  from tb_chat_rooms t1 left join tb_lives t2 on t1.live_id=t2.live_id where room_id=#{roomId}")
    ChatRoomDetail queryChatRoomById(Integer roomId);


}
