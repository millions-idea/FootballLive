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
}
