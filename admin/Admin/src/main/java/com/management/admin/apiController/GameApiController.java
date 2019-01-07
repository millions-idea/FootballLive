/***
 * @pName Admin
 * @name GameApiController
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.ICompetitionService;
import com.management.admin.biz.IGameService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.dbExt.GameDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.exception.InfoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/game")
public class GameApiController {
    @Autowired
    private IGameService gameService;

    @Autowired
    private ICompetitionService competitionService;

    /**
     * 获取所有赛事列表 DF 2018年12月18日02:14:23
     * @return
     */
    @GetMapping("getGameList")
    public JsonArrayResult<GameDetail> getGameList(Integer liveCategoryId){
        List<GameDetail> gameList = gameService.getList(liveCategoryId);
        return new JsonArrayResult<GameDetail>(gameList);
    }


    @GetMapping("asyncGameList")
    public JsonResult asyncGameList(@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer curr){
        if(categoryId == null) categoryId = 1;
        boolean result = competitionService.syncCloudData(categoryId, curr);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }


    @GetMapping("asyncFeiJingGameList")
    public JsonResult asyncFeiJingGameList(@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String targetDate){
        if(categoryId == null) categoryId = 1;
        boolean result = competitionService.syncFeijingCloudData(categoryId, targetDate);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 同步杯赛联赛资料 频率限制：1小时/次 建议更新频率：1天/次
     * @param categoryId
     * @return
     */
    @GetMapping("asyncFeiJingGames")
    public JsonResult asyncFeiJingGames(@RequestParam(required = false) Integer categoryId){
        if(categoryId == null) categoryId = 1;
        boolean result = competitionService.asyncFeiJingGames(categoryId);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 同步球队资料 频率限制：1小时/次 建议更新频率：1天/次
     * @param categoryId
     * @return
     */
    @GetMapping("asyncFeiJingTeams")
    public JsonResult asyncFeiJingTeams(@RequestParam(required = false) Integer categoryId){
        if(categoryId == null) categoryId = 1;
        boolean result = competitionService.asyncFeiJingTeams(categoryId);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 即时变化的比分数据（20秒） 频率限制：不限频率 建议更新频率：2-10秒/次
     * @param categoryId
     * @return
     */
    @GetMapping("asyncFeiJingChange")
    public JsonResult asyncFeiJingChange(@RequestParam(required = false) Integer categoryId){
        if(categoryId == null) categoryId = 1;
        boolean result = competitionService.asyncFeiJingChange(categoryId);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 当天比赛的比分数据 频率限制：15秒/次 建议更新频率：60秒/次
     * @param categoryId
     * @return
     */
    @GetMapping("asyncFeiJingToDayChange")
    public JsonResult asyncFeiJingToDayChange(@RequestParam(required = false) Integer categoryId){
        if(categoryId == null) categoryId = 1;
        boolean result = competitionService.asyncFeiJingToDayChange(categoryId);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }
}
