/***
 * @pName Admin
 * @name NMOdds
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
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NMOdds {
    private Map<String, NMOddsTeams> teams;
    private Map<String, NMOddsEvents> events;
    private List<List<String>> matches;
}
