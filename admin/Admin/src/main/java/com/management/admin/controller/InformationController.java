package com.management.admin.controller;

import com.management.admin.biz.IInformationService;
import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.dbExt.InformationDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/management/information")
public class InformationController {


    @Autowired
    private IInformationService informationService;

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
}
