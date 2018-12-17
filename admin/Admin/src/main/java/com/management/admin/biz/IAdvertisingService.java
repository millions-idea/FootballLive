package com.management.admin.biz;

import com.management.admin.entity.db.Advertising;

import java.util.List;

public interface IAdvertisingService {

    /**
     * 查询所有广告链接
     * @return
     */
    List<Advertising> queryAll();
}
