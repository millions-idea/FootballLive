/***
 * @pName Live
 * @name UserController
 * @user HongWei
 * @date 2019/1/23
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.biz.IGameService;
import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.db.PublishMessage;
import com.management.admin.entity.resp.HotCategory;
import com.management.admin.entity.resp.HotSchedule;
import com.management.admin.entity.template.SessionModel;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 登录弹窗 DF 2019年1月23日00:21:44
     * @return
     */
    @GetMapping("loginPop")
    public String loginPop(){
        return "user/login";
    }

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IGameService gameService;


    @GetMapping("mark")
    public String mark(@RequestParam(required = false) Integer gameId, final Model model){
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
        return "user/mark";
    }

    /**
     * 查询赛程列表 DF 2019年1月13日06:04:53
     * @param model
     * @return
     */
    @GetMapping("getMarkScheduleList")
    public String getMarkScheduleList(final Model model, final HttpServletRequest request){
        SessionModel session = SessionUtil.getSession(request);
        if(session != null){
            List<HotSchedule> hotScheduleList = scheduleService.getMarkScheduleList(session.getUserId());
            model.addAttribute("hotScheduleList", hotScheduleList);
        }else{
            model.addAttribute("hotScheduleList", null);
        }
        return "user/mark_list";
    }


    @GetMapping("history")
    public String history(@RequestParam(required = false) Integer gameId, final Model model){
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
        return "user/history";
    }

    /**
     * 查询赛程列表 DF 2019年1月13日06:04:53
     * @param model
     * @return
     */
    @GetMapping("getHistoryScheduleList")
    public String getHistoryScheduleList(final Model model, final HttpServletRequest request){
        SessionModel session = SessionUtil.getSession(request);
        if(session != null){
            List<HotSchedule> hotScheduleList = scheduleService.getHistoryScheduleList(session.getUserId());
            model.addAttribute("hotScheduleList", hotScheduleList);
        }else{
            model.addAttribute("hotScheduleList", null);
        }
        return "user/history_list";
    }

    @GetMapping("message")
    public String message(final Model model, final HttpServletRequest request){
        SessionModel session = SessionUtil.getSession(request);
        if(session != null){
            List<PublishMessage> publishMessages = scheduleService.getMessageList(session.getUserId());
            model.addAttribute("publishMessages", publishMessages);
        }else{
            model.addAttribute("publishMessages", null);
        }
        return "user/message";
    }

}
