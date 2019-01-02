package com.management.admin.biz;


import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.GameCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName ICompetitionService
 * @Description 赛事管理接口
 * @Author ZXL01
 * @Date 2018/12/17 14:38
 * Version 1.0
 **/

/**
 * 查询赛事信息
 */
public interface ICompetitionService {

    /**
     * 查询所有赛事信息 DF 2018-12-17 14:39:562
     * @return
     */
    List<Game> getGames();

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    List<GameCategory> getCompetitionLimit(Integer page, String limit);

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    Integer getCompetitionLimitCount();

    /**
     * 添加赛事信息 DF 2018-12-18 14:43:462
     * @return
     */
    boolean addCompetition(Game game);

    /**
     * 查询赛事分类  DF 2018-12-18 14:43:462
     * @return
     */
    List<LiveCategory> selectLiveCategory();

    /**
     * 删除赛事信息 提莫 2018年12月18日19:33:30
     * @return
     */
    boolean  deleteCompetition(@Param("gameId") Integer gameId);

    /**
     * 同步云端赛事数据 DF 2019年1月1日18:41:42
     * @param categoryId
     * @return
     */
    boolean syncCloudData(Integer categoryId);
}
