package com.management.admin.controller;

import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.IInformationService;
import com.management.admin.biz.IScheduleService;
import com.management.admin.biz.ITeamService;
import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.InformationDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/management/information")
public class InformationController {


    @Autowired
    private IInformationService informationService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private ITeamService teamService;

    @GetMapping("/index")
    public String index(){
        return "information/index";
    }

    /**
     * 情报信息列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getInformationLimit")
    @ResponseBody
    @WebLog(section = "Information",content = "查看所有的情报信息")
    public JsonArrayResult<InformationDetail> getLiveLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
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

    /**
     * 根据直播间查询所属的赛事信息
     * @return
     */
    @PostMapping("selectgames")
    @ResponseBody
    public JsonResult<LiveDetail> selectgames(Integer liveId){
        LiveDetail liveDetail=informationService.selectGamesByLive(liveId);
        if(liveDetail!=null){
          return new JsonResult<LiveDetail>().successful(liveDetail);
        }
        return JsonResult.failing();
    }

    /**
     * 修改情报信息
     * @param information
     * @return
     */
    @PostMapping("/setInformationContent")
    @ResponseBody
    @WebLog(section = "Information",content = "修改情报信息")
    public JsonResult setInformationContent(InformationDetail information){
        boolean result = informationService.modifyInfromation(information);
        if(result){
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

    /**
     * 添加情报信息
     * @param information
     * @return
     */
    @PostMapping("/insertInformation")
    @ResponseBody
    @WebLog(section = "Information",content = "添加情报信息")
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
        Map<String,Integer> statuses = new HashMap<>();
        statuses.put("未开始", 0);
        statuses.put("正在直播", 1);
        statuses.put("已结束", 2);

        ScheduleGameTeam scheduleGameTeam = scheduleService.selectScheduleByInfoId(isrId);

        model.addAttribute("scheduleGameTeam", scheduleGameTeam);
        model.addAttribute("statuses",statuses);

        String teamIds = scheduleGameTeam.getTeamId();
        String[] split = teamIds.split(",");

        List<Team> teams = teamService.getTeams(teamIds);
        model.addAttribute("teams",teams);

        InformationDetail informationDetail = informationService.queryInformationById(isrId);
        model.addAttribute("informationDetail",informationDetail);
        return "information/edit";
    }

    @GetMapping("/add")
    public String add(final Model model){

        return "information/add";
    }
}
