/***
 * @pName Admin
 * @name FriendDetail
 * @user HongWei
 * @date 2018/12/7
 * @desc
 */
package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendDetail {
    /*friends*/
    private Integer relationId;
    private Integer userId;
    private Integer friendId;
    private Date addDate;

    /*cloud*/
    private Long createtime;
    private Boolean bidirection;
    private String faccid;
}
