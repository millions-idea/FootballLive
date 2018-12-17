package com.management.admin.biz.impl;

import com.management.admin.biz.IAdvertisingService;
import com.management.admin.entity.db.Advertising;
import com.management.admin.repository.AdvertisingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisingServiceImpl implements IAdvertisingService {

    private AdvertisingMapper advertisingMapper;

    @Autowired
    public AdvertisingServiceImpl(AdvertisingMapper advertisingMapper){
        this.advertisingMapper = advertisingMapper;
    }



    /**
     * 查询所有广告链接
     *
     * @return
     */
    @Override
    public List<Advertising> queryAll() {
        return advertisingMapper.queryAllAdvertising();
    }
}
