package com.management.admin.biz.impl;

import com.management.admin.biz.IChatRoomService;
import com.management.admin.entity.db.ChatRoom;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.resp.NAGroup;
import com.management.admin.entity.resp.NAMsg;
import com.management.admin.entity.template.Constant;
import com.management.admin.repository.ChatRoomMapper;
import com.management.admin.repository.utils.ConditionUtil;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.http.NeteaseImUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomServiceImpl implements IChatRoomService {


    private ChatRoomMapper chatRoomMapper;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomMapper chatRoomMapper) {
        this.chatRoomMapper = chatRoomMapper;
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
    public List<ChatRoomDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition, state, beginTime, endTime);
        List<ChatRoomDetail> list = chatRoomMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return chatRoomMapper.selectRoomCount();
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
        return chatRoomMapper.selectLimitCount(state, beginTime, endTime, where);
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
            where += " AND (" + ConditionUtil.like("live_id", condition, true, "t2");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t2");
                where += " OR " + ConditionUtil.like("room_id", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("live_title", condition, true, "t2") + ")";
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
     * 根据聊天室编号查询相应的聊天室详情 狗蛋 2018年12月19日02:53:14
     *
     * @param roomId
     * @return
     */
    @Override
    public ChatRoomDetail queryChatRoomById(Integer roomId) {
        return chatRoomMapper.queryChatRoomById(roomId);
    }

    /**
     * 发送信息
     * @param MsgPassword
     * @param liveId
     * @param msg
     * @return
     */
    public String sendMsg(String MsgPassword,Integer liveId,String msg){
        ChatRoom chatRoom = chatRoomMapper.selectByLive(liveId);

        if(chatRoom == null) return "直播间不存在";

        if(!MsgPassword.equals(Constant.MsgPassword)){
            return "发送密码错误！";
        }
        String body = "from=" +  Constant.HotAccId + "&ope=1"
                + "&to=" + chatRoom.getChatRoomId() + "&type=0" + "&body=" + "{\"msg\":\"" + msg + "\"}";

        System.out.println("假人气参数:" + body);

        String response = NeteaseImUtil.post("nimserver/msg/sendMsg.action", body);

        System.out.println("假人气响应:" + response);

        NAGroup model = JsonUtil.getModel(response, NAGroup.class);

        if (!model.getCode().equals(200)) return response;

        return null;
    }

    /**
     * 发送消息给所有直播间
     *
     * @param MsgPassword
     * @param liveId
     * @param msg
     */
    @Override
    public String sendMsgAllLive(String MsgPassword, Integer liveId, String msg) {
        List<ChatRoom> liveList =chatRoomMapper.selectAll();
        for (ChatRoom item:liveList) {

            String response = NeteaseImUtil.post("nimserver/msg/sendMsg.action", "from=" +  Constant.HotAccId + "&ope=1"
                    + "&to=" + item.getChatRoomId() + "&type=0" + "&body={\"msg\":\""+msg+"\"}");
            NAGroup model = JsonUtil.getModel(response, NAGroup.class);
            if (!model.getCode().equals(200)) return "同步云端数据失败";
        }

        return null;
    }
}
