/***
 * @pName Live
 * @name UserController
 * @user HongWei
 * @date 2019/1/23
 * @desc
 */
package com.management.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 登录弹窗 DF 2019年1月23日00:21:44
     * @return
     */
    @GetMapping("loginPop")
    public String loginPop(){
        return "user/login";
    }
}
