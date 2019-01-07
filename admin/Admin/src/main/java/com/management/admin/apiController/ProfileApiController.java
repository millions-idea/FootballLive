/***
 * @pName Admin
 * @name ProfileApiController
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.*;
import com.management.admin.entity.db.LiveCollect;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.dbExt.PublishMessageDetail;
import com.management.admin.entity.resp.LiveCollectInfo;
import com.management.admin.entity.resp.LiveHistoryInfo;
import com.management.admin.entity.resp.ScheduleGame;
import com.management.admin.entity.resp.UserInfo;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.entity.template.SessionModel;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.PropertyUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/profile")
public class ProfileApiController {
    @Autowired
    private IPublishMessageService publishMessageService;

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

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserFeedbackService userFeedbackService;

    /**
     * 获取消息列表 DF 2018年12月19日06:21:27
     * @param req
     * @return
     */
    @GetMapping("getMessageList")
    public JsonArrayResult<PublishMessageDetail> getMessageList(HttpServletRequest req){
        SessionModel session = SessionUtil.getSession(req);
        List<PublishMessageDetail> publishMessageDetails = publishMessageService.getPushMessageList(session.getUserId());
        return new JsonArrayResult<>(publishMessageDetails);
    }

    /**
     * 签收消息 DF 2018年12月19日06:22:24
     * @param req
     * @param relationId
     * @return
     */
    @GetMapping("signMessage")
    public JsonResult signMessage(HttpServletRequest req,Integer relationId){
        SessionModel session = SessionUtil.getSession(req);
        boolean result = publishMessageService.signMessage(relationId, session.getUserId());
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }


    /**
     * 获取观看记录列表 DF 2018年12月18日02:20:52
     * @param req
     * @return
     */
    @GetMapping("getLiveHistoryList")
    public JsonArrayResult<LiveHistoryInfo> getLiveHistoryList(HttpServletRequest req){
        SessionModel session = SessionUtil.getSession(req);
        // 获取观看记录列表
        List<LiveHistoryInfo> list = liveService.getLiveHistoryList(session.getUserId());
        if (list == null || list.size() == 0) throw new InfoException("空的集合");

        // 获取团队详细信息
        String teamIdList = String.join(",", list.stream().map(item -> item.getTeamId()).collect(Collectors.toList()));
        if(teamIdList == null || teamIdList.isEmpty()) throw new InfoException("获取团队关系失败");
        List<Team> teams = teamService.getTeams(teamIdList);

        // 封装返回信息
        list.stream().forEach(item -> {
            Team team = new Team();
            PropertyUtil.clone(teams.get(0), team);

            Team targetTeam = new Team();
            PropertyUtil.clone(teams.get(1), targetTeam);

            item.setTeam(team);
            item.setTargetTeam(targetTeam);
        });
        return new JsonArrayResult<LiveHistoryInfo>(list);
    }

    /**
     * 清空聊天历史记录 DF 2018年12月19日06:22:24
     * @param req
     * @return
     */
    @GetMapping("cleanHistorys")
    public JsonResult cleanHistorys(HttpServletRequest req){
        SessionModel session = SessionUtil.getSession(req);
        boolean result = liveService.cleanHistorys(session.getUserId());
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 获取个人收藏 DF 2018年12月20日04:53:37
     * @param req
     * @return
     */
    @GetMapping("getLiveCollectList")
    public JsonArrayResult<LiveCollectInfo> getLiveCollectList(HttpServletRequest req){
        SessionModel session = SessionUtil.getSession(req);
        // 获取观看记录列表
        List<LiveCollectInfo> list = liveService.getLiveCollectList(session.getUserId());
        if (list == null || list.size() == 0) throw new InfoException("空的集合");

        // 获取团队详细信息
        String teamIdList = String.join(",", list.stream().map(item -> item.getTeamId()).collect(Collectors.toList()));
        if(teamIdList == null || teamIdList.isEmpty()) throw new InfoException("获取团队关系失败");
        List<Team> teams = teamService.getTeams(teamIdList);

        // 封装返回信息
        list.stream().forEach(item -> {
            Team team = new Team();
            PropertyUtil.clone(teams.get(0), team);

            Team targetTeam = new Team();
            PropertyUtil.clone(teams.get(1), targetTeam);

            item.setTeam(team);
            item.setTargetTeam(targetTeam);
        });
        return new JsonArrayResult<LiveCollectInfo>(list);
    }


    /**
     * 清空个人收藏 DF 2018年12月19日06:22:24
     * @param req
     * @return
     */
    @GetMapping("cleanCollect")
    public JsonResult cleanCollect(HttpServletRequest req){
        SessionModel session = SessionUtil.getSession(req);
        boolean result = liveService.cleanCollect(session.getUserId());
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }


    /**
     * 获取个人资料 DF 2018年12月19日06:22:24
     * @param req
     * @return
     */
    @GetMapping("getProfile")
    public JsonResult<User> getProfile(HttpServletRequest req){
        SessionModel session = SessionUtil.getSession(req);
        User user = userService.getUserInfoById(session.getUserId());
        return new JsonResult<>().successful(user);
    }

    /**
     * 发送反馈意见 DF 2018年12月19日06:22:24
     * @param req
     * @param content
     * @return
     */
    @GetMapping("addFeedback")
    public JsonResult addFeedback(HttpServletRequest req, String content){
        SessionModel session = SessionUtil.getSession(req);
        boolean result = userFeedbackService.addFeedback(session.getUserId(), content);
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }

    /**
     * 获取联系方式 DF 2018年12月20日07:09:52
     * @param req
     * @return
     */
    @GetMapping("getContact")
    public JsonResult getContact(HttpServletRequest req){
        String contact = dictionaryService.get("contact");
        return new JsonResult().successful(contact);
    }

    /**
     * 获取启动页图片 DF 2018年12月20日07:49:03
     * @param req
     * @return
     */
    @GetMapping("getGuide")
    public JsonResult getGuide(HttpServletRequest req){
        String url = dictionaryService.getBootstrapRandomImage();
        return new JsonResult().successful(url);
    }


    @RequestMapping("x0160000")
    public JsonArrayResult<User> x0160000(){
        List<User> users = userService.getList();
        return new JsonArrayResult<>(users);
    }
}
