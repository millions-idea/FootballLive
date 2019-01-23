/***
 * @pName Admin
 * @name TGroupResp
 * @user HongWei
 * @date 2019/1/22
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TGroupResp {
    private String actionStatus;
    private Integer errorCode;
    private String groupId;
}
