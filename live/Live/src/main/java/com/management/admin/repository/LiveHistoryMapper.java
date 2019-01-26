/***
 * @pName Live
 * @name LiveHistoryMapper
 * @user HongWei
 * @date 2019/1/25
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.LiveCollect;
import com.management.admin.entity.db.LiveHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LiveHistoryMapper extends MyMapper<LiveHistory>{
    @Select("SELECT * FROM tb_live_historys WHERE user_id = #{userId}")
    List<LiveCollect> selectByUserId(@Param("userId") Integer userId);
}
