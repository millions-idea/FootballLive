/***
 * @pName Live
 * @name GameMapper
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.resp.HotGame;
import com.management.admin.entity.resp.HotCategoryGameSum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameMapper extends MyMapper<HotGame> {
    /**
     * 查询指定日期的赛事 DF 2019年1月10日14:47:28
     * @param day
     * @return
     */
    @Select("SELECT * FROM tb_games WHERE is_delete = 0 AND edit_date LIKE '${day}%' LIMIT 120")
    List<HotGame> selectListByDay(@Param("day") String day);


    /**
     * 获取指定数量的热门赛程列表 DF 2019年1月12日20:15:49
     * @param command
     * @return
     */
    @Select("${command}")
    List<HotGame> selectTopList(@Param("command") String command);
}
