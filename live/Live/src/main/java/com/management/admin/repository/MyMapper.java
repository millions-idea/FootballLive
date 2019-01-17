package com.management.admin.repository;

import com.management.admin.repository.extendsMapper.InsertListUpdateMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>, InsertListUpdateMapper<T> {
    //TODO 特别注意，该接口不能被扫描到，否则会出错
}
