/***
 * @pName Kafka
 * @name DictionaryConfiguration
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.management.admin.config;

import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.template.DataDictionary;
import com.management.admin.entity.db.Dictionary;
import com.management.admin.exception.InfoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DictionaryConfiguration {
    @Autowired
    private IDictionaryService dictionaryService;

    @Bean
    public List<Dictionary> loadDictionary(){
        List<Dictionary> list = dictionaryService.getList();
        if(list == null || list.isEmpty()) throw new InfoException("数据字典不能为空");
        return list;
    }

    @Bean
    public Map<String,Dictionary> initDictionary(){
        List<Dictionary> dictionaryList = loadDictionary();
        Map<String,Dictionary> dataDictionaryList = new HashMap<>();
        dictionaryList.forEach(dictionary -> dataDictionaryList.put(dictionary.getKey() , dictionary));
        DataDictionary.DATA_DICTIONARY = dataDictionaryList;
        return dataDictionaryList;
    }
}
