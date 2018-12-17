package com.management.admin.repository;

import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.dbExt.LiveHotDetail;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/***
 * 直播信息mapper
 */
@Mapper
public interface LiveMapper extends MyMapper<Live>{

    @Select("SELECT t1.*,t2.game_date, t2.game_duration, t2.status AS scheduleStatus, t3.game_name, t3.game_icon,t4.* " +
            "FROM tb_lives t1 LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t2.game_id = t3.game_id " +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t2.team_id where t1.status=0 and ${condition} GROUP BY t1.live_id ORDER BY t1.add_date DESC LIMIT #{page},${limit}")
    /**
     * 分页查询 韦德 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    List<LiveDetail> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.live_id) FROM tb_lives t1 LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t2.game_id = t3.game_id " +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t2.team_id where t1.status=0 and "+
            " ${condition}")
    /**
     * 分页查询记录数 韦德 2018年8月30日11:33:30
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    Integer selectLimitCount(@Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    /**
     * 根据编号修改直播间标题
     * @param liveId
     * @param title
     * @return
     */
    @Update("update tb_lives set title=#{title} where live_id=#{liveId}")
    Integer modifyLiveTitleById(@Param("liveId") Integer liveId,@Param("title") String title);

    /**
     * 根据编号修改直播赛程
     * @param liveId
     * @param scheduleId
     * @return
     */
    @Update("update tb_lives set schedule_id=#{scheduleId} where live_id=#{liveId}")
    Integer modifyLiveScheduleId(@Param("liveId") Integer liveId,@Param("scheduleId") Integer scheduleId);

    @Select("select count(*) from tb_lives")
    Integer selectLiveCount();

    @Update("update tb_lives set status=1 where live_id=#{liveId}")
    Integer deleteLive(Integer liveId);

    @Select("SELECT t1.*,t2.game_date,t2.schedule_id ,t2.game_duration, t2.status AS scheduleStatus, t3.game_name, t3.game_icon,t4.*,t5.target_url,t6.content " +
            "FROM tb_lives t1 LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t2.game_id = t3.game_id " +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t2.team_id " +
            "LEFT JOIN tb_advertisings t5 ON t5.ad_id = t1.ad_id " +
            "LEFT JOIN tb_informations t6 ON t6.live_id = t1.live_id AND t6.game_id = t3.game_id" +
            " where t1.live_id=#{liveId}")
    LiveDetail queryLiveDetailByLiveId(Integer liveId);


    @Update("update tb_lives set live_title = #{liveTitle}" +
            ",live_date =#{liveDate},schedule_id=#{scheduleId}" +
            ",status=#{status},source_url=#{sourceUrl}" +
            ",ad_id=#{adId} where live_id=#{liveId}")
    Integer modifyLiveById(Live live);

    /**
     * 查询热门直播信息 DF 2018年12月17日23:43:07
     * @return
     */
    @Select("SELECT t1.*, t2.team_id, t3.game_name FROM tb_lives t1 " +
            "LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t3.game_id = t2.game_id " +
            "WHERE t1.status = 0 AND t2.is_delete = 0 AND t3.is_delete = 0 " +
            "ORDER BY t1.live_date ASC LIMIT 2")
    List<LiveHotDetail> selectHotLives();
}
