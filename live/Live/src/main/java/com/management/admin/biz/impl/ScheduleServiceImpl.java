/***
 * @pName Live
 * @name ScheduleServiceImpl
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IScheduleService;
import com.management.admin.entity.resp.HotInformation;
import com.management.admin.entity.resp.HotSchedule;
import com.management.admin.repository.InformationMapper;
import com.management.admin.repository.ScheduleMapper;
import com.management.admin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    private final ScheduleMapper scheduleMapper;
    private final InformationMapper informationMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper, InformationMapper informationMapper) {
        this.scheduleMapper = scheduleMapper;
        this.informationMapper = informationMapper;
    }

    /**
     * 获取指定数量的热门赛程 DF 2019年1月10日11:15:31
     *
     * @return
     */
    @Override
    public List<HotSchedule> getTopList() {
        //热门赛程列表(查询当天和次日)
        String toDay = (Integer.valueOf(DateUtil.getCurrentDay())) + "";
        if(toDay.length() < 2) toDay = "0" + toDay;
        String tomorrow = (Integer.valueOf(DateUtil.getCurrentDay()) + 1) + "";
        if(tomorrow.length() < 2) tomorrow = "0" + tomorrow;

        List<HotSchedule> toDayList = scheduleMapper.selectTopListLimit(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + toDay);
        List<HotSchedule> tomorrowList = scheduleMapper.selectTopListLimit(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + tomorrow);
        toDayList.addAll(tomorrowList);

        LinkedList<HotSchedule> hotScheduleList = new LinkedList<>();
        hotScheduleList.addAll(toDayList);

        if(hotScheduleList == null) return null;
        //手动排序赛程列表
        LinkedList<HotSchedule> list = new LinkedList<>();
        /*hotScheduleList.stream().filter(item -> (item.getSourceUrl() != null && !item.getSourceUrl().equals("#"))).forEach(item -> {
            list.add(item);
        });*/

        //排序
        getSortedScheduleList(null, null, toDay, tomorrow, hotScheduleList, list);

        list.forEach(item -> {
            item.setGameDateString(DateUtil.getFormatDateTime(item.getGameDate()));
            switch (item.getStatus().intValue()){
                case 0:
                    item.setShortStatus("未开始");
                    item.setShortButtonName("等待开始");
                    break;
                case 1:
                    item.setShortStatus("进行中");
                    item.setShortButtonName("进行中");
                    if(item.getSourceUrl()!= null && !item.getSourceUrl().equals("#")){
                        item.setShortStatus("直播中");
                        item.setShortButtonName("观看直播");
                    }
                    break;
                case 2:
                    item.setShortStatus("已结束");
                    item.setShortButtonName("已结束");
                    break;
                case 3:
                    item.setShortStatus("推迟");
                    item.setShortButtonName("推迟");
                    break;
                case 4:
                    item.setShortStatus("完场");
                    item.setShortButtonName("完场");
                    break;
                default:
                    item.setShortStatus("其他");
                    item.setShortButtonName("其他");
                    break;
            }
            item.setShortGameDate(DateUtil.getFormatDateMinute(item.getGameDate()));
        });
        return list;
    }

    /**
     * 获取热门情报列表 DF 2019年1月10日15:12:52
     *
     * @return
     */
    @Override
    public List<HotInformation> getTopInformationList() {
        List<HotInformation> hotInformations = informationMapper.selectTopList();
        if(hotInformations != null && hotInformations.size() > 0){
            hotInformations.forEach(item -> {
                if(item.getGameDate() != null){
                    item.setGameDateString(DateUtil.getFormatDateTime(item.getGameDate()));
                    item.setShortGameDate(DateUtil.getFormatDateMinute(item.getGameDate()));
                }
            });
        }
        return hotInformations;
    }

    /**
     * 获取最近的赛程列表 DF 2019年1月12日21:48:57
     *
     * @return
     */
    @Override
    public List<HotSchedule> getRecentList() {
        //热门赛程列表(查询昨天、今天、前天)
        String yesterDay = (Integer.valueOf(DateUtil.getCurrentDay()) - 1) + "";
        if(yesterDay.length() < 2) yesterDay = "0" + yesterDay;
        String toDay = (Integer.valueOf(DateUtil.getCurrentDay())) + "";
        if(toDay.length() < 2) toDay = "0" + toDay;
        String tomorrow = (Integer.valueOf(DateUtil.getCurrentDay()) + 1) + "";
        if(tomorrow.length() < 2) tomorrow = "0" + tomorrow;

        List<HotSchedule> yesterDayList = scheduleMapper.selectTopListLimit(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + yesterDay);
        List<HotSchedule> toDayList = scheduleMapper.selectTopListLimit(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + toDay);
        List<HotSchedule> tomorrowList = scheduleMapper.selectTopListLimit(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + tomorrow);
        yesterDayList.addAll(toDayList);
        toDayList.addAll(tomorrowList);

        LinkedList<HotSchedule> hotScheduleList = new LinkedList<>();
        hotScheduleList.addAll(toDayList);

        if(hotScheduleList == null) return null;
        //手动排序赛程列表
        LinkedList<HotSchedule> list = new LinkedList<>();
        /*hotScheduleList.stream().filter(item -> (item.getSourceUrl() != null && !item.getSourceUrl().equals("#"))).forEach(item -> {
            list.add(item);
        });*/

        //排序
        getSortedScheduleList(null, yesterDay, toDay, tomorrow, hotScheduleList, list);

        list.forEach(item -> {
            item.setGameDateString(DateUtil.getFormatDateTime(item.getGameDate()));
            switch (item.getStatus().intValue()){
                case 0:
                    item.setShortStatus("未开始");
                    item.setShortButtonName("等待开始");
                    break;
                case 1:
                    item.setShortStatus("进行中");
                    item.setShortButtonName("进行中");
                    if(item.getSourceUrl()!= null && !item.getSourceUrl().equals("#")){
                        item.setShortStatus("直播中");
                        item.setShortButtonName("观看直播");
                    }
                    break;
                case 2:
                    item.setShortStatus("已结束");
                    item.setShortButtonName("已结束");
                    break;
                case 3:
                    item.setShortStatus("推迟");
                    item.setShortButtonName("推迟");
                    break;
                case 4:
                    item.setShortStatus("完场");
                    item.setShortButtonName("完场");
                    break;
                default:
                    item.setShortStatus("其他");
                    item.setShortButtonName("其他");
                    break;
            }
            item.setShortGameDate(DateUtil.getFormatDateMinute(item.getGameDate()));
        });
        return list;
    }

    /**
     * 查询赛程列表 DF 2019年1月13日06:09:24
     *
     * @param type
     * @param gameId
     * @param date
     * @return
     */
    @Override
    public List<HotSchedule> getScheduleList(Integer type, Integer gameId, String date) {
        //热门赛程列表(查询昨天、今天、前天)
        String yesterDay = (Integer.valueOf(DateUtil.getCurrentDay()) - 1) + "";
        if(yesterDay.length() < 2) yesterDay = "0" + yesterDay;
        String toDay = (Integer.valueOf(DateUtil.getCurrentDay())) + "";
        if(toDay.length() < 2) toDay = "0" + toDay;
        String tomorrow = (Integer.valueOf(DateUtil.getCurrentDay()) + 1) + "";
        if(tomorrow.length() < 2) tomorrow = "0" + tomorrow;

        StringBuffer buffer = new StringBuffer();
        //查询全部、热门、直播中
        if(gameId != null && gameId > 0){
            buffer.append(" t1.game_id=" + gameId);
        }
        if(date != null && date.length() > 0){
            buffer.append(" LOCATE( '" + date + "', t1.game_date) > 0 ");
        }

        if(type != null && type.equals(4)){
            buffer.append(" AND t1.status=1 AND t4.source_url != '#' ");
        }

        LinkedList<HotSchedule> hotScheduleList = new LinkedList<>();
        LinkedList<HotSchedule> list = new LinkedList<>();

        if(type == null || type.equals(0)){
            StringBuffer selectCommandBuffer = new StringBuffer();
            selectCommandBuffer.append(getAtDayCommand(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + toDay));
            selectCommandBuffer.append("UNION ");
            selectCommandBuffer.append(getAtDayCommand(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + tomorrow));
            selectCommandBuffer.append("UNION ");
            selectCommandBuffer.append(getAtDayCommand(DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + yesterDay));
            hotScheduleList = scheduleMapper.selectTopListCommand(selectCommandBuffer.toString());
        }else{
            if(buffer.toString().isEmpty()){
                buffer.append(" 1=1 ");
            }
            hotScheduleList = scheduleMapper.selecList(buffer.toString());

            hotScheduleList.stream().filter(item -> !list.contains(item)).forEach(item -> {
                list.add(item);
            });
        }

        //排序
        getSortedScheduleList(type, yesterDay, toDay, tomorrow, hotScheduleList, list);

        list.forEach(item -> {
            if(item.getCategoryName() != null && item.getCategoryName().contains("足球")){
                item.setIsFootball(true);
            }
            item.setGameDateString(DateUtil.getFormatDateTime(item.getGameDate()));
            switch (item.getStatus().intValue()){
                case 0:
                    item.setShortStatus("未开始");
                    item.setShortButtonName("等待开始");
                    item.setShortStyleClassName("wait");
                    break;
                case 1:
                    item.setShortStatus("进行中");
                    item.setShortButtonName("进行中");
                    item.setShortStyleClassName("wait");
                    if(item.getSourceUrl()!= null && !item.getSourceUrl().equals("#")){
                        item.setShortStatus("直播中");
                        item.setShortButtonName("观看直播");
                        item.setShortStyleClassName("on");
                    }
                    break;
                case 2:
                    item.setShortStatus("已结束");
                    item.setShortButtonName("已结束");
                    item.setShortStyleClassName("off");
                    break;
                case 3:
                    item.setShortStatus("推迟");
                    item.setShortButtonName("推迟");
                    item.setShortStyleClassName("off");
                    break;
                case 4:
                    item.setShortStatus("完场");
                    item.setShortButtonName("完场");
                    item.setShortStyleClassName("off");
                    break;
                default:
                    item.setShortStatus("其他");
                    item.setShortButtonName("其他");
                    break;
            }
            item.setShortGameDate(DateUtil.getFormatDateMinute(item.getGameDate()));
        });

        return list;
    }

    private void getSortedScheduleList(Integer type, String yesterDay, String toDay, String tomorrow, LinkedList<HotSchedule> hotScheduleList, LinkedList <HotSchedule> list) {
        List<HotSchedule> yesterDayList = null;
        List<HotSchedule> toDayList = null;
        List<HotSchedule> tomorrowList = null;

        if(type == null){
            String finalYesterDayList = yesterDay;
            yesterDayList = hotScheduleList.stream().filter(t -> (t.getGameDate() != null && yesterDay != null) && DateUtil.getFormatDateTime(t.getGameDate()).contains(yesterDay)).collect(Collectors.toList());
            String finalToDay = toDay;
            toDayList = hotScheduleList.stream().filter(t ->(t.getGameDate() != null && toDay != null) && DateUtil.getFormatDateTime(t.getGameDate()).contains(finalToDay)).collect(Collectors.toList());
            String finalTomorrow = tomorrow;
            tomorrowList = hotScheduleList.stream().filter(t -> (t.getGameDate() != null && tomorrow != null) && DateUtil.getFormatDateTime(t.getGameDate()).contains(finalTomorrow)).collect(Collectors.toList());

            yesterDayList.sort(Comparator.comparing(HotSchedule::getGameDate));
            toDayList.sort(Comparator.comparing(HotSchedule::getGameDate));
            tomorrowList.sort(Comparator.comparing(HotSchedule::getGameDate));
        }else if(type.equals(-1)){
            String finalYesterDayList = yesterDay;
            yesterDayList = hotScheduleList.stream().filter(t -> (t.getGameDate() != null && yesterDay != null) && DateUtil.getFormatDateTime(t.getGameDate()).contains(yesterDay)).collect(Collectors.toList());
            yesterDayList.sort(Comparator.comparing(HotSchedule::getStatus));
            yesterDayList.sort(Comparator.comparing(HotSchedule::getGameDate));
        }else if(type.equals(1)){
            String finalToDay = toDay;
            toDayList = hotScheduleList.stream().filter(t ->(t.getGameDate() != null && toDay != null) && DateUtil.getFormatDateTime(t.getGameDate()).contains(finalToDay)).collect(Collectors.toList());
            toDayList.sort(Comparator.comparing(HotSchedule::getStatus));
            toDayList.sort(Comparator.comparing(HotSchedule::getGameDate));
        }else if(type.equals(2)){
            String finalTomorrow = tomorrow;
            tomorrowList = hotScheduleList.stream().filter(t -> (t.getGameDate() != null && tomorrow != null) && DateUtil.getFormatDateTime(t.getGameDate()).contains(finalTomorrow)).collect(Collectors.toList());
            tomorrowList.sort(Comparator.comparing(HotSchedule::getStatus));
            tomorrowList.sort(Comparator.comparing(HotSchedule::getGameDate));
        }

        hotScheduleList.clear();
        if(type != null && type.equals(1)){
            toDayList.forEach(t -> {
                defaultSorted(list, t);
            });
            toDayList.forEach(t -> {
                Optional<HotSchedule> first = hotScheduleList.stream().filter(i -> i.getScheduleId().equals(t.getScheduleId())).findFirst();
                if (!first.isPresent()) {
                    hotScheduleList.add(t);
                }
            });
        }else if(type != null && type.equals(-1)){
            yesterDayList.forEach(t -> {
                defaultSorted(list, t);
            });
            yesterDayList.forEach(t -> {
                Optional<HotSchedule> first = hotScheduleList.stream().filter(i -> i.getScheduleId().equals(t.getScheduleId())).findFirst();
                if (!first.isPresent()) {
                    hotScheduleList.add(t);
                }
            });
        }else if(type != null && type.equals(2)){
            tomorrowList.forEach(t -> {
                defaultSorted(list, t);
            });
            tomorrowList.forEach(t -> {
                Optional<HotSchedule> first = hotScheduleList.stream().filter(i -> i.getScheduleId().equals(t.getScheduleId())).findFirst();
                if (!first.isPresent()) {
                    hotScheduleList.add(t);
                }
            });
        }else{
            hotScheduleList.clear();
            yesterDayList.sort(Comparator.comparing(HotSchedule::getGameDate));
            toDayList.sort(Comparator.comparing(HotSchedule::getGameDate));
            tomorrowList.sort(Comparator.comparing(HotSchedule::getGameDate));

            yesterDayList.forEach(t -> {
                defaultSorted(list, t);
            });
            yesterDayList.forEach(t -> {
                Optional<HotSchedule> first = hotScheduleList.stream().filter(i -> i.getScheduleId().equals(t.getScheduleId())).findFirst();
                if (!first.isPresent()) {
                    hotScheduleList.add(t);
                }
            });

            tomorrowList.forEach(t -> {
                defaultSorted(list, t);
            });
            tomorrowList.forEach(t -> {
                Optional<HotSchedule> first = hotScheduleList.stream().filter(i -> i.getScheduleId().equals(t.getScheduleId())).findFirst();
                if (!first.isPresent()) {
                    hotScheduleList.add(t);
                }
            });

            toDayList.forEach(t -> {
                defaultSorted(list, t);
            });
            toDayList.forEach(t -> {
                Optional<HotSchedule> first = hotScheduleList.stream().filter(i -> i.getScheduleId().equals(t.getScheduleId())).findFirst();
                if (!first.isPresent()) {
                    hotScheduleList.add(t);
                }
            });


        }


        list.clear();
        //hotScheduleList.sort(Comparator.comparing(HotSchedule::getStatus).reversed());

        //将正在直播、进行中的比赛放在第一位展示
        /*hotScheduleList.stream().filter(item -> !list.contains(item)).forEach(item -> {
            defaultSorted(list, item);
        });*/

        //把正在直播的放在首位
        hotScheduleList.sort((g1, g2) -> g1.getGameDate().compareTo(g2.getGameDate()));

        List<HotSchedule> tempList = hotScheduleList.stream().filter(item -> item.getStatus().equals(1) && (item.getSourceUrl() != null && item.getSourceUrl() != "#")).collect(toList());
        list.addAll(tempList);

        tempList = hotScheduleList.stream().filter(item -> item.getStatus().equals(1) && !(item.getSourceUrl() != null && item.getSourceUrl() != "#")).collect(toList());
        list.addAll(tempList);

        tempList = hotScheduleList.stream().filter(item -> item.getStatus().equals(0)).collect(toList());
        list.addAll(tempList);

        tempList = hotScheduleList.stream().filter(item -> item.getStatus().equals(2)).collect(toList());
        list.addAll(tempList);

        tempList = hotScheduleList.stream().filter(item -> !list.contains(item)).collect(toList());
        list.addAll(tempList);

        /*list.stream().forEach(item -> {
            if(item.getStatus().equals(0) ){
                Integer index = list.indexOf(item);
                HotSchedule firstItem = list.get(0);
                list.set(index, firstItem);
                list.set(0, item);
            }
        });


        list.stream().forEach(item -> {
            if(item.getStatus().equals(1)  && (item.getSourceUrl() == null || item.getSourceUrl().equals("#"))  ){
                Integer index = list.indexOf(item);
                HotSchedule firstItem = list.get(0);
                list.set(index, firstItem);
                list.set(0, item);
            }
        });

        list.stream().forEach(item -> {
            if(item.getStatus().equals(1) && (item.getSourceUrl() != null && !item.getSourceUrl().equals("#")) ){
                Integer index = list.indexOf(item);
                HotSchedule firstItem = list.get(0);
                list.set(index, firstItem);
                list.set(0, item);
            }
        });*/
        System.out.println(list);
    }

    private void defaultSorted(LinkedList<HotSchedule> list, HotSchedule item) {
        if(item.getStatus().equals(1) && (item.getSourceUrl() == null || item.getSourceUrl().equals("#"))){
            list.addFirst(item);
        }else if(item.getStatus().equals(1) && (item.getSourceUrl() != null && !item.getSourceUrl().equals("#")) ){
            list.addFirst(item);
        }else{
            list.add(item);
        }
    }

    private String getAtDayCommand(String day){
        return "(SELECT *,  " +
                "   t2.team_name AS masterTeamName, t3.team_name AS targetTeamName,  " +
                "   t2.team_icon AS masterTeamIcon, t3.team_icon AS targetTeamIcon " +
                "   FROM tb_schedules t1  " +
                "   LEFT JOIN tb_teams t2 ON t1.master_team_id = t2.team_id  " +
                "   LEFT JOIN tb_teams t3 ON t1.target_team_id = t3.team_id " +
                "   LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id " +
                "   LEFT JOIN tb_games t5 ON t1.game_id = t5.game_id " +
                "   LEFT JOIN tb_live_categorys t6 ON t5.category_id = t6.category_id " +
                "   WHERE t1.`status` = 0  AND LOCATE('" + day + "', t1.game_date) > 0 ORDER BY t1.game_date ASC)  " +
                "UNION " +
                "(SELECT *,  " +
                "   t2.team_name AS masterTeamName, t3.team_name AS targetTeamName,  " +
                "   t2.team_icon AS masterTeamIcon, t3.team_icon AS targetTeamIcon " +
                "   FROM tb_schedules t1  " +
                "   LEFT JOIN tb_teams t2 ON t1.master_team_id = t2.team_id  " +
                "   LEFT JOIN tb_teams t3 ON t1.target_team_id = t3.team_id " +
                "   LEFT JOIN tb_lives t4 ON t4.schedule_id = t1.schedule_id " +
                "   LEFT JOIN tb_games t5 ON t1.game_id = t5.game_id " +
                "   LEFT JOIN tb_live_categorys t6 ON t5.category_id = t6.category_id " +
                "   WHERE t1.`status` = 1 AND LOCATE('" + day + "', t1.game_date) > 0  ORDER BY t1.game_date ASC) ";
    }
}
