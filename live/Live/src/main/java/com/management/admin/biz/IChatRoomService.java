/***
 * @pName Live
 * @name IChatRoomService
 * @user HongWei
 * @date 2019/1/24
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.ChatRoom;

public interface IChatRoomService {
    /**
     * 是否在黑名单中 DF 2019年1月24日15:19:58
     * @param userId
     * @param liveId
     * @return
     */
    boolean isBlack(Integer userId, Integer liveId);

    ChatRoom getTencentChatRoom(Integer liveId);
}
