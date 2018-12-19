/***
 * @pName Admin
 * @name configurationController
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.db.Dictionary;
import com.management.admin.entity.template.JsonResult;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName configurationController
 * @Description 系统配置
 * @Author ZXL0
 * @Date 2018/12/17 10:47
 * Version 1.0
 **/
@Controller
@RequestMapping("management/configuration")
public class configurationController {

    @Autowired
    private IDictionaryService dictionaryService;
    /**
     * 跳转到系统配置界面
     * @return
     */
    @GetMapping("/index")
    public String index(final Model model){
        List<Dictionary> list=dictionaryService.getList();
        model.addAttribute("banner",list);
        return "configuration/index";
    }

    @GetMapping("/upadteConfig")
    @ResponseBody
    public JsonResult upadteConfig(String key,String value){
        val result = dictionaryService.upadteConfig(key, value);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
}
