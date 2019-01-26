/***
 * @pName Admin
 * @name LiveHistory
 * @user HongWei
 * @date 2018/12/20
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_live_historys")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveHistory {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Integer historyId;
    /**
     * 直播间id
      */
    private Integer liveId;
    /**
     * 用户id
     */
    private Integer userId;

    private Integer scheduleId;

}
