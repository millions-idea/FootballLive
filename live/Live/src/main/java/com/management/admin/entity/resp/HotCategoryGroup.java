/***
 * @pName Live
 * @name HotScheduleGroup
 * @user HongWei
 * @date 2019/1/12
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
public class HotCategoryGroup {
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
