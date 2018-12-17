/***
 * @pName Admin
 * @name CommonApiController
 * @user HongWei
 * @date 2018/12/11
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.entity.template.JsonResult;
import com.management.admin.utils.Base64Util;
import com.management.admin.utils.ImageUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("api/common")
public class CommonApiController {
    /**
     * 获取base64编码 DF 2018年12月11日07:05:05
     * @param url
     * @return
     */
    @GetMapping("getBaseDecode")
    public JsonResult getBaseDecode(String url) throws Exception {
        byte[] bs = ImageUtil.getBytes(url);
        String encode = Base64Util.encode(bs);
        encode = "" + encode;
        return new JsonResult().successful(encode);
    }
}
