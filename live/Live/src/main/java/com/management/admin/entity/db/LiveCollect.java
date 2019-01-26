/***
 * @pName Live
 * @name LiveCollect
 * @user HongWei
 * @date 2019/1/24
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
import java.util.Date;

@Table(name = "tb_live_collects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiveCollect {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    private Integer collectId;
    private Integer userId;
    private Integer liveId;
    private Integer scheduleId;
    private Integer isCancel;
    private Date addDate;
    private Date editDate;
}
