/***
 * @pName Admin
 * @name NAChatRoomData
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NAChatRoomData {
    private Integer roomId;
    private Boolean valid;
    private String announcement;
    private String name;
    private String broadcasturl;
    private String ext;
    private String creator;
}
