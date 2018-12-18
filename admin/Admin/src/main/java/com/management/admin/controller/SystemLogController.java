package com.management.admin.controller;

import com.management.admin.biz.ISystemLogService;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.SystemLog;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("management/systemlog")
public class SystemLogController {

    @Autowired
    private ISystemLogService systemLogService;

    @GetMapping("/index")
    public String index(){
        return "systemlog/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getSystemLogLimit")
    @ResponseBody
    public JsonArrayResult<Live> getLiveLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<SystemLog> list = systemLogService.getLimit(page, limit, condition, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = systemLogService.getCount();
        } else {
            count = systemLogService.getLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }
    @GetMapping("/getSystemLogById")
    public String getSystemLogById(Integer logId, final Model model){
        // 1：执行查询，2：放入到模型中，3：返回页面跳转
        SystemLog systemLog = systemLogService.querySystemLogById(logId);
        model.addAttribute("systemLog",systemLog);
        String addDateStr = DateUtil.getFormatDateTime(systemLog.getAddDate(),"yyyy-MM-dd HH:mm:ss");
        model.addAttribute("addDateStr",addDateStr);
        return "systemlog/details";
    }
}
