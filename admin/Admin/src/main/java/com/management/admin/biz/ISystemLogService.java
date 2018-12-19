package com.management.admin.biz;

import com.management.admin.entity.db.SystemLog;
import com.management.admin.entity.dbExt.LiveDetail;

import java.util.List;

public interface ISystemLogService {


    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    List<SystemLog> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime);


    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     * @return
     */
    Integer getCount();


    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getLimitCount(String condition, Integer state, String beginTime, String endTime);

    /**
     * 根据日志编号查询相应日志编号详情 狗蛋 2018年12月18日21:33:19
     * @param logId
     * @return
     */
    SystemLog querySystemLogById(Integer logId);
}
