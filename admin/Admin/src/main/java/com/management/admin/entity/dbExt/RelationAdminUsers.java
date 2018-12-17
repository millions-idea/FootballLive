package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;

import java.util.Date;

/**
 * @ClassName RelationAdminUsers
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/17 20:52
 * Version 1.0
 **/
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RelationAdminUsers {
    private Integer userId;
    private Integer permissionGroupId;
    private String phone;
    private String password;
    private Integer  status;
    private Integer type;
    private Date addDate;
    private Date editDate;
}
