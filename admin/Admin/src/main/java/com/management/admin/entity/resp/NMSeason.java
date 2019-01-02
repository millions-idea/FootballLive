/***
 * @pName Admin
 * @name NMSeason
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
public class NMSeason {
    private NMSeasonAreas areas;
    private NMSeasonCountries countries;
    private NMSeasonCompetitions competitions;
}
