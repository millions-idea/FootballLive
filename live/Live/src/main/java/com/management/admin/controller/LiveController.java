/***
 * @pName Live
 * @name LiveController
 * @user HongWei
 * @date 2019/1/18
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.resp.HotSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/live")
public class LiveController {
    @Autowired
    private IScheduleService scheduleService;

    @GetMapping(value = {"", "/index"})
    public String index(Integer liveId, final Model model, final RedirectAttributes redirectAttributes){
        if(liveId == null) {
            redirectAttributes.addAttribute("errorText", "您要查找的直播间不存在");
            return "redirect:/errorPage/index";
        }
        HotSchedule live = scheduleService.getLive(liveId);
        if(live == null) {
            redirectAttributes.addAttribute("errorText", "您要查找的直播间不存在");
            return "redirect:/errorPage/index";
        }

        if(live.getContent() == null || live.getContent().isEmpty()) live.setContent("暂无情报内容");
        model.addAttribute("live", live);
        return "live/index";
    }

    /**
     * 直播间成绩 DF 2019年1月19日10:16:32
     * @param liveId
     * @param model
     * @return
     */
    @GetMapping("/grade")
    public String grade(Integer liveId, final Model model){
        if(liveId == null) {
            HotSchedule live = new HotSchedule();
            live.setScheduleGrade("0-0");
            live.setMasterCornerKick(0);
            live.setTargetCornerKick(0);
            live.setMasterRedChess(0);
            live.setTargetRedChess(0);
            live.setMasterYellowChess(0);
            live.setTargetYellowChess(0);
            live.setGameDuration(0);
            model.addAttribute("live", live);
            return "live/grade";
        }
        HotSchedule live = scheduleService.getLive(liveId);
        if(live == null) {
            live = new HotSchedule();
            live.setScheduleGrade("0-0");
            live.setMasterCornerKick(0);
            live.setTargetCornerKick(0);
            live.setMasterRedChess(0);
            live.setTargetRedChess(0);
            live.setMasterYellowChess(0);
            live.setTargetYellowChess(0);
            live.setGameDuration(0);
            model.addAttribute("live", live);
            return "live/grade";
        }
        model.addAttribute("live", live);
        return "live/grade";
    }
}
