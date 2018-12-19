package com.management.admin.repository;

import com.management.admin.entity.db.PublishMessage;
import com.management.admin.entity.dbExt.PublishMessageDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface PublishMessageMapper extends MyMapper<PublishMessage> {

    /**
     * 添加推送消息
     * @param publishMessage
     * @return
     */
    @Insert("insert into tb_publish_messages(message,add_date,type) values(#{message},#{addDate},#{type})")
    @Options(useGeneratedKeys = true, keyProperty = "msgId")
    Integer insertPublishMessage(PublishMessageDetail publishMessage);
}
