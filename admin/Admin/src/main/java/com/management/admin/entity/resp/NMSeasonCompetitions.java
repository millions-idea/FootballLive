/***
 * @pName Admin
 * @name NMSeasonCompetitions
 * @user HongWei
 * @date 2019/1/1
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NMSeasonCompetitions {
    private Integer id;
    private Integer area_id;
    private Integer country_id;
    private String name_zh;
    private String short_name_zh;
    private String name_zht;
    private String short_name_zht;
    private String name_en;
    private String short_name_en;
    private String logo;
    private List<NMSeasonItem> seasons;
}
