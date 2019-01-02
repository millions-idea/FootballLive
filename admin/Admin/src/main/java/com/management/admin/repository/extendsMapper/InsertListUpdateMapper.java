/***
 * @pName management
 * @name InsertListUpdateMapper
 * @user DF
 * @date 2018/8/27
 * @desc
 */
package com.management.admin.repository.extendsMapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.List;

@RegisterMapper
public interface InsertListUpdateMapper<T>  {
    @Options(useGeneratedKeys = false, keyProperty = "id")
    @InsertProvider(type = ExtendsInsertListProvider.class, method = "dynamicSQL")
    int inserUpdatetList(List<T> recordList);
}
