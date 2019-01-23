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
        String response = HttpUtil.post("https://console.tim.qq.com/v4/group_open_http_svc/create_group?usersig=eJxlj01vgkAURff8CsKWpp0PEMfEBVqj2FojGlNXE*K80ak4TnGwYtP-3khNSuLbnpN77-t2XNf1Fq-zx2y9PpTaclsZ8NyO6yHv4R8aowTPLKeFuINwNqoAnkkLRQ1xGIYEoaajBGirpLoZmdgrjRv8KHa8LvkLCBDCEWNB0FTUpoaTwayfjAYrsn1-g10b0c1pWmq56m0ZXZTDiLXMCEfDqEjsvF*dn2PVm7wAyHRMngi0kUg*qH-5TIOvfGqWTFY*HV-8eJnqWZ7H3W6j0qo93D4KrnNYizXoCYqjOuhaIAiHmFB0Pc-5cX4B3o9cKQ__&identifier=admin1&sdkappid=1400179944&random=99999999&contenttype=json",
                /*"{\r\n\"Type\": \"AVChatRoom\", \r\n\"Name\": \"TestGroup\"\r\n}",*/
                "{\r\n\"Type\": \"AVChatRoom\", \r\n\"Name\": \"TestGroup\"\r\n}",
                null);
        System.out.println(response);
    }
}
