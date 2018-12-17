package com.management.admin.biz.impl;

import com.management.admin.biz.IInformationService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.InformationDetail;
import com.management.admin.entity.enums.UserRoleEnum;
import com.management.admin.repository.InformationMapper;
import com.management.admin.repository.utils.ConditionUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationServiceImpl implements IInformationService {

    private InformationMapper informationMapper;

    public InformationServiceImpl(InformationMapper informationMapper){
        this.informationMapper = informationMapper;
    }



    /**
     * 添加情报
     *
     * @param information
     * @return
     */
    @Override
    public Integer insertInformation(Information information) {
        return informationMapper.insertInformation(information);
    }


    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param userRole
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<InformationDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        // 计算分页位置
        page = ConditionUtil.extractPageIndex(page, limit);
        String where = extractLimitWhere(condition,state, beginTime, endTime);
        List<InformationDetail> list = informationMapper.selectLimit(page, limit, state, beginTime, endTime, where);
        return list;
    }

    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     *
     * @return
     */
    @Override
    public Integer getCount() {
        return informationMapper.selectCount(new Information());
    }

    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param userRole
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public Integer getLimitCount(String condition,Integer state, String beginTime, String endTime) {
        String where = extractLimitWhere(condition,state, beginTime, endTime);
        return informationMapper.selectLimitCount(state, beginTime, endTime, where);
    }

    /**
     * 添加情报
     *
     * @param information
     * @return
     */
    @Override
    public Integer InsertInformation(Information information) {
        return informationMapper.insertInformation(information);
    }

    /**
     * 修改情报内容
     *
     * @param information
     * @return
     */
    @Override
    public Integer modifyInfromation(Information information) {
        return informationMapper.modifyInformationById(information);
    }

    /**
     * 删除情报内容
     *
     * @param informationId
     * @return
     */
    @Override
    public Integer deleteInformation(Integer informationId) {
        return informationMapper.deleteInformationById(informationId);
    }

    /**
     * 提取分页条件
     * @return
     */
    private String extractLimitWhere(String condition, Integer isEnable,  String beginTime, String endTime) {
        // 查询模糊条件
        String where = " 1=1";
        if(condition != null) {
            condition = condition.trim();
            where += " AND (" + ConditionUtil.like("informationId", condition, true, "t1");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t1");
                where += " OR " + ConditionUtil.like("update_date", condition, true, "t1");
            }
            where += " OR " + ConditionUtil.like("live_id", condition, true, "t1");
            where += " OR " + ConditionUtil.like("game_id", condition, true, "t1")+ ")";
        }

        // 取两个日期之间或查询指定日期
        where = extractBetweenTime(beginTime, endTime, where);
        return where.trim();
    }


    /**
     * 提取两个日期之间的条件
     * @return
     */
    private String extractBetweenTime(String beginTime, String endTime, String where) {
        if ((beginTime != null && beginTime.contains("-")) &&
                endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (beginTime != null && beginTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }else if (endTime != null && endTime.contains("-")){
            where += " AND t1.add_date BETWEEN #{beginTime} AND #{endTime}";
        }
        return where;
    }
}
