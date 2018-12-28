package com.management.admin.controller;

import com.management.admin.biz.IGameService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.dbExt.GameDetail;
import com.management.admin.entity.template.JsonArrayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("management/game")
public class GameController {

    @Autowired
    private IGameService gameService;

    /**
     * 查询所有赛事
     * @return
     */
    @GetMapping("/getAll")
    @ResponseBody
    public JsonArrayResult<GameDetail> getAll(){
        return new JsonArrayResult<GameDetail>(gameService.queryAll());
    }
}