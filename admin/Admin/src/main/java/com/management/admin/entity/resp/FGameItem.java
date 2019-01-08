/***
 * @pName Admin
 * @name FSchedules
 * @user HongWei
 * @date 2019/1/5
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FGameItem {
   private List<FGameMetch> match;
}
