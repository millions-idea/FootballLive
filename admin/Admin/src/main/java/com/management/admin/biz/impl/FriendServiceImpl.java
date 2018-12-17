/***
 * @pName Admin
 * @name FriendServiceImpl
 * @user HongWei
 * @date 2018/12/7
 * @desc
 */
package com.management.admin.biz.impl;

import com.management.admin.biz.IFriendService;
import com.management.admin.entity.db.Friend;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.FriendDetail;
import com.management.admin.entity.resp.NAFriends;
import com.management.admin.entity.resp.NASignIn;
import com.management.admin.exception.InfoException;
import com.management.admin.repository.FriendMapper;
import com.management.admin.repository.UserMapper;
import com.management.admin.utils.DateConvertUtil;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.JsonUtil;
import com.management.admin.utils.http.NeteaseImUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements IFriendService {
    private final FriendMapper friendMapper;
    private final UserMapper userMapper;

    @Autowired
    public FriendServiceImpl(FriendMapper friendMapper, UserMapper userMapper) {
        this.friendMapper = friendMapper;
        this.userMapper = userMapper;
    }

    /**
     * 查询好友关系列表 DF 2018年12月7日05:34:06
     *
     * @param userId
     * @return
     */
    @Override
    public NAFriends getFriends(Integer userId) {
        // 1.查询用户基本信息
        User user = userMapper.selectByPrimaryKey(userId);

        // 2.查询云数据库上的好友关系名单
        Integer createTime = DateConvertUtil.DateToTimestamp(user.getAddDate());
        String response = NeteaseImUtil.post("nimserver/friend/get.action", "accid=" + user.getPhone() + "&createtime=" + createTime);
        NAFriends model = JsonUtil.getModel(response, NAFriends.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");
        return model;
    }

    /**
     * 设置黑名单/静音 DF 2018年12月7日06:04:00
     *
     * @param accId 用户帐号，最大长度32字符，必须保证一个 APP内唯一
     * @param targetAcc 被加黑或加静音的帐号
     * @param relationType 本次操作的关系类型,1:黑名单操作，2:静音列表操作
     * @param value 操作值，0:取消黑名单或静音，1:加入黑名单或静音
     * @return
     */
    @Override
    public NAFriends setSpecialRelation(String accId, String targetAcc, Integer relationType, Integer value) {
        String response = NeteaseImUtil.post("nimserver/user/setSpecialRelation.action",
                "accid=" + accId + "&targetAcc=" + targetAcc + "&relationType=" + relationType + "&value=" + value);
        NAFriends model = JsonUtil.getModel(response, NAFriends.class);
        if (!model.getCode().equals(200)) throw new InfoException("同步云端数据失败");
        return null;
    }
}
