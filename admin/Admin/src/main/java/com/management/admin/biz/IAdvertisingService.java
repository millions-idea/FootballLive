package com.management.admin.biz;

import com.management.admin.entity.db.Advertising;
import com.management.admin.entity.dbExt.AdvertisingDetail;
import com.management.admin.entity.dbExt.LiveDetail;

import java.util.List;

public interface IAdvertisingService {

    /**
     * 查询所有广告链接
     * @return
     */
    List<Advertising> queryAll();

    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    List<AdvertisingDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime);


    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     * @return
     */
    Integer getCount();


    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getLimitCount(String condition, Integer state, String beginTime, String endTime);

    /**
     * 修改广告信息
     * @param advertising
     * @return
     */
    Integer modifyAdvertising(AdvertisingDetail advertising);

    /**
     * 添加广告信息
     * @param advertisingDetail
     * @return
     */
    Integer insertAdvertising(AdvertisingDetail advertisingDetail);

    /**
     * 删除广告信息
     * @param advertisingId
     * @return
     */
    Integer deleteAdvertising(Integer advertisingId);

    /**
     * 根据广告编号查询相应的广告
     * @param adId
     * @return
     */
    AdvertisingDetail queryAdvertisingDetailById(Integer adId);
}
