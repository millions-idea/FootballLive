/***
 * @pName Admin
 * @name UserController
 * @user HongWei
 * @date 2018/12/16
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.User;
import com.management.admin.entity.db.UserFeedback;
import com.management.admin.entity.dbExt.LiveCollectDetail;
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
@RequestMapping("/management/member")
public class MemberController {
    @Autowired
    private IUserService userService;

    @GetMapping("/index")
    public String index() {
        return "member/index";
    }

    @GetMapping("/backIndex")
    public String backIndex() {
        return "member/backIndex";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getMemberLimit")
    @ResponseBody
    public JsonArrayResult<User> getMemberLimit(Integer page, String limit, String condition, Integer type, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<User> list = userService.getLimit(page, limit, condition, UserRoleEnum.Member, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = userService.getCount();
        } else {
            count = userService.getLimitCount(condition, UserRoleEnum.Member, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    @GetMapping("edit")
    public String edit(Integer userId, final Model model) {
        User user = userService.getUserInfoById(userId);
        List<LiveCollectDetail> collectDetail = userService.queryLiveCollectByUserId(userId);
        model.addAttribute("editDate",DateUtil.getFormatDateTime(user.getEditDate(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("addDate",DateUtil.getFormatDateTime(user.getAddDate(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("collectDetail",collectDetail);
        model.addAttribute("user", user);
        return "member/edit";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getuserFeedbackLimit")
    @ResponseBody
    public JsonArrayResult<User> getBackLimit(Integer page, String limit, String condition, Integer type, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<User> list = userService.getBackLimit(page, limit, condition, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = userService.getBackCount();
        } else {
            count = userService.getBackLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    @GetMapping("/backDetails")
    public String backDetails(Integer backUserId, final Model model) {
        User user = userService.queryBackUserById(backUserId);
        model.addAttribute("addDate", DateUtil.getFormatDateTime(user.getAddDate(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("backTime" ,DateUtil.getFormatDateTime(user.getBlackTime(), "yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("user", user);
        return "member/backDetails";
    }
}
