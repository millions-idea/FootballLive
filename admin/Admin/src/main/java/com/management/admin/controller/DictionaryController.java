package com.management.admin.controller;

import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.template.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("management/dictionary")
public class DictionaryController {

    @Autowired
    private IDictionaryService dictionaryService;

    /**
     * 修改联系我们
     * @param value
     * @return
     */
    @GetMapping("/setContact")
    @WebLog(section = "Config",content = "修改联系我们")
    public JsonResult setContact(String value){
        Integer result = dictionaryService.updateById(17,value);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
    /**
     * 跳转联系我们
     */
    @GetMapping("/contact")
    public String contact(){
        return "systemConfig/contactConfig";
    }
}
