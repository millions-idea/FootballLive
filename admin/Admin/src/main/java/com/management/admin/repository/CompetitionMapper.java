package com.management.admin.repository;

import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.dbExt.GameCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @ClassName CompetitionMapper
 * @Description 赛事管理
 * @Author Timor
 * @Date 2018/12/18 1:48
 * Version 1.0
 **/
@Mapper
public interface CompetitionMapper {


    @Select("SELECT * FROM tb_games as t1 LEFT JOIN tb_live_categorys as t2 on t1.category_id=t2.category_id " +
            " WHERE t1.is_delete=0  LIMIT #{page},${limit}")
    /**
     * 分页查询 Timor 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @return
     */
    List<GameCategory> selectLimit(@Param("page") Integer page, @Param("limit") String limit);

    @Select("SELECT COUNT(game_id) FROM tb_games ")

    /**
     * 分页查询记录数 提莫 2018年8月30日11:33:30
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    Integer selectLimitCount();


}
