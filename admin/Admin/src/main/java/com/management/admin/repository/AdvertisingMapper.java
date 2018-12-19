package com.management.admin.repository;

import com.management.admin.entity.db.Advertising;
import com.management.admin.entity.dbExt.AdvertisingDetail;
import com.management.admin.entity.dbExt.InformationDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdvertisingMapper extends MyMapper<Advertising> {

    /**
     * 查询所有广告信息
     * @return
     */
    @Select("select * from tb_advertisings")
    List<Advertising> queryAllAdvertising();

    @Select("select t1.ad_id,t2.live_id,t2.live_title,t1.type,t1.source_url," +
            "t1.target_url from tb_advertisings as t1 left join tb_lives as t2 on " +
            "t2.ad_id = t1.ad_id  " +
            "WHERE ${condition} and t1.is_delete=0 and t2.status=0 GROUP BY t1.ad_id DESC LIMIT #{page},${limit}")
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
    List<AdvertisingDetail> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("select t2.live_id,t2.live_title,t1.type,t1.source_url," +
                       "t1.target_url from tb_advertisings as t1 left join tb_lives as t2 on " +
                       "t2.ad_id = t1.ad_id " +
                       "WHERE ${condition} and t1.is_delete=0 and t2.status=0 ")
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

    @Select("select count(*) from tb_advertisings where is_delete=0")
    Integer selectAdvertisingCount();

    @Update("update tb_advertisings set type=#{type},source_url=#{sourceUrl}" +
            ",target_url=#{targetUrl} where ad_id = #{adId} and is_delete=0")
    Integer modifyAdvertising(AdvertisingDetail advertising);

    @Update("update tb_advertisings set is_delete=1 where ad_id=#{adId}")
    Integer deleteAdvertisingById(Integer adId);

    @Select("select t2.live_id,t2.live_title,t1.ad_id,t1.type,t1.source_url," +
            "t1.target_url from tb_advertisings as t1 left join tb_lives as t2 on " +
            " t2.ad_id = t1.ad_id where t1.ad_id=#{advertisingId}")
    AdvertisingDetail queryAdvertisingById(Integer advertisingId);

    @Insert("insert into tb_advertisings(type,source_url,target_url) values(#{type},#{sourceUrl},#{targetUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "adId")
    Integer insertAdvertising(AdvertisingDetail advertising);
}
