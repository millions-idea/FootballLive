package com.management.admin.repository;

import com.management.admin.entity.db.ChatRoomUserRelation;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChatRoomUserRelationMapper extends MyMapper<ChatRoomUserRelation> {

    // ��ѯָ���������û��б�
    @Select("select t2.room_id,t2.frequency,t3.user_id,t3.photo,t3.nick_name,t3.signature " +
            " from tb_chat_room_user_relations t1 left join tb_chat_rooms t2 on t1.room_id=t2.room_id " +
            "left join tb_users t3 on t1.user_id=t3.user_id where t2.room_id=#{roomId} and t1.is_black_list =0 and " +
            " ${condition} GROUP BY t3.user_id ORDER BY t3.add_date DESC LIMIT #{page},${limit}")
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
            , @Param("condition") String condition
            , @Param("roomId") Integer roomId);

    @Select("SELECT COUNT(t1.live_id) from tb_chat_room_user_relations t1 left join tb_chat_rooms t2 on t1.room_id=t2.room_id" +
            " left join tb_users t3 on t1.user_id=t3.user_id where t2.room_id=#{roomId} and t1.is_black_list=0 and ${condition}")
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
            , @Param("condition") String condition
            , @Param("roomId") Integer roomId);

    /**
     * ��ѯ�ܼ�¼��
     *
     * @return
     */
    @Select("select count(*) from tb_chat_room_user_relations t1 left join tb_chat_rooms t2 on t1.room_id=t2.room_id " +
            " left join tb_users t3 on t1.user_id=t3.user_id where t2.room_id=#{roomId} and t1.is_black_list=0")
    Integer selectChatRoomUserCount(Integer roomId);

    /**
     * �����û�
     * @param userId
     * @return
     */
    @Update("update tb_chat_room_user_relations set is_black_list=1 where user_id=#{userId}")
    Integer ShieldingUser(Integer userId);
}
