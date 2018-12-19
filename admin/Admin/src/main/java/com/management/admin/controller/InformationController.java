package com.management.admin.controller;

import com.management.admin.biz.IInformationService;
import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.dbExt.InformationDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.utils.DateUtil;
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
@RequestMapping("/management/information")
public class InformationController {


    @Autowired
    private IInformationService informationService;

    @GetMapping("/index")
    public String index(){
        return "information/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getInformationLimit")
    @ResponseBody
    public JsonArrayResult<Information> getLiveLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<InformationDetail> list = informationService.getLimit(page, limit, condition, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = informationService.getCount();
        } else {
            count = informationService.getLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }
    @PostMapping("/setInformationContent")
    @ResponseBody
    public JsonResult setInformationContent(Information information){
        Integer result = informationService.modifyInfromation(information);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
    @GetMapping("/deleteInformation")
    @ResponseBody
    public JsonResult deleteInformation(Integer isrId){
        Integer result = informationService.deleteInformation(isrId);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
    @PostMapping("/insertInformation")
    @ResponseBody
    public JsonResult insertInformation(Information information){
        // TODO 注意如果一次添加多个情报给一个赛程或者直播间，直播详情点开时会出异常
        Integer result = informationService.insertInformation(information);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 获取情报详情
     * @param isrId
     * @param model
     * @return
     */
    @GetMapping("/getInformationById")
    public String getInformationById(Integer isrId, final Model model){
        InformationDetail informationDetail = informationService.queryInformationById(isrId);
        informationDetail.setAddDateStr(DateUtil.getFormatDateTime(informationDetail.getAddDate(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("informationDetail",informationDetail);
        return "information/details";
    }

    @GetMapping("/edit")
    public String edit(Integer isrId,final Model model){
        InformationDetail informationDetail = informationService.queryInformationById(isrId);
        model.addAttribute("informationDetail",informationDetail);
        return "information/edit";
    }
    @GetMapping("/add")
    public String add(){
        return "information/add";
    }
}
