package com.management.admin.controller;

import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.IPublishMessageService;
import com.management.admin.entity.db.PublishMessage;
import com.management.admin.entity.dbExt.PublishMessageDetail;
import com.management.admin.entity.template.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("management/publishMessage")
public class PublishMessageController {

    @Autowired
    private IPublishMessageService publishMessageService;

    @GetMapping("/index")
    public String index(){
        return "pushMessage/index";
    }

    /**
     * 推送消息
     * @param publishMessageDetail
     * @return
     */
    @PostMapping("/pushMessage")
    @ResponseBody
    @WebLog(section = "PublishMessage",content = "推送消息")
    public JsonResult pushMessage(PublishMessageDetail publishMessageDetail){
        Integer result = publishMessageService.pushMessage(publishMessageDetail);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
}
