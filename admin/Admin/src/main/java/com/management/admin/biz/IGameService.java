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
}
