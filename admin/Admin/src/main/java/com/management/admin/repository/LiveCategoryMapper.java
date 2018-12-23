/***
 * @pName Admin
 * @name LiveCategoryMapper
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.dbExt.GameCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LiveCategoryMapper extends MyMapper<LiveCategory>{

    /**
     * 根据Id查询 Timor 2018年8月30日11:33:22
     * @param categoryId
     * @return
     */
    @Select(" select * from tb_live_categorys  where  category_id=#{categoryId}")
    LiveCategory selectCategoryById(Integer categoryId);


    /**
     * 分页查询 Timor 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @return
     */
    @Select(" select * from tb_live_categorys  where  is_delete=0 ORDER BY sort ASC LIMIT #{page},${limit}")
    List<LiveCategory> selectLimit(@Param("page") Integer page, @Param("limit") String limit);

    /**
     * 分页查询记录数 提莫 2018年8月30日11:33:30
     * @return
     */
    @Select("SELECT COUNT(category_id) FROM tb_live_categorys ")
    Integer selectLimitCount();

    /**
     * 查询全部直播分类信息列表--排序 DF 2018年12月18日01:43:20
     * @return
     */
    @Select("SELECT * FROM tb_live_categorys WHERE is_delete = 0 AND is_show = 1 ORDER BY sort ASC")
    List<LiveCategory> selectAllOrderBy();

    /**
     * 根据分类ID删除直播分类 Timor 2018年12月20日01:43:20
     * @return
     */
    @Update("update tb_live_categorys set is_delete = 1 where category_id=#{category}")
    Integer  deleteCategory(Integer categoryId);

    /**
     * 添加直播分类 Timor 2018年12月20日01:43:20
     * @return
     */
    @Insert("insert into tb_live_categorys(category_name,category_background_image_url,sort) " +
            "  values (#{categoryName},#{categoryBackgroundImageUrl},#{sort})")
    Integer  insertCategory(LiveCategory category);

    /**
     * 根据Id修改直播分类 Timor 2018年12月20日01:43:20
     * @return
     */
    @Update("update tb_live_categorys set category_name=#{categoryName},category_background_image_url=#{categoryBackgroundImageUrl},sort=#{sort}" +
            "  where category_Id=#{categoryId}")
    Integer  updateCategory(LiveCategory category);
}
