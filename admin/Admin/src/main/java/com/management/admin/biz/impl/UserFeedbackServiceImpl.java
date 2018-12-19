package com.management.admin.biz.impl;

import com.management.admin.biz.IUserFeedbackService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.User;
import com.management.admin.entity.db.UserFeedback;
import com.management.admin.entity.enums.UserRoleEnum;
import com.management.admin.repository.UserFeedbackMapper;
import com.management.admin.repository.utils.ConditionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFeedbackServiceImpl implements IUserFeedbackService {

    private UserFeedbackMapper feedbackMapper;

    @Autowired
    public UserFeedbackServiceImpl(UserFeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }

    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param userRole
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<UserFeedback> getLimit(Integer page, String limit, String condition,Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition,state, beginTime, endTime);
        List<UserFeedback> list = feedbackMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return feedbackMapper.selectCount(new UserFeedback());
    }

    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getLimitCount(String condition, Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        return feedbackMapper.selectLimitCount(state, beginTime, endTime, where);
    }

    private String extractLimitWhere(String condition,Integer isEnable,  String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if(condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("user_id", condition, true, "t1");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t1");
                where += " OR " + ConditionUtil.like("update_date", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("user_nick", condition, true, "t1");
            where += " OR " + ConditionUtil.like("user_phone", condition, true, "t1");
            where += " OR " + ConditionUtil.like("ip", condition, true, "t1") + ")";
        }

/*
        if(userRole.equals(UserRoleEnum.SuperAdmin)){
            where +=" AND (" +ConditionUtil.match("type", "1", true, "t1");
            where +=" OR " +ConditionUtil.match("type", "2", true, "t1") + ")";
        }else{
        }
*/

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
     * 根据用户反馈编号查询相应的用户反馈
     *
     * @param feedbackId
     * @return
     */
    @Override
    public UserFeedback queryUserFeedbackById(Integer feedbackId) {
        return feedbackMapper.queryUserFeedbackById(feedbackId);
    }
}
