package com.management.admin.repository;

import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.dbExt.LiveHotDetail;
import com.management.admin.entity.resp.LiveInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/***
 * 直播信息mapper
 */
@Mapper
public interface LiveMapper extends MyMapper<Live>{

    @Select("SELECT t1.*,t2.game_date, t2.game_duration, t2.status AS scheduleStatus, t3.game_name, t3.game_icon,t4.* " +
            "FROM tb_lives t1 LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id  " +
            "LEFT JOIN tb_games t3 ON t2.game_id = t3.game_id " +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t2.team_id  where" +
            "  t1.status=0 and t3.is_delete=0 and t4.is_delete=0" +
            " and t2.is_delete=0 and ${condition} GROUP BY t1.live_id ORDER BY t1.add_date DESC LIMIT #{page},${limit}")
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
            "LEFT JOIN tb_teams t4 ON t4.team_id = t2.team_id where t1.status=0 and t3.is_delete=0 and t4.is_delete=0 " +
            "             and t2.is_delete=0 and ${condition}")
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
     * 查询总记录数
     * @return
     */
    @Select("select count(*) from tb_lives where status=0")
    Integer selectLiveCount();

    /**
     * 根据编号修改直播间标题
     * @param liveId
     * @param title
     * @return
     */
    @Update("update tb_lives set title=#{title} where live_id=#{liveId}")
    Integer modifyLiveTitleById(@Param("liveId") Integer liveId,@Param("title") String title);

    /**
     * 根据编号修改直播间状态
     * @param liveId
     * @param liveStatus
     * @return
     */
    @Update("update tb_lives set live_status=#{liveStatus} where live_id=#{liveId}")
    Integer modifyLiveStatusById(@Param("liveId") Integer liveId,@Param("liveStatus")Integer  liveStatus);
    /**
     * 根据编号修改直播赛程
     * @param liveId
     * @param scheduleId
     * @return
     */
    @Update("update tb_lives set schedule_id=#{scheduleId} where live_id=#{liveId}")
    Integer modifyLiveScheduleId(@Param("liveId") Integer liveId,@Param("scheduleId") Integer scheduleId);

    /**
     * 删除直播间 狗蛋 2018年12月19日02:44:36
     * @param liveId
     * @return
     */
    @Update("update tb_lives set status=1 where live_id=#{liveId}")
    Integer deleteLive(Integer liveId);

    /**
     * 根据编号查询相应的直播间
     * @param liveId
     * @return
     */
    @Select("SELECT t1.*,t2.game_date,t2.schedule_id ,t2.game_duration, t2.status AS scheduleStatus, t3.game_name, t3.game_icon,t4.*,t5.target_url,t6.content " +
            "FROM tb_lives t1 LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t2.game_id = t3.game_id " +
            "LEFT JOIN tb_teams t4 ON t4.team_id = t2.team_id " +
            "LEFT JOIN tb_advertisings t5 ON t5.ad_id = t1.ad_id " +
            "LEFT JOIN tb_informations t6 ON t6.live_id = t1.live_id AND t6.game_id = t3.game_id" +
            " where t1.live_id=#{liveId} and t1.status=0 LIMIT 1")
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
 /**
     * 查询直播间详情信息 DF 2018年12月18日15:27:04
     * @param liveId
     * @return
     */
    @Select("SELECT  " +
            " t1.live_id, t1.live_title, t1.live_date, t1.source_url, " +
            " t3.game_name, t2.game_date, t2.game_duration, t2.`status`, t2.schedule_result,  " +
            " t2.schedule_grade, t2.win_team_id, t2.team_id, " +
            " t4.type, t4.source_url AS playerAdUrl, t4.target_url AS playerAdTargetUrl, " +
            " t5.chat_room_id, t5.room_id, t5.frequency " +
            "FROM tb_lives t1 LEFT JOIN tb_schedules t2 ON t2.schedule_id = t1.schedule_id " +
            "LEFT JOIN tb_games t3 ON t3.game_id = t2.game_id " +
            "LEFT JOIN tb_advertisings t4 ON t4.ad_id = t1.ad_id " +
            "LEFT JOIN tb_chat_rooms t5 ON t5.live_id = t1.live_id " +
            "WHERE t1.live_id = #{liveId}")
    LiveInfo selectLiveDetailInfo(@Param("liveId") Integer liveId);

    @Insert("INSERT INTO tb_lives (live_title,live_date,schedule_id,status,share_count,collect_count,source_url,ad_id,add_date) " +
            "VALUES(#{liveTitle}, #{liveDate}, #{scheduleId}, #{status}, #{shareCount}, #{collectCount}, #{sourceUrl}, #{adId}, #{addDate})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertReturn(Live live);


    @Select("select * from tb_lives where status=0")
    List<Live> queryAll();

	@Select("select * from tb_lives where status=0")
    @Update("update tb_lives set ad_id=#{adId} where live_id=#{liveId} and status=0")
    Integer modifyAdvertising(@Param("adId") Integer adId,@Param("liveId") Integer liveId);

    /**
     * 增加收藏次数 DF 2018年12月20日02:40:34
     * @param liveId
     * @return
     */
    @Update("UPDATE tb_lives SET collect_count = collect_count + 1 WHERE live_id = #{liveId}")
    int addCollectCount(@Param("liveId") Integer liveId);

    /**
     * 减少收藏次数 DF 2018年12月20日02:42:30
     * @param liveId
     * @return
     */
    @Update("UPDATE tb_lives SET collect_count = collect_count - 1 WHERE live_id = #{liveId}")
    int reduceCollectCount(@Param("liveId") Integer liveId);

    /**
     * 是否删除直播间（正常，删除） DF 2018年12月20日02:52:35
     * @param liveId
     * @param status
     * @return
     */
    @Update("UPDATE tb_schedules SET `status` = #{status} WHERE game_id = " +
            "(SELECT game_id FROM tb_lives WHERE live_id = #{liveId})")
    int updateStatus(@Param("liveId") Integer liveId, @Param("status") int status);

    /**
     * 更新直播间状态为 未开始 DF 2018年12月20日02:52:35
     * @param liveId
     * @return
     */
    @Update("UPDATE tb_lives SET `live_status` = 0 WHERE live_id = #{liveId}")
    int beingLiveStatus(Integer liveId);

    /**
     * 更新直播间状态为 正在直播 DF 2018年12月20日02:52:35
     * @param liveId
     * @return
     */
    @Update("UPDATE tb_lives SET `live_status` = 1 WHERE live_id = #{liveId}")
    int endLiveStatus(Integer liveId);
}
