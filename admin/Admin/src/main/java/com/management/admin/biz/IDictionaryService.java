/***
 * @pName management
 * @name DictionaryService
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.Dictionary;

import java.util.List;
import java.util.Map;

public interface IDictionaryService extends IBaseService<Dictionary> {

    /**
     * 获取首页聚合数据 DF  2018年12月14日09:46:24
     * @return
     */
    List<Dictionary> getHomeGroupInfo();

    /**
     * 获取webApp全站广告数据 DF 2018年12月18日15:25:17
     * @return
     */
    List<Dictionary> getWebAppAdvertising();

    Integer updateById(Integer dictionaryId,String url);
}
