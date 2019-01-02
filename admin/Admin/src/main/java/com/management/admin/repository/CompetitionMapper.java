package com.management.admin.repository;

import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.dbExt.GameCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName CompetitionMapper
 * @Description 赛事管理
 * @Author Timor
 * @Date 2018/12/18 1:48
 * Version 1.0
 **/
@Mapper
public interface CompetitionMapper extends MyMapper<Game> {

    @Select("select * from tb_games where is_delete=0")
    /**
     * 查询ALL Timor 2018年8月30日11:33:22
     * @return
     */
    List<Game> selectGames();


    @Select("SELECT * FROM tb_games as t1 LEFT JOIN tb_live_categorys as t2 on t1.category_id=t2.category_id " +
            " WHERE t1.is_delete=0  LIMIT #{page},${limit}")
    /**
     * 分页查询 Timor 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @return
     */
    List<GameCategory> selectLimit(@Param("page") Integer page, @Param("limit") String limit);

    @Select("SELECT COUNT(game_id) FROM tb_games WHERE is_delete = 0")
    /**
     * 分页查询记录数 提莫 2018年8月30日11:33:30
     * @return
     */
    Integer selectLimitCount();

    @Update("update tb_games set is_delete=1 where game_id=#{gameId}")
    /**
     * 删除赛事信息 提莫 2018年12月18日19:33:30
     * @return
     */
    Integer deleteCompetition(@Param("gameId") Integer gameId);


    @Select("select * from tb_live_categorys")
    /**
     * 查询直播分类 提莫 2018年12月18日14:50:30
     * @return
     */
    List<LiveCategory> selectLiveCategory();


    @Insert("insert into tb_games(game_name,game_icon,category_id) values (#{gameName},#{gameIcon},#{categoryId})")
    /**
     * 添加赛事信息 提莫 2018年12月18日14:50:30
     * @return
     */
    Integer addCompetition(Game game);


}
