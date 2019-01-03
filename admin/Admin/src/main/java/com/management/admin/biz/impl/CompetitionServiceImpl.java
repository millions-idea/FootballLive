package com.management.admin.biz.impl;

import com.management.admin.biz.ICompetitionService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.resp.NMOdds;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.CompetitionMapper;
import com.management.admin.repository.ScheduleMapper;
import com.management.admin.repository.TeamMapper;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.IdWorker;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.http.NamiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName ICompetitionServiceImpl
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/18 2:01
 * Version 1.0
 **/

/**
 * 赛事管理接口实现类 DF 2018年12月18日02:49:10
 */
@Service
@Transactional
public class CompetitionServiceImpl implements  ICompetitionService {

    private final CompetitionMapper competitionMapper;
    private final ScheduleMapper scheduleMapper;
    private final TeamMapper teamMapper;

    @Autowired
    public CompetitionServiceImpl(CompetitionMapper competitionMapper, ScheduleMapper scheduleMapper, TeamMapper teamMapper){
        this.competitionMapper=competitionMapper;
        this.scheduleMapper = scheduleMapper;
        this.teamMapper = teamMapper;
    }

    /**
     * 查询ALL赛事 DF 2018-12-18 14:43:462
     * @return
     */
    @Override
    public List<Game> getGames() {
        List<Game> games=competitionMapper.selectGames();
        if(games!=null){
            return games;
        }
        return null;
    }

    /**
     * 查询直播分类 DF 2018-12-18 14:43:462
     * @return
     */
    @Override
    public List<LiveCategory> selectLiveCategory() {
        List<LiveCategory> liveCategories=competitionMapper.selectLiveCategory();
        if(liveCategories!=null){
            return liveCategories;
        }
        return null ;
    }

    /**
     * 添加赛事信息 DF 2018-12-18 14:43:462
     * @return
     */
    @Override
    public boolean addCompetition(Game game) {
        int result=competitionMapper.addCompetition(game);
        if(result>0) {
            return true;
        }
        return false;
    }

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    @Override
    public Integer getCompetitionLimitCount() {
        return competitionMapper.selectLimitCount();
    }

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<GameCategory> getCompetitionLimit(Integer page, String limit) {
        page = ConditionUtil.extractPageIndex(page, limit);
        List<GameCategory> list = competitionMapper.selectLimit(page, limit);
        return list;
    }

    /**
     * 删除赛事信息 提莫 2018年12月18日19:33:30
     * @return
     */
    @Override
    public boolean deleteCompetition(Integer gameId) {
        Integer  result=competitionMapper.deleteCompetition(gameId);
        if(result>0){
            return true;
        }
        return false;
    }

    /**
     * 同步云端赛事数据 DF 2019年1月1日18:41:42
     *
     * @param categoryId
     * @return
     */
    @Override
    @Transactional
    public boolean syncCloudData(Integer categoryId) {
        //拉取远程接口返回的数据
        String response = NamiUtil.get("sports/football/odds/list", null);
        NMOdds model = JsonUtil.getModel(response, NMOdds.class);
        if(model == null) throw new InfoException("同步接口数据失败");

        //批量同步本地数据库
        List<Game> gameList = new ArrayList<>();
        model.getEvents().forEach((k,v) -> {
            try {
                Integer id = IdWorker.getFlowIdWorkerInstance().nextInt32(8);
                Game game = new Game();
                game.setGameId(id);
                game.setCloudId(v.getId());
                game.setGameName(v.getShort_name_zh());
                game.setGameIcon("https://cdn.leisu.com/eventlogo/" + v.getLogo());
                game.setCategoryId(categoryId);
                game.setIsDelete(0);
                gameList.add(game);
            } catch (Exception e) {
            }
        });
        competitionMapper.inserUpdatetList(gameList);

        List<Team> teamList = new ArrayList<>();
        model.getTeams().forEach((k,v) -> {
            try {
                Integer id = IdWorker.getFlowIdWorkerInstance().nextInt32(8);
                Team team = new Team();
                team.setTeamId(id);
                team.setTeamName(v.getName_zh());
                team.setTeamIcon("https://cdn.leisu.com/teamflag_s/" + v.getLogo());
                team.setCloudId(v.getId());
                Optional<Integer> first = gameList.stream().filter(item -> item.getCloudId().equals(v.getMatchevent_id()))
                        .map(item -> item.getGameId())
                        .findFirst();
                if (first.isPresent()){
                    team.setGameId(first.get());
                }else{
                    team.setGameId(-1);
                }
                teamList.add(team);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if(teamList.size() > 0){
            teamMapper.inserUpdatetList(teamList);
        }

        List<Schedule> schedules = new ArrayList<>();
        List<List<String>> matches = model.getMatches();

        matches.stream().forEach(matche -> {
            Integer namiScheduleId = Integer.valueOf(matche.get(0));
            Integer scheduleId = new Integer(matche.get(1));
            Integer status = Integer.valueOf(matche.get(2));
            if(status.equals(1)){
                //未开始
                status = 0;
            }else if(status.intValue() >= 2 && status.intValue() <= 7){
                //进行中
                status = 1;
            }else if(status.equals(8)){
                //已结束
                status = 2;
            }else if(status.intValue() >= 9 && status.intValue() <= 13){
                //延迟
                status = 3;
            }else{
                status = 4;
            }

            Date scheduleDate = DateUtil.timeStamp2Date(matche.get(3),null);
            Date beginDate = DateUtil.timeStamp2Date(matche.get(4),null);
            String[] masterInlineArray = matche.get(5).split(",");
            Integer masterTeamId = new Integer(masterInlineArray[0].replace("[",""));
            Integer masterGrade = Integer.valueOf(masterInlineArray[2]);
            Integer masterRedChess = Integer.valueOf(masterInlineArray[4]);
            Integer masterYellowChess = Integer.valueOf(masterInlineArray[5]);
            Integer masterCornerKick = Integer.valueOf(masterInlineArray[6]);

            String[] targetInlineArray = matche.get(6).split(",");
            Integer targetTeamId = new Integer(targetInlineArray[0].replace("[",""));
            Integer targetGrade = Integer.valueOf(targetInlineArray[2]);
            Integer targetRedChess = Integer.valueOf(targetInlineArray[4]);
            Integer targetYellowChess = Integer.valueOf(targetInlineArray[5]);
            Integer targetCornerKick = Integer.valueOf(targetInlineArray[6]);

            Optional<Team> masterTeam = teamList.stream().filter(item -> item.getCloudId().equals(masterTeamId)).findFirst();
            Integer localMasterTeamId = 0;
            if(masterTeam.isPresent()){
                localMasterTeamId = masterTeam.get().getTeamId();
            }else{
                localMasterTeamId = -1;
            }

            Optional<Team> targetTeam = teamList.stream().filter(item -> item.getCloudId().equals(targetTeamId)).findFirst();
            Integer localTargetTeamId = 0;
            if(targetTeam.isPresent()){
                localTargetTeamId = targetTeam.get().getTeamId();
            }else {
                localTargetTeamId = -1;
            }

            Schedule schedule = new Schedule();
            schedule.setTeamId(localMasterTeamId + "," + localTargetTeamId);
            schedule.setMasterTeamId(localMasterTeamId);
            schedule.setTargetTeamId(localTargetTeamId);
            schedule.setStatus(status);
            schedule.setScheduleGrade(masterGrade + "-" + targetGrade);
            schedule.setCloudId(scheduleId);
            schedule.setGameDate(scheduleDate);
            schedule.setGameDuration("90");
            schedule.setIsDelete(0);
            schedule.setIsHot(0);
            schedule.setGameId(0);
            schedule.setEditDate(new Date());
            schedule.setMasterRedChess(masterRedChess);
            schedule.setMasterYellowChess(masterYellowChess);
            schedule.setMasterCornerKick(masterCornerKick);
            schedule.setTargetRedChess(targetRedChess);
            schedule.setTargetYellowChess(targetYellowChess);
            schedule.setTargetCornerKick(targetCornerKick);
            schedule.setNamiScheduleId(namiScheduleId);

            Optional<Integer> first = gameList.stream().filter(item -> item.getCloudId().equals(scheduleId))
                    .map(item -> item.getGameId())
                    .findFirst();
            if(first.isPresent() && localMasterTeamId >0 && localTargetTeamId > 0){
                schedule.setGameId(first.get());
            }else{
                schedule.setGameId(-1);
            }
            schedules.add(schedule);
        });
        if (schedules.size() > 0){
            scheduleMapper.inserUpdatetScheduleList(schedules);
        }
        //除非接口调用失败, 否则永远返回成功
        return true;
    }
}