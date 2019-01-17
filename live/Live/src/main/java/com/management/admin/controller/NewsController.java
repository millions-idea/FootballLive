/***
 * @pName Live
 * @name NewsController
 * @user HongWei
 * @date 2019/1/13
 * @desc
 */
package com.management.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {

    @GetMapping("contact")
    public String contact(){
        return "news/contact";
    }

    @GetMapping("about")
    public String about(){
        return "news/about";
    }
}
