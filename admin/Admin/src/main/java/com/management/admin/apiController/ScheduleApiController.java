package com.management.admin.apiController;

import com.alibaba.fastjson.JSON;
import com.management.admin.biz.impl.ScheduleServiceImpl;
import com.management.admin.entity.dbExt.LiveScheduleDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ScheduleApiController
 * @Description 赛程
 * @Author ZXL01
 * @Date 2018/12/27 17:02
 * Version 1.0
 **/

@RestController
@RequestMapping("api/schedule")
public class ScheduleApiController {
    @Autowired
    private ScheduleServiceImpl scheduleService;

    /**
     * 修改赛程状态为正在直播 timor 2018-12-27 17:19:562
     * @return
     */
    @GetMapping("beingschedule")
    public JsonResult beingSchedule(Integer scheduleId){
        Integer result=scheduleService.beingSchedule(scheduleId);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 修改赛程状态为已经结束 timor 2018-12-27 17:20:562
     * @return
     */
    @GetMapping("endschedule")
    public JsonResult endSchedule(Integer scheduleId){
        Integer result=scheduleService.endSchedule(scheduleId);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 同步赛程列表 DF 2019年1月2日20:42:54
     * @return
     */
    @GetMapping("asyncScheduleList")
    public JsonResult asyncScheduleList(){
        scheduleService.asyncScheduleList();
        return JsonResult.successful();
    }

    @GetMapping("asyncFeiJingScheduleList")
    public JsonResult asyncFeiJingScheduleList(){
        scheduleService.asyncFeiJingScheduleList();
        return JsonResult.successful();
    }

    /**
     * 根据时间查询赛事赛程列表 DF 2019年1月4日14:13:55
     * @param date
     * @return
     */
    @GetMapping("getSchedules")
    public JsonArrayResult<LiveScheduleDetail> getSchedules(String date){
        List<LiveScheduleDetail> scheduleDetailList = scheduleService.getScheduleDetailList(null, null, date);
        scheduleDetailList.stream().forEach(item -> item.setLiveTitle(item.getMasterTeamName() + " VS " + item.getTargetTeamName()));
        return new JsonArrayResult<>(scheduleDetailList);
    }


}
