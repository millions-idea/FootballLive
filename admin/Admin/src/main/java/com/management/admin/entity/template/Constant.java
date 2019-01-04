/***
 * @pName management
 * @name Constant
 * @user DF
 * @date 2018/8/13
 * @desc
 */
package com.management.admin.entity.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


public interface Constant {
    /**
     * 信息RSA公钥
     */
    String INFO_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSOALmVvE5bVFfvZASgLz1J4lvUH0SddzerZeQw6UiTZAfC+wDYizMn40IeGkX7Ypvs0FG9757iG3kmW0lad2wWWJRs4Ch8BDOr8EwYFG3glfGMlDJdkvXkq3KRHKXZ+sSo+zD9iiYW5Zbl9sGSmVgl2gMRmH0MX7/q4tIoyCfNwIDAQAB";

    /**
     * 信息RSA私钥
     */
    String INFO_PRI_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJI4AuZW8TltUV+9kBKAvPUniW9QfRJ13N6tl5DDpSJNkB8L7ANiLMyfjQh4aRftim+zQUb3vnuIbeSZbSVp3bBZYlGzgKHwEM6vwTBgUbeCV8YyUMl2S9eSrcpEcpdn6xKj7MP2KJhblluX2wZKZWCXaAxGYfQxfv+ri0ijIJ83AgMBAAECgYBXIpffymoAbfvzURUdYai5c7E8K7wNFz3jWZGcDGtJMO21ArbIIWZPHrm+FXuTbdR00l8ROqxCE8BhXWkkxZz9Bb7dQbLM6vQ7F0MrJr8Phzd5sJ36clFCh1v9eO5MvLXQCGggczBAPD6iqHn/o5SnzkASiOU0GAJ3XX5C3HKNkQJBANa0pdLGOgKIHDkJ0KG9ploUi5iCRu3ayePP6QsiAViYXZkhTHZzhnhUliSb8OIuLRPSZsn/FeVm9TX6K5Zc/H8CQQCuV0+ltTQ0gKC3bJY/p7/csrv12Hq7EsGac5SrkvMh2Z1K38FI6s3HjOWsUmMNWaoFGKPfpD8fzI8n9AYiz+FJAkAXCZshWpOu4dvlYuA3+pl24O15l8D0lJ1FDHH899EdKHwiiigiLZMoJitWEkVuf3XmwpS05+H5k2qMOCOlJqURAkA5FRlc9lGbhyoYJJOlT/V/+32TZ0f4DJIbUhCxhLHauXfI1i5hA5BQ8A8bNvZ7nooOlRzC0vJgJQgSWtHPgCURAkAWOry3CA8GsXsZ2fYIeqCZXIGzd4Alv7TQRWlgppRy3WmdIwBkVgQhSIAT0HiUteWVYgexrIGC6rDNXE4zEKfL";


    /**
     * 财务模块专用RSA公钥
     */
    String FINANCE_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYa5wMSo1iyXu0nrNizOnVkKFpREm1sgaY1sJu/be3tf//DRwoolEpwBXch9Y11OBrIItd0e1TXVoZvFiRboachimheGWJFl+Xxj8UrQFWz+tuqB5q9YNvwJVAX7WKKDHenwuXTTt/+jZI8mhRQPhBLdGM6hYCRloo87vOJPh0KQIDAQAB";

    /**
     * 财务模块专用RSA私钥
     */
    String FINANCE_PRIVATE_KEY ="MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBANhrnAxKjWLJe7Ses2LM6dWQoWlESbWyBpjWwm79t7e1//8NHCiiUSnAFdyH1jXU4Gsgi13R7VNdWhm8WJFuhpyGKaF4ZYkWX5fGPxStAVbP626oHmr1g2/AlUBftYooMd6fC5dNO3/6NkjyaFFA+EEt0YzqFgJGWijzu84k+HQpAgMBAAECgYEAxUzdV6tD2FK9L34bJjCP4T5YmOJDnJyvPPlVCuDNc3M97eSizjykZ76Rb5p65FqtOZzS0XaHuR96+8lkqTktB6pd7gjieqRxxL8Dfj6nlzt41Lm9wZ2RQOQLo3+VNQZexaGrQFLTIy697p2pv6gdxUAwuxFJx2rfOgx8KiDbdf0CQQDvlEpna7A1kvLp8E1+5gLQ7oMbWyfkHUNma3EI2pKlouVCN19y0q4LonDX99zZaGhY4aabTouozz7xcl9XvJgvAkEA50D3ujzmqHzXCj9j9TgTKvHdWiHb2xyyWO+EoBvA8hqVY6njqpgZB2/imBMedSfQ/ar5a/49mq6Un73IOnDLJwJBAJoWr1AbJAchD1lFNCKk/zSv4uUqWNrPs3ThL585LU6ZGYjgImSwej6DaL6O7Z1rGInqAAUtnIejW+Fg5U+BvgMCQQCBZpk4XVu0bCboVRBZ50bSgQSbqtabhTNHEL/l16Hf14BAhJAgpXtb7f+dmRUx+VW4nDN1eo5+P7JqsIeaLAOBAkEAtSnfzjqfuwAaOMlU5p2IcUn6ba9JuPB7jDwGq0f+SahZNNYJxeNnOd0QrsTHplMCUhbED9HGgIviLC3wPz55/w==";



    /**
     * 标注为回收数据
     */
    String RECYCLE = "RECYCLE";

    /**-
     * 系统账户id
     */
    Integer SYSTEM_ACCOUNTS_ID = 1;


    /**
     * 允许上传的文件类型
     */
    String[] FILE_TYPES = {
            "jpg",
            "png"
    };

    /**
     * 允许上传的MIME文件类型
     */
    String[] MIME_TYPES = {
            "image/png",
            "image/jpeg"
    };


    /**
     * 阿里短信服务accessKeyId
     */
    String AccessKeyId = "LTAIknx8YUZuaTH1";

    /**
     * 阿里短信服务accessKeySecret
     */
    String AccessKeySecret = "dUTcIBGiCBtuXy89tU3ZJ6KMzDHUQi";

    /**
     * 网易IM通信服务appKey
     */
    String AppKey = "c58b616a2dbfa83bf7857364f77ae13b";//76688b21d1656063933c1199a3e425a1

    /**
     * 网易IM通信服务appSecret
     */
    String AppSecret = "ef72133173f2";//b416985f7f9a
    /**
     * 网易热点用户
     */
    String HotAccId = "10000000000";
    /**
     * 系统热点用户(一般为管理员外的员工账户id)
     */
    Integer HotUserId = 1;

    /**
     * 消息密码
     */
    String MsgPassword =  "25f9e794323b453885f5181f1b624d0b";

    String BindDomain = "appapi.yaboabc.com,appliveadmin.yaboabc.com,live.yaboabc.cn,localhost";

    String BindAdminDomain = "appliveadmin.yaboabc.com";

    String NamiUser = "yeqing";

    String NamiSecret = "ys57ytxt4h2hpkxx";

    /**
     * 操作云信时记得把appkey换到测试环境上，不要直接操作正式环境的app应用
     */
    Boolean DebugMode = false;
}
