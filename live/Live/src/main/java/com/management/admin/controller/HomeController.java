/***
 * @pName Live
 * @name HomeController
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.biz.IGameService;
import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.resp.HotGame;
import com.management.admin.entity.resp.HotInformation;
import com.management.admin.entity.resp.HotSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IGameService gameService;

    @GetMapping(value = {"","/","/index"})
    public String index(final Model model){
        //热门赛程
        List<HotSchedule> collect = getHotSchedule();
        //今天赛事
        List<HotGame> toDayList = gameService.getToDayList();
        //热门情报
        List<HotInformation> topHotInformationList = scheduleService.getTopInformationList();
        if(topHotInformationList.size() < 4){
            Integer diff = 4 - topHotInformationList.size();
            for (int i = 0; i < diff; i++) {
                HotInformation hotInformation = new HotInformation();
                topHotInformationList.add(hotInformation);
            }
        }
        model.addAttribute("topList", collect);
        model.addAttribute("toDayList", toDayList);
        model.addAttribute("topInformationList", topHotInformationList);
        return "home/index";
    }

    @GetMapping("/getHotSchedule")
    public String getHotSchedule(final Model model){
        List<HotSchedule> collect = getHotSchedule();
        model.addAttribute("topList", collect);
        return "home/hotSchedule";
    }

    /**
     * 获取热门赛程 DF 2019年1月11日19:42:54
     * @return
     */
    private List<HotSchedule> getHotSchedule(){
        List<HotSchedule> topList = scheduleService.getTopList();
        List<HotSchedule> collect = topList.stream().limit(5).collect(Collectors.toList());
        if(collect.size() < 5){
            Integer diff = 5 - collect.size();
            for (int i = 0; i < diff; i++) {
                collect.add(new HotSchedule());
            }
        }
        return collect;
    }

    /**
     * 网站头 DF 2019年1月12日16:15:13
     * @param index
     * @param model
     * @return
     */
    @GetMapping("header")
    public String header(Integer index, final Model model){
        model.addAttribute("index", index);
        return "/schedule/index";
    }
}
