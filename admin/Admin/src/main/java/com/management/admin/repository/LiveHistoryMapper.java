/***
 * @pName Admin
 * @name LiveHistoryMapper
 * @user HongWei
 * @date 2018/12/20
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.LiveHistory;
import com.management.admin.entity.resp.LiveHistoryInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LiveHistoryMapper extends MyMapper<LiveHistory> {

    /**
     * 添加观看历史 DF 2018年12月20日03:31:32
     * @param liveHistory
     * @return
     */
    @Insert("INSERT INTO tb_live_historys (user_id, live_id) VALUES(#{userId}, #{liveId}) ON DUPLICATE KEY UPDATE active_date=NOW()")
    int insertOrUpdate(LiveHistory liveHistory);

    /**
     * 查询观看历史 DF 2018年12月20日04:17:46
     * @param userId
     * @return
     */
    @Select("SELECT * FROM `tb_live_historys` t1 " +
            "LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id " +
            "LEFT JOIN tb_schedules t3 ON t3.schedule_id = t2.schedule_id " +
            "LEFT JOIN tb_games t4 ON t4.game_id = t3.game_id " +
            "WHERE t1.user_id = #{userId}")
    List<LiveHistoryInfo> getLiveHistoryList(@Param("userId") Integer userId);

    /**
     * 清空观看历史 DF 2018年12月20日04:46:37
     * @param userId
     * @return
     */
    @Delete("DELETE FROM tb_live_historys WHERE user_id = #{userId}")
    int cleanHistorys(@Param("userId") Integer userId);
}
