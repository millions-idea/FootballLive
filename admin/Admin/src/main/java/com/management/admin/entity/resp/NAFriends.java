/***
 * @pName Admin
 * @name NAFirends
 * @user HongWei
 * @date 2018/12/7
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NAFriends {
    private Integer code;
    private Integer size;
    private List<Friend> friends;

    @Getter
    @Setter
    public class Friend{
        private Long createtime;
        private Boolean bidirection;
        private String faccid;
    }
}
