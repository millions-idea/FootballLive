package com.management.admin.controller;

/**
 * @ClassName TeamControllor
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/18 19:48
 * Version 1.0
 **/

import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.ICompetitionService;
import com.management.admin.biz.ITeamService;
import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.dbExt.TeamCompetition;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
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
 * @ClassName TeamControllor
 * @Description 球队管理
 * @Author ZXL01
 * @Date 2018/12/17 10:47
 * Version 1.0
 **/
@Controller
@RequestMapping("management/team")
public class TeamControllor {
    @Autowired
    private ITeamService teamService;
    @Autowired
    private ICompetitionService competitionService;


    /**
     * 跳转到球队管理界面
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "team/index";
    }

    /**
     * 球队列表 提莫 2018年8月29日11:42:31
     * @return
     */
    @GetMapping("/getTeamLimit")
    @ResponseBody
    @WebLog(section = "Team",content = "查看球队列表")
    public JsonArrayResult<TeamCompetition> getMemberLimit(Integer page, String limit){
        Integer count = 0;
        List<TeamCompetition> list = teamService.getTeamLimit(page,limit);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        count = teamService .getTeamLimitCount();
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    /**
     * 查询球队信息 提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("add")
    public String addCompetition(final Model model){
        List<Game> games=competitionService.getGames();
        model.addAttribute("games",games);
        return "team/add";
    }

    /**
     * 添加赛事信息 提莫 2018年12月18日15:06:31
     * @return
     */
    @GetMapping("addTeam")
    @ResponseBody
    @WebLog(section = "Team",content = "添加赛程")
    public JsonResult addCompetition(Team team){
        boolean  result=teamService.addTeam(team) ;
        if(result){
            return  JsonResult.successful();
        }
        return  JsonResult.failing();
    }

    /**
     * 删除赛事信息 提莫 2018年12月18日15:06:31
     * @return
     */
    @GetMapping("deleteTeam")
    @ResponseBody
    @WebLog(section = "Team",content = "删除赛程信息")
    public JsonResult deleteCompetition(Integer teamId){
        boolean  result=teamService.deleteTeam(teamId);
        if(result){
            return  JsonResult.successful();
        }
        return  JsonResult.failing();
    }

}
