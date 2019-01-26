/***
 * @pName Live
 * @name LiveHistory
 * @user HongWei
 * @date 2019/1/25
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_live_historys")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveHistory {
    private Integer historyId;
    private Integer liveId;
    private Integer userId;
    private Date activeDate;
    private Integer scheduleId;
}
