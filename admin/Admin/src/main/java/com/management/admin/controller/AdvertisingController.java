package com.management.admin.controller;

import com.management.admin.biz.IAdvertisingService;
import com.management.admin.entity.db.Advertising;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.template.JsonArrayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("management/advertising")
public class AdvertisingController {

    @Autowired
    private IAdvertisingService advertisingService;

    /**
     * 查询所有广告
     * @return
     */
    @GetMapping("/getAll")
    @ResponseBody
    public JsonArrayResult<Advertising> getAll(){
        return new JsonArrayResult<Advertising>(advertisingService.queryAll());
    }
}
