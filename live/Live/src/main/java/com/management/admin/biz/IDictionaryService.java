/***
 * @pName management
 * @name DictionaryService
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.resp.DictionaryEntity;
import com.management.admin.entity.resp.VersionInfo;

import java.util.List;

public interface IDictionaryService extends IBaseService<DictionaryEntity> {



    Integer updateById(Integer dictionaryId, String url);


    /**
     *根据key修改value
     */
    Integer upadteConfig(String key, String value);

    /**
     * 根据key获取string类型的字典value DF 2018年12月20日06:27:13
     * @param key
     * @return
     */
    String get(String key);

}
