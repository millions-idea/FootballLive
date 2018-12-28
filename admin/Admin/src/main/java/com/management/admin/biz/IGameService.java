package com.management.admin.biz;

import com.management.admin.entity.db.Game;
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
}
