package com.management.admin.biz.impl;

import com.management.admin.biz.IChatRoomUserRelationService;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.repository.ChatRoomUserRelationMapper;
import com.management.admin.repository.utils.ConditionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomUserRelationServiceImpl implements IChatRoomUserRelationService {


    private ChatRoomUserRelationMapper relationMapper;

    @Autowired
    public ChatRoomUserRelationServiceImpl(ChatRoomUserRelationMapper relationMapper) {
        this.relationMapper = relationMapper;
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
    public List<ChatRoomDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime, Integer roomId) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        List<ChatRoomDetail> list = relationMapper.selectLimit(page, limit, state, beginTime, endTime, where,roomId);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount(Integer roomId) {
        return relationMapper.selectChatRoomUserCount(roomId);
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
    public Integer getLimitCount(String condition, Integer state, String beginTime, String endTime,Integer roomId) {
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        return relationMapper.selectLimitCount(state, beginTime, endTime, where,roomId);
    }

    /**
     * 拉黑用户 狗蛋 2018年12月19日03:54:30
     *
     * @param userId
     * @return
     */
    @Override
    public Integer shieldingUser(Integer userId) {
        return relationMapper.ShieldingUser(userId);
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
            where += " AND (" + ConditionUtil.like("user_id", condition, true, "t3");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t3");
                where += " OR " + ConditionUtil.like("nick_name", condition, true, "t3");
            }
            where += " OR " + ConditionUtil.like("nick_name", condition, true, "t4") + ")";
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
}
