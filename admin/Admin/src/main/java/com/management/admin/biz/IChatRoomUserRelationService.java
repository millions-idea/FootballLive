package com.management.admin.biz;

import com.management.admin.entity.dbExt.ChatRoomDetail;

import java.util.List;

public interface IChatRoomUserRelationService {

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
    List<ChatRoomDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime,Integer roomId);


    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     * @return
     */
    Integer getCount(Integer roomId);


    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getLimitCount(String condition, Integer state, String beginTime, String endTime,Integer roomId);

    /**
     * 拉黑用户 狗蛋 2018年12月19日03:54:30
     * @param userId
     * @return
     */
    Integer shieldingUser(Integer userId);
}
