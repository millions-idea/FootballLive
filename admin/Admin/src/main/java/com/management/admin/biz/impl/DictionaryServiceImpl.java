/***
 * @pName management
 * @name DictionaryServiceImpl
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.db.Dictionary;
import com.management.admin.repository.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements IDictionaryService {
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
    public List<Dictionary> getList() {
        return dictionaryMapper.selectAll();
    }

    /**
     * 获取首页聚合数据 DF  2018年12月14日09:46:24
     *
     * @return
     */
    @Override
    public List<Dictionary> getHomeGroupInfo() {
        String[] keys = {"banner.image1",
            "banner.image2",
            "banner.image3",
            "bootstrap.image1",
            "bootstrap.image2",
            "bootstrap.image3",
            "home.text.ad",
            "contact"};
        for (int i = 0; i < keys.length; i++) {
            keys[i] = "'" +  keys[i] + "'";
        }
        String join = String.join(",", keys);
        List<Dictionary> dictionaries = dictionaryMapper.selectInKey(join);
        return dictionaries;
    }
}
