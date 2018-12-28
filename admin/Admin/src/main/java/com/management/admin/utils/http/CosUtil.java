/***
 * @pName Admin
 * @name CosUtil
 * @user HongWei
 * @date 2018/12/10
 * @desc
 */
package com.management.admin.utils.http;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.management.admin.utils.Base64Util;
import com.management.admin.utils.JsonUtil;
import com.sun.corba.se.impl.activation.ServerMain;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.applet.Main;

import java.io.*;
import java.security.Key;
import java.util.UUID;

public class CosUtil {
    private static final Logger logger = LoggerFactory.getLogger(CosUtil.class);
/*    private static COSCredentials cosCredentials;
    private static ClientConfig clientConfig;
    private static COSClient cosClient;*/

    // 阿里云
    private static OSSClient ossClient;

    private final static String bucketName = "yabolive";
/*    private final static String secretId = "AKIDY0GMH0Rt9IJSQJxXuFcnnbCbnXij4jXN";
    private final static String secretKey = "492zqeMrAJG1riV6bv27yjHn9f4Ii6bw";*/
    // 阿里云
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    private final static String accessKeyId = "LTAIR0oyIQND1tDJ";
    private final static String accessKeySecret = "Xqde9mlClOMyAVVjLPmr9pnaUUX8Wb";

    public static String endpoint = "http://oss-cn-beijing.aliyuncs.com/";
    public static String accessUrl = "http://yabolive.oss-cn-beijing.aliyuncs.com/";

    /**
     * 上传文件
     * @param
     * @return
     * @throws Exception
     */
    public static String upload(MultipartFile multipartFile) throws Exception {
        try {
            File localFile = new File("C:\\bucket.png");

            multipartFile.transferTo(localFile);

            String key =  "upload/" + UUID.randomUUID().toString() + ".png";

            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
            InputStream inputStream = new FileInputStream(localFile);
            ossClient.putObject(bucketName, key, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            return accessUrl + key;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "IOException";
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return "IllegalStateException";
        } catch (OSSException e) {
            System.out.println(e.getMessage());
            return "OSSException";
        } catch (ClientException e) {
            System.out.println(e.getMessage());
           return "ClientException";
        }
    }

    /**
     * 上传base64编码的数据
     * @param data
     * @return
     * @throws Exception
     */
    public static String upload(String data) throws Exception {
        Base64Util.decodeToFile("C:\\bucket.png", data);

        File localFile = new File("C:\\bucket.png");

        String key =  "upload/" + UUID.randomUUID().toString() + ".png";

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        InputStream inputStream = new FileInputStream(localFile);
        ossClient.putObject(bucketName, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        return accessUrl + key;
    }


}
