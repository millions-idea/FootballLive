package com.management.admin.biz;

import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameDetail;

import java.util.List;

public interface IGameService {

    /**
     * 查询所有的赛事
     * @return
     */
    List<GameDetail> queryAll();

    /**
     * 获取游戏赛事列表 DF 2018年12月28日19:45:11
     * @param liveCategoryId
     * @return
     */
    List<GameDetail> getList(Integer liveCategoryId);

    /**
     * 获取所有赛事信息 DF 2018年12月29日12:56:59
     * @return
     */
    List<Game> getList();

    /**
     * 获取球队信息列表 DF 2018年12月29日13:28:03
     * @param categoryId
     * @return
     */
    List<Team> getTeams(Integer categoryId);

}
