/***
 * @pName Live
 * @name GameToDay
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotGame {
    private Integer gameId;
    private String gameName;
    private String gameIcon;
    private Integer categoryId;

    private Integer scheduleCount;
}
