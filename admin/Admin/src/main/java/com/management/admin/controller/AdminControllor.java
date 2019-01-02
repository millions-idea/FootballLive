package com.management.admin.controller;

import com.alibaba.fastjson.JSON;
import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.IUserService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.PermissionRelation;
import com.management.admin.entity.dbExt.RelationAdminUsers;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.entity.template.SessionModel;
import com.management.admin.exception.InfoException;
import com.management.admin.utils.MD5Util;
import com.management.admin.utils.StringUtil;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName AdminControllor
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/17 10:47
 * Version 1.0
 **/
@Controller
@RequestMapping("management/admin")
public class AdminControllor {
    @Autowired
    private IUserService userService;

    @GetMapping("/index")
    @WebLog(section = "User", content = "访问管理员管理")
    public String index(HttpServletRequest request, final Model model) {

        return "admin/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getAdminLimit")
    @ResponseBody
    @WebLog(section = "User", content = "访问管理员列表")
    public JsonArrayResult<AdminUser> getMemberLimit(Integer page, String limit, String condition, Integer type, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<AdminUser> list = userService.getAdminLimit(page, limit, condition, state, beginTime, endTime);
        System.err.println(list);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = userService.getAdminCount();
        } else {
            count = userService.getAdminLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    @GetMapping("edit")
    @WebLog(section = "User", content = "修改管理员信息")
    public String edit(HttpServletRequest request, Integer userId, final Model model) {
        SessionModel session = SessionUtil.getSession(request);

        AdminUser user = userService.getAdminUserInfoById(session.getUserId());
        AdminUser targetUser = userService.getAdminUserInfoById(userId);
        model.addAttribute("user", user);
        model.addAttribute("targetUser", targetUser);
        return "admin/adminEdit.html";
    }

    /**
     * 跳转到修改密码页面 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/updatePassword")
    public String updatePassword() {
        return "admin/updatepassword";
    }

    /**
     * 修改管理员密码  提莫 2018-11-30 13:28:30
     *
     * @return 成功返回一个users对象
     */
    @PostMapping(value = "updateuserpwd")
    @ResponseBody
    @WebLog(section = "User", content = "修改管理员密码")
    public JsonResult setUpdatePwd(HttpServletRequest request, String phone, String password, String newpwd) {
        boolean result = userService.resetAdminPassword(request, phone, password, newpwd);
        if (result) {
            return JsonResult.successful();
        } else {
            return JsonResult.failing();
        }
    }

    /**
     * 超级管理员修改信息  提莫 2018-11-30 13:28:30
     *
     * @return 成功返回一个users对象
     */
    @PostMapping(value = "updateSupAdmin")
    @ResponseBody
    @WebLog(section = "User", content = "超级管理员修改信息")
    public JsonResult setSupUpdatePwd(String phone, String password, Integer status) {
        boolean result = userService.resetSupAdmin(phone, password, status);
        if (result) {
            return JsonResult.successful();
        } else {
            return JsonResult.failing();
        }
    }

    @GetMapping("/insertAdmin")
    public String insertAdmin() {
        return "admin/insertAdmin";
    }

    /**
     * 新增管理员  提莫 2018-12-17 13:28:30
     *
     * @return 成功返回一个users对象
     */
    @PostMapping(value = "insertAdmin")
    @ResponseBody
    @WebLog(section = "User", content = "添加管理员")
    public JsonResult insertAdmin(HttpServletRequest req, String phone, String password, Integer type) {
        //验证权限
        SessionModel session = SessionUtil.getSession(req);
        AdminUser adminUserInfoById = userService.getAdminUserInfoById(session.getUserId());
        if(!adminUserInfoById.getStatus().equals(1)) throw new InfoException("权限不足");

        JsonResult jsonResult = new JsonResult();
        RelationAdminUsers adminUsers = new RelationAdminUsers();
        adminUsers.setPhone(phone);
        String rpassword = MD5Util.encrypt32(MD5Util.encrypt32(phone + password));
        adminUsers.setPassword(rpassword);
        adminUsers.setType(type);
        adminUsers.setPermissionGroupId(type);
        String checkPhone = userService.checkPhone(adminUsers.getPhone());

        if (checkPhone != null) {
            jsonResult.setMsg("该账号已经存在");
        } else if (userService.insertAdminUsers(adminUsers)) {
            jsonResult.setCode(200);
            jsonResult.setMsg("添加成功");
        } else {
            jsonResult.setCode(500);
            jsonResult.setMsg("添加失败");
        }
        return jsonResult;
    }

    /**
     * 删除管理员  提莫 2018-12-17 23:28:30
     *
     * @return 成功true
     */
    @GetMapping(value = "deleteAdmin")
    @ResponseBody
    @WebLog(section = "User", content = "删除管理员")
    public JsonResult deleteAdmin(Integer userId, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();

        Integer sessionId = SessionUtil.getSession(request).getUserId();
        Integer type = userService.selectType(sessionId);

        if (type == 2) {
            jsonResult.setMsg("无权删除");
        } else if (userService.deleteAdmin(userId)) {
            jsonResult.setCode(200);
            jsonResult.setMsg("删除成功");
        } else {
            jsonResult.setCode(500);
            jsonResult.setMsg("删除失败");
        }
        return jsonResult;
    }
}
