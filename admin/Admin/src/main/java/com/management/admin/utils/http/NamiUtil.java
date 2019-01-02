/***
 * @pName Admin
 * @name NamiUtil
 * @user HongWei
 * @date 2019/1/1
 * @desc
 */
package com.management.admin.utils.http;

import com.management.admin.entity.template.Constant;
import com.utility.http.HttpUtil;

public class NamiUtil {
    private static String user = Constant.NamiUser;
    private static String secret = Constant.NamiSecret;

    public static String get(String url, String data) {
        url = "http://open.sportnanoapi.com/api/" + url + "?user=" + user + "&secret=" + secret;
        if(data != null) url += "&" + data;
        String result = HttpUtil.get(url, null);
        System.out.println("Url:" + url + " Response:" + data);
        return result;
    }
}
