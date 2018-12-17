/***
 * @pName Admin
 * @name ILiveCategoryService
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.LiveCategory;

import java.util.List;

public interface ILiveCategoryService {

    /**
     * 获取全部直播分类信息列表 DF 2018年12月18日01:43:01
     * @return
     */
    List<LiveCategory> getLiveCategorys();
}
