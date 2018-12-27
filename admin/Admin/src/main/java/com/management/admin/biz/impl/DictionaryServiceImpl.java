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
import com.management.admin.entity.resp.DictionaryInfo;
import com.management.admin.entity.resp.VersionInfo;
import com.management.admin.repository.DictionaryMapper;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<DictionaryInfo> getHomeGroupInfo() {
        String[] keys = {"banner.image1",
            "banner.image2",
                "banner.image3",
                "banner.image1.targetUrl",
                "banner.image2.targetUrl",
                "banner.image3.targetUrl",
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
        List<DictionaryInfo> dictionaryInfos = new ArrayList<>();
        dictionaries.stream().forEach(item -> {
            DictionaryInfo dictionaryInfo = new DictionaryInfo();
            dictionaryInfo.setDictionaryId(item.getDictionaryId());
            dictionaryInfo.setKey(item.getKey());
            dictionaryInfo.setValue(item.getValue());
            if (item.getKey().contains("banner.image")){
                Optional<Dictionary> targetUrl = dictionaries.stream().filter(d -> d.getKey().contains(item.getKey() + ".targetUrl")).findFirst();
                if (targetUrl.isPresent() && targetUrl.get() != null){
                    dictionaryInfo.setUrl(targetUrl.get().getValue());
                }
            }
            dictionaryInfos.add(dictionaryInfo);
        });
        return dictionaryInfos;
    }

    /**
     * 获取webApp全站广告数据 DF 2018年12月18日15:25:17
     *
     * @return
     */
    @Override
    public List<Dictionary> getWebAppAdvertising() {
        String[] keys = {"webapp.image.ad.url", "webapp.image.ad.target.url"};
        String joinKey =StringUtil.joinChar(",", keys);
        List<Dictionary> dictionaries = dictionaryMapper.selectInKey(joinKey);
        return dictionaries;
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
        Dictionary dictionary = dictionaryMapper.selectOneKey(key);
        if(dictionary != null) return dictionary.getValue();
        return null;
    }

    /**
     * 获取版本号 DF 2018年12月20日06:39:59
     *
     * @return
     */
    @Override
    public VersionInfo getVersion() {
        List<Dictionary> list = dictionaryMapper.selectInKey("'version', 'iosDownload' , 'androidDownload'");
        String version =list.stream().filter(item -> item.getKey().equals("version")).findFirst().get().getValue();
        String iosDownload = list.stream().filter(item -> item.getKey().equals("iosDownload")).findFirst().get().getValue();
        String androidDownload = list.stream().filter(item -> item.getKey().equals("androidDownload")).findFirst().get().getValue();
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setVersion(version);
        versionInfo.setIosDownload(iosDownload);
        versionInfo.setAndroidDownload(androidDownload);
        return versionInfo;
    }

    /**
     * 随机获取启动页图片 DF 2018年12月20日07:50:18
     *
     * @return
     */
    @Override
    public String getBootstrapRandomImage() {
        List<Dictionary> list = dictionaryMapper.selectInKey("'bootstrap.image1', 'bootstrap.image2', 'bootstrap.image3'");
        if(list != null && list.size() > 0){
            Random rand = new Random();
            return list.get(rand.nextInt(list.size())).getValue();
        }
        return null;
    }

    /**
     * 获取联系方式 DF 2018年12月26日05:07:30
     *
     * @return
     */
    @Override
    public Dictionary getContact() {
        Dictionary dictionary = dictionaryMapper.selectOneKey("contact");
        return dictionary;
    }
}
