package com.management.admin.repository;

import com.management.admin.entity.db.UserFeedback;
import com.management.admin.entity.dbExt.LiveDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserFeedbackMapper extends MyMapper<UserFeedback>{


    @Select("SELECT t1.* from tb_user_feedback t1 where ${condition} GROUP BY t1.feedback_id ORDER BY t1.add_date DESC LIMIT #{page},${limit}")
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
    List<UserFeedback> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.feedback_id) from tb_user_feedback t1 where ${condition}  ")
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

    /**
     * 查询总记录数
     * @return
     */
    @Select("select count(*) from tb_user_feedback")
    Integer selectLiveCount();

    /**
     * 根据编号查询相应的用户反馈
     * @param feedbackId
     * @return
     */
    @Select("select * from tb_user_feedback where feedback_id=#{feedbackId}")
    UserFeedback queryUserFeedbackById(Integer feedbackId);
}
