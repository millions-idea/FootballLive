/***
 * @pName Live
 * @name IGameService
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.resp.HotCategory;
import com.management.admin.entity.resp.HotGame;
import com.management.admin.entity.resp.HotCategoryGameSum;

import java.util.List;

public interface IGameService {
    /**
     * 获取今天的赛事列表 DF 2019年1月12日20:16:54
     * @return
     */
    List<HotGame> getToDayList();


    /**
     * 统计指定分类下的赛程总数 DF 2019年1月12日20:15:15
     * @param join
     * @return
     */
    List<HotCategoryGameSum> countGameSchedules(List<Integer> join);

    /**
     * 获取指定数量的热门赛程列表 DF 2019年1月12日20:15:49
     * @param limit
     * @return
     */
    List<HotGame> getTopList(int limit, List<HotCategory> hotCategoryList);


    /**
     * 查询指定分类 DF 2019年1月12日23:50:15
     * @param params
     * @return
     */
    List<HotCategory> getCategoryList(String... params);
}
