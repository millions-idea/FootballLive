/***
 * @pName Admin
 * @name LiveCollect
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_live_collects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveCollect {
    private Integer collectId;
    private Integer userId;
    private Integer liveId;
    private Integer scheduleId;
    private Integer isCancel;
    private Date addDate;
    private Date editDate;
}
