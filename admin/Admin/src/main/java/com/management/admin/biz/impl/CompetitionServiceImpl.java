package com.management.admin.biz.impl;

import com.management.admin.biz.ICompetitionService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.db.Schedule;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.resp.*;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.CompetitionMapper;
import com.management.admin.repository.GameMapper;
import com.management.admin.repository.ScheduleMapper;
import com.management.admin.repository.TeamMapper;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.*;
import com.management.admin.utils.http.NamiUtil;
import com.utility.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;

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
    private final Logger logger = LoggerFactory.getLogger(CompetitionServiceImpl.class);
    private final CompetitionMapper competitionMapper;
    private final ScheduleMapper scheduleMapper;
    private final TeamMapper teamMapper;
    private final GameMapper gameMapper;

    @Autowired
    public CompetitionServiceImpl(CompetitionMapper competitionMapper, ScheduleMapper scheduleMapper, TeamMapper teamMapper, GameMapper gameMapper){
        this.competitionMapper=competitionMapper;
        this.scheduleMapper = scheduleMapper;
        this.teamMapper = teamMapper;
        this.gameMapper = gameMapper;
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
    public boolean syncCloudData(Integer categoryId, Integer isCurr) {
        //拉取远程接口返回的数据
        String day = (Integer.valueOf(DateUtil.getCurrentDay()) + 1) + "";
        if(isCurr != null && isCurr.equals(1)){
            day = (Integer.valueOf(DateUtil.getCurrentDay())) + "";
        }
        if(day.length() < 2) day = "0" + day;
        String date = DateUtil.getCurrentYear() + DateUtil.getCurrentMonth() + day;
        String response = NamiUtil.get("sports/football/odds/list", "date=" + date);
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
                team.setEditDate(new Date());
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
            teamMapper.inserUpdatetTeamList(teamList);
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
            if(status.intValue() == 2){
                //选出胜利方
                if(masterGrade > targetGrade){
                    schedule.setWinTeamId(masterTeamId);
                }else if(targetGrade > masterGrade){
                    schedule.setWinTeamId(targetTeamId);
                }
            }
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

            //计算开场分钟数
            if(schedule.getStatus().equals(1)){
                long diffMinute = DateUtil.getDiffMinute(scheduleDate);
                schedule.setGameTime(diffMinute + "");
            }

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

    /**
     * 同步飞鲸体育数据 DF 2019年1月5日16:19:51
     *
     * @param categoryId
     * @return
     */
    @Override
    @Transactional
    public boolean syncFeijingCloudData(Integer categoryId, String targetDate) {
        //批量插入赛程
        //日期，当没有参数时，返回当日足球比赛资料 string：yyyy-MM-dd 非必填
        String day = Integer.valueOf(Integer.valueOf(DateUtil.getCurrentDay()) + 1) + "";
        if(day.length() < 2){
            day = "0" + day;
        }
        if(targetDate != null){
            day = targetDate;
        }
        String date = DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + day;
        String response = HttpUtil.get("http://interface.win007.com/zq/BF_XML.aspx?date=" + date, null);
        if(response != null){
            response = response.replace("<?xml  version=\"1.0\" encoding=\"utf-8\"?>", "");
            String jsonString = XmlUtil.documentToJSONObject(response).toJSONString();
            System.out.println(jsonString);
            FSchedules fSchedules = JsonUtil.getModel(jsonString, FSchedules.class);
            if(fSchedules == null) throw new InfoException("序列化失败");
            List<Game> gameList = new ArrayList<>();
            List<Team> teamList = new ArrayList<>();
            List<Schedule> scheduleList = new ArrayList<>();
            fSchedules.getList().forEach(item -> {
                item.getMatch().forEach(nItem -> {
                    //获取联赛信息
                    String scheduleInfo = nItem.getC();
                    String scheduleName = scheduleInfo.split(",")[0];
                    String gameScheduleId = scheduleInfo.split(",")[3];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date scheduleDate = null;//比赛时间
                    try {
                        nItem.setD(nItem.getD().replace("/", "-"));
                        scheduleDate = sdf.parse(nItem.getD());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //获取主队球队信息
                    String masterTeamInfo = nItem.getH();
                    String masterTeamName = masterTeamInfo.split(",")[0];
                    String masterTeamId = masterTeamInfo.split(",")[3];
                    String masterTeamGrade = nItem.getJ();
                    String masterRedChess = nItem.getN();

                    //获取客队球队信息
                    String targetTeamInfo = nItem.getI();
                    String targetTeamName = targetTeamInfo.split(",")[0];
                    String targetTeamId = targetTeamInfo.split(",")[3];
                    String targetTeamGrade = nItem.getK();
                    String targetRedChess = nItem.getO();


                    String yellowCount = nItem.getYellow();//0-0
                    //获取比赛说明2 <explain2>;|2;|;|;;;;</explain2>
                    String explain2 = nItem.getExplain2();
                    String masterCornerKick = "0";
                    String targetCornerKick = "0";
                    if(explain2 != null && explain2.length() > 0){
                        String cornerKick = explain2.split("|")[2];
                        if(!cornerKick.contains(";") || cornerKick.equals(";")){
                            masterCornerKick = "0";
                            targetCornerKick = "0";
                        }else{
                            masterCornerKick = cornerKick.split(";")[0];
                            targetCornerKick = cornerKick.split(";")[1];
                        }
                    }


                    //【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10:取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
                    Integer cloudStatus = new Integer(nItem.getF());
                    Integer localStatus = 0;

                    //正在直播
                    if(cloudStatus.intValue() >= 1 && cloudStatus.intValue() <= 5){
                        localStatus = 1;
                    }

                    //已结束
                    else if(cloudStatus.intValue() == -1 || cloudStatus.intValue() == -10 || cloudStatus.intValue() == -13){
                        localStatus = 2;
                    }

                    //延迟
                    else if(cloudStatus.intValue() == -14){
                        localStatus = 3;
                    }
                    else if(cloudStatus.intValue() == 0){
                        localStatus = 0;
                    }
                    //待定或者其他
                    else {
                        localStatus = 4;
                    }


                    //创建或更新赛事
                    Game game = new Game();
                    game.setGameId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                    game.setCloudId(Integer.valueOf(gameScheduleId));
                    game.setGameName(scheduleName);
                    game.setGameIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                    game.setCategoryId(categoryId);
                    game.setIsDelete(0);
                    gameList.add(game);

                    //创建或更新球队
                    Team masterTeam = new Team();
                    masterTeam.setTeamId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                    masterTeam.setTeamName(masterTeamName);
                    masterTeam.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                    masterTeam.setCloudId(Integer.valueOf(masterTeamId));
                    masterTeam.setGameId(game.getCloudId());
                    teamList.add(masterTeam);

                    Team targetTeam = new Team();
                    targetTeam.setTeamId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                    targetTeam.setTeamName(targetTeamName);
                    targetTeam.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                    targetTeam.setCloudId(Integer.valueOf(targetTeamId));
                    targetTeam.setGameId(game.getCloudId());
                    teamList.add(targetTeam);

                    Schedule schedule = new Schedule();
                    schedule.setTeamId(masterTeam.getTeamId() + targetTeam.getTeamId() + "");//传入云id,动态查询本地id X
                    schedule.setMasterTeamId(masterTeam.getTeamId());//传入云id,动态查询本地id X
                    schedule.setTargetTeamId(targetTeam.getTeamId());//传入云id,动态查询本地id X
                    schedule.setStatus(localStatus);
                    schedule.setScheduleGrade(masterTeamGrade + "-" + targetTeamGrade);
                    schedule.setCloudId(Integer.valueOf(nItem.getA()));
                    schedule.setGameDate(scheduleDate);
                    schedule.setGameDuration("90");
                    schedule.setIsDelete(0);
                    schedule.setIsHot(0);
                    schedule.setGameId(game.getCloudId());//传入云id, 动态查询数据 X
                    schedule.setEditDate(new Date());
                    schedule.setMasterRedChess(Integer.valueOf(masterRedChess));
                    if(yellowCount.contains("-")){
                        schedule.setMasterYellowChess(Integer.valueOf(yellowCount.split("-")[0]));
                        schedule.setTargetYellowChess(Integer.valueOf(yellowCount.split("-")[1]));
                    }else{
                        schedule.setMasterYellowChess(0);
                        schedule.setTargetYellowChess(0);
                    }

                    schedule.setMasterCornerKick(Integer.valueOf(masterCornerKick));
                    schedule.setTargetRedChess(Integer.valueOf(targetRedChess));
                    schedule.setTargetCornerKick(Integer.valueOf(targetCornerKick));
                    schedule.setNamiScheduleId(Integer.valueOf(gameScheduleId));
                    schedule.setScheduleResult("");
                    schedule.setCategoryId(categoryId);

                    //计算开场分钟数
                    if(schedule.getStatus().equals(1)){
                        long diffMinute = DateUtil.getDiffMinute(scheduleDate);
                        schedule.setGameTime(diffMinute + "");
                    }else{
                        schedule.setGameTime("-1");
                    }

                    scheduleList.add(schedule);

                });
            });

            //新增或更新联赛
            insertOrUpdateGame(gameList, gameList.size() > 0);

            //新增或更新球队
            insertOrUpdateTeam(teamList, teamList.size() > 0);

            //新增或更新赛程
            insertOrUpdateSchedule(scheduleList);
        }


        //主动关闭已结束的比赛
        doScheduleGC();


        return true;
    }

    @Override
    @Transactional
    public boolean asyncFeiJingGames(Integer categoryId) {
        //批量插入赛事
        String response = HttpUtil.get("http://interface.win007.com/zq/League_XML.aspx", null);
        if(response != null){
            response = response.replace("<?xml  version=\"1.0\" encoding=\"utf-8\"?>", "");
            String jsonString = XmlUtil.documentToJSONObject(response).toJSONString();
            FGames fGames = JsonUtil.getModel(jsonString, FGames.class);
            if(fGames != null){
                List<Game> gameList = new ArrayList<>();
                fGames.getList().forEach(item -> {
                    item.getMatch().forEach(nItem -> {
                        try {
                            Integer id = IdWorker.getFlowIdWorkerInstance().nextInt32(8);
                            Game game = new Game();
                            game.setGameId(id);
                            game.setCloudId(Integer.valueOf(nItem.getId()));
                            game.setGameName(nItem.getGb_short());
                            if(nItem.getLogo() == null || nItem.getLogo().length() == 0 || nItem.getLogo().length() == 27){
                                game.setGameIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                            }else{
                                game.setGameIcon(nItem.getLogo() + "?win007=sell");
                            }
                            game.setCategoryId(categoryId);
                            game.setIsDelete(0);
                            gameList.add(game);
                        } catch (Exception e) {
                        }
                    });
                });
                if(gameList.size() > 0){
                    //分页插入数据库, 每次100条
                    PagingList<Game> teamPagingList = new PagingList<>(gameList, 1000);
                    while (teamPagingList.hasNext()) {
                        List<Game> games = teamPagingList.next();
                        competitionMapper.inserUpdatetList(games);
                    }
                }

            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean asyncFeiJingTeams(Integer categoryId) {
        //批量插入球队
        String response = HttpUtil.get("http://interface.win007.com/zq/Team_XML.aspx", null);
        if(response != null){
            response = response.replace("<?xml  version=\"1.0\" encoding=\"utf-8\"?>", "");
            logger.info("**************************************************************************");
            logger.info(response);
            logger.info("**************************************************************************");
            String jsonString = XmlUtil.documentToJSONObject(response).toJSONString();
            FTeams fTeams = JsonUtil.getModel(jsonString, FTeams.class);
            List<Team> teamList = new ArrayList<>();
            fTeams.getList().forEach(item -> {
                item.getI().forEach(nItem -> {
                    try {
                        Integer id = IdWorker.getFlowIdWorkerInstance().nextInt32(8);
                        Team team = new Team();
                        team.setTeamId(id);
                        team.setTeamName(nItem.getG());
                        if( nItem.getFlag() == null ||  nItem.getFlag().length() == 0){
                            team.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                        }else{
                            team.setTeamIcon("http://zq.win007.com/Image/team/" + nItem.getFlag() + "?win007=sell");
                        }
                        team.setCloudId(Integer.valueOf(nItem.getId()));
                        //传入云id, 数据库动态查询
                        team.setGameId(Integer.valueOf(nItem.getLsID()));
                        teamList.add(team);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            if(teamList.size() > 0){
                //分页插入数据库, 每次1000条
                PagingList<Team> teamPagingList = new PagingList<>(teamList, 1000);
                while (teamPagingList.hasNext()) {
                    List<Team> teams = teamPagingList.next();
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("INSERT INTO tb_teams(team_id, team_name,team_icon,game_id,is_delete,edit_date,cloud_id) ");
                    buffer.append(" VALUES");
                    teams.forEach(team -> {
                        buffer.append("(" + SQLUtil.extractTeamValues(team) + "),");
                    });
                    String execSql = buffer.toString();
                    execSql = execSql.substring(0, execSql.length() - 1);
                    execSql += " ON DUPLICATE KEY UPDATE ";
                    execSql += " game_id=VALUES(game_id),";
                    execSql += " team_icon=VALUES(team_icon),";
                    execSql += " edit_date=NOW()";
                    teamMapper.insertOrUpdateList(execSql);
                }
            }
        }
        return true;
    }

    @Override
    public boolean asyncFeiJingChange(Integer categoryId) {
        //20秒 150秒
        String response = HttpUtil.get("http://interface.win007.com/zq/change2.xml", null);
        if(response != null){
            response = response.replace("<?xml  version=\"1.0\" encoding=\"gb2312\"?>", "");
            String jsonString = XmlUtil.documentToJSONObject(response).toJSONString();
            FChange fChange = JsonUtil.getModel(jsonString, FChange.class);
            List<Schedule> scheduleList = new ArrayList<>();
            fChange.getC().forEach(item -> {
                /*【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
                【比赛时间】小时:分数，例：20:30
                【开场时间】上半场为上半场开场时间,下半场为开下半场开场时间。js日期时间格式，月份从0开始编号。
                【赛程说明】若需用到，最好是用正则表达式将 <a>...</a>这些内容过滤
                【是否有阵容】1:有，0/空:无
                【比赛日期】月-日
                【比赛说明2】例：CCTV5 上海体育 北京体育;|2;|4;3|90,2-1;3-3;1,2-1;1-3;2
                                含义如下：
                【CCTV5 上海体育 北京体育】事件中的电视台信息。
                【2；】先开球 (1：主，2：客);
                【4;3】角球数 主num;客num
                【90,2-1】90分钟 主得分-客得分
                【3-3】两回合 主得分-客得分
                【1,2-1】1：（1: 120分钟/含常规时间；2: 加时/不含常规时间；3: 加时中/含常规时间），2-1：主得分-客得分
                【1-3】点球 主进球数-客进球数
                【2】胜出（1：主，2：客）*/
                String info = item.getH();
                String[] arr = info.split("\\^");
                String scheduleId = arr[0];
                //【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
                String status = arr[1];
                //【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10:取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
                Integer cloudStatus = new Integer(status);
                Integer localStatus = 0;

                //正在直播
                if(cloudStatus.intValue() >= 1 && cloudStatus.intValue() <= 5){
                    localStatus = 1;
                }

                //已结束
                else if(cloudStatus.intValue() == -1 || cloudStatus.intValue() == -10 || cloudStatus.intValue() == -13){
                    localStatus = 2;
                }

                //延迟
                else if(cloudStatus.intValue() == -14){
                    localStatus = 3;
                }
                else if(cloudStatus.intValue() == 0){
                    localStatus = 0;
                }
                else {
                    localStatus = 4;
                }
                String masterTeamGrade = arr[2];
                String targetTeamGrade = arr[3];
                String masterUpHalfGrade = arr[4];
                String targetUpHalfGrade = arr[5];
                String masterTeamRedChess = arr[6];
                String targetTeamRedChess = arr[7];
                //【比赛时间】小时:分数，例：20:30
                String gameTime = arr[8];
                //【开场时间】上半场为上半场开场时间,下半场为开下半场开场时间。js日期时间格式，月份从0开始编号。
                String beginDate = arr[9];
                //【赛程说明】若需用到，最好是用正则表达式将 <a>...</a>这些内容过滤
                String scheduleDesc = arr[10];
                //【是否有阵容】1:有，0/空:无
                String isBattle = arr[11];
                //【比赛日期】月-日
                String masterTeamYellow = arr[12];
                String targetTeamYellow = arr[13];
                String gameDate = arr[14];
                /*【比赛说明2】例：CCTV5 上海体育 北京体育;|2;|4;3|90,2-1;3-3;1,2-1;1-3;2
                                含义如下：
                【CCTV5 上海体育 北京体育】事件中的电视台信息。
                【2；】先开球 (1：主，2：客);
                【4;3】角球数 主num;客num
                【90,2-1】90分钟 主得分-客得分
                【3-3】两回合 主得分-客得分
                【1,2-1】1：（1: 120分钟/含常规时间；2: 加时/不含常规时间；3: 加时中/含常规时间），2-1：主得分-客得分
                【1-3】点球 主进球数-客进球数
                【2】胜出（1：主，2：客）*/
                String gameDesc = arr[15];
                String masterTeamCornerKick = arr[16];
                String targetTeamCornerKick = arr[17];
                String winTeamId = null;


                Schedule schedule = new Schedule();
                schedule.setCloudId(Integer.valueOf(scheduleId));
                schedule.setStatus(localStatus);
                schedule.setMasterCornerKick(Integer.valueOf(masterTeamCornerKick));
                schedule.setTargetCornerKick(Integer.valueOf(targetTeamCornerKick));
                schedule.setMasterYellowChess(Integer.valueOf(masterTeamYellow));
                schedule.setTargetYellowChess(Integer.valueOf(targetTeamYellow));
                schedule.setMasterYellowChess(Integer.valueOf(masterTeamRedChess));
                schedule.setTargetRedChess(Integer.valueOf(targetTeamRedChess));
                schedule.setScheduleGrade(masterTeamGrade + "-" + targetTeamGrade);
                scheduleList.add(schedule);
            });

            if(scheduleList.size() > 0){
                StringBuffer buffer = new StringBuffer();
                scheduleList.forEach(schedule -> {
                    buffer.append("UPDATE tb_schedules SET ");
                    buffer.append("status=" + schedule.getStatus() + ",");
                    buffer.append("master_red_chess=" + schedule.getMasterRedChess() + ",");
                    buffer.append("master_yellow_chess=" + schedule.getMasterYellowChess() + ",");
                    buffer.append("master_corner_kick=" + schedule.getMasterCornerKick() + ",");
                    buffer.append("target_red_chess=" + schedule.getTargetRedChess() + ",");
                    buffer.append("target_yellow_chess=" + schedule.getTargetYellowChess() + ",");
                    buffer.append("target_corner_kick=" + schedule.getTargetCornerKick() + ",");
                    buffer.append("schedule_grade='" + schedule.getScheduleGrade() + "' ");
                    buffer.append("WHERE cloud_id=" + schedule.getCloudId() + ";");
                });
                String execSql = buffer.toString();
                execSql = execSql.substring(0, execSql.length() - 1);
                scheduleMapper.execUpdate(execSql);
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean asyncFeiJingToDayChange(Integer categoryId) {
        String response = HttpUtil.get("http://interface.win007.com/zq/today.aspx", null);
        if(response != null){
            response = response.replace("<?xml  version=\"1.0\" encoding=\"utf-8\"?>", "");
            String jsonString = XmlUtil.documentToJSONObject(response).toJSONString();
            System.out.println(jsonString);
            FToDay fToDay = JsonUtil.getModel(jsonString, FToDay.class);
            if(fToDay != null){
                List<Team> teamList = new ArrayList<>();
                List<Game> gameList = new ArrayList<>();
                List<Schedule> scheduleList = new ArrayList<>();
                AtomicReference<Integer> itemCount = new AtomicReference<>(0);
                AtomicReference<Integer> nItemCount = new AtomicReference<>(0);
                fToDay.getList().forEach(item -> {
                    itemCount.getAndSet(itemCount.get() + 1);
                    item.getMatch().forEach(nItem -> {
                        nItemCount.getAndSet(nItemCount.get() + 1);
                        //获取联赛信息
                        String gameScheduleId =nItem.getLeagueID();
                        String gameScheduleName = nItem.getLeague().split(",")[0];
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date scheduleDate = null;//比赛时间
                        try {
                            nItem.setTime(nItem.getTime().replace("/", "-"));
                            scheduleDate = sdf.parse(nItem.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //获取主队球队信息
                        String masterTeamInfo = nItem.getHome();
                        String masterTeamName = masterTeamInfo.split(",")[0];
                        String masterTeamId = masterTeamInfo.split(",")[3];
                        String masterTeamGrade = nItem.getHomeScore();
                        String masterRedChess = nItem.getRed1();

                        //获取客队球队信息
                        String targetTeamInfo = nItem.getAway();
                        String targetTeamName = targetTeamInfo.split(",")[0];
                        String targetTeamId = targetTeamInfo.split(",")[3];
                        String targetTeamGrade = nItem.getAwayScore();
                        String targetRedChess = nItem.getRed2();

                        String masterCornerKick = nItem.getCorner1();
                        String targetCornerKick =  nItem.getCorner2();


                        //【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10:取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
                        Integer cloudStatus = new Integer(nItem.getState());
                        Integer localStatus = 0;

                        //正在直播
                        if(cloudStatus.intValue() >= 1 && cloudStatus.intValue() <= 5){
                            localStatus = 1;
                        }

                        //已结束
                        else if(cloudStatus.intValue() == -1 || cloudStatus.intValue() == -10 || cloudStatus.intValue() == -13){
                            localStatus = 2;
                        }

                        //延迟
                        else if(cloudStatus.intValue() == -14){
                            localStatus = 3;
                        }
                        else if(cloudStatus.intValue() == 0){
                            localStatus = 0;
                        }
                        //待定或者其他
                        else {
                            localStatus = 4;
                        }


                        //创建或更新赛事
                        Integer gameId = IdWorker.getFlowIdWorkerInstance().nextInt32(8);
                        Game game = new Game();
                        game.setGameId(gameId);
                        game.setCloudId(Integer.valueOf(gameScheduleId));
                        game.setGameName(gameScheduleName);
                        game.setGameIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                        game.setCategoryId(categoryId);
                        game.setIsDelete(0);
                        gameList.add(game);

                        //创建或更新球队
                        Team masterTeam = new Team();
                        masterTeam.setTeamId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                        masterTeam.setTeamName(masterTeamName);
                        masterTeam.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                        masterTeam.setCloudId(Integer.valueOf(masterTeamId));
                        masterTeam.setGameId(game.getCloudId());//通过云id查询gameId
                        teamList.add(masterTeam);

                        Team targetTeam = new Team();
                        targetTeam.setTeamId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                        targetTeam.setTeamName(targetTeamName);
                        targetTeam.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                        targetTeam.setCloudId(Integer.valueOf(targetTeamId));
                        targetTeam.setGameId(game.getCloudId());//通过云id查询gameId
                        teamList.add(targetTeam);

                        Schedule schedule = new Schedule();
                        schedule.setTeamId(masterTeam.getTeamId() + "," + targetTeam.getTeamId());
                        schedule.setMasterTeamId(masterTeam.getTeamId());
                        schedule.setTargetTeamId(targetTeam.getTeamId());
                        schedule.setStatus(localStatus);

                        //设置胜利球队
                        if(schedule.getStatus().equals(2)){
                            if(Integer.valueOf(masterTeamGrade) > Integer.valueOf(targetTeamGrade)){
                                schedule.setWinTeamId(Integer.valueOf(masterTeamId));
                            }else if(Integer.valueOf(targetTeamGrade) > Integer.valueOf(masterTeamGrade)){
                                schedule.setWinTeamId(Integer.valueOf(targetTeamId));
                            }
                        }

                        schedule.setScheduleGrade(masterTeamGrade + "-" + targetTeamGrade);
                        schedule.setCloudId(Integer.valueOf(nItem.getID()));
                        schedule.setGameDate(scheduleDate);
                        schedule.setGameDuration("90");
                        schedule.setIsDelete(0);
                        schedule.setIsHot(0);
                        schedule.setGameId(game.getCloudId());//通过云id查询gameId
                        schedule.setEditDate(new Date());
                        schedule.setMasterRedChess(Integer.valueOf(masterRedChess));
                        schedule.setMasterYellowChess(Integer.valueOf(nItem.getYellow1()));
                        schedule.setTargetYellowChess(Integer.valueOf(nItem.getYellow2()));

                        schedule.setMasterCornerKick(Integer.valueOf(masterCornerKick));
                        schedule.setTargetRedChess(Integer.valueOf(targetRedChess));
                        schedule.setTargetCornerKick(Integer.valueOf(targetCornerKick));
                        schedule.setNamiScheduleId(Integer.valueOf(gameScheduleId));
                        schedule.setScheduleResult("");
                        schedule.setCategoryId(categoryId);

                        //计算开场分钟数
                        if(schedule.getStatus().equals(1)){
                            long diffMinute = DateUtil.getDiffMinute(scheduleDate);
                            schedule.setGameTime(diffMinute + "");
                        }else{
                            schedule.setGameTime("-1");
                        }

                        scheduleList.add(schedule);


                    });
                });

                //新增或更新联赛
                insertOrUpdateGame(gameList, gameList.size() > 0);

                //新增或更新球队
                insertOrUpdateTeam(teamList, teamList.size() > 0);

                //新增或更新赛程
                insertOrUpdateSchedule(scheduleList);
            }
        }
        return true;
    }


    private void insertOrUpdateGame(List<Game> gameList, boolean b) {
        if (b) {

            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO tb_games(game_id,game_name,game_icon,category_id,is_delete,is_hot,cloud_id,edit_date) ");
            buffer.append("VALUES");
            gameList.forEach(game -> {
                buffer.append("(" + SQLUtil.extractLocalGameValues(game) + "),");
            });
            String execSql = buffer.toString();
            execSql = execSql.substring(0, execSql.length() - 1);
            execSql += " ON DUPLICATE KEY UPDATE ";
            execSql += " game_id=VALUES(game_id),";
            //execSql += " game_icon=VALUES(game_icon), ";
            execSql += " edit_date=NOW()";
            gameMapper.insertOrUpdateList(execSql);
        }
    }

    private void insertOrUpdateTeam(List<Team> teamList, boolean b) {
        if (b) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO tb_teams(team_id,team_name,team_icon,game_id,is_delete,edit_date,cloud_id) ");
            buffer.append("VALUES");
            teamList.forEach(team -> {
                buffer.append("(" + SQLUtil.extractLocalTeamValues(team) + "),");
            });
            String execSql = buffer.toString();
            execSql = execSql.substring(0, execSql.length() - 1);
            execSql += " ON DUPLICATE KEY UPDATE ";
            execSql += " game_id=VALUES(game_id),";
            execSql += " team_id=VALUES(team_id),";
            //execSql += " team_icon=VALUES(team_icon), ";
            execSql += " edit_date=NOW()";
            teamMapper.insertOrUpdateList(execSql);
        }
    }

    private void insertOrUpdateSchedule(List<Schedule> scheduleList) {
        if(scheduleList.size() > 0){
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO tb_schedules(game_id,team_id,game_date,game_duration,status,is_delete,schedule_result,schedule_grade,win_team_id,master_team_id,target_team_id,is_hot,cloud_id,edit_date,master_red_chess,master_yellow_chess,master_corner_kick,target_red_chess,target_yellow_chess,target_corner_kick,nami_schedule_id,game_time,category_id) ");
            buffer.append("VALUES");
            scheduleList.forEach(schedule -> {
                buffer.append("(" + SQLUtil.extractLocalScheduleValues(schedule) + "),");
            });
            String execSql = buffer.toString();
            execSql = execSql.substring(0, execSql.length() - 1);
            execSql += " ON DUPLICATE KEY UPDATE ";
            execSql += " game_date=VALUES(game_date),";
            execSql += " status=VALUES(status),";
            execSql += " schedule_result=VALUES(schedule_result),";
            execSql += " schedule_grade=VALUES(schedule_grade),";
            execSql += " win_team_id=VALUES(win_team_id),";
            execSql += " team_id=VALUES(team_id),";
            execSql += " game_id=VALUES(game_id),";
            execSql += " master_team_id=VALUES(master_team_id),";
            execSql += " target_team_id=VALUES(target_team_id),";
            execSql += " master_red_chess=VALUES(master_red_chess),";
            execSql += " master_yellow_chess=VALUES(master_yellow_chess),";
            execSql += " master_corner_kick=VALUES(master_corner_kick),";
            execSql += " target_red_chess=VALUES(target_red_chess),";
            execSql += " target_yellow_chess=VALUES(target_yellow_chess),";
            execSql += " target_corner_kick=VALUES(target_corner_kick),";
            execSql += " game_time=VALUES(game_time),";
            execSql += " edit_date=NOW()";
            scheduleMapper.insertOrUpdateList(execSql);
        }
    }

    private void doScheduleGC() {
        try{
            String response;List<Schedule> onWayScheduleList =  scheduleMapper.selectOnWay();
            if(onWayScheduleList != null){
                List<String> idList = onWayScheduleList.stream().map(item -> item.getCloudId() + "").collect(toList());
                response = HttpUtil.get("http://interface.win007.com/zq/BF_XMLByID.aspx?id=" + String.join(",", idList), null);
                if(response != null){
                    response = response.replace("<?xml  version=\"1.0\" encoding=\"utf-8\"?>", "");
                    String jsonString = XmlUtil.documentToJSONObject(response).toJSONString();
                    System.out.println(jsonString);
                    FSchedules fSchedules = JsonUtil.getModel(jsonString, FSchedules.class);
                    if(fSchedules == null) throw new InfoException("序列化失败");
                    List<Game> gameList = new ArrayList<>();
                    List<Team> teamList = new ArrayList<>();
                    List<Schedule> scheduleList = new ArrayList<>();
                    fSchedules.getList().forEach(item -> {
                        item.getMatch().forEach(nItem -> {
                            //获取联赛信息
                            String scheduleInfo = nItem.getC();
                            String scheduleName = scheduleInfo.split(",")[0];
                            String gameScheduleId = scheduleInfo.split(",")[3];
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date scheduleDate = null;//比赛时间
                            try {
                                nItem.setD(nItem.getD().replace("/", "-"));
                                scheduleDate = sdf.parse(nItem.getD());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //获取主队球队信息
                            String masterTeamInfo = nItem.getH();
                            String masterTeamName = masterTeamInfo.split(",")[0];
                            String masterTeamId = masterTeamInfo.split(",")[3];
                            String masterTeamGrade = nItem.getJ();
                            String masterRedChess = nItem.getN();

                            //获取客队球队信息
                            String targetTeamInfo = nItem.getI();
                            String targetTeamName = targetTeamInfo.split(",")[0];
                            String targetTeamId = targetTeamInfo.split(",")[3];
                            String targetTeamGrade = nItem.getK();
                            String targetRedChess = nItem.getO();

                            String yellowCount = nItem.getYellow();//0-0
                            //获取比赛说明2 <explain2>;|2;|;|;;;;</explain2>
                            String explain2 = nItem.getExplain2();
                            String masterCornerKick = "0";
                            String targetCornerKick = "0";
                            if(explain2 != null && explain2.length() > 0){
                                String cornerKick = explain2.split("|")[2];
                                if(!cornerKick.contains(";") || cornerKick.equals(";")){
                                    masterCornerKick = "0";
                                    targetCornerKick = "0";
                                }else{
                                    masterCornerKick = cornerKick.split(";")[0];
                                    targetCornerKick = cornerKick.split(";")[1];
                                }
                            }


                            //【比赛状态】0:未开，1:上半场，2:中场，3:下半场，4:加时，5:点球，-1:完场，-10:取消，-11:待定，-12:腰斩，-13:中断，-14:推迟
                            Integer cloudStatus = new Integer(nItem.getF());
                            Integer localStatus = 0;

                            //正在直播
                            if(cloudStatus.intValue() >= 1 && cloudStatus.intValue() <= 5){
                                localStatus = 1;
                            }

                            //已结束
                            else if(cloudStatus.intValue() == -1 || cloudStatus.intValue() == -10 || cloudStatus.intValue() == -13){
                                localStatus = 2;
                            }

                            //延迟
                            else if(cloudStatus.intValue() == -14 || cloudStatus.intValue() == -11){
                                localStatus = 3;
                            }
                            else if(cloudStatus.intValue() == 0){
                                localStatus = 0;
                            }
                            //待定或者其他
                            else {
                                localStatus = 4;
                            }

                            //创建或更新赛事
                            Game game = new Game();
                            game.setGameId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                            game.setCloudId(Integer.valueOf(gameScheduleId));
                            game.setGameName(scheduleName);
                            game.setGameIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                            game.setCategoryId(1);
                            game.setIsDelete(0);
                            gameList.add(game);

                            //创建或更新球队
                            Team masterTeam = new Team();
                            masterTeam.setTeamId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                            masterTeam.setTeamName(masterTeamName);
                            masterTeam.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                            masterTeam.setCloudId(Integer.valueOf(masterTeamId));
                            masterTeam.setGameId(game.getCloudId());
                            teamList.add(masterTeam);

                            Team targetTeam = new Team();
                            targetTeam.setTeamId(IdWorker.getFlowIdWorkerInstance().nextInt32(8));
                            targetTeam.setTeamName(targetTeamName);
                            targetTeam.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
                            targetTeam.setCloudId(Integer.valueOf(targetTeamId));
                            targetTeam.setGameId(game.getCloudId());
                            teamList.add(targetTeam);

                            Schedule schedule = new Schedule();
                            schedule.setTeamId(null);//传入云id,动态查询本地id
                            schedule.setMasterTeamId(masterTeam.getTeamId());//传入云id,动态查询本地id
                            schedule.setTargetTeamId(targetTeam.getTeamId());//传入云id,动态查询本地id
                            schedule.setStatus(localStatus);

                            schedule.setScheduleGrade(masterTeamGrade + "-" + targetTeamGrade);
                            schedule.setCloudId(Integer.valueOf(nItem.getA()));
                            schedule.setGameDate(scheduleDate);
                            schedule.setGameDuration("90");
                            schedule.setIsDelete(0);
                            schedule.setIsHot(0);
                            schedule.setGameId(game.getCloudId());//传入云id, 动态查询数据
                            schedule.setEditDate(new Date());
                            schedule.setMasterRedChess(Integer.valueOf(masterRedChess));
                            if(yellowCount.contains("-")){
                                schedule.setMasterYellowChess(Integer.valueOf(yellowCount.split("-")[0]));
                                schedule.setTargetYellowChess(Integer.valueOf(yellowCount.split("-")[1]));
                            }else{
                                schedule.setMasterYellowChess(0);
                                schedule.setTargetYellowChess(0);
                            }

                            schedule.setMasterCornerKick(Integer.valueOf(masterCornerKick));
                            schedule.setTargetRedChess(Integer.valueOf(targetRedChess));
                            schedule.setTargetCornerKick(Integer.valueOf(targetCornerKick));
                            schedule.setNamiScheduleId(Integer.valueOf(gameScheduleId));
                            schedule.setScheduleResult("");
                            schedule.setCategoryId(1);
                            //计算开场分钟数
                            if(schedule.getStatus().equals(1)){
                                long diffMinute = DateUtil.getDiffMinute(scheduleDate);
                                schedule.setGameTime(diffMinute + "");
                            }else{
                                schedule.setGameTime("-1");
                            }

                            scheduleList.add(schedule);
                        });
                    });

                    //新增或更新联赛
                    insertOrUpdateGame(gameList, gameList.size() > 0);

                    //新增或更新球队
                    insertOrUpdateTeam(teamList, teamList.size() > 0);

                    //新增或更新赛程
                    insertOrUpdateSchedule(scheduleList);

                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public static class SQLUtil{
        public static String extractTeamValues(Team team){
            StringBuffer buffer = new StringBuffer();
            //team_id
            buffer.append(team.getTeamId() + ",");
            //team_name
            buffer.append("'" + team.getTeamName() + "',");
            //team_icon
            if(team.getTeamIcon() == null || team.getTeamIcon().isEmpty()){
                team.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
            }
            buffer.append("'" +team.getTeamIcon() + "',");
            //game_id
            buffer.append("(SELECT game_id FROM tb_games WHERE cloud_id = " + team.getGameId() + " LIMIT 1),");
            //is_delete
            buffer.append("0,");
            //edit_date
            buffer.append("NOW(),");
            //cloud_id
            buffer.append(team.getCloudId());
            return buffer.toString();
        }

        public static String extractLocalTeamValues(Team team){
            StringBuffer buffer = new StringBuffer();
            //team_id
            buffer.append(team.getTeamId() + ",");
            //team_name
            buffer.append("'" + team.getTeamName() + "',");
            //team_icon
            if(team.getTeamIcon() == null || team.getTeamIcon().isEmpty()){
                team.setTeamIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
            }
            buffer.append("'" +team.getTeamIcon() + "',");
            //game_id
            buffer.append("(SELECT game_id FROM tb_games WHERE cloud_id = "+ team.getGameId()  +" LIMIT 1), ");
            //buffer.append(team.getGameId() + ",");
            //is_delete
            buffer.append("0,");
            //edit_date
            buffer.append("NOW(),");
            //cloud_id
            buffer.append(team.getCloudId());
            return buffer.toString();
        }

        public static String extractLocalGameValues(Game game){
            StringBuffer buffer = new StringBuffer();
            //game_id
            buffer.append(game.getGameId() + ",");
            //game_name
            buffer.append("'" + game.getGameName() + "',");
            //game_icon
            if(game.getGameIcon() == null || game.getGameIcon().isEmpty()){
                game.setGameIcon("http://yabolive.oss-cn-beijing.aliyuncs.com/upload/d39b453b-bc68-472a-8d8f-0da79e880dbf.png");
            }
            buffer.append("'" +game.getGameIcon() + "',");
            //category_id
            buffer.append(game.getCategoryId() + ",");
            //is_delete
            buffer.append("0,");
            //is_hot
            buffer.append("0,");
            //cloud_id
            buffer.append(game.getCloudId() + ",");
            //edit_date
            buffer.append("NOW()");
            return buffer.toString();
        }

        public static String extractScheduleValues(Schedule schedule) {
            StringBuffer buffer = new StringBuffer();
            //game_id
            buffer.append("(SELECT game_id FROM tb_games WHERE cloud_id = " + schedule.getGameId() + " LIMIT 1),");
            //team_id
            buffer.append(" (SELECT CONCAT(");
            buffer.append("(SELECT team_id FROM tb_teams WHERE cloud_id = "+ schedule.getMasterTeamId() +" LIMIT 1), ");
            buffer.append("',',");
            buffer.append("(SELECT team_id FROM tb_teams WHERE cloud_id = "+ schedule.getTargetTeamId() +" LIMIT 1))),");
            //game_date
            buffer.append("'" + DateUtil.getFormatDateTime(schedule.getGameDate()) + "',");
            //game_duration
            buffer.append("" + schedule.getGameDuration() + ",");
            //status
            buffer.append("" + schedule.getStatus() + ",");
            //is_delete
            buffer.append("0,");
            //schedule_result
            buffer.append("'"+ schedule.getScheduleResult() + "',");
            //schedule_grade
            buffer.append("'"+ schedule.getScheduleGrade() +"',");
            //win_team_id
            //设置胜利球队
            if(schedule.getStatus().equals(2)){
                String grades = schedule.getScheduleGrade();
                if(grades != null){
                    String[] split = grades.split("-");
                    if(split.length == 2){
                        if(Integer.valueOf(split[0]) > Integer.valueOf(split[1])){
                            buffer.append("(SELECT team_id FROM tb_teams WHERE cloud_id = "+ schedule.getMasterTeamId() +" LIMIT 1),");
                        }else if(Integer.valueOf(split[1]) > Integer.valueOf(split[0])){
                            buffer.append("(SELECT team_id FROM tb_teams WHERE cloud_id = "+ schedule.getTargetTeamId() +" LIMIT 1),");
                        }else{
                            buffer.append("0,");
                        }
                    }else{
                        buffer.append("0,");
                    }
                }else{
                    buffer.append("0,");
                }
            }else{
                buffer.append("0,");
            }

            //master_team_id
            buffer.append("(SELECT team_id FROM tb_teams WHERE cloud_id = "+ schedule.getMasterTeamId() +" LIMIT 1),");
            //target_team_id
            buffer.append("(SELECT team_id FROM tb_teams WHERE cloud_id = "+ schedule.getTargetTeamId() +" LIMIT 1),");
            //is_hot
            buffer.append("0,");
            //cloud_id
            buffer.append("" + schedule.getCloudId() + ",");
            //edit_date
            buffer.append("'" + DateUtil.getFormatDateTime(schedule.getEditDate()) + "',");
            //master_red_chess
            buffer.append("" + schedule.getMasterRedChess() + ",");
            //master_yellow_chess
            buffer.append("" + schedule.getMasterYellowChess() + ",");
            //master_corner_kick
            buffer.append("" + schedule.getMasterCornerKick() + ",");
            //target_red_chess
            buffer.append("" + schedule.getTargetRedChess() + ",");
            //target_yellow_chess
            buffer.append("" + schedule.getTargetYellowChess() + ",");
            //target_corner_kick
            buffer.append("" + schedule.getTargetCornerKick() + ",");
            //nami_schedule_id
            buffer.append("" + schedule.getNamiScheduleId() + ",");
            //game_time
            buffer.append("'" + schedule.getGameTime() + "'");
            return buffer.toString();
        }

        public static String extractLocalScheduleValues(Schedule schedule) {
            StringBuffer buffer = new StringBuffer();
            //game_id
            buffer.append("(SELECT game_id FROM tb_games WHERE cloud_id = "+ schedule.getGameId()  +" LIMIT 1), ");
            //buffer.append(schedule.getGameId() + ",");
            //team_id
            buffer.append("'" + schedule.getMasterTeamId() + "," + schedule.getTargetTeamId() + "'" + ",");
            //game_date
            buffer.append("'" + DateUtil.getFormatDateTime(schedule.getGameDate()) + "',");
            //game_duration
            buffer.append("" + schedule.getGameDuration() + ",");
            //status
            buffer.append("" + schedule.getStatus() + ",");
            //is_delete
            buffer.append("0,");
            //schedule_result
            buffer.append("'"+ schedule.getScheduleResult() + "',");
            //schedule_grade
            buffer.append("'"+ schedule.getScheduleGrade() +"',");
            //win_team_id
            //设置胜利球队
            if(schedule.getStatus().equals(2)){
                String grades = schedule.getScheduleGrade();
                if(grades != null){
                    String[] split = grades.split("-");
                    if(split.length == 2){
                        if(Integer.valueOf(split[0]) > Integer.valueOf(split[1])){
                            buffer.append(schedule.getMasterTeamId() +",");
                        }else if(Integer.valueOf(split[1]) > Integer.valueOf(split[0])){
                            buffer.append(schedule.getTargetTeamId() +",");
                        }else{
                            buffer.append("0,");
                        }
                    }else{
                        buffer.append("0,");
                    }
                }else{
                    buffer.append("0,");
                }
            }else{
                buffer.append("0,");
            }

            //master_team_id
            buffer.append(schedule.getMasterTeamId() +",");
            //target_team_id
            buffer.append(schedule.getTargetTeamId() +",");
            //is_hot
            buffer.append("0,");
            //cloud_id
            buffer.append("" + schedule.getCloudId() + ",");
            //edit_date
            buffer.append("'" + DateUtil.getFormatDateTime(schedule.getEditDate()) + "',");
            //master_red_chess
            buffer.append("" + schedule.getMasterRedChess() + ",");
            //master_yellow_chess
            buffer.append("" + schedule.getMasterYellowChess() + ",");
            //master_corner_kick
            buffer.append("" + schedule.getMasterCornerKick() + ",");
            //target_red_chess
            buffer.append("" + schedule.getTargetRedChess() + ",");
            //target_yellow_chess
            buffer.append("" + schedule.getTargetYellowChess() + ",");
            //target_corner_kick
            buffer.append("" + schedule.getTargetCornerKick() + ",");
            //nami_schedule_id
            buffer.append("" + schedule.getNamiScheduleId() + ",");
            //game_time
            buffer.append("'" + schedule.getGameTime() + "',");
            //category_id
            buffer.append("" + schedule.getCategoryId() + "");
            return buffer.toString();
        }

    }
}