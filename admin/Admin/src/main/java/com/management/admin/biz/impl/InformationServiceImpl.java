package com.management.admin.biz.impl;

import com.management.admin.biz.IInformationService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Information;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.InformationDetail;
import com.management.admin.entity.enums.UserRoleEnum;
import com.management.admin.repository.GameMapper;
import com.management.admin.repository.InformationMapper;
import com.management.admin.repository.LiveMapper;
import com.management.admin.repository.utils.ConditionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class InformationServiceImpl implements IInformationService {

    private InformationMapper informationMapper;
    private LiveMapper liveMapper;
    private GameMapper gameMapper;

    @Autowired
    public InformationServiceImpl(InformationMapper informationMapper, LiveMapper liveMapper, GameMapper gameMapper) {
        this.informationMapper = informationMapper;
        this.liveMapper = liveMapper;
        this.gameMapper = gameMapper;
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
        information.setAddDate(new Date());
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
            where += " AND (" + ConditionUtil.like("isr_id", condition, true, "t1");
            if (condition.split("-").length == 2){
                where += " OR " + ConditionUtil.like("add_date", condition, true, "t1");
                where += " OR " + ConditionUtil.like("update_date", condition, true, "t1");
                where += " OR " + ConditionUtil.like("like_title", condition, true, "t2");
                where += " OR " + ConditionUtil.like("content", condition, true, "t1");
                where += " OR " + ConditionUtil.like("status", condition, true, "t2");
            }
            where += " OR " + ConditionUtil.like("live_id", condition, true, "t1");
            where += " OR " + ConditionUtil.like("game_name", condition, true, "t3");
            where += " OR " + ConditionUtil.like("game_id", condition, true, "t3")+ ")";
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

    /**
     * 根据编号查询情报并封装
     * @param isrId
     * @return
     */
    @Override
    @Transactional
    public InformationDetail queryInformationById(Integer isrId) {
        InformationDetail informationDetail = informationMapper.queryInformationById(isrId);
        if(informationDetail.getScheduleStatus()==0){
            informationDetail.setScheduleStatusStr("未开始");
        }else if(informationDetail.getScheduleStatus()==1){
            informationDetail.setScheduleStatusStr("正在直播");
        }else {
            informationDetail.setScheduleStatusStr("已结束");
        }
        return informationDetail;
    }
}
