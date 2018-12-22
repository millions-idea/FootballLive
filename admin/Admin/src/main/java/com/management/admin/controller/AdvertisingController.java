package com.management.admin.controller;

import com.management.admin.annotaion.WebLog;
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
     * 广告列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getAdvertisingLimit")
    @ResponseBody
    @WebLog(section = "Advertising", content = "查看所有广告")
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

    /**
     * 添加广告信息 狗蛋 2018年12月21日20:08:11
     * @return
     */
    @GetMapping("/add")
    public String add(){
        return "advertising/add";
    }

    /**
     * 修改广告信息
     * @param adId
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String edit(Integer adId, final Model model){
        AdvertisingDetail advertising = advertisingService.queryAdvertisingDetailById(adId);
        model.addAttribute("advertising",advertising);
        return "advertising/edit";
    }

    /**
     * 查看广告详情
     * @param adId
     * @param model
     * @return
     */
    @GetMapping("/details")
    public String details(Integer adId, final Model model){
        AdvertisingDetail advertising = advertisingService.queryAdvertisingDetailById(adId);
        model.addAttribute("advertising",advertising);
        return "advertising/details";
    }

    /**
     * 添加广告信息
     * @param advertisingDetail
     * @return
     */
    @PostMapping("/addAdvertisingInfo")
    @ResponseBody
    @WebLog(section = "Advertising", content = "添加广告信息")
    public JsonResult addAdvertisingInfo(AdvertisingDetail advertisingDetail){
        Integer result = advertisingService.insertAdvertising(advertisingDetail);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 修改广告信息
     * @param advertisingDetail
     * @return
     */
    @PostMapping("/setAdvertisingInfo")
    @ResponseBody
    @WebLog(section = "Advertising", content = "修改广告信息")
    public JsonResult setAdvertisingInfo(AdvertisingDetail advertisingDetail){
        Integer result = advertisingService.modifyAdvertising(advertisingDetail);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 删除广告信息
     * @param adId
     * @return
     */
    @GetMapping("/deleteAdvertising")
    @ResponseBody
    @WebLog(section = "Advertising", content = "删除广告信息")
    public JsonResult deleteAdvertising(Integer adId){
        Integer result = advertisingService.deleteAdvertising(adId);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
}
