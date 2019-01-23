/***
 * @pName Admin
 * @name TMessageBody
 * @user HongWei
 * @date 2019/1/22
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TMessageBody {
    private String msgType;
    private TMessageContent msgContent;
}
