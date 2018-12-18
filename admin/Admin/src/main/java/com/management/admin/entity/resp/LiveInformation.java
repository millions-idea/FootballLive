/***
 * @pName Admin
 * @name LiveInformation
 * @user HongWei
 * @date 2018/12/18
 * @desc
 */
package com.management.admin.entity.resp;

import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveInformation {
    /**
     * 情报信息(可空)
     */
    private Information information;
}
