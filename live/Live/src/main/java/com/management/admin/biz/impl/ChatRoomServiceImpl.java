/***
 * @pName Live
 * @name ChatRoomServiceImpl
 * @user HongWei
 * @date 2019/1/24
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IChatRoomService;
import com.management.admin.entity.db.ChatRoom;
import com.management.admin.entity.db.ChatRoomUserRelation;
import com.management.admin.repository.ChatRoomMapper;
import com.management.admin.repository.ChatRoomRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomServiceImpl implements IChatRoomService {
    private final ChatRoomRelationMapper chatRoomRelationMapper;
    private final ChatRoomMapper chatRoomMapper;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomRelationMapper chatRoomRelationMapper, ChatRoomMapper chatRoomMapper) {
        this.chatRoomRelationMapper = chatRoomRelationMapper;
        this.chatRoomMapper = chatRoomMapper;
    }

    /**
     * 是否在黑名单中 DF 2019年1月24日15:19:58
     *
     * @param userId
     * @param liveId
     * @return
     */
    @Override
    public boolean isBlack(Integer userId, Integer liveId) {
        ChatRoomUserRelation chatRoomUserRelation = chatRoomRelationMapper.isBlack(userId, liveId);
        return chatRoomUserRelation != null;
    }

    @Override
    public ChatRoom getTencentChatRoom(Integer liveId) {
        List<ChatRoom> chatRooms = chatRoomMapper.selectByLive(liveId);
        if(chatRooms != null && chatRooms.size() > 0 ){
            Optional<ChatRoom> first = chatRooms.stream().filter(item -> item.getChatRoomId().contains("@")).findFirst();
            if(first.isPresent()){
                return first.get();
            }
        }
        return null;
    }
}
