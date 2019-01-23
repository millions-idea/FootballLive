/***
 * @pName tls
 * @name TlsUtil
 * @user HongWei
 * @date 2019/1/20
 * @desc
 */
package com.utility.tls;

import com.tls.sigcheck.tls_sigcheck;

import java.io.*;

public class TlsUtil {
    public static String getUserSig(String sdkAppid, String identifier) throws Exception {
        tls_sigcheck demo = new tls_sigcheck();

        // 使用前请修改动态库的加载路径
        // demo.loadJniLib("D:\\src\\oicq64\\tinyid\\tls_sig_api\\windows\\64\\lib\\jni\\jnisigcheck.dll");
        demo.loadJniLib("C:\\tls\\jnisigcheck_mt_x64.dll");

        File priKeyFile = new File("C:\\tls\\ec_key.pem");
        StringBuilder strBuilder = new StringBuilder();
        String s = "";

        BufferedReader br = new BufferedReader(new FileReader(priKeyFile));
        while ((s = br.readLine()) != null) {
            strBuilder.append(s + '\n');
        }
        br.close();
        String priKey = strBuilder.toString();
        int ret = demo.tls_gen_signature_ex2(sdkAppid, identifier, priKey);
        String returnContent = "";
        if (0 != ret) {
            returnContent = demo.getErrMsg();
        }
        else
        {
            returnContent = demo.getSig();
        }
        return returnContent;
    }
}
