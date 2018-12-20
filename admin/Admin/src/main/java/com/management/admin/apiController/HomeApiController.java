/***
 * @pName Admin
 * @name HomeApiController
 * @user HongWei
 * @date 2018/12/13
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.IDictionaryService;
import com.management.admin.biz.ILiveCategoryService;
import com.management.admin.biz.ILiveService;
import com.management.admin.biz.ITeamService;
import com.management.admin.entity.db.Dictionary;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.LiveHotDetail;
import com.management.admin.entity.resp.DictionaryInfo;
import com.management.admin.entity.resp.HotGame;
import com.management.admin.entity.resp.VersionInfo;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/home")
public class HomeApiController {
    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private ILiveService liveService;

    @Autowired
    private ITeamService teamService;

    @Autowired
    private ILiveCategoryService liveCategoryService;

    /**
     * 查询首页板块聚合信息 DF 2018年12月13日17:02:55
     * @return
     */
    @GetMapping("getGroupInfo")
    public JsonArrayResult<DictionaryInfo> getHomeGroupInfo(){
        //1.轮播广告
        //2.走马灯广告
        //3.分类板块信息
        List<DictionaryInfo> maps = dictionaryService.getHomeGroupInfo();
        if(maps != null && maps.size() > 0) return new JsonArrayResult<DictionaryInfo>(maps);
        return JsonArrayResult.failing();
    }

    /**
     * 获取热门赛事列表 DF 2018年12月17日23:22:11
     * @return
     */
    @GetMapping("getHotGameList")
    public JsonArrayResult<HotGame> getHotGameList(){
        // 获取热门赛事信息
        List<LiveHotDetail> liveHotDetailList = liveService.getHotLives();
        if(liveHotDetailList == null || liveHotDetailList.size() == 0) throw new InfoException("获取热门赛事失败");

        // 获取团队详细信息
        String teamIdList = String.join(",", liveHotDetailList.stream().map(item -> item.getTeamId()).collect(Collectors.toList()));
        if(teamIdList == null || teamIdList.isEmpty()) throw new InfoException("获取团队关系失败");
        List<Team> teams = teamService.getTeams(teamIdList);

        // 封装返回信息
        List<HotGame> hotGames = new ArrayList<>();
        liveHotDetailList.stream().forEach(item -> {
            HotGame hotGame = new HotGame();
            hotGame.setLiveId(item.getLiveId());
            hotGame.setLiveTitle(item.getLiveTitle());
            hotGame.setLiveDate(DateUtil.getFormatDateTime(item.getLiveDate()));

            hotGame.setGameId(item.getGameId());
            hotGame.setGameName(item.getGameName());

            Team team = new Team();
            PropertyUtil.clone(teams.get(0), team);

            Team targetTeam = new Team();
            PropertyUtil.clone(teams.get(1), targetTeam);

            hotGame.setTeam(team);
            hotGame.setTargetTeam(targetTeam);

            hotGames.add(hotGame);
        });
        return new JsonArrayResult<HotGame>(hotGames);
    }

    /**
     * 获取直播分类信息列表 DF 2018年12月18日01:45:44
     * @return
     */
    @GetMapping("getLiveCategoryList")
    public JsonArrayResult<LiveCategory> getLiveCategoryList(){
        List<LiveCategory> liveCategoryList = liveCategoryService.getLiveCategorys();
        return new JsonArrayResult<LiveCategory>(liveCategoryList);
    }

    /**
     * 获取版本号 DF 2018年12月20日06:25:35
     * @return
     */
    @GetMapping("getVersion")
    public JsonResult getVersion(){
        VersionInfo versionInfo = dictionaryService.getVersion();
        versionInfo.setUpdate(versionInfo.getVersion().contains("U") ? 1 : 0);
        versionInfo.setVersion(versionInfo.getVersion().replace("U","").trim());
        return new JsonResult().successful(versionInfo);
    }
}
