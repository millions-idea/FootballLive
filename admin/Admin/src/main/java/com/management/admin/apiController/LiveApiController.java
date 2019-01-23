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
import com.management.admin.entity.template.SessionModel;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.PropertyUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Date;
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
     * @param gameId            赛事id 选填
     * @param liveCategoryId    直播分类id 选填
     * @param date              日期时间 2018-12-20 yyyy:MM:dd 选填
     * @return
     */
    @GetMapping("getLiveGameDetailList")
    public JsonArrayResult<ScheduleGame> getLiveGameDetailList(@RequestParam(required = false) Integer gameId,
                                                               @RequestParam(required = false) Integer liveCategoryId,
                                                               @RequestParam(required = false) String date){
        // 获取赛事信息列表
        List<LiveScheduleDetail> list1 = scheduleService.getScheduleDetailList(gameId, liveCategoryId, date);

        if (list1 == null || list1.size() == 0) throw new InfoException("空的集合");

        // 如果当前是凌晨时间段，将前一天的数据也查询出来并且合并显示
        if(DateUtil.getCurrentDay() != "01" && (Integer.valueOf(DateUtil.getCurrentHour()) <= 6)){

            date = DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + "0"+ (Integer.valueOf(DateUtil.getCurrentDay()) - 1);
            List<LiveScheduleDetail> list = scheduleService.getScheduleDetailList(gameId, liveCategoryId, date);
            List<LiveScheduleDetail> list2 = new ArrayList<>();
            List<LiveScheduleDetail> finalList = list1;
            list.stream().forEach(item -> {
                boolean isExist = finalList.stream().anyMatch(nItem -> nItem.getGameId().equals(item.getGameId()) &&
                        nItem.getMasterTeamId().equals(item.getMasterTeamId())
                        &&
                        nItem.getTargetTeamId().equals(item.getTargetTeamId()));
                if(!isExist && item.getStatus().equals(1)){
                    list2.add(item);
                }
            });
            list2.addAll(list1);
            list1 = list2;
        }


        // 获取团队详细信息
        String teamIdList = String.join(",", list1.stream().map(item -> item.getTeamId()).collect(Collectors.toList()));
        if(teamIdList == null || teamIdList.isEmpty()) throw new InfoException("获取团队关系失败");
        List<Team> teams = teamService.getTeams(teamIdList);

        // 封装返回信息
        List<ScheduleGame> scheduleGames = new ArrayList<>();
        list1.stream().forEach(item -> {
            ScheduleGame scheduleGame = new ScheduleGame();
            scheduleGame.setLiveId(item.getLiveId());
            scheduleGame.setLiveTitle(item.getLiveTitle());
            if (item.getLiveDate() != null){
                scheduleGame.setLiveDate(DateUtil.getFormatDateTime(item.getLiveDate()));
            }
            scheduleGame.setSourceUrl(item.getSourceUrl());
            scheduleGame.setCategoryId(item.getCategoryId());

            scheduleGame.setGameId(item.getGameId());
            scheduleGame.setGameName(item.getGameName());
            scheduleGame.setStatus(item.getStatus());

            Team team = new Team();
            PropertyUtil.clone(teams.get(0), team);

            Team targetTeam = new Team();
            PropertyUtil.clone(teams.get(1), targetTeam);

            scheduleGame.setTeam(team);
            scheduleGame.setTargetTeam(targetTeam);

            scheduleGame.setMasterTeamId(item.getMasterTeamId());
            scheduleGame.setMasterTeamName(item.getMasterTeamName());
            scheduleGame.setMasterTeamIcon(item.getMasterTeamIcon());

            scheduleGame.setTargetTeamId(item.getTargetTeamId());
            scheduleGame.setTargetTeamName(item.getTargetTeamName());
            scheduleGame.setTargetTeamIcon(item.getTargetTeamIcon());

            scheduleGame.setScheduleGrade(item.getScheduleGrade());
            scheduleGame.setScheduleResult(item.getScheduleResult());
            scheduleGame.setWinTeamId(item.getWinTeamId());
            if(item.getWinTeamId() == null){
                String[] split = item.getScheduleGrade().split("-");
                Integer masterGrade = new Integer(split[0]);
                Integer targetGrade = new Integer(split[1]);
                if(masterGrade > targetGrade){
                    scheduleGame.setWinTeamId(item.getMasterTeamId());
                }
                if(masterGrade < targetGrade){
                    scheduleGame.setWinTeamId(item.getTargetTeamId());
                }
            }
            scheduleGame.setMasterRedChess(item.getMasterRedChess());
            scheduleGame.setMasterYellowChess(item.getMasterYellowChess());
            scheduleGame.setMasterCornerKick(item.getMasterCornerKick());

            scheduleGame.setTargetRedChess(item.getTargetRedChess());
            scheduleGame.setTargetYellowChess(item.getTargetYellowChess());
            scheduleGame.setTargetCornerKick(item.getTargetCornerKick());

            scheduleGame.setCloudId(item.getCloudId());
            scheduleGame.setGameDate(item.getGameDate());

            scheduleGame.setSourceUrl(item.getSourceUrl());

            //计算开场分钟数
            if(scheduleGame.getStatus().equals(1)){
                long diffMinute = DateUtil.getDiffMinute(item.getGameDate());
                scheduleGame.setGameTime(diffMinute + "");
            }

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
    public JsonResult<LiveInfo> getLiveInfo(HttpServletRequest req, Integer liveId){
        SessionModel session = SessionUtil.getSession(req);
        //获取直播间信息
        LiveInfo liveInfo = liveService.getLiveDetailInfo(liveId);
        if(liveInfo == null) return new JsonResult().failingAsString("加载直播间失败");
        //根据时间判断比赛是否已经开始,如果已经开始将直播状态改为 正在直播
        /*Date beginDate = DateUtil.getDate(liveInfo.getGameDate(), "yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        if(currentDate.compareTo(beginDate) > 0){
            liveService.setBeginLive(liveId);
        }*/

        if(session != null){
            //加入群组
            liveInfo.setChatRoomErrorMsg(liveService.addGroup(session.getPhone(), session.getUserId(), liveId));
            //添加观看历史
            liveService.addHistory(session.getUserId(), liveId);
        }
        //查询球队信息
        List<Team> teamList = teamService.getTeams(liveInfo.getTeamId());
        if(teamList == null || teamList.size() == 0) return new JsonResult<>().failingAsString("加载球队数据失败");
        liveInfo.setTeamList(teamList);
        //获取全站广告信息
        List<Dictionary> advertisingList = dictionaryService.getWebAppAdvertising();
        if(advertisingList == null || advertisingList.size() != 2) return new JsonResult().failingAsString("加载直播间信息失败");
        liveInfo.setAdUrl(advertisingList.get(0).getValue());
        liveInfo.setAdTargetUrl(advertisingList.get(1).getValue());
        return new JsonResult().successful(liveInfo);
    }

    /**
     * 加入聊天室 DF 2018年12月29日14:26:03
     * @param req
     * @param liveId
     * @return
     */
    @GetMapping("joinRoom")
    public JsonResult joinRoom(HttpServletRequest req, Integer liveId){
        SessionModel session = SessionUtil.getSession(req);
        //加入群组
        liveService.addGroup(session.getPhone(), session.getUserId(), liveId);
        return JsonResult.successful();
    }

    /**
     * 获取直播间情报信息 DF 2018年12月18日20:51:40
     * @param liveId
     * @return
     */
    @GetMapping("getInformation")
    public JsonResult<LiveInformation> getInformation(Integer liveId, Integer gameId){
        LiveInformation liveInformation = new LiveInformation();
        //查询情报信息
        Information information = informationService.getLiveInformation(liveId, gameId);
        liveInformation.setInformation(information);
        return new JsonResult<>().successful(liveInformation);
    }

    /**
     * 添加收藏 DF 2018年12月19日03:58:21
     * @param liveId
     * @return
     */
    @PostMapping("addCollect")
    public JsonResult addCollect(HttpServletRequest req, Integer liveId){
        SessionModel session = SessionUtil.getSession(req);
        Boolean result = liveService.addCollect(liveId, session.getUserId());
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 取消收藏 DF 2018年12月19日03:58:21
     * @param liveId
     * @return
     */
    @PostMapping("cancelCollect")
    public JsonResult cancelCollect(HttpServletRequest req, Integer liveId){
        SessionModel session = SessionUtil.getSession(req);
        Boolean result = liveService.cancelCollect(liveId, session.getUserId());
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 获取情报信息列表 DF 2018年12月18日02:20:52
     * @param gameId            赛事id 选填
     * @param liveCategoryId    直播分类id 选填
     * @param date              日期时间 2018-12-20 yyyy:MM:dd 选填
     * @return
     */
    @GetMapping("getInformationDetailList")
    public JsonArrayResult<ScheduleGame> getInformationDetailList(@RequestParam(required = false) Integer gameId,
                                                               @RequestParam(required = false) Integer liveCategoryId,
                                                               @RequestParam(required = false) String date){
        // 获取赛事信息列表
        List<LiveScheduleDetail> scheduleDetailList = scheduleService.getInformationDetailList(gameId, liveCategoryId, date);
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
            scheduleGame.setCategoryId(item.getCategoryId());
            scheduleGame.setScheduleResult(item.getForecastResult());
            scheduleGame.setScheduleGrade(item.getForecastGrade());
            scheduleGame.setWinTeamIcon(item.getWinTeamIcon());
            scheduleGame.setWinTeamName(item.getWinTeamName());
            scheduleGame.setWinTeamId(item.getForecastTeamId());

            scheduleGame.setGameId(item.getGameId());
            scheduleGame.setGameName(item.getGameName());
            scheduleGame.setStatus(item.getStatus());

            Team team = new Team();
            PropertyUtil.clone(teams.get(0), team);

            Team targetTeam = new Team();
            PropertyUtil.clone(teams.get(1), targetTeam);

            scheduleGame.setTeam(team);
            scheduleGame.setTargetTeam(targetTeam);


            scheduleGame.setMasterTeamId(item.getMasterTeamId());
            scheduleGame.setMasterTeamName(item.getMasterTeamName());
            scheduleGame.setMasterTeamIcon(item.getMasterTeamIcon());

            scheduleGame.setTargetTeamId(item.getTargetTeamId());
            scheduleGame.setTargetTeamIcon(item.getTargetTeamIcon());
            scheduleGame.setTargetTeamName(item.getTargetTeamName());

            scheduleGame.setForecastGrade(item.getForecastGrade());
            scheduleGame.setForecastResult(item.getForecastResult());
            scheduleGame.setForecastTeamId(item.getForecastTeamId());

        scheduleGames.add(scheduleGame);
        });
        return new JsonArrayResult<ScheduleGame>(scheduleGames);
    }

    /**
     * 退出群组 DF 2018年12月24日19:22:29
     * @param req
     * @param liveId
     * @return
     */
    @GetMapping("leaveTeam")
    public JsonResult leaveTeam(HttpServletRequest req, Integer liveId){
        SessionModel session = SessionUtil.getSession(req);
        String result = liveService.leaveGroup(session.getUserId(), session.getPhone(), liveId);
        if(result != null && result.equals("SUCCESS")) return JsonResult.successful();
        return new JsonResult().failingAsString(result);
    }


     /**
     * 更改直播间状态配置直播源(自动开通直播间) DF 2019年1月4日14:19:24
     * @param scheduleId
     * @param sourceUrl
     * @return
     */
    @GetMapping("openLive")
    public JsonResult openLive(Integer scheduleId, String sourceUrl){
        boolean result = scheduleService.openLive(scheduleId, sourceUrl);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    @GetMapping("exitLive")
    public JsonResult exitLive(Integer liveId){
        boolean result = liveService.deleteLive(liveId) > 0;
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 删除所有已结束状态但还有直播间的数据 DF 2019年1月4日21:25:24
     * @return
     */
    @GetMapping("cleanLives")
    public JsonResult cleanLives(){
        boolean result = scheduleService.cleanLives();
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

}
