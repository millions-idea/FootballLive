/***
 * @pName Admin
 * @name NMAsyncSchedule
 * @user HongWei
 * @date 2019/1/2
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
public class NMAsyncSchedule {
    private Integer namiScheduleId;
    private Integer status;
    private Integer masterGrade;
    private Integer masterRedChess;
    private Integer masterYellowChess;
    private Integer masterCornerKick;
    private Integer targetGrade;
    private Integer targetRedChess;
    private Integer targetYellowChess;
    private Integer targetCornerKick;
    private Integer masterTeamId;
    private Integer targetTeamId;
}
