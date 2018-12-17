package com.management.admin.biz;

import com.management.admin.entity.db.Game;

import java.util.List;

public interface IGameService {

    /**
     * 查询所有的赛事
     * @return
     */
    List<Game> queryAll();
}
