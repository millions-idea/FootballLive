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
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface InsertListUpdateScheduleMapper<T>  {
    @Options(useGeneratedKeys = false, keyProperty = "id")
    @InsertProvider(type = ExtendsInsertListScheduleProvider.class, method = "dynamicSQL")
    int inserUpdatetScheduleList(List<T> recordList);
}
