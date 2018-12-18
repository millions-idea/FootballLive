package com.management.admin.biz;

import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.InformationDetail;
import com.management.admin.entity.enums.UserRoleEnum;

import java.util.List;

public interface IInformationService {
    /**
     * 添加情报
     * @param information
     * @return
     */
    Integer insertInformation(Information information);

    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    List<InformationDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime);


    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     * @return
     */
    Integer getCount();


    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getLimitCount(String condition, Integer state, String beginTime, String endTime);

    /**
     * 添加情报
     * @param information
     * @return
     */
    Integer InsertInformation(Information information);

    /**
     * 修改情报内容
     * @param information
     * @return
     */
    Integer modifyInfromation(Information information);

    /**
     * 删除情报内容
     * @param informationId
     * @return
     */
    Integer deleteInformation(Integer informationId);

    /**
     * 获取直播间情报信息 DF 2018年12月18日20:49:22
     * @param liveId
     * @return
     */
    Information getLiveInformation(Integer liveId);
}
