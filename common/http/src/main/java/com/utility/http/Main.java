/***
 * @pName http
 * @name Main
 * @user HongWei
 * @date 2018/11/28
 * @desc
 */
package com.utility.http;

public class Main {
    public static void main(String[] args) {
        String response = HttpUtil.get("http://www.baidu.com/", null);
        System.out.println(response);
    }
}
