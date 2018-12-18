/***
 * @pName Admin
 * @name LiveCategoryMapper
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.LiveCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LiveCategoryMapper extends MyMapper<LiveCategory>{
    /**
     * 查询全部直播分类信息列表--排序 DF 2018年12月18日01:43:20
     * @return
     */
    @Select("SELECT * FROM tb_live_categorys WHERE is_delete = 0 ORDER BY sort ASC")
    List<LiveCategory> selectAllOrderBy();
}
