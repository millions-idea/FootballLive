package com.management.admin.repository;

import com.management.admin.entity.db.Game;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameMapper extends MyMapper<Game> {
    /**
     * 查询所有的赛事
     * @return
     */
    @Select("select * from tb_games and is_delete=0")
    List<Game> queryAllGame();

    @Select("select * from tb_game where game_id=#{gameId} and is_delete=0")
    Game queryGameById(Integer gameId);
}
