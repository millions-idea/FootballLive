/***
 * @pName Admin
 * @name FToDay
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
public class FToDay {
    private List<FToDayItem> list;
}
