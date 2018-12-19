package com.management.admin.controller;

import com.management.admin.biz.IAdvertisingService;
import com.management.admin.entity.db.Advertising;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.dbExt.AdvertisingDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/management/advertising")
public class AdvertisingController {

    @Autowired
    private IAdvertisingService advertisingService;

    @GetMapping("/index")
    public String index(){
        return "advertising/index";
    }


    /**
     * 查询所有广告
     * @return
     */
    @GetMapping("/getAll")
    @ResponseBody
    public JsonArrayResult<Advertising> getAll(){
        return new JsonArrayResult<Advertising>(advertisingService.queryAll());
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getAdvertisingLimit")
    @ResponseBody
    public JsonArrayResult<AdvertisingDetail> getLiveLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<AdvertisingDetail> list = advertisingService.getLimit(page, limit, condition, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = advertisingService.getCount();
        } else {
            count = advertisingService.getLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        System.out.println(jsonArrayResult.getData());
        return jsonArrayResult;
    }
    @GetMapping("/add")
    public String add(){
        return "advertising/add";
    }
    @GetMapping("/edit")
    public String edit(Integer adId, final Model model){
        AdvertisingDetail advertising = advertisingService.queryAdvertisingDetailById(adId);
        model.addAttribute("advertising",advertising);
        return "advertising/edit";
    }
    @GetMapping("/details")
    public String details(Integer adId, final Model model){
        AdvertisingDetail advertising = advertisingService.queryAdvertisingDetailById(adId);
        model.addAttribute("advertising",advertising);
        return "advertising/details";
    }
    @PostMapping("/addAdvertisingInfo")
    @ResponseBody
    public JsonResult addAdvertisingInfo(AdvertisingDetail advertisingDetail){
        Integer result = advertisingService.insertAdvertising(advertisingDetail);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
    @PostMapping("/setAdvertisingInfo")
    @ResponseBody
    public JsonResult setAdvertisingInfo(AdvertisingDetail advertisingDetail){
        Integer result = advertisingService.modifyAdvertising(advertisingDetail);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
    @GetMapping("/deleteAdvertising")
    @ResponseBody
    public JsonResult deleteAdvertising(Integer adId){
        Integer result = advertisingService.deleteAdvertising(adId);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
}
