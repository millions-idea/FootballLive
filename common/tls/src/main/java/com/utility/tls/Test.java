/***
 * @pName tls
 * @name Test
 * @user HongWei
 * @date 2019/1/20
 * @desc
 */
package com.utility.tls;

public class Test {
    public static void main(String[] args) throws Exception {
        String userSig = TlsUtil.getUserSig("1400179944","admin1");
        System.out.println(userSig);
    }
}
