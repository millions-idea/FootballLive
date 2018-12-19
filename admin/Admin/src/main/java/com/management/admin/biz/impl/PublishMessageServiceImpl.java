package com.management.admin.biz.impl;

import com.management.admin.biz.IPublishMessageService;
import com.management.admin.entity.db.PublishMessage;
import com.management.admin.entity.db.PublishMessageRelation;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.PublishMessageDetail;
import com.management.admin.repository.PublishMessageMapper;
import com.management.admin.repository.PublishMessageRelationMapper;
import com.management.admin.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PublishMessageServiceImpl implements IPublishMessageService {

    private UserMapper userMapper;
    private PublishMessageMapper publishMessageMapper;
    private PublishMessageRelationMapper publishMessageRelationMapper;

    @Autowired
    public PublishMessageServiceImpl(UserMapper userMapper, PublishMessageMapper publishMessageMapper, PublishMessageRelationMapper publishMessageRelationMapper) {
        this.userMapper = userMapper;
        this.publishMessageMapper = publishMessageMapper;
        this.publishMessageRelationMapper = publishMessageRelationMapper;
    }

    /**
     * 推送消息
     * @param publishMessageDetail
     * @return
     */
    @Override
    @Transactional
    public Integer pushMessage(PublishMessageDetail publishMessageDetail) {
        // 2：遍历建立消息关系集合
        List<PublishMessageRelation> relationList = new ArrayList<>();
        publishMessageDetail.setAddDate(new Date());
        // 写入推送消息表
        Integer result2 = publishMessageMapper.insertPublishMessage(publishMessageDetail);
        // 如果填写手机号
        if(publishMessageDetail.getPhone()!=null&&publishMessageDetail.getPhone().length()>0){
            PublishMessageRelation relation = new PublishMessageRelation();
            User user = userMapper.selectUserByPhone(publishMessageDetail.getPhone());
            relation.setMsgId(publishMessageDetail.getMsgId());
            relation.setUserId(user.getUserId());
            relationList.add(relation);
        }else {
            // 没有手机号
            List<User> userList = userMapper.selectAll();
            userList.forEach(item ->{
                PublishMessageRelation relation = new PublishMessageRelation();
                relation.setMsgId(publishMessageDetail.getMsgId());
                relation.setUserId(item.getUserId());
                relation.setAddDate(new Date());
                relationList.add(relation);
            });
        }
        // 推送消息！ 1：获取所有的用户，


        // 写入到关系表
        Integer result = publishMessageRelationMapper.insertList(relationList);
        if(result>0&&result2>0){
            return 1;
        }
        return 0;
    }

    /**
     * 获取推送消息列表 DF 2018年12月19日06:19:32
     *
     * @param userId
     * @return
     */
    @Override
    public List<PublishMessageDetail> getPushMessageList(Integer userId) {
        return publishMessageRelationMapper.selectByUserId(userId);
    }

    /**
     * 签收指定消息 DF 2018年12月19日06:23:39
     *
     * @param relationId
     * @param userId
     * @return
     */
    @Override
    public boolean signMessage(Integer relationId, Integer userId) {
        return publishMessageRelationMapper.updateSignMessage(relationId, userId) > 0;
    }
}
