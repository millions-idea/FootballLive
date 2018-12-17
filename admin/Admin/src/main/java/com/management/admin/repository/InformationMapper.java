package com.management.admin.repository;

import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.InformationDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InformationMapper extends MyMapper<Information> {

    /**
     * 添加情报
     * @param information
     * @return
     */
    @Insert("insert into tb_informations(game_id,live_id,content) values(gameId,liveId,content)")
    Integer insertInformation(Information information);

    @Update("update tb_informations set live_id=#{liveId},game_id=#{gameId}" +
            ",content=#{content} where information_id = #{informationId} and is_delete=0")
    Integer modifyInformationById(Information informationId);

    @Select("select * from tb_information where is_delete=0")
    List<Information> queryAll();

    @Update("update tb_informations set is_delete=1 where information_id=#{informationId}")
    Integer deleteInformationById(Integer informationId);

    @Select("SELECT t1.*,t2.live_date, t2.live_title, t2.status AS scheduleStatus, t3.game_name, t3.game_icon\n" +
            "FROM tb_informations t1 LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id and t2.status=0 \n" +
            "LEFT JOIN tb_games t3 ON t1.game_id = t3.game_id and t3.is_delete=0 \n " +
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

    @Select("SELECT COUNT(t1.user_id) FROM tb_informations t1 LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id and t2.status=0 \n" +
            "LEFT JOIN tb_games t3 ON t1.game_id = t3.game_id t3.is_delete=0 "+
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

    @Select("SELECT t1.*,t2.live_date, t2.live_title, t2.status AS scheduleStatus, t3.game_name, t3.game_icon\\n\" +\n" +
            "            \"FROM tb_informations t1 LEFT JOIN tb_lives t2 ON t2.live_id = t1.live_id and t2.status=0 \\n\" +\n" +
            "            \"LEFT JOIN tb_games t3 ON t1.game_id = t3.game_id and t3.is_delete=0 where informationId=#{informationId}")
    InformationDetail queryInformationById(Integer informationId);
}
