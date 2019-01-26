/***
 * @pName Admin
 * @name configurationController
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.controller;

import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.IDictionaryService;
import com.management.admin.biz.ILiveCategoryService;
import com.management.admin.entity.db.Dictionary;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.db.Team;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.dbExt.ScheduleGameTeam;
import com.management.admin.entity.template.Constant;
import com.management.admin.entity.template.DataDictionary;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.utility.http.HttpUtil;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName configurationController
 * @Description 系统配置
 * @Author ZXL0
 * @Date 2018/12/17 10:47
 * Version 1.0
 **/
@Controller
@RequestMapping("management/configuration")
public class configurationController {

    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
    private ILiveCategoryService liveCategoryService;
    /**
     * 跳转到系统配置界面
     * @return
     */
    @GetMapping("/index")
    @WebLog(section = "Config",content = "访问系统配置")
    public String index(final Model model){
        List<Dictionary> list=dictionaryService.getList();
        model.addAttribute("banner",list);
        return "configuration/index";
    }

    /**
     * 修改banner图片，启动图，公告
     * @return
     */
    @PostMapping ("/upadteConfig")
    @ResponseBody
    @WebLog(section = "Config",content = "修改banner图，启动图，公告。")
    public JsonResult upadteConfig(String key,String value){
        val result = dictionaryService.upadteConfig(key, value);
        if(result>0){
            HttpUtil.get(Constant.WebUrl + "api/config/reload", null);
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 跳转到直播分类管理界面
     * @return
     */
    @GetMapping("/classification")
    public String classification(final Model model){
        List<LiveCategory> list=liveCategoryService.getLiveCategorys();
        model.addAttribute("category",list);
        return "configuration/classification";
    }
    /**
     * 新增界面 提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("addCategory")
    public String addCompetition(){

        return "configuration/addCategory";
    }


    /**
     * 查询所有分类信息
     * @return
     */
    @GetMapping("/getAllCategory")
    @ResponseBody
    public JsonArrayResult<LiveCategory> getAll(Integer page, String limit){
        Integer count = 0;
        List<LiveCategory> list =liveCategoryService.getLimit(page,limit);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        count = liveCategoryService.getLimitCount();
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }
    /**
     * 删除直播分类
     * @return
     */
    @GetMapping("/deleteCategory")
    @ResponseBody
    @WebLog(section = "Config",content = "删除直播分类")
    public JsonResult deleteCategory(Integer categoryId){
        Integer result=liveCategoryService.deleteCategoryById(categoryId);
        if (result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 添加直播分类
     * @return
     */
    @GetMapping("/insertCategory")
    @ResponseBody
    @WebLog(section = "Config",content = "添加直播分类")
    public JsonResult insertCategory(LiveCategory category){
        Integer result=liveCategoryService.insertCategory(category);
        if (result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 修改直播分类
     * @return
     */
    @GetMapping("/updateCategory")
    @ResponseBody
    @WebLog(section = "Config",content = "修改直播分类")
    public JsonResult updateCategory(LiveCategory category){
        Integer result=liveCategoryService.updateCategoryById(category);
        if (result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }

    /**
     * 修改分类传递显示参数  提莫 2018年12月18日11:42:31
     * @return
     */
    @GetMapping("editCategory")
    public String updateSchedule(final Model model,Integer categoryId){
        LiveCategory liveCategory = liveCategoryService.selectCategoryById(categoryId);

        Map<String,Integer> sort = new HashMap<>();
        sort.put("一级", 0);
        sort.put("二级", 1);
        sort.put("三级", 2);
        sort.put("四级", 3);
        sort.put("五级", 4);
        sort.put("六级", 5);
        sort.put("七级", 6);
        Map<String,Integer> select = new HashMap<>();
        select.put("是", 1);
        select.put("否", 0);

        model.addAttribute("select",select);
        model.addAttribute("liveCategory",liveCategory);
        model.addAttribute("sorts",sort);
        return "configuration/editCategory";
    }

    /**
     * 跳转到联系我们
     * @return
     */
    @GetMapping("/contactUs")
    public String contactUs(final Model model){
        Dictionary data = dictionaryService.getContact();
        model.addAttribute("model", data);
        return "configuration/contactUs";
    }

}
