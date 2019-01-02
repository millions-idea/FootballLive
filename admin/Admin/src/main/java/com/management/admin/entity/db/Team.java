/***
 * @pName Admin
 * @name Team
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_teams")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Team {
    private Integer teamId;
    private String teamName;
    private String teamIcon;
    private Integer gameId;
    private Integer cloudId;
    private Date editDate;

}
