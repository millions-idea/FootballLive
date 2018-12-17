/***
 * @pName Admin
 * @name FriendMapper
 * @user HongWei
 * @date 2018/12/7
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.Friend;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface FriendMapper extends MyMapper<Friend> {

}
