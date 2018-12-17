package com.management.admin.repository;

import com.management.admin.entity.db.Advertising;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdvertisingMapper extends MyMapper<Advertising> {

    /**
     * 查询所有广告信息
     * @return
     */
    @Select("select * from tb_advertisings")
    List<Advertising> queryAllAdvertising();
}
