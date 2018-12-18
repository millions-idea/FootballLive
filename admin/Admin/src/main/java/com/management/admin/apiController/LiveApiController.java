/***
 * @pName Admin
 * @name LiveApiController
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.*;
import com.management.admin.entity.db.Dictionary;
import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.resp.HotGame;
import com.management.admin.entity.resp.LiveInfo;
import com.management.admin.entity.resp.LiveInformation;
import com.management.admin.entity.resp.ScheduleGame;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/live")
public class LiveApiController {
    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private ITeamService teamService;

    @Autowired
    private ILiveService liveService;

    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private IInformationService informationService;

    /**
     * 获取赛程信息列表 DF 2018年12月18日02:20:52
     * @param gameId
     * @return
     */
    @GetMapping("getLiveGameDetailList")
    public JsonArrayResult<ScheduleGame> getLiveGameDetailList(@RequestParam(required = false) Integer gameId){
        // 获取赛事信息列表
        List<LiveScheduleDetail> scheduleDetailList = scheduleService.getScheduleDetailList(gameId);
        if (scheduleDetailList == null || scheduleDetailList.size() == 0) throw new InfoException("空的集合");

        // 获取团队详细信息
        String teamIdList = String.join(",", scheduleDetailList.stream().map(item -> item.getTeamId()).collect(Collectors.toList()));
        if(teamIdList == null || teamIdList.isEmpty()) throw new InfoException("获取团队关系失败");
        List<Team> teams = teamService.getTeams(teamIdList);

        // 封装返回信息
        List<ScheduleGame> scheduleGames = new ArrayList<>();
        scheduleDetailList.stream().forEach(item -> {
            ScheduleGame scheduleGame = new ScheduleGame();
            scheduleGame.setLiveId(item.getLiveId());
            scheduleGame.setLiveTitle(item.getLiveTitle());
            scheduleGame.setLiveDate(DateUtil.getFormatDateTime(item.getLiveDate()));
            scheduleGame.setSourceUrl(item.getSourceUrl());

            scheduleGame.setGameId(item.getGameId());
            scheduleGame.setGameName(item.getGameName());
            scheduleGame.setStatus(item.getStatus());

            Team team = new Team();
            PropertyUtil.clone(teams.get(0), team);

            Team targetTeam = new Team();
            PropertyUtil.clone(teams.get(1), targetTeam);

            scheduleGame.setTeam(team);
            scheduleGame.setTargetTeam(targetTeam);

            scheduleGames.add(scheduleGame);
        });
        return new JsonArrayResult<ScheduleGame>(scheduleGames);
    }


    /**
     * 获取直播间详情信息 DF 2018年12月18日14:34:16
     * @param liveId
     * @return
     */
    @GetMapping("getLiveInfo")
    public JsonResult<LiveInfo> getLiveInfo(Integer liveId){
        //获取直播间信息
        LiveInfo liveInfo = liveService.getLiveDetailInfo(liveId);
        if(liveInfo == null) return new JsonResult().failingAsString("加载直播间失败");
        //查询球队信息
        List<Team> teamList = teamService.getTeams(liveInfo.getTeamId());
        if(teamList == null || teamList.size() == 0) return new JsonResult<>().failingAsString("加载球队数据失败");
        liveInfo.setTeamList(teamList);

        //获取聊天室信息

        //获取全站广告信息
        List<Dictionary> advertisingList = dictionaryService.getWebAppAdvertising();
        if(advertisingList == null || advertisingList.size() != 2) return new JsonResult().failingAsString("加载直播间信息失败");
        liveInfo.setAdUrl(advertisingList.get(0).getValue());
        liveInfo.setAdTargetUrl(advertisingList.get(1).getValue());
        return new JsonResult().successful(liveInfo);
    }

    /**
     * 获取直播间情报信息 DF 2018年12月18日20:51:40
     * @param liveId
     * @return
     */
    @GetMapping("getInformation")
    public JsonResult<LiveInformation> getInformation(Integer liveId){
        LiveInformation liveInformation = new LiveInformation();

        //查询情报信息
        Information information = informationService.getLiveInformation(liveId);
        liveInformation.setInformation(information);

        return new JsonResult<>().successful(liveInformation);
    }
}
