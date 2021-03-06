package com.management.admin.controller;

import com.management.admin.annotaion.WebLog;
import com.management.admin.biz.IChatRoomService;
import com.management.admin.entity.db.Live;
import com.management.admin.entity.dbExt.ChatRoomDetail;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("management/chatRoom")
public class ChatRoomController {

    @Autowired
    private IChatRoomService chatRoomService;

    @GetMapping("/index")
    public String index(){
        return "chatRoom/index";
    }

    /**
     * 会员列表 韦德 2018年8月29日11:42:31
     *
     * @return
     */
    @GetMapping("/getchatRoomLimit")
    @ResponseBody
    @WebLog(section = "ChatRoom",content = "查看所有的直播间")
    public JsonArrayResult<Live> getLiveLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime) {
        Integer count = 0;
        List<ChatRoomDetail> list = chatRoomService.getLimit(page, limit, condition, state, beginTime, endTime);
        JsonArrayResult jsonArrayResult = new JsonArrayResult(0, list);
        if (StringUtil.isBlank(condition)
                && StringUtil.isBlank(beginTime)
                && StringUtil.isBlank(endTime)
                && (state == null || state == 0)) {
            count = chatRoomService.getCount();
        } else {
            count = chatRoomService.getLimitCount(condition, state, beginTime, endTime);
        }
        jsonArrayResult.setCount(count);
        return jsonArrayResult;
    }

    /**
     * 获取聊天室详情 狗蛋 2018年12月19日04:13:09
     * @param roomId
     * @param model
     * @return
     */
    @GetMapping("/getChatRoomDetails")
    @WebLog(section = "ChatRoom",content = "查看聊天室详情")
    public String getChatRoomDetails(Integer roomId, final Model model){
        ChatRoomDetail chatRoomDetail = chatRoomService.queryChatRoomById(roomId);
        model.addAttribute("chatRoomDetail",chatRoomDetail);
        return "chatRoom/details";
    }

    /**
     * 向指定直播间发送消息
     * @param msgPassword
     * @param liveId
     * @param msg
     * @return
     */
    @GetMapping("sendMsg")
    @ResponseBody
    @WebLog(section = "ChatRoom",content = "发送消息给直播间")
    public JsonResult sendMsg(String msgPassword,Integer liveId,String msg){
        String result = chatRoomService.sendMsg(msgPassword,liveId,msg);
        if(result!=null){
            return new JsonResult().failingAsString(result);
        }
        return JsonResult.successful();
    }
    /**
     * 向所有直播间发送消息
     * @param msgPassword
     * @param liveId
     * @param msg
     * @return
     */
    @PostMapping("sendAllLiveMsg")
    @ResponseBody
    @WebLog(section = "ChatRoom",content = "发送消息给直播间")
    public JsonResult sendAllLiveMsg(String msgPassword,Integer liveId,String msg){
        String result = chatRoomService.sendMsgAllLive(msgPassword,liveId,msg);
        if(result!=null){
            return JsonResult.failing();
        }
        return JsonResult.successful();
    }
}
