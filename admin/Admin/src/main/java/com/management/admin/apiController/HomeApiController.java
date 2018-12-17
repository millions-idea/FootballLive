/***
 * @pName Admin
 * @name HomeApiController
 * @user HongWei
 * @date 2018/12/13
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.IDictionaryService;
import com.management.admin.entity.db.Dictionary;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/home")
public class HomeApiController {
    @Autowired
    private IDictionaryService dictionaryService;

    /**
     * 查询首页板块聚合信息 DF 2018年12月13日17:02:55
     * @return
     */
    @GetMapping("getGroupInfo")
    public JsonArrayResult<Dictionary> getHomeGroupInfo(){
        //1.轮播广告
        //2.走马灯广告
        //3.分类板块信息
        List<Dictionary> maps = dictionaryService.getHomeGroupInfo();
        if(maps != null && maps.size() > 0) return new JsonArrayResult<Dictionary>(maps);
        return JsonArrayResult.failing();
    }

}
