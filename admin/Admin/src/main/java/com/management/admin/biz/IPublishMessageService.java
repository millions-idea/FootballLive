package com.management.admin.biz;

import com.management.admin.entity.db.PublishMessage;
import com.management.admin.entity.dbExt.PublishMessageDetail;

public interface IPublishMessageService {

    Integer pushMessage(PublishMessageDetail publishMessageDetail);
}