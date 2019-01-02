package com.management.admin.apiController;

import com.alibaba.fastjson.JSON;
import com.management.admin.biz.impl.ScheduleServiceImpl;
import com.management.admin.entity.template.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
