/***
 * @pName Admin
 * @name TMessageParam
 * @user HongWei
 * @date 2019/1/22
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TMessageParam {
    private String groupId;
    private Integer random;
    private TMessageBody msgBody;

}
