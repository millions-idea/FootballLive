/***
 * @pName Admin
 * @name LiveCollectMapper
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.LiveCollect;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LiveCollectMapper extends MyMapper<LiveCollect> {
    /**
     * 取消关注 DF 2018年12月19日04:05:45
     * @param liveId
     * @param userId
     * @return
     */
    @Update("UPDATE tb_live_collects SET edit_date=NOW(),is_cancel=1 WHERE live_id=#{liveId} AND user_id=#{userId} ")
    int cancelCollect(@Param("liveId") Integer liveId,@Param("userId") Integer userId);

    /**
     * 插入或更新 DF 2018年12月19日05:24:08
     * @param liveCollect
     * @return
     */
    @Insert("INSERT INTO tb_live_collects(user_id,live_id,is_cancel,add_date) " +
                " VALUES(#{userId}, #{liveId}, 0, NOW()) ON DUPLICATE KEY UPDATE  is_cancel = CASE is_cancel WHEN 0 THEN 1 WHEN 1 THEN 0 END,edit_date=NOW()")
    int insertOrUpdate(LiveCollect liveCollect);
}
