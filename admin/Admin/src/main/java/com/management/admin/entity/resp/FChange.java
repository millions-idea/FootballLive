/***
 * @pName Admin
 * @name FChange
 * @user HongWei
 * @date 2019/1/6
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FChange {
    private List<FChangeItem> c;
}
