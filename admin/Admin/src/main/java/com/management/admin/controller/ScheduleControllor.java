package com.management.admin.controller;

import com.management.admin.biz.ICompetitionService;
import com.management.admin.biz.IScheduleService;
import com.management.admin.biz.ITeamService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
import com.management.admin.entity.dbExt.TeamCompetition;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamControllor
 * @Description 赛程管理
 * @Author ZXL01
 * @Date 2018/12/17 10:47
 * Version 1.0
 **/
@Controller
@RequestMapping("management/schedule")
public class ScheduleControllor {

    @Autowired
    private IScheduleService scheduleService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private ICompetitionService competitionService;


    /**
     * 跳转到赛程管理界面
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "schedule/index";
    }

    /**
     * 球队列表 提莫 2018年8月29日11:42:31
     * @return
     */
    @GetMapping("/getScheduleLimit")
    @ResponseBody
    public JsonArrayResult<ScheduleGameTeam> getMemberLimit(Integer page, String limit){
        Integer count = 0;
        List<ScheduleGameTeam> list = scheduleService.getScheduleLimit(page,limit);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        count = scheduleService .getScheduleLimitCount();
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    /**
     * 查看赛程双方球队提莫 2018年8月29日11:42:31
     * @return
     */
    @GetMapping("/queryTeam")
    @ResponseBody
    public JsonResult queryTeam(String teamId){
        List<Team> list=teamService.getTeams(teamId);
        //StringBuffer buffer =new StringBuffer();
        JsonResult result =new JsonResult();
        if(list.size()!=0){
            String TName=""+list.get(0).getTeamName()+"     VS     "+list.get(1).getTeamName();;
            result.setCode(200);
            result.setMsg(TName);
        }else {
            result.setCode(500);
            result.setMsg("失败");
        }
        return result;
    }

    /**
     * 删除赛程信息 2018年12月19日1:48:31
     * @return
     */
    @GetMapping("/deleteSchedule")
    @ResponseBody
    public JsonResult deleteSchedule(Integer scheduleId){
         if(scheduleService.deleteSchedule(scheduleId)){
             return JsonResult.successful();
         }
         return JsonResult.failing();
    }

    /**
     * 直播分类 提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("add")
    public String addSchedule(final Model model){
        List<Game> games=competitionService.getGames();
        List<Team> teams=teamService.getAllTeams();
        model.addAttribute("games",games);
        model.addAttribute("teams",teams);
        return "schedule/add";
    }
    /**
     * 修改赛程传递显示参数  提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("edit")
    public String updateSchedule(final Model model,Integer scheduleId){

        List<Game> games=competitionService.getGames();
        List<Team> teams=teamService.getAllTeams();

        ScheduleGameTeam scheduleGameTeam=scheduleService.selectScheduleById(scheduleId);



        String tameIds=scheduleGameTeam.getTeamId();
        String[] split = tameIds.split(",");

        Map<String,Integer> statuses = new HashMap<>();
        statuses.put("未开始", 0);
        statuses.put("正在直播", 1);
        statuses.put("已结束", 2);

        String primaryId=split[0];
        String secondId=split[1];
        model.addAttribute("primaryId",primaryId);
        model.addAttribute("secondId",secondId);
        model.addAttribute("scheduleGameTeam", scheduleGameTeam);
        model.addAttribute("games",games);
        model.addAttribute("teams",teams);
        model.addAttribute("statuses",statuses);
        return "schedule/update";
    }

    /**
     * 添加赛程 提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("addSchedule")
    @ResponseBody
   public JsonResult addSchedule(Schedule schedule){
        System.err.println(schedule);
        if(scheduleService.addSchedule(schedule)){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 修改赛程 提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("updateSchedule")
    @ResponseBody
    public JsonResult updateSchedule(Schedule schedule){
        if(scheduleService.updateSchedule(schedule)){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

}
