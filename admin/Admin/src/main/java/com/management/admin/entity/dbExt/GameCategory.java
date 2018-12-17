package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName GameCategory
 * @Description 赛事信息与分类封装类
 * @Author ZXL01
 * @Date 2018/12/18 4:06
 * Version 1.0
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameCategory {
    private Integer gameId;

    private String gameName;

    private String gameIcon;

    private Integer categoryId;

    private Integer isDelete;

    private String categoryName;

    private String categoryBackgroundImage;

    private Integer sort;

}
