/***
 * @pName Admin
 * @name GameApiController
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.IGameService;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.template.JsonArrayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/game")
public class GameApiController {
    @Autowired
    private IGameService gameService;

    /**
     * 获取所有赛事列表 DF 2018年12月18日02:14:23
     * @return
     */
    @GetMapping("getGameList")
    public JsonArrayResult<Game> getGameList(){
        List<Game> gameList = gameService.queryAll();
        return new JsonArrayResult<Game>(gameList);
    }
}
