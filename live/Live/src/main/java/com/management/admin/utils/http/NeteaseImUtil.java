/***
 * @pName Admin
 * @name NeteaseImUtil
 * @user HongWei
 * @date 2018/11/28
 * @desc
 */
package com.management.admin.utils.http;

import com.management.admin.entity.template.Constant;
import com.management.admin.utils.IdWorker;
import com.utility.http.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 网易IM工具类 DF 2018年11月28日00:37:24
 */
public class NeteaseImUtil {
    private static final Logger logger = LoggerFactory.getLogger(NeteaseImUtil.class);
    private static final String contentType = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String apiUrl = "https://api.netease.im/";

    /**
     * 构建header参数 DF 2018年11月28日01:12:42
     * @param appKey 开发者平台分配的appkey
     * @param appSecret
     * @return
     */
    private static Map<String,String> buildHeader(String appKey, String appSecret){
        /*参数	参数说明
        AppKey	开发者平台分配的appkey
        Nonce	随机数（最大长度128个字符）
        CurTime	当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
        CheckSum	SHA1(AppSecret + Nonce + CurTime),三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)*/
        Map<String,String> hashMap = new HashMap<>();
        try {
            hashMap.put("AppKey", appKey);
            hashMap.put("AppSecret", appSecret);
            hashMap.put("Nonce", IdWorker.getFlowIdWorkerInstance().nextId() + "");
            hashMap.put("CurTime", System.currentTimeMillis() / 1000 + "");
            String checkNum = hashMap.get("AppSecret") + hashMap.get("Nonce") + hashMap.get("CurTime");
            hashMap.put("CheckSum", DigestUtils.sha1Hex(checkNum).toLowerCase());
            hashMap.put("Content-Type", contentType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    /**
     * 使用网易协议模拟POST请求 DF 2018年11月28日01:12:35
     * @param url
     * @param json
     * @return
     */
    public static String post(String url, String json){
        String response = HttpUtil.postForm(apiUrl + url, json, NeteaseImUtil.buildHeader(Constant.AppKey, Constant.AppSecret));
        logger.debug(url, response);
        System.out.println(url);
        System.out.println(response);
        return response;
    }

}
