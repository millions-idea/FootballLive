package com.management.admin.biz.impl;

import com.management.admin.biz.IAdvertisingService;
import com.management.admin.entity.db.Advertising;
import com.management.admin.entity.dbExt.AdvertisingDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.repository.AdvertisingMapper;
import com.management.admin.repository.LiveMapper;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdvertisingServiceImpl implements IAdvertisingService {

    private AdvertisingMapper advertisingMapper;
    private LiveMapper liveMapper;

    @Autowired
    public AdvertisingServiceImpl(AdvertisingMapper advertisingMapper,LiveMapper liveMapper){
        this.advertisingMapper = advertisingMapper;
        this.liveMapper = liveMapper;
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


    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     *
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<AdvertisingDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        List<AdvertisingDetail> list = advertisingMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return advertisingMapper.selectAdvertisingCount();
    }

    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     *
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getLimitCount(String condition, Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        return advertisingMapper.selectLimitCount(state, beginTime, endTime, where);
    }

    /**
     * 提取分页条件
     * @return
     */
    private String extractLimitWhere(String condition, Integer isEnable,  String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if(condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("ad_id", condition, true, "t1");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("type", condition, true, "t1");
            }
            where += ")";
        }
        // 取两个日期之间或查询指定日期
        where = extractBetweenTime(beginTime, endTime, where);
        return where.trim();
    }
    /**
     * 提取两个日期之间的条件
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (beginTime != null && beginTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }

    /**
     * 修改广告信息
     *
     * @param advertising
     * @return
     */
    @Override
    @Transactional
    public Integer modifyAdvertising(AdvertisingDetail advertising) {
        Integer result = liveMapper.modifyAdvertisingByLiveId(advertising.getLiveId(),advertising.getAdId());
        Integer result2 = advertisingMapper.modifyAdvertising(advertising);
        if(result>0&&result2>0){
            return 1;
        }
        return 0;
    }

    /**
     * 添加广告信息
     *
     * @param advertisingDetail
     * @return
     */
    @Override
    @Transactional
    public Integer insertAdvertising(AdvertisingDetail advertisingDetail) {
        Advertising advertising = new Advertising();
        PropertyUtil.clone(advertisingDetail,advertising);
        Integer result = advertisingMapper.insertAdvertising(advertisingDetail);
        // 设置直播间广告
        Integer result2 = liveMapper.modifyAdvertising(advertisingDetail.getAdId(),advertisingDetail.getLiveId());
        if(result>0&&result2>0){
            return 1;
        }
        return 0;
    }

    /**
     * 删除广告信息
     *
     * @param advertisingId
     * @return
     */
    @Override
    public Integer deleteAdvertising(Integer advertisingId) {
        return advertisingMapper.deleteAdvertisingById(advertisingId);
    }

    /**
     * 根据广告编号查询相应的广告
     *
     * @param adId
     * @return
     */
    @Override
    public AdvertisingDetail queryAdvertisingDetailById(Integer adId) {
        AdvertisingDetail advertisingDetail =advertisingMapper.queryAdvertisingById(adId);
        if(advertisingDetail.isType()){
            advertisingDetail.setTypeStr("图片广告");
        }else {
            advertisingDetail.setTypeStr("视频广告");
        }
        return advertisingDetail;
    }
}
