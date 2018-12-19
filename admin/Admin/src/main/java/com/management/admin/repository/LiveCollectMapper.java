/***
 * @pName Admin
 * @name LiveCollectMapper
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.LiveCollect;
import com.management.admin.entity.resp.LiveCollectInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * 查询个人收藏 DF 2018年12月20日04:55:15
     * @param userId
     * @return
     */
    @Select("SELECT * FROM tb_live_collects t1 " +
            "LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id " +
            "LEFT JOIN tb_schedules t3 ON t3.schedule_id = t2.schedule_id " +
            "LEFT JOIN tb_games t4 ON t4.game_id = t3.game_id " +
            "WHERE t1.user_id = #{userId}")
    List<LiveCollectInfo> getLiveCollectList(@Param("userId") Integer userId);

    /**
     * 清空个人收藏 DF 2018年12月20日04:59:00
     * @param userId
     * @return
     */
    @Delete("DELETE FROM tb_live_collects WHERE user_id=#{userId}")
    int cleanCollect(Integer userId);
}
