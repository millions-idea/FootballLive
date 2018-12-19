package com.management.admin.repository;

import com.management.admin.entity.db.PublishMessageRelation;
import com.management.admin.entity.dbExt.PublishMessageDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PublishMessageRelationMapper extends MyMapper<PublishMessageRelation> {
    /**
     * 查询推送消息关系列表 DF 2018年12月19日06:20:53
     * @param userId
     * @return
     */
    @Select("SELECT t1.*, t2.message FROM tb_publish_message_relations t1 " +
            "LEFT JOIN tb_publish_message t2 ON t1.msg_id = t2.msg_id " +
            "WHERE user_id=#{userId} ORDER BY add_date ASC")
    List<PublishMessageDetail> selectByUserId(@Param("userId") Integer userId);

    /**
     * 签收指定消息 DF 2018年12月19日06:23:39
     * @param relationId
     * @param userId
     * @return
     */
    @Update("UPDATE tb_publish_message_relations SET is_read=1,read_date=NOW() WHERE relation_id=#{relationId} AND user_id=#{userId}")
    int updateSignMessage(@Param("relationId") Integer relationId, @Param("userId") Integer userId);
}
