package com.management.admin.controller;

/**
 * @ClassName CompetitionControllor
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/18 2:09
 * Version 1.0
 **/

import com.management.admin.biz.ICompetitionService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.utils.StringUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public JsonArrayResult<GameCategory> getMemberLimit(Integer page, String limit){
        Integer count = 0;
        List<GameCategory> list = competitionService.getCompetitionLimit(page,limit);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        count = competitionService.getCompetitionLimitCount();
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

}
