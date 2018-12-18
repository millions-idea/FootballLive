package com.management.admin.controller;

import com.management.admin.biz.IChatRoomUserRelationService;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("management/chatRoomUser")
public class ChatRoomUserController {

    @Autowired
    private IChatRoomUserRelationService relationService;


    @GetMapping("/index")
    public String index(){
        return "chatRoomUser/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getchatRoomLimit")
    @ResponseBody
    public JsonArrayResult<Live> getLiveLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime,Integer roomId) {
        Integer count = 0;
        List<ChatRoomDetail> list = relationService.getLimit(page, limit, condition, state, beginTime, endTime,roomId);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = relationService.getCount(roomId);
        } else {
            count = relationService.getLimitCount(condition, state, beginTime, endTime,roomId);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    /**
     * 拉黑用户 狗蛋 2018-12-19 05:18:31
     * @param userId
     * @return
     */
    @GetMapping("/shieldingUser")
    @ResponseBody
    public JsonResult shieldingUser(Integer userId){
        Integer result = relationService.shieldingUser(userId);
        if(result>0){
            return JsonResult.successful();
        }
        return JsonResult.failing();
    }
}
