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
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.repository.LiveCategoryMapper;
import com.management.admin.repository.utils.ConditionUtil;
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
     * 根据Id查询分类信息 DF 2018年12月17日14:40:233
     * @return
     */
    @Override
    public LiveCategory selectCategoryById(Integer categoryId) {
        return liveCategoryMapper.selectCategoryById(categoryId);
    }

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    @Override
    public Integer getLimitCount() {
        return liveCategoryMapper.selectLimitCount();
    }

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<LiveCategory> getLimit(Integer page, String limit) {
        page = ConditionUtil.extractPageIndex(page, limit);
        List<LiveCategory> list = liveCategoryMapper.selectLimit(page, limit);
        return list;
    }

    /**
     * 获取全部直播分类信息列表 DF 2018年12月18日01:43:01
     * @return
     */
    public List<LiveCategory> getLiveCategorys(){
        return liveCategoryMapper.selectAllOrderBy();
    }

    /**
     * 根据ID删除直播分类 Timor 2018年12月20日01:43:01
     * @return
     */
    @Override
    public Integer deleteCategoryById(Integer categoryId) {
        return liveCategoryMapper.deleteCategory(categoryId);
    }

    /**
     * 根据ID修改直播分类 Timor 2018年12月20日01:43:01
     * @return
     */
    @Override
    public Integer updateCategoryById(LiveCategory category) {
        return liveCategoryMapper.updateCategory(category);
    }

    /**
     * 新增直播分类 Timor 2018年12月20日01:43:01
     * @return
     */
    @Override
    public Integer insertCategory(LiveCategory category) {
        return liveCategoryMapper.insertCategory(category);
    }
}
