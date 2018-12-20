package com.management.admin.entity.db;

import lombok.*;
import org.omg.CORBA.INTERNAL;

import javax.persistence.Table;

/**
 * @ClassName LiveCategory
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/18 1:43
 * Version 1.0
 **/

@Table(name = "tb_live_categorys")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LiveCategory {
    private Integer categoryId;

    private String categoryName;

    private String categoryBackgroundImageUrl;

    private Integer sort;

    private Integer isDelete;
}
