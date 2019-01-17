package com.management.admin.utils.SMS;


import com.alibaba.fastjson.JSON;
import com.management.admin.utils.SMS.request.SmsSendRequest;
import com.management.admin.utils.SMS.response.SmsSendResponse;

/**
 * @ClassName SmsUtil
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/22 22:10
 * Version 1.0
 **/

public class PaasSmsUtil {

    public static final String charset = "utf-8";
    // 请登录zz.253.com 获取创蓝API账号(非登录账号,示例:N1234567)
    public static String account = "N2073464";
    // 请登录zz.253.com 获取创蓝API密码(非登录密码)
    public static String password = "ZDpjMw3Rckd0b5";

    //短信发送的URL 请登录zz.253.com 获取完整的URL接口信息
    public static String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";


    public static boolean sendMessage(String phone ,String msg){

        //状态报告
        String report= "false";
        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
        String code=smsSingleResponse.getCode();
        if(code.equals("0")){
            return true;
        }
        return false;
    }

}
