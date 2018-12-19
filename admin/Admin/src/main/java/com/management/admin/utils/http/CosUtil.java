/***
 * @pName Admin
 * @name CosUtil
 * @user HongWei
 * @date 2018/12/10
 * @desc
 */
package com.management.admin.utils.http;

import com.management.admin.utils.Base64Util;
import com.management.admin.utils.JsonUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.security.Key;
import java.util.UUID;

public class CosUtil {
    private static final Logger logger = LoggerFactory.getLogger(CosUtil.class);
    private static COSCredentials cosCredentials;
    private static ClientConfig clientConfig;
    private static COSClient cosClient;
    // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
    private final static String bucketName = "yihaojia-1251694474";
    private final static String secretId = "AKIDY0GMH0Rt9IJSQJxXuFcnnbCbnXij4jXN";
    private final static String secretKey = "492zqeMrAJG1riV6bv27yjHn9f4Ii6bw";

    public static String accessUrl = "https://yihaojia-1251694474.cos.ap-chengdu.myqcloud.com/";

    static {
        // 1 初始化用户身份信息(secretId, secretKey)
        cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, C0OS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了0设置region, https(默认http), 超时, 代理等set方法, 使用可参见源码或者接口文档FAQ中说明
        clientConfig = new ClientConfig(new Region("ap-chengdu"));
        // 3 生成cos客户端
        cosClient = new COSClient(cosCredentials, clientConfig);
    }



    /**
     * 上传文件
     * @param
     * @return
     * @throws Exception
     */
    public static String upload(MultipartFile multipartFile) throws Exception {
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        //String encode = Base64Util.encodeFile("C:\\face.jpg");
        // Base64Util.decodeToFile("C:\\bucket.png", data);
        File localFile = new File("C:\\bucket.png");
        multipartFile.transferTo(localFile);
//        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), localFile);
        // 指定要上传到 COS 上对象键
        // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key =  "upload/" + UUID.randomUUID().toString() + ".png";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        logger.debug("COS_" + key + "_" + putObjectResult == null ? "NULL" : JsonUtil.getJson(putObjectResult));
        return accessUrl + key;
    }

    /**
     * 上传文件
     * @param data
     * @return
     * @throws Exception
     */
    public static String upload(String data) throws Exception {
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        String encode = Base64Util.encodeFile("C:\\face.jpg");
        Base64Util.decodeToFile("C:\\bucket.png", data);
        File localFile = new File("C:\\bucket.png");
        // 指定要上传到 COS 上对象键
        // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key =  "face/" + UUID.randomUUID().toString() + ".png";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        logger.debug("COS_" + key + "_" + putObjectResult == null ? "NULL" : JsonUtil.getJson(putObjectResult));
        return accessUrl + key;
    }

}
