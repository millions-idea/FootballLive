/***
 * @pName Live
 * @name LiveCategoryMapper
 * @user HongWei
 * @date 2019/1/12
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.resp.HotCategory;
import com.management.admin.entity.resp.HotCategoryGameSum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LiveCategoryMapper extends MyMapper<HotCategory>{
    /**
     * 查询直播分类 DF 2019年1月12日23:52:34
     * @param command
     * @return
     */
    @Select("${command}")
    List<HotCategory> selectInName(@Param("command") String command);


    /**
     * 统计指定分类的赛程总数 DF 2019年1月12日20:29:50
     * @param command
     * @return
     */
    @Select("${command}")
    List<HotCategoryGameSum> countSchedules(@Param("command") String command);
}
