package com.management.admin.biz;


import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.GameCategory;

import java.util.List;

/**
 * @ClassName ICompetitionService
 * @Description 赛事管理接口
 * @Author ZXL01
 * @Date 2018/12/17 14:38
 * Version 1.0
 **/

/**
 * 查询赛事信息
 */
public interface ICompetitionService {

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    List<GameCategory> getCompetitionLimit(Integer page, String limit);

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    Integer getCompetitionLimitCount();

    /**
     * 查询赛事信息记录总数 DF 2018-12-17 14:43:462
     * @return
     */
    Integer getAdminCount();


}
