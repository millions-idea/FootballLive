/***
 * @pName Admin
 * @name NMOddsTeams
 * @user HongWei
 * @date 2019/1/1
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
public class NMOddsTeams {
    private Integer id;
    private String name_zh;
    private String name_zht;
    private String name_en;
    private String logo;
    private Integer matchevent_id;
}