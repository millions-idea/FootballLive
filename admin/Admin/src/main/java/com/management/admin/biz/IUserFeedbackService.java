package com.management.admin.biz;

import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.User;
import com.management.admin.entity.db.UserFeedback;
import com.management.admin.entity.enums.UserRoleEnum;

import java.util.List;

public interface IUserFeedbackService {
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
    List<UserFeedback> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime);

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
    Integer getLimitCount(String condition,Integer state, String beginTime, String endTime);

    /**
     * 根据用户反馈编号查询相应的用户反馈
     * @param feedbackId
     * @return
     */
    UserFeedback queryUserFeedbackById(Integer feedbackId);

    /**
     * 添加反馈 DF 2018年12月20日07:55:20
     * @param userId
     * @param content
     * @return
     */
    boolean addFeedback(Integer userId, String content);
}
