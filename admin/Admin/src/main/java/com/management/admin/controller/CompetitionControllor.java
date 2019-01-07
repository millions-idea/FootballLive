package com.management.admin.controller;

/**
 * @ClassName CompetitionControllor
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/18 2:09
 * Version 1.0
 **/

import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.ICompetitionService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.StringUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName  CompetitionControllor
 * @Description 赛事管理
 * @Author ZXL01
 * @Date 2018/12/17 10:47
 * Version 1.0
 **/
@Controller
@RequestMapping("/competition")
public class CompetitionControllor {
    @Autowired
    private ICompetitionService competitionService;

    /**
     * 跳转到赛事管理界面
     * @return
     */
    @GetMapping("/index")
    public String index(){

        return "competition/index";
    }
    /**
     * 赛事列表 提莫 2018年8月29日11:42:31
     * @return
     */
    @GetMapping("/getCompetitionLimit")
    @ResponseBody
    @WebLog(section = "Competition",content = "查看赛事列表")
    public JsonArrayResult<GameCategory> getMemberLimit(Integer page, String limit){
        Integer count = 0;
        List<GameCategory> list = competitionService.getCompetitionLimit(page,limit);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        count = competitionService.getCompetitionLimitCount();
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    /**
     * 直播分类 提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("add")
    public String addCompetition(final Model model){
        List<LiveCategory> liveCategories=competitionService.selectLiveCategory();
        model.addAttribute("categorys",liveCategories);
        return "competition/add";
    }

    /**
     * 添加赛事信息 提莫 2018年12月18日15:06:31
     * @return
     */
    @GetMapping("addCompetition")
    @ResponseBody
    @WebLog(section = "Competition", content = "添加赛事信息")
    public JsonResult addCompetition(Game  game){
         System.out.println(game);
         boolean  result=competitionService.addCompetition(game);
         if(result){
             return  JsonResult.successful();
         }
         return  JsonResult.failing();
    }

    /**
     * 删除赛事信息 提莫 2018年12月18日15:06:31
     * @return
     */
    @GetMapping("deleteGame")
    @ResponseBody
    @WebLog(section = "Competition", content = "访问用户管理")
    public JsonResult deleteCompetition(Integer gameId){
        boolean  result=competitionService.deleteCompetition(gameId);
        if(result){
            return  JsonResult.successful();
        }
        return  JsonResult.failing();
    }

    @GetMapping("syncData")
    public String syncData(final Model model){
        List<LiveCategory> liveCategories=competitionService.selectLiveCategory();
        model.addAttribute("categorys",liveCategories);
        return "competition/syncData";
    }


    @GetMapping("syncCloudData")
    @ResponseBody
    public JsonResult syncCloudData(Integer categoryId, @RequestParam(required = false) Integer isCurr){
        if(categoryId == null) throw new InfoException("请选择要同步的分类");
        boolean result = competitionService.syncCloudData(categoryId, isCurr);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }
}
