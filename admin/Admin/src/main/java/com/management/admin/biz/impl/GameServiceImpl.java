package com.management.admin.biz.impl;

import com.management.admin.biz.IGameService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.dbExt.GameDetail;
import com.management.admin.repository.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements IGameService {

    private GameMapper gameMapper;

    @Autowired
    public GameServiceImpl(GameMapper gameMapper){
        this.gameMapper = gameMapper;
    }

    /**
     * 查询所有的赛事
     *
     * @return
     */
    @Override
    public List<GameDetail> queryAll() {
        return gameMapper.queryAllGame();
    }

    /**
     * 获取游戏赛事列表 DF 2018年12月28日19:45:11
     *
     * @param liveCategoryId
     * @return
     */
    @Override
    public List<GameDetail> getList(Integer liveCategoryId) {
        if(liveCategoryId == null || liveCategoryId.equals(0)) return gameMapper.queryAllGame();
        return gameMapper.selectList(liveCategoryId);
    }
}
