/***
 * @pName Live
 * @name HotCategory
 * @user HongWei
 * @date 2019/1/12
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotCategory {
    private Integer categoryId;
    private String categoryName;
    private String categoryBackgroundImageUrl;
    private Integer sort;
    private Integer isDelete;
    private Integer isShow;
    private Integer isLeft;
    private Integer isMain;

    private Integer scheduleCount;
}
