package com.management.admin.repository;

import com.management.admin.entity.db.Game;
import com.management.admin.entity.dbExt.GameDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameMapper extends MyMapper<Game> {
    /**
     * 查询所有的赛事
     * @return
     */
    @Select("SELECT * FROM tb_games t1 " +
            "LEFT JOIN tb_schedules t2 ON t2.game_id = t1.game_id " +
            "WHERE t1.is_delete= 0 ORDER BY t2.game_date DESC  ")
    List<GameDetail> queryAllGame();

    @Select("select * from tb_game where game_id=#{gameId} and is_delete=0")
    Game queryGameById(Integer gameId);
}
