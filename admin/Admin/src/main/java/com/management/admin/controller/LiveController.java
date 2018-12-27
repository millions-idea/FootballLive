package com.management.admin.controller;

import com.alibaba.fastjson.JSON;
import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.ILiveService;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/management/live")
public class LiveController {

    @Autowired
    private ILiveService liveService;

/*    public LiveApiController(ILiveService liveService){
        this.liveService = liveService;
    }*/

    @GetMapping("/index")
    public String index() {
        return "live/index";
    }

    /**
     * 直播间列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getLiveLimit")
    @ResponseBody
    @WebLog(section = "live",content = "查看直播间列表")
    public JsonArrayResult<Live> getLiveLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<LiveDetail> list = liveService.getLimit(page, limit, condition, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = liveService.getCount();
        } else {
            count = liveService.getLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    /**
     * 跳转直播间修改
     * @param liveId
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String edit(Integer liveId, final Model model) {
        LiveDetail live = liveService.queryLiveDetails(liveId);
        live.setLiveDateStr(DateUtil.getFormatDateTime(live.getLiveDate(), "yyyy-MM-dd HH:mm:ss"));
        live.setAddDateStr(DateUtil.getFormatDateTime(live.getAddDate(), "yyyy-MM-dd HH:mm:ss"));
        live.setGameDateStr(DateUtil.getFormatDateTime(live.getGameDate(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("live", live);
        return "live/edit";
    }

    /**
     * 删除直播间
     * @param liveId
     * @return
     */
    @GetMapping("/delete")
    @ResponseBody
    @WebLog(section = "live",content = "删除直播间")
    public JsonResult delete(Integer liveId) {
        Integer live = liveService.deleteLive(liveId);
        if (live > 0) {
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 获取直播间详情
     * @param liveId
     * @param model
     * @return
     */
    @GetMapping("/getLiveDetailByLiveId")
    public String getLiveDetailByLiveId(Integer liveId, final Model model) {
        LiveDetail live = liveService.queryLiveDetails(liveId);
        Integer Status=live.getLiveStatus();
        String liveStatus="";
        if(Status==0){
            liveStatus="未开始";
        }else if(Status==1){
            liveStatus="正在直播";
        }
        live.setLiveDateStr(DateUtil.getFormatDateTime(live.getLiveDate(), "yyyy-MM-dd HH:mm:ss"));
        live.setAddDateStr(DateUtil.getFormatDateTime(live.getAddDate(), "yyyy-MM-dd HH:mm:ss"));
        live.setGameDateStr(DateUtil.getFormatDateTime(live.getGameDate(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("live", live);
        model.addAttribute("liveStatus",liveStatus);
        return "live/details";
    }

    /**
     * 跳转添加直播间
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return "live/add";
    }

    /**
     * 修改直播间信息
     * @param liveDetail
     * @return
     */
    @PostMapping("/setLiveInfo")
    @ResponseBody
    @WebLog(section = "live",content = "修改直播间信息")
    public JsonResult setLiveInfo(LiveDetail liveDetail) {
        Integer result = liveService.modifyLive(liveDetail);
        if (result > 0) {
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    @PostMapping("/addLiveInfo")
    @ResponseBody
    @WebLog(section = "live",content = "添加直播间")
    public JsonResult addLiveInfo(LiveDetail liveDetail) {
        Boolean result = liveService.insertLive(liveDetail);
        if (result) return JsonResult.successful();
        return JsonResult.failing();
    }

    @GetMapping("/getAll")
    @ResponseBody
    public JsonArrayResult<Live> getAll(){
        return new JsonArrayResult<Live>(liveService.queryAll());
    }
}
