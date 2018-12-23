package com.management.admin.biz;

import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;

import java.util.List;

public interface IChatRoomService {

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
    List<ChatRoomDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime);


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
     * 根据聊天室编号查询相应的聊天室详情 狗蛋 2018年12月19日02:53:14
     * @param roomId
     * @return
     */
    ChatRoomDetail queryChatRoomById(Integer roomId);

    /**
     * 发送消息给直播间
     * @param MsgPassword
     * @param liveId
     * @param msg
     * @return
     */
    String sendMsg(String MsgPassword,Integer liveId,String msg);

    /**
     * 发送消息给所有直播间
     */

    String sendMsgAllLive(String MsgPassword,Integer liveId,String msg);
}
