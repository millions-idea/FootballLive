/***
 * @pName management
 * @name DictionaryService
 * @user DF
 * @date 2018/8/16
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.Dictionary;
import com.management.admin.entity.resp.DictionaryInfo;
import com.management.admin.entity.resp.VersionInfo;

import java.util.List;
import java.util.Map;

public interface IDictionaryService extends IBaseService<Dictionary> {

    /**
     * 获取首页聚合数据 DF  2018年12月14日09:46:24
     * @return
     */
    List<DictionaryInfo> getHomeGroupInfo();

    /**
     * 获取webApp全站广告数据 DF 2018年12月18日15:25:17
     * @return
     */
    List<Dictionary> getWebAppAdvertising();

    Integer updateById(Integer dictionaryId,String url);


    /**
     *根据key修改value
     */
    Integer upadteConfig(String key,String value);

    /**
     * 根据key获取string类型的字典value DF 2018年12月20日06:27:13
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 获取版本号 DF 2018年12月20日06:39:59
     * @return
     */
    VersionInfo getVersion();

    /**
     * 随机获取启动页图片 DF 2018年12月20日07:50:18
     * @return
     */
    String getBootstrapRandomImage();

    /**
     * 获取联系方式 DF 2018年12月26日05:07:30
     * @return
     */
    Dictionary getContact();
}
