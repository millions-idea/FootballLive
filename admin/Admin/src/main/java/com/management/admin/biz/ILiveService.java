package com.management.admin.biz;

import com.management.admin.entity.db.Live;
import com.management.admin.entity.db.User;
import com.management.admin.entity.dbExt.LiveDetail;
import com.management.admin.entity.dbExt.LiveHotDetail;
import com.management.admin.entity.resp.LiveInfo;

import java.util.List;

public interface ILiveService {

    /**
     * 分页加载 韦德 2018年8月30日11:29:00
     * @param page
     * @param limit
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    List<LiveDetail> getLimit(Integer page, String limit, String condition, Integer state, String beginTime, String endTime);


    /**
     * 加载总记录数 韦德 2018年8月30日11:29:11
     * @return
     */
    Integer getCount();


    /**
     * 加载分页记录数 韦德 2018年8月30日11:29:22
     * @param condition
     * @param state
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getLimitCount(String condition, Integer state, String beginTime, String endTime);

    /**
     * 获取用户信息--用户id DF 2018年12月16日19:11:09
     * @param liveId
     * @return
     */
    Live getLiveInfo(Integer liveId);

    Integer deleteLive(Integer liveId);

    LiveDetail queryLiveDetails(Integer liveId);

    /**
     * 添加直播间
     * @param liveDetail
     * @return
     */
    Boolean insertLive(LiveDetail liveDetail);

    /**
     * 修改直播间
     * @param liveDetail
     * @return
     */
    Integer modifyLive(LiveDetail liveDetail);

    /**
     * 获取热门直播信息 DF 2018年12月17日23:42:36
     * @return
     */
    List<LiveHotDetail> getHotLives();
    /**
     * 获取直播间详情信息 DF 2018年12月18日15:27:04
     * @param liveId
     * @return
     */
    LiveInfo getLiveDetailInfo(Integer liveId);

    /**
     * 添加收藏 DF 2018年12月19日03:59:40
     * @param liveId
     * @param userId
     * @return
     */
    Boolean addCollect(Integer liveId, Integer userId);

    /**
     * 取消收藏 DF 2018年12月19日03:59:48
     * @param liveId
     * @param userId
     * @return
     */
    Boolean cancelCollect(Integer liveId, Integer userId);    List<Live> queryAll();







}