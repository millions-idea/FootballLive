/***
 * @pName Admin
 * @name Friend
 * @user HongWei
 * @date 2018/12/7
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_friends")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private Integer relationId;
    private Integer userId;
    private Integer friendId;
    private Date addDate;
}
