package com.management.admin.repository;

import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.InformationDetail;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InformationMapper extends MyMapper<Information> {

    /**
     * 添加情报
     * @param information
     * @return
     */
    @Insert("insert into tb_informations(game_id,live_id,content,add_date) values(#{gameId},#{liveId},#{content},#{addDate})")
    Integer insertInformation(Information information);

    @Update("update tb_informations set live_id=#{liveId},game_id=#{gameId}" +
            ",content=#{content} where isr_id = #{isrId} and is_delete=0")
    Integer modifyInformationById(Information informationId);

    @Select("select * from tb_information where is_delete=0")
    List<Information> queryAll();

    @Update("update tb_informations set is_delete=1 where isr_id=#{informationId}")
    Integer deleteInformationById(Integer informationId);

    @Select("SELECT t1.*,t2.live_date, t2.live_title, t2.status AS scheduleStatus, t3.game_name, t3.game_icon " +
            "FROM tb_informations t1 LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id and t2.status=0 " +
            "LEFT JOIN tb_games t3 ON t1.game_id = t3.game_id  " +
            "WHERE ${condition} and t1.is_delete=0 GROUP BY t1.isr_id ORDER BY t1.add_date DESC LIMIT #{page},${limit}")
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
    List<InformationDetail> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.isr_id) FROM tb_informations t1 LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id and t2.status=0  " +
            "LEFT JOIN tb_games t3 ON t1.game_id = t3.game_id "+
            "WHERE ${condition} and t1.is_delete=0")
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

    @Select("SELECT t1.*,t2.live_date, t2.live_title, t2.status AS scheduleStatus, t3.game_name, t3.game_icon " +
            "FROM tb_informations t1 LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id and t2.status=0  " +
            "LEFT JOIN tb_games t3 ON t1.game_id = t3.game_id and t3.is_delete=0 where isr_id=#{informationId}")
    InformationDetail queryInformationById(Integer informationId);

    /**
     * 查询直播间情报信息 DF 2018年12月18日20:50:17
     * @param liveId
     * @return
     */
    @Select("SELECT * FROM tb_informations WHERE live_id=#{liveId}")
    Information selectByLiveId(@Param("liveId") Integer liveId);

    /**
     * 查询赛程信息列表 DF 2018年12月18日02:26:40
     * @param condition
     * @return
     */
    @Select("SELECT *,t5.team_icon AS winTeamIcon, t5.team_name AS winTeamName FROM tb_informations t1 " +
            "LEFT JOIN  tb_lives t2 ON t2.live_id = t1.live_id " +
            "LEFT JOIN tb_schedules t3 ON t3.schedule_id = t2.schedule_id " +
            "LEFT JOIN tb_games t4 ON t4.game_id = t1.game_id " +
            "LEFT JOIN tb_teams t5 ON t5.team_id = t3.win_team_id " +
            "WHERE t1.is_delete = 0 AND t2.status = 0 AND t3.is_delete = 0 AND t4.is_delete = 0  " +
            "AND ${condition} AND (t3.schedule_result IS NOT NULL OR t3.schedule_grade IS NOT NULL) " +
            "ORDER BY t2.live_date ")
    List<LiveScheduleDetail> selectInformationDetailList(@Param("gameId") Integer gameId, @Param("categoryId") Integer categoryId,
                                                      @Param("condition") String condition);
}
