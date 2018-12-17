mui.init();


mui.plusReady(function(){
	var self = plus.webview.currentWebview();
	var id = self.id;
	var liveTitle = self.title;
	var gameName = self.gameName;
	var videoUrl = self.videoUrl;
	var status = self.status;
	
	//更新基本信息
	$("#liveTitle").text(gameName + "：" + liveTitle);
	
	app.logger("live_videoUrl", videoUrl)
	
	$(".btnLive").click(function(){
		//视频播放器组件
		var video = new plus.video.VideoPlayer('video',{
		    src: videoUrl
		    //src:'http://play.512ck.cn/live/test1.m3u8'
		    //src:'http://ivi.bupt.edu.cn/hls/lytv.m3u8'
	    });
	    // 监听开始播放事件
		video.addEventListener('play', function(e){
			//plus.nativeUI.alert('Video play');
		}, false)
		// 监听播放进度更新事件
		video.addEventListener('timeupdate', function(e){
			//console.log("timeupdate:" + JSON.stringify(e));
		}, false);
		// 监听播放结束事件
		video.addEventListener('ended', function(e){
			//plus.nativeUI.alert('Video ended');
		}, false);    
		
		video.play();
	});
})

$(function(){
	// 切换选项卡
	$(".live-tabs li").click(function(){
		var tabindex= $(this).attr("tabindex");
		$(".live-tabs li").removeClass("active");
		$(".live-tabs li").eq(tabindex).addClass("active");
		$("div[name='content']").hide();
		$("div[name='content'][tabindex='" + tabindex + "']").show();
	});
	
})
