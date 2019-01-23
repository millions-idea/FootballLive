/***
 * @pName Live
 * @name ScheduleController
 * @user HongWei
 * @date 2019/1/12
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.biz.IGameService;
import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.resp.*;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IGameService gameService;

    @GetMapping(value = {"","/","/index"})
    public String index(@RequestParam(required = false) Integer gameId, final Model model){
        //查询足球、篮球、斯诺克分类赛事，只取前7条数据
        List<HotCategory> hotCategoryList = gameService.getCategoryList("足球", "篮球", "斯诺克");
        //查询全部分类下的全部赛程的近三天数据
        List<HotSchedule> scheduleServiceRecentList = scheduleService.getRecentList();
        List<Integer> ids = new ArrayList<>();
        List<HotSchedule> nCollect = scheduleServiceRecentList.stream().filter(// 过滤去重
                v -> {
                    boolean flag = !ids.contains(v.getGameId());
                    ids.add(v.getGameId());
                    return flag;
                }
        ).collect(Collectors.toList());
        List<HotSchedule> collect = nCollect.stream().limit(7).collect(Collectors.toList());
        if(collect == null || collect.size() < 7){
            Integer diff = 7 - collect.size();
            for (int i = 0; i < diff; i++) {
                collect.add(new HotSchedule());
            }
        }
        model.addAttribute("categorys", hotCategoryList);
        model.addAttribute("schedules", collect);
        model.addAttribute("gameId", gameId);
        return "schedule/index";
    }


    /**
     * 获取分类赛事列表 DF 2019年1月13日05:11:37
     * @param model
     * @return
     */
    @GetMapping("getCategorySchedule")
    public String getCategorySchedule(final Model model, Integer categoryId){
        List<HotSchedule> scheduleServiceRecentList = scheduleService.getRecentList();
        List<Integer> ids = new ArrayList<>();
        List<HotSchedule> nCollect = scheduleServiceRecentList.stream().filter(// 过滤去重
                v -> {
                    boolean flag = !ids.contains(v.getGameId());
                    ids.add(v.getGameId());
                    return flag;
                }
        ).collect(Collectors.toList());
        List<HotSchedule> collect = nCollect.stream().filter(item -> item.getCategoryId() != null && item.getCategoryId().equals(categoryId)).collect(Collectors.toList());
        model.addAttribute("list", collect);
        return "schedule/categorySchedule";
    }


    /**
     * 查询赛程列表 DF 2019年1月13日06:04:53
     * @param type
     * @param gameId
     * @param date
     * @param model
     * @return
     */
    @GetMapping("getScheduleList")
    public String getScheduleList(Integer type, @RequestParam(required = false)  Integer gameId, String date, final Model model){
        if(type == null) type = 1;
        if(type != null && type.equals(0)) type = null;
        String yesterDay = (Integer.valueOf(DateUtil.getCurrentDay()) - 1) + "";
        if(yesterDay.length() < 2) yesterDay = "0" + yesterDay;
        String toDay = (Integer.valueOf(DateUtil.getCurrentDay())) + "";
        if(toDay.length() < 2) toDay = "0" + toDay;
        String tomorrow = (Integer.valueOf(DateUtil.getCurrentDay()) + 1) + "";
        if(tomorrow.length() < 2) tomorrow = "0" + tomorrow;
        String dateTime = DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" ;
        if(type != null){
            if(type.equals(-1)){
                dateTime += yesterDay;
            }else if(type.equals(1)){
                dateTime += toDay;
            }else if(type.equals(2)) {
                dateTime += tomorrow;
            }else{
                dateTime += toDay;
            }
            date = dateTime;
        }

        List<HotSchedule> hotScheduleList = scheduleService.getScheduleList(type, gameId, date);
        model.addAttribute("hotScheduleList", hotScheduleList);
        return "schedule/list";
    }

}
