/***
 * @pName Admin
 * @name LiveCategoryServiceImpl
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.ILiveCategoryService;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.repository.LiveCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveCategoryServiceImpl implements ILiveCategoryService {
    private final LiveCategoryMapper liveCategoryMapper;

    @Autowired
    public LiveCategoryServiceImpl(LiveCategoryMapper liveCategoryMapper) {
        this.liveCategoryMapper = liveCategoryMapper;
    }

    /**
     * 获取全部直播分类信息列表 DF 2018年12月18日01:43:01
     * @return
     */
    public List<LiveCategory> getLiveCategorys(){
        return liveCategoryMapper.selectAllOrderBy();
    }
}
