/***
 * @pName management
 * @name HomeController
 * @user HongWei
 * @date 2018/8/27
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.annotaion.WebLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class HomeController {
    @GetMapping("/index")
    @WebLog(section = "User", content = "管理员登陆")
    public String index(){
        return "home/index";
    }
}
