/***
 * @pName Live
 * @name ScheduleApiController
 * @user HongWei
 * @date 2019/1/10
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.IScheduleService;
import com.management.admin.controller.BaseController;
import com.management.admin.entity.resp.HotSchedule;
import com.management.admin.entity.template.JsonArrayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/schedule")
public class ScheduleApiController extends BaseController{
    @Autowired
    private IScheduleService scheduleService;

    /**
     * 获取热门赛程列表 DF 2019年1月10日12:11:33
     * @return
     */
    @GetMapping("getTopList")
    public JsonArrayResult<HotSchedule> getTopList(){
        List<HotSchedule> topList = scheduleService.getTopList();
        return new JsonArrayResult<>(topList);
    }
}
