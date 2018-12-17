package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName AdminUser
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/17 14:36
 * Version 1.0
 **/

@Table(name =  "tb_admin_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminUser {
    @Id
    private Integer userId;
    private String phone;
    private String password;
    private Integer  status;
    private Integer type;
    private Date addDate;
    private Date editDate;
    private String remark;
}
