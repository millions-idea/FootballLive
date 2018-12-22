package com.management.admin.biz.impl;

import com.management.admin.biz.ISystemLogService;
import com.management.admin.entity.db.SystemLog;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.dbExt.SystemLogDetail;
import com.management.admin.repository.SystemLogMapper;
import com.management.admin.repository.utils.ConditionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements ISystemLogService {



    private SystemLogMapper systemLogMapper;

    @Autowired
    public SystemLogServiceImpl(SystemLogMapper systemLogMapper) {
        this.systemLogMapper = systemLogMapper;
    }

    /**
     * 根据日志编号查询相应日志编号详情 狗蛋 2018年12月18日21:33:19
     *
     * @param logId
     * @return
     */
    @Override
    public SystemLogDetail querySystemLogById(Integer logId) {
        return systemLogMapper.querySystemLogById(logId);
    }

    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     *
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<SystemLogDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        List<SystemLogDetail> list = systemLogMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return systemLogMapper.selectSystemLogCount();
    }

    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     *
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getLimitCount(String condition, Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        return systemLogMapper.selectLimitCount(state, beginTime, endTime, where);
    }
    /**
     * 提取分页条件
     * @return
     */
    private String extractLimitWhere(String condition, Integer isEnable,  String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if(condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("log_id", condition, true, "t1");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t1");
                where += " OR " + ConditionUtil.like("section", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("content", condition, true, "t1")+")";
        }
        // 取两个日期之间或查询指定日期
        where = extractBetweenTime(beginTime, endTime, where);
        return where.trim();
    }
    /**
     * 提取两个日期之间的条件
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (beginTime != null && beginTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }

    /**
     * 添加系统日志 狗蛋 2018年12月21日19:54:29
     *
     * @param systemLog
     * @return
     */
    @Override
    public Integer insertSystemLog(SystemLog systemLog) {
        return systemLogMapper.insertSystemLog(systemLog);
    }
}
