/***
 * @pName http
 * @name HttpUtils
 * @user HongWei
 * @date 2018/11/28
 * @desc
 */
package com.utility.http;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * http请求工具类 DF 2018年11月28日00:06:44
 */
public class HttpUtil {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType FORM
            = MediaType.parse("application/x-www-form-urlencoded");

    private static OkHttpClient client =  new OkHttpClient();

    /**
     * 发送GET请求 DF 2018年11月28日00:17:10
     * @param url
     * @param header
     * @return
     */
    public static String get(String url, Map<String, String> header){
        // 构建工厂对象
        Request.Builder builder = new Request.Builder()
                .url(url);

        // 设置请求头信息, 留空不设置
        if(header != null) {
            builder.headers(Headers.of(header));
        }

        // 构建request对象并访问返回response给上层
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送POST请求 DF 2018年11月28日00:17:20
     * @param url
     * @param json
     * @param header
     * @return
     */
    public static String post(String url, String json, Map<String, String> header){
        RequestBody body = RequestBody.create(JSON, json);
        return post(url, body,header);
    }

    /**
     * 发送POST请求 DF 2018年11月28日00:17:20
     * @param url
     * @param data
     * @param header
     * @return
     */
    public static String postForm(String url, String data, Map<String, String> header){
        RequestBody body = RequestBody.create(FORM, data);
        return post(url, body,header);
    }

    private static String post(String url, RequestBody body, Map<String, String> header){
        // 构建工厂对象
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);

        // 设置请求头信息, 留空不设置
        if(header != null) {
            builder.headers(Headers.of(header));
        }

        // 构建request对象并访问返回response给上层
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
