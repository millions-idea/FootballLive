/***
 * @pName Admin
 * @name ILiveCategoryService
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.dbExt.GameCategory;

import java.util.List;

public interface ILiveCategoryService {

    /**
     * 根据Id查询分类 DF 2018-12-17 14:39:562
     * @param categoryId
     * @return
     */
    LiveCategory selectCategoryById(Integer categoryId);


    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    List<LiveCategory> getLimit(Integer page, String limit);

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    Integer getLimitCount();


    /**
     * 获取全部直播分类信息列表 DF 2018年12月18日01:43:01
     * @return
     */
    List<LiveCategory> getLiveCategorys();

    /**
     * 根据ID删除直播分类 Timor 2018年12月20日01:43:01
     * @return
     */
    Integer  deleteCategoryById(Integer categoryId);

    /**
     * 根据ID修改直播分类 Timor 2018年12月20日01:43:01
     * @return
     */
    Integer  updateCategoryById(LiveCategory category);

    /**
     * 新增直播分类 Timor 2018年12月20日01:43:01
     * @return
     */
    Integer  insertCategory(LiveCategory category);

}
