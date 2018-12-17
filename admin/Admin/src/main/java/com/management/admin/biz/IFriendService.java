/***
 * @pName Admin
 * @name IFriendService
 * @user HongWei
 * @date 2018/12/7
 * @desc
 */
package com.management.admin.biz;

import com.management.admin.entity.db.Friend;
import com.management.admin.entity.dbExt.FriendDetail;
import com.management.admin.entity.resp.NAFriends;

import java.util.List;

public interface IFriendService {
    /**
     * 查询好友关系列表 DF 2018年12月7日05:34:06
     * @param userId
     * @return
     */
    NAFriends getFriends(Integer userId);

    /**
     * 设置黑名单/静音 DF 2018年12月7日06:04:00
     *
     * @param accId 用户帐号，最大长度32字符，必须保证一个 APP内唯一
     * @param targetAcc 被加黑或加静音的帐号
     * @param relationType 本次操作的关系类型,1:黑名单操作，2:静音列表操作
     * @param value 操作值，0:取消黑名单或静音，1:加入黑名单或静音
     * @return
     */
    NAFriends setSpecialRelation(String accId, String targetAcc, Integer relationType, Integer value);
}
