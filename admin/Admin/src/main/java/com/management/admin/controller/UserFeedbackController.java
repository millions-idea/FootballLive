package com.management.admin.controller;

import com.management.admin.biz.IUserFeedbackService;
import com.management.admin.entity.db.User;
import com.management.admin.entity.db.UserFeedback;
import com.management.admin.entity.enums.UserRoleEnum;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.utils.DateUtil;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("management/userFeedback")
public class UserFeedbackController {

    @Autowired
    private IUserFeedbackService feedbackService;

    @GetMapping("/index")
    public String index(){
        return "userFeedback/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     * @return
     */
    @GetMapping("/getuserFeedbackLimit")
    @ResponseBody
    public JsonArrayResult<User> getMemberLimit(Integer page, String limit, String condition, Integer type, Integer state, String beginTime, String endTime){
        Integer count = 0;
        List<UserFeedback> list = feedbackService.getLimit(page, limit, condition,state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)){
            count = feedbackService.getCount();
        }else{
            count = feedbackService.getLimitCount(condition,state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    @GetMapping("/details")
    public String details(Integer feedbackId, final Model model){
        UserFeedback feedback = feedbackService.queryUserFeedbackById(feedbackId);
        model.addAttribute("feedback",feedback);
        model.addAttribute("addDate", DateUtil.getFormatDateTime(feedback.getAddDate(), "yyyy-MM-dd HH:mm:ss"));
        return "userFeedback/details";
    }

}
