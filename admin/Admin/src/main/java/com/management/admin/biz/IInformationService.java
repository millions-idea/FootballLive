package com.management.admin.biz;

import com.management.admin.entity.db.Information;

public interface IInformationService {
    /**
     * 添加情报
     * @param information
     * @return
     */
    Integer insertInformation(Information information);
}
