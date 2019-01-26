/***
 * @pName Live
 * @name LiveCollectMapper
 * @user HongWei
 * @date 2019/1/24
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.LiveCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LiveCollectMapper extends MyMapper<LiveCollect> {
    @Select("SELECT * FROM tb_live_collects WHERE user_id=#{userId} AND is_cancel = 0")
    List<LiveCollect> selectByUserId(@Param("userId") Integer userId);
}
