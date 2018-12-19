package com.management.admin.biz;

import com.management.admin.entity.db.PublishMessage;
import com.management.admin.entity.dbExt.PublishMessageDetail;

import java.util.List;

public interface IPublishMessageService {

    Integer pushMessage(PublishMessageDetail publishMessageDetail);

    /**
     * 获取推送消息列表 DF 2018年12月19日06:19:32
     * @param userId
     * @return
     */
    List<PublishMessageDetail> getPushMessageList(Integer userId);

    /**
     * 签收指定消息 DF 2018年12月19日06:23:39
     * @param relationId
     * @param userId
     * @return
     */
    boolean signMessage(Integer relationId, Integer userId);
}