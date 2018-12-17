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

    @Select("select t2.live_id,t2.live_title,t1.type,t1.source_url,\n" +
            "t1.target_url from tb_advertisings as t1 left join tb_lives as t2 on\n" +
            "t2.ad_id = t1.ad_id and t2.is_delete=0 \n " +
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

    @Select("select t2.live_id,t2.live_title,t1.type,t1.source_url,\\n\" +\n" +
            "            \"t1.target_url from tb_advertisings as t1 left join tb_lives as t2 on\\n\" +\n" +
            "            \"t2.ad_id = t1.ad_id and t2.is_delete=0 \\n \" +\n" +
            "            \"WHERE ${condition} and t1.is_delete=0 ")
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

    @Update("update tb_advertising set type=#{type},source_url=#{sourceUrl}" +
            ",target_url=#{targetUrl} where ad_id = #{adId}")
    Integer modifyAdvertising(Advertising advertising);

    @Update("update tb_advertising set is_delete=1")
    Integer deleteAdvertisingById(Integer adId);

    @Select("select t2.live_id,t2.live_title,t1.type,t1.source_url,\\n\" +\n" +
            "            \"t1.target_url from tb_advertisings as t1 left join tb_lives as t2 on\\n\" +\n" +
            "            \"t2.ad_id = t1.ad_id and t2.is_delete=0 where advertising_id=#{advertisingId} ")
    AdvertisingDetail queryAdvertisingById(Integer advertisingId);
}
