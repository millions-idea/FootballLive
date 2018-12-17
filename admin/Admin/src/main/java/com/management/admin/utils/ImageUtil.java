/***
 * @pName Admin
 * @name ImageUtil
 * @user HongWei
 * @date 2018/12/11
 * @desc
 */
package com.management.admin.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtil {
    public static byte[] getBytes(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int len = 20480;
                byte tmp [] = new byte[len];
                int i ;
                while((i=inputStream.read(tmp, 0, len))>0){
                    baos.write(tmp, 0, i);
                }
                byte imgs[] = baos.toByteArray();
                return imgs;
            }
        } catch (IOException e) {
            System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }


}
