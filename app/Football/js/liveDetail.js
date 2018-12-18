mui.init();

var liveService = {
	/**
	 * 获取直播间信息 DF 2018年12月18日20:54:54
	 * @param {Object} param
	 * @param {Object} callback
	 */
	getLiveInfo: function(param, callback){
		$.get(app.config.apiUrl + "api/live/getLiveInfo", param, function(data){
			app.logger("live", JSON.stringify(data));
			callback(data);
		});
	},
	
	/**
	 * 获取直播间情报信息 DF 2018年12月18日20:55:21
	 * @param {Object} param
	 * @param {Object} callback
	 */
	getInformation: function(param, callback){
		$.get(app.config.apiUrl + "api/live/getInformation", param, function(data){
			app.logger("live", JSON.stringify(data));
			callback(data);
		});
	}
}
 
 
mui.plusReady(function(){
	//获取直播详情
	var self = plus.webview.currentWebview();
	var id = self.liveId;
	
	//打开等待加载弹窗
	plus.nativeUI.showWaiting("正在加载页面资源");
	
	//获取直播间信息
	liveService.getLiveInfo({
		liveId: parseInt(id)	
	}, function(res){
		//进入直播间失败
		if(app.utils.ajax.isError(res)) {
			plus.nativeUI.closeWaiting();
			app.utils.msgBox.msg("加载直播间时出错");
			plus.webview.currentWebview().close();
			return;
		}
		
		//关闭等待加载弹窗
		plus.nativeUI.closeWaiting();
		
		var live = res.msg;
	
		//更新直播间标题
		$("#liveTitle").text(live.gameName + ": " + live.liveTitle);
		 
		//显示比赛状态
		var status = "...";
		if(live.status == 0) status = "未开始";
		if(live.status == 1) status = "正在直播";
		if(live.status == 2) status = "已结束";
		$(".shade .info .status").text(status);
		$(".shade .leftv img").attr("src", live.teamList[0].teamIcon);
		$(".shade .rightv img").attr("src", live.teamList[1].teamIcon);
		
		
		//设置播放器相关信息
		if(live.status == 1) $(".btnLive").show();
		$(".btnLive").click(function(){
			createPlayer(live.type, live.playerAdUrl, live.playerTargetUrl, live.sourceUrl);
		});
		$(".video .info .grade").text(live.scheduleGrade);
		
		//设置全站广告位
		$(".advertising img").attr("src", live.adUrl);
		$(".advertising img").click(function(){
			plus.runtime.openURL(live.adTargetUrl);
		});
		
		// 切换选项卡
		$(".live-tabs li").click(function(){
			var tabindex= $(this).attr("tabindex");  
			//判断是否切换到情报页
			if(tabindex == 1){
				plus.nativeUI.showWaiting("正在获取最新的情报信息");
				liveService.getInformation({
					liveId: live.liveId
				}, function(information){
					if(app.utils.ajax.isError(information)) {
						plus.nativeUI.closeWaiting();
						app.utils.msgBox.msg("加载最新情报信息失败");
						return;
					}
					
					var inf = information.msg;
					
					//球队信息设置
					$(".live .lefte img").attr("src", live.teamList[0].teamIcon);
					$(".live .lefte span").text(live.teamList[0].teamName);
					//判断是否为胜利球队
					if(live.teamList[0].teamId == live.winTeamId){
						$(".live .lefte").removeClass("left-hot").addClass("left-hot");
						if($(".live .lefte").html().indexOf("shoucangjiaobiao") == -1){
							$(".live .lefte img").before('<i class="icon iconfont icon-shoucangjiaobiao-copy"></i>');
						}
					}
					
					//比赛结果设置
					if(live.scheduleGrade == null){
						$(".live .infoe span").text("-/-");
					}else{
						var scheduleStatus = live.scheduleGrade;
						if(live.scheduleResult != null){
							scheduleStatus = live.scheduleResult + ":" + " " +  live.scheduleGrade;
						}
						$(".live .infoe span").text(scheduleStatus);
					}
					
					$(".live .righte img").attr("src", live.teamList[1].teamIcon);
					$(".live .righte span").text(live.teamList[1].teamName);
					//判断是否为胜利球队
					if(live.teamList[1].teamId == live.winTeamId){
						$(".live .righte").removeClass("right-hot").addClass("right-hot");
						if($(".live .righte").html().indexOf("shoucangjiaobiao") == -1){
							$(".live .righte img").before('<i class="icon iconfont icon-shoucangjiaobiao-copy"></i>');
						}
					}
					
					//推荐理由渲染
					if(inf.content == null){
						$(".information-content").html("<span style='color:#BEBEBE'>暂无情报</span>");
					}else{
						$(".information-content").html(inf.content);
					}

					
					plus.nativeUI.closeWaiting();
					$(".live-tabs li").removeClass("active");
					$(".live-tabs li").eq(tabindex).addClass("active");
					$("div[name='content']").hide();
					$("div[name='content'][tabindex='" + tabindex + "']").show();
				})
			}else{
				$(".live-tabs li").removeClass("active");
				$(".live-tabs li").eq(tabindex).addClass("active");
				$("div[name='content']").hide();
				$("div[name='content'][tabindex='" + tabindex + "']").show();
			}
			
		});
		
	})
	
	
	
})

$(function(){ 
	//动态调整直播面板区域高度
	var dynamicHeight = $("header").height() + $(".video").height() + $(".advertising").height();
	$(".dynamic-padding").height(dynamicHeight - 3);
	
	$(".message-list").height(($(".mui-content").height() - 50) - $(".header").height() - $(".top-fixed").height());
	
	//动态调整直播面板浮动项
	$(".video .leftv, .video .rightv").css("padding-top", "60px");
	$(".shade .info").css("padding-top", "25px");
})


/**
 * 创建视频播放器组件 DF 2018年12月18日14:12:52
 * @param {Object} type 播放器广告类型；图片或视频
 * @param {Object} adUrl 播放器广告地址；图片或视频，图片固定展示5s，视频播放完毕后紧接着直播
 * @param {Object} targetUrl 用户点击播放器广告跳转的目标网址
 * @param {Object} videoUrl 播放器直播视频链接
 */
function createPlayer(type, adUrl, targetUrl, videoUrl){
	//视频播放器组件
	var video = new plus.video.VideoPlayer('video',{
	    src: videoUrl
	    //src:'http://play.512ck.cn/live/test1.m3u8'
	    //src:'http://ivi.bupt.edu.cn/hls/lytv.m3u8'
    });
    // 监听开始播放事件
	video.addEventListener('play', function(e){
		//plus.nativeUI.alert('Video play');
	}, false);
	// 监听播放进度更新事件
	video.addEventListener('timeupdate', function(e){
		//console.log("timeupdate:" + JSON.stringify(e));
	}, false);
	// 监听播放结束事件
	video.addEventListener('ended', function(e){
		//plus.nativeUI.alert('Video ended');
	}, false);
	video.play();
}


function refreshChatHeight(){
	var height = (document.documentElement.clientHeight + 50);
	$(".message-list").css("height", height + "px");
}
