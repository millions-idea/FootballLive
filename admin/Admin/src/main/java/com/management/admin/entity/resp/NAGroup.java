/***
 * @pName Admin
 * @name NAGroup
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NAGroup {
    private Integer code;
    private String tid;
    private faccid faccid;
    private String roomid;

    @Getter
    @Setter
    public class faccid{
        private String[] accid;
        private String msg;
    }
}
