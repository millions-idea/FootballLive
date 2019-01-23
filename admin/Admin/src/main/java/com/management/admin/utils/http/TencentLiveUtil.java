/***
 * @pName Admin
 * @name NamiUtil
 * @user HongWei
 * @date 2019/1/1
 * @desc
 */
package com.management.admin.utils.http;

import com.management.admin.entity.template.Constant;
import com.management.admin.utils.IdWorker;
import com.utility.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TencentLiveUtil {
    private static final Logger logger = LoggerFactory.getLogger(TencentLiveUtil.class);
    private static String user = Constant.NamiUser;
    private static String secret = Constant.NamiSecret;

    public static String get(String url, String data) {
        url = "https://console.tim.qq.com/v4/group_open_http_svc/" + url + "?" + getQueryParams();
        if(data != null) url += "&" + data;
        String result = HttpUtil.get(url, null);
        logger.info("TencentLiveUtil_get()_" + url + "_" + result);
        return result;
    }

    public static String post(String url, String json) {
        url = "https://console.tim.qq.com/v4/" + url + "?" + getQueryParams();
        String result = HttpUtil.post(url, json, null);
        logger.info("TencentLiveUtil_post()_" + url + "_" + result);
        return result;
    }

    private static String getQueryParams(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("usersig=" + Constant.TencentUserSig + "&");
        buffer.append("identifier=" + Constant.TencentAdminAccount + "&");
        buffer.append("sdkappid=" + Constant.TencentSdkAppId + "&");
        buffer.append("random=" + IdWorker.getFlowIdWorkerInstance().nextInt32(8) + "&");
        buffer.append("contenttype=json");
        return buffer.toString();
    }
}
