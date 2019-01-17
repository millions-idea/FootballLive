/***
 * @pName management
 * @name DictionaryServiceImpl
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.resp.DictionaryEntity;
import com.management.admin.entity.resp.VersionInfo;
import com.management.admin.repository.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryEntity> implements IDictionaryService {
    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryServiceImpl(DictionaryMapper dictionaryMapper) {
        this.dictionaryMapper = dictionaryMapper;
    }


    /**
     * 查询数据字典信息列表 2018年10月31日21:46:59
     *
     * @return
     */
    @Override
    public List<DictionaryEntity> getList() {
        return dictionaryMapper.selectAll();
    }


    /**
     * 根据键设置值
     *
     * @param dictionaryId
     * @param url
     * @return
     */
    @Override
    public Integer updateById(Integer dictionaryId, String url) {
        return dictionaryMapper.updateUrlById(dictionaryId,url);
    }

    /**
     * 根据键设置值
     * @param key
     * @param value
     * @return
     */
    @Override
    public Integer upadteConfig(String key, String value) {
        return dictionaryMapper.updateUrlByKey(key,value);
    }

    /**
     * 根据key获取string类型的字典value DF 2018年12月20日06:27:13
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        DictionaryEntity dictionaryEntity = dictionaryMapper.selectOneKey(key);
        if(dictionaryEntity != null) return dictionaryEntity.getValue();
        return null;
    }

}
