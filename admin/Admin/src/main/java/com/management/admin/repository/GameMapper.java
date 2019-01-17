package com.management.admin.repository;

import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameMapper extends MyMapper<Game> {
    /**
     * 查询所有的赛事
     * @return
     */
    @Select("SELECT * FROM tb_games t1 " +
            "            LEFT JOIN tb_schedules t2 ON t2.game_id = t1.game_id " +
            "            WHERE t1.is_delete= 0 ORDER BY IF(ISNULL(t2.game_date),1,0) ASC,t2.game_date ASC")
    List<GameDetail> queryAllGame();

    @Select("select * from tb_game where game_id=#{gameId} and is_delete=0")
    Game queryGameById(Integer gameId);

    /**
     * 查询游戏赛事列表 DF 2018年12月28日19:46:18
     * @param liveCategoryId
     * @return
     */
    @Select("SELECT * FROM tb_games t1 " +
            "  LEFT JOIN tb_schedules t2 ON t2.game_id = t1.game_id " +
            "  WHERE t1.is_delete= 0 AND t1.category_id=#{liveCategoryId} GROUP BY t1.game_id ORDER BY t2.game_id DESC")
    List<GameDetail> selectList(@Param("liveCategoryId") Integer liveCategoryId);

    /**
     * 查询赛事绑定球队信息列表 DF 2018年12月29日13:29:53
     * @param categoryId
     * @return
     */
    @Select("SELECT * FROM tb_games t1 " +
            "LEFT JOIN tb_live_categorys t2 ON t2.category_id = t1.category_id " +
            "LEFT JOIN tb_teams t3 ON t3.game_id = t1.game_id " +
            "WHERE t1.category_id = #{categoryId}")
    List<Team> selectTeams(@Param("categoryId") Integer categoryId);


    @Insert("${execSql}")
    void insertOrUpdateList(@Param("execSql") String execSql);
}
