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
import com.management.admin.entity.enums.UserRoleEnum;
import com.management.admin.entity.template.JsonArrayResult;
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
    public String index(){
        return "member/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     * @return
     */
    @GetMapping("/getMemberLimit")
    @ResponseBody
    public JsonArrayResult<User> getMemberLimit(Integer page, String limit, String condition,Integer type, Integer state, String beginTime, String endTime){
        Integer count = 0;
        List<User> list = userService.getLimit(page, limit, condition, UserRoleEnum.Member, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)){
            count = userService.getCount();
        }else{
            count = userService.getLimitCount(condition,UserRoleEnum.Member, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    @GetMapping("edit")
    public String edit(Integer userId, final Model model){
        User user = userService.getUserInfoById(userId);
        model.addAttribute("user", user);
        return "member/edit";
    }
}
