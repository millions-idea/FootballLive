/***
 * @pName Live
 * @name GameServiceImpl
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IGameService;
import com.management.admin.entity.resp.HotCategory;
import com.management.admin.entity.resp.HotGame;
import com.management.admin.entity.resp.HotCategoryGameSum;
import com.management.admin.repository.GameMapper;
import com.management.admin.repository.LiveCategoryMapper;
import com.management.admin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements IGameService {
    private final GameMapper gameMapper;
    private final LiveCategoryMapper liveCategoryMapper;

    @Autowired
    public GameServiceImpl(GameMapper gameMapper, LiveCategoryMapper liveCategoryMapper) {
        this.gameMapper = gameMapper;
        this.liveCategoryMapper = liveCategoryMapper;
    }

    @Override
    public List<HotGame> getToDayList() {
        String day = (Integer.valueOf(DateUtil.getCurrentDay())) + "";
        if(day.length() < 2) day = "0" + day;
        String date = DateUtil.getCurrentYear() + "-"+  DateUtil.getCurrentMonth() + "-"+ day;
        List<HotGame> hotGames = gameMapper.selectListByDay(date);
        return hotGames;
    }

    /**
     * 统计指定分类下的赛程总数 DF 2019年1月12日20:15:15
     *
     * @param join
     * @return
     */
    @Override
    public List<HotCategoryGameSum> countGameSchedules(List<Integer> join) {
        StringBuffer buffer = new StringBuffer();
        join.stream().forEach(item -> {
            buffer.append(" (SELECT COUNT(t2.game_id) AS count,t1.category_id  FROM tb_games t1 ");
            buffer.append(" LEFT JOIN tb_schedules t2 ON t2.game_id = t1.game_id ");
            buffer.append(" WHERE t1.category_id = " + item.intValue() + ") ");
            buffer.append(" UNION ");
        });
        String command = buffer.toString().substring(0, buffer.toString().length() - 6);
        List<HotCategoryGameSum> hotCategoryGameSumList =  liveCategoryMapper.countSchedules(command);
        return hotCategoryGameSumList;
    }

    /**
     * 获取指定数量的热门赛程列表 DF 2019年1月12日20:15:49
     *
     * @param limit
     * @return
     */
    @Override
    public List<HotGame> getTopList(int limit, List<HotCategory> hotCategoryList) {
        StringBuffer buffer = new StringBuffer();
        hotCategoryList.forEach(item -> {
            buffer.append("SELECT category_id, game_id, game_name, game_icon,scheduleCount FROM ( ");
            buffer.append(" (SELECT category_id, game_id, game_name, game_icon, 0 AS scheduleCount FROM tb_games WHERE category_id=" + item.getCategoryId() + " ORDER BY edit_date DESC LIMIT 100) ");
            buffer.append(" UNION ");
            buffer.append(" ( ");
            buffer.append(" SELECT t1.category_id, t1.game_id, t1.game_name, t1.game_icon, (SELECT COUNT(*) FROM tb_schedules t2 WHERE t2.game_id = t1.game_id) AS scheduleCount  ");
            buffer.append(" FROM tb_games t1 WHERE t1.category_id=" + item.getCategoryId() + " ORDER BY t1.edit_date DESC LIMIT 100)  ");
            buffer.append(") r1 ORDER BY r1.scheduleCount DESC LIMIT " + 7 + ";  ");
        });
        List<HotGame> list = gameMapper.selectTopList(buffer.toString());
        return list;
    }

    /**
     * 查询指定分类 DF 2019年1月12日23:50:15
     *
     * @param params
     * @return
     */
    @Override
    public List<HotCategory> getCategoryList(String... params) {
        if(params == null || params.length == 0) return null;
        String toDay = (Integer.valueOf(DateUtil.getCurrentDay())) + "";
        StringBuffer buffer = new StringBuffer();
        for (String param : params) {
            buffer.append("SELECT *,");
            buffer.append("(SELECT COUNT(*) FROM tb_schedules t1  " +
                    "LEFT JOIN tb_games t2 ON t1.game_id = t2.game_id " +
                    "WHERE t2.category_id = e1.category_id AND t1.game_date LIKE '" + DateUtil.getCurrentYear() + "-" + DateUtil.getCurrentMonth() + "-" + toDay + "%') AS scheduleCount ");
            buffer.append(" FROM tb_live_categorys e1 WHERE category_name LIKE " + "'" + param + "%' AND is_delete = 0 AND is_show = 1 ");
            buffer.append("UNION ");
        }
        String command = buffer.toString().substring(0, buffer.toString().length() - 6);
        return liveCategoryMapper.selectInName(command);
    }
}
