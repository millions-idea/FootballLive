/***
 * @pName Admin
 * @name ProfileApiController
 * @user HongWei
 * @date 2018/12/19
 * @desc
 */
package com.management.admin.apiController;

import com.management.admin.biz.IPublishMessageService;
import com.management.admin.entity.dbExt.PublishMessageDetail;
import com.management.admin.entity.resp.UserInfo;
import com.management.admin.entity.template.JsonArrayResult;
import com.management.admin.entity.template.JsonResult;
import com.management.admin.entity.template.SessionModel;
import com.management.admin.utils.web.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.util.List;

@RestController
@RequestMapping("api/profile")
public class ProfileApiController {
    @Autowired
    private IPublishMessageService publishMessageService;

    /**
     * 获取消息列表 DF 2018年12月19日06:21:27
     * @param req
     * @return
     */
    @GetMapping("getMessageList")
    public JsonArrayResult<PublishMessageDetail> getMessageList(HttpServletRequest req){
        SessionModel session = SessionUtil.getSession(req);
        List<PublishMessageDetail> publishMessageDetails = publishMessageService.getPushMessageList(session.getUserId());
        return new JsonArrayResult<>(publishMessageDetails);
    }

    /**
     * 签收消息 DF 2018年12月19日06:22:24
     * @param req
     * @param relationId
     * @return
     */
    @GetMapping("signMessage")
    public JsonResult signMessage(HttpServletRequest req,Integer relationId){
        SessionModel session = SessionUtil.getSession(req);
        boolean result = publishMessageService.signMessage(relationId, session.getUserId());
        if(result) return JsonResult.successful();
        return JsonResult.failing();
    }
}
