mui.init();


var liveService = {
	/**
	 * 获取直播间信息 DF 2018年12月18日20:54:54
	 * @param {Object} param
	 * @param {Object} callback
	 */
	getLiveInfo: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/live/getLiveInfo"), param, function(data){
			app.logger("live", JSON.stringify(data));
			callback(data);
		});
	},
	
	leaveTeam: function(param){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/live/leaveTeam"), param, function(data){
			app.logger("离开群组", JSON.stringify(data));
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
	},
	
	/**
	 * 添加收藏 DF 2018年12月19日03:56:24
	 * @param {Object} param
	 * @param {Object} callback
	 */
	addCollect: function(param, callback){
		$.post(app.utils.toUrl(app.config.apiUrl + "api/live/addCollect"), param, function(data){
			app.logger("live", JSON.stringify(data));
			callback(data);
		});
	},
	
	/**
	 * 取消收藏 DF 2018年12月19日03:56:19
	 * @param {Object} param
	 * @param {Object} callback
	 */
	cancelCollect: function(param, callback){
		$.post(app.utils.toUrl(app.config.apiUrl+ "api/live/cancelCollect") , param, function(data){
			app.logger("live", JSON.stringify(data));
			callback(data);
		});
	},
	
	/**
	 * 加入聊天室 DF 2018年12月29日14:28:51
	 */
	joinRoom: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl+ "api/live/joinRoom") , param, function(data){
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
	
	
	$(".share").click(function(){
		app.utils.copyTo(app.config.shareUrl);
		app.utils.msgBox.msg("已为您复制宣传网址，快发给朋友吧~");
	})
	

	//获取直播间信息
	liveService.getLiveInfo({
		liveId: parseInt(id)	
	}, function(res){
		
		var view = plus.webview.getWebviewById("index");
		if(view != null){
			view.evalJS("createNIM()");
		}
		
		
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

		var cacheString = plus.storage.getItem("userInfo");
		if(cacheString == null || cacheString.length == 0){
			disableChat("请点击此处登录");
			$(".editor").unbind("click").bind("click",function(){
				app.utils.openNewWindow("login.html", "login");
				return;
			});
		}else{
			if(live.chatRoomErrorMsg != null){
				if(live.chatRoomErrorMsg == "您已被加入直播间黑名单"){
					disableChat("您已被管理员设置禁止发言");				
				}else if(live.chatRoomErrorMsg != "SUCCESS"){
					console.log("chatRoomErrorMsg != null" + ":" + "全员禁言")
					console.log(live.chatRoomErrorMsg)
					disableChat("全员禁言");
				}
			}			
		}
		
 
		
		var old_back = mui.back;
	    mui.back = function(event) {
	    	console.log("back")
	    	//退出群
	    	if(live.chatRoomId != null){
	    		liveService.leaveTeam({
	    			liveId: live.liveId
	    		});
	    		/*app.utils.call("leaveTeam", {
		    		teamId: live.chatRoomId
		    	})*/
	    	}
	        old_back();
	    }
		
	
		//更新直播间标题
		var liveTitle = live.liveTitle; 
		if(live.liveTitle.length > 14) liveTitle = live.liveTitle.substr(0, liveTitle.length - 4) + "...";
		$("#liveTitle").text(live.gameName + ": " + liveTitle);
		 
		//显示比赛状态
		var status = "...";
		if(live.status == 0) status = "未开始";
		if(live.status == 1) status = "正在直播";
		if(live.status == 2) status = "已结束";
		$(".shade .info .status").text(status);
		$(".shade .leftv img").attr("src", live.teamList[0].teamIcon);
		$(".shade .rightv img").attr("src", live.teamList[1].teamIcon);
		
		if(live.status == 2 && plus.storage.getItem("userInfo") != null){
			console.log("status == 2" + ":" + "全员禁言")
	    	disableChat("全员禁言");
		}
		
		//设置播放器相关信息
		if(live.status == 1) $(".btnLive").show();
		$(".btnLive").click(function(){
			console.log("开始播放" + live.type + ", " + live.playerAdUrl + ", " + live.playerTargetUrl + ", " + live.sourceUrl)
			createPlayer(live.type, live.playerAdUrl, live.playerTargetUrl, live.sourceUrl);
		});
		
		if(live.scheduleGrade != null && live.scheduleGrade.toString().length > 0){
			$(".video .info .grade").text(live.scheduleGrade);
		}
		
		//设置全站广告位
		$(".advertising").css("background-image", "url("+live.adUrl+")");
		$(".advertising").click(function(){
			plus.runtime.openURL(live.adTargetUrl);
		});
		
		//切换选项卡
		$(".live-tabs li").click(function(){
			var tabindex= $(this).attr("tabindex");  
			//判断是否切换到情报页
			if(tabindex == 1){
				plus.nativeUI.showWaiting("正在获取最新的情报信息");
				liveService.getInformation({
					liveId: live.liveId,
					gameId: live.gameId
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
					if(live.scheduleGrade == null || live.scheduleGrade.toString().length == 0){
						$(".live .infoe span").text("-/-");
					}else{
						var scheduleStatus = live.scheduleGrade;
						if(live.scheduleResult != null && live.scheduleResult.toString().length > 0){
							scheduleStatus = live.scheduleResult + " " +  live.scheduleGrade;
						}
						if(scheduleStatus = "0-0"){
							$(".live .infoe span").text("-/-");
						}else{
							$(".live .infoe span").text(scheduleStatus);
						}

					}
					
					$(".live .righte img").attr("src", live.teamList[1].teamIcon);
					$(".live .righte span").text(live.teamList[1].teamName);
					//判断是否为胜利球队
					if(live.teamList[1].teamId == live.winTeamId){
						$(".live .righte").removeClass("right-hot").addClass("right-hot");
						if($(".live .righte").html().indexOf("shoucangjiaobiao") == -1){
							$(".live .righte img").before('<i class="icon iconfont icon-shoucangjiaobiao"></i>');
						}
					}
					
					//推荐理由渲染
					if(inf.information == null || inf.information.content == null){
						$(".information-content").html("<span style='color:#BEBEBE'>暂无情报</span>");
					}else{
						$(".information-content").html(inf.information.content);
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
 		
		
		// 发送消息
	    $(".send").unbind("click").bind("click",function(){ 
	    	liveService.joinRoom({
	    		liveId: live.liveId
	    	}, function(){});
	    	
	    	sendMessage(live.chatRoomId); 
	    });
	    
	    
	    
	    //收藏
	    $(".collect").click(function(){
	    	//判断是否登录
	    	var cacheString = plus.storage.getItem("userInfo");
	    	if(cacheString == null || cacheString.length == 0){
	    		app.utils.openNewWindow("login.html", "login");
	    		return;
	    	}
	    	
	    	
	    	var that = $(this);
	    	if($(that).hasClass("icon-shoucang1")){
	    		
	    		liveService.addCollect({
	    			liveId: live.liveId
	    		}, function(collectRes){
	    			app.print("收藏成功");
		    		$(that).removeClass("icon-shoucang1").addClass("icon-shoucangxing2");	
	    		});
	    	}else{
	    		liveService.cancelCollect({
	    			liveId: live.liveId
	    		}, function(collectRes){
	    			app.print("取消收藏成功");
		    		$(that).removeClass("icon-shoucangxing2").addClass("icon-shoucang1");	
	    		});
	    	}
	    })
	})
	
})




/**
 * 发送消息 DF 2018年12月9日21:10:09
 */
function sendMessage(accid){
	var html = $(".message-input").html();
	if(html == null || html.length <= 0) return app.utils.msgBox.msg("请输入聊天内容");
	var msgHtml = html;
	
	$(".message-input").html("");
	
	var msgListHtml = "";
	var timeComponents = $(".time");
	var currentTime = app.utils.timestampToMinute(new Date()),
		repeatTimeCount = 0;
	
	for (var i = 0; i < timeComponents.length; i++) {
		var time = $(".time").eq(i).text();
		if(time == currentTime){
			repeatTimeCount++;
		}
	}
	
	if(repeatTimeCount == 0){
		msgListHtml += '<div class="time-split">';
		msgListHtml += '	<span class="time">' + currentTime + '</span>';
		msgListHtml += '</div>';
	}
	
	msgListHtml += '<div class="rightd">';
	msgListHtml += '    <span class="rightd_h">';
	msgListHtml += '        <img src="images/head-default.png" />';
	msgListHtml += '    </span>';
	msgListHtml += '    <div class="speech right" data-msg="' + msgHtml + '" >';
	msgListHtml += '         ' + html;
	msgListHtml += '    </div>';
	msgListHtml += '</div>';
	$(".message-list .message-list-container").append(msgListHtml);
	
	
	// 发送消息
	var webview = plus.webview.getWebviewById("index");
	mui.fire(webview, "sendText", {
		to: accid,
		text: msgHtml
	}) 
	
	scollEnd();
}


$(function(){ 
	//动态调整直播面板区域高度
	refreshChatHeight();
	
	//动态调整直播面板浮动项
	$(".video .leftv, .video .rightv").css("padding-top", "60px");
	$(".shade .info").css("padding-top", "25px");
	
	// 计算高度
    $(".message-input").click(function(){
    	/*$(".editor").css("bottom","");
    	$(".editor").css("top", $("header").height() + $(".video").height()  + "px");
    	$(".editor").css("z-index", "999");*/
    })
    
    
    //分享
    $(".share").click(function(){
    	plus.nativeUI.showWaiting("分享给朋友");
		plus.nativeUI.closeWaiting();
    })
})


function openShare() {
	shareWebview();
} 
/**
 *分享窗口
 */
function shareWebview() {
	ws = plus.webview.currentWebview();
	if (sharew) { // 避免快速多次点击创建多个窗口
		return;
	}
	var top = plus.display.resolutionHeight - 134;
	var href = "share.html"; 
	sharew = plus.webview.create(href, "share.html", {
		width: '100%',
		height: '134',
		top: top,
		scrollIndicator: 'none',
		scalable: false,
		popGesture: 'none'
	}, {
		shareInfo: {
			"href": "www.baidu.com",
			"title": "【加工跟单】新的订单详情",
			"content": "欢迎使用加工跟单APP,点击查看订单详情！",
			"pageSourceId": ws.id
		}
	});
	sharew.addEventListener("loaded", function() {
		sharew.show('slide-in-bottom', 300);
	}, false);
	// 显示遮罩层  
	ws.setStyle({
		mask: "rgba(0,0,0,0.5)"
	});
	// 点击关闭遮罩层
	ws.addEventListener("maskClick", closeMask, false);
		}
 
		function closeMask() {
			ws.setStyle({
				mask: "none"
	});
	//避免出现特殊情况，确保分享页面在初始化时关闭 
	if (!sharew) {
		sharew = plus.webview.getWebviewById("share.html");
	}
	if (sharew) {
		sharew.close();
		sharew = null;
	}
}


 



/**
 * 创建视频播放器组件 DF 2018年12月18日14:12:52
 * @param {Object} type 播放器广告类型；图片或视频
 * @param {Object} adUrl 播放器广告地址；图片或视频，图片固定展示5s，视频播放完毕后紧接着直播
 * @param {Object} targetUrl 用户点击播放器广告跳转的目标网址
 * @param {Object} videoUrl 播放器直播视频链接
 */
function createPlayer(type, adUrl, targetUrl, videoUrl){
	if(type == null){
		playVideo(videoUrl);
		return;
	}
	//播前广告
	if(type == 0){
		if(adUrl == null){
			playVideo(videoUrl);
		}
		
		playVideoAd(adUrl, targetUrl, function(){
			playVideo(videoUrl);
		});
	}else if(type == 1){
		playImageAd(adUrl, targetUrl, function(){
			playVideo(videoUrl);
		});
	} 
}


function refreshChatHeight(){
	var dynamicHeight = $("header").height() + $(".video").height() + $(".advertising").height();
	$(".dynamic-padding").height(dynamicHeight - 3);
	$(".message-list").height(($(".mui-content").height() - 50) - $(".header").height() - $(".top-fixed").height());
}


// 渲染历史记录
window.addEventListener("updateHistoryMsgsUI", function(event){
	var accid = event.detail.accid;
	var msgs = event.detail.obj;
	
	app.logger("live", "UpdateHistoryMsgsUI-" + accid);
	
	var html = "";
	
	for (var i = 0; i < msgs.length; i++) {
		
		var item = msgs[i]; 
		
		html += getMessagePartHtml(item);
	}
	
	$(".message-list .message-list-container").append(html);
		
	scollEnd();
})


/**
 * 滚动到底部 DF 2018年12月9日18:00:10
 */
function scollEnd(){
	var areaMsgList = document.getElementById("message-list"); 
	areaMsgList.scrollTop = areaMsgList.scrollHeight + areaMsgList.offsetHeight;
}
 


/**
 * 获取消息列表 DF 2018年12月9日18:26:08
 * @param {Object} obj
 */
function getMessagePartHtml(obj){
	var msgListHtml = "";
	var timeComponents = $(".time");
	var currentTime = app.utils.timestampToMinute(obj.time),
		repeatTimeCount = 0;
	
	if(timeComponents != null && timeComponents.length > 0){
		for (var i = 0; i < timeComponents.length; i++) {
			var time = $(".time").eq(i).text();
			if(time == currentTime){
				repeatTimeCount++;
			}
		}
	}
	
	// 处理会话时间，60秒刷新一次会话界面
	if(repeatTimeCount == 0){
		msgListHtml += '<div class="time-split">';
		msgListHtml += '	<span class="time">' + currentTime + '</span>';
		msgListHtml += '</div>';
	}
	
	// 根据flow属性判断本次收到的msg是发送方发来还是接收方发来的消息
	var positionStyle = obj.flow == "in" ? "left" : "right";
	
	var avatar = "images/head-default.png";
		
	msgListHtml += '<div class="' + positionStyle + 'd">';
	msgListHtml += '    <span class="' + positionStyle + 'd_h">';
	msgListHtml += '        <img name="' + positionStyle + '" src="' +  avatar + '" />';
	msgListHtml += '    </span>';
	msgListHtml += '    <div class="speech ' + positionStyle + '">';
	
	var body = obj.text; 
	if(body.indexOf("[") != -1 && body.indexOf("]") != -1 ){
		var arr = body.split("[");
		for (var i = 0; i < arr.length; i++) {
			var item = arr[i];
			if(item.indexOf("[") != -1 || item.indexOf("]") != -1) {
				if(item.indexOf("[") == -1) item = "[" + item;
				if(item.indexOf("]") == -1) item = item + "]";
				var key = item.substr(item.indexOf("["), item.indexOf("]") + 1);
				var emojiObj = findEmojiImg(key);
				if(emojiObj != null){
					body = body.replace("[","").replace("]","");
					body = body.replaceX(emojiObj.key, "<img class='face' src='" + emojiObj.img + "'/>");
				}
			}
		}
	}
	
	console.log(JSON.stringify(obj));
	
	if(obj.type == "text"){
		msgListHtml += '         ' + body;
	}else if(obj.type == "image"){
		msgListHtml += '         ' + "<img class='diyImage' src='" + obj.file.url + "' data-preview-src='' data-preview-group='2'/>";
	}else if(obj.type == "notification") {
		var currentUser = obj.attach.users[0].account.substr(6, obj.attach.users[0].account.length);
		if(obj.attach.type == "addTeamMembers"){
			msgListHtml += '         ' +  "用户" + currentUser +"进入了直播间";
		}else if(obj.attach.type == "leaveTeam"){
			msgListHtml += '         ' +  "用户" + currentUser +"离开了直播间";
		}

	}else{
		msgListHtml += '         ' +  "不支持的消息格式";
	}
	msgListHtml += '    </div>';
	msgListHtml += '</div>';
	return msgListHtml;
}

/**
 * 加入群组回调事件 DF 2018年12月19日01:46:38
 * @param {Object} error
 * @param {Object} obj
 */
function applyTeamDone(error, obj){
	if(error != null){
	    disableChat("聊天室人数已满,无法加入聊天");
	    return;
	}
	app.utils.msgBox.msg("您已成功进入聊天室, 赶快发表一下自己的看法吧~");
}


function disableChat(msg){
	$(".message-input").attr("contenteditable",false);
	$(".message-input").text(msg);
	$(".message-input").css("color", "#b5b5b5");
	
	$(".send").attr("disabled",true);
	$(".send").css("color", "#b5b5b5");
}




function shareAction(s, ex) {
    app.print("分享操作：");
    if (!s) {
        app.print("无效的分享服务！");
        return;
    }
    if (s.authenticated) {
        app.print("---已授权---");
        shareMessage(s, ex);
    } else {
        app.print("---未授权---");
        s.authorize(function() {
            shareMessage(s, ex);
        }, function(e) {
            app.print("认证授权失败：" + e.code + " - " + e.message);
        });
    }
}



function shareMessage(s,ex){
    var msg={content:sharecontent.value,extra:{scene:ex}};
    if(pic&&pic.realUrl){
        msg.pictures=[pic.realUrl];
    }

    s.send( msg, function(){
        alert( "分享到\""+s.description+"\"成功！ " );
    }, function(e){
        alert( "分享到\""+s.description+"\"失败: "+e.code+" - "+e.message );
    } );
}



function cancelAuth(){
	try{
	    for ( var i in shares ) {
	        var s = shares[i];
	        if ( s.authenticated ) {
	            app.print( "取消\""+s.description+"\"");
	        }
	        s.forbid();
	    }
	    // 取消授权后需要更新服务列表
	    updateServices();
	    app.print( "操作成功！" );
	}catch(e){alert(e);}
}



function playImageAd(url, targetUrl, callback){	
	var html = $(".video").html();

	$(".video .info").hide(); 
	
	$(".video .player").css("background-image", "url("+ url +")");
	
	 $(".video").click(function(){
		plus.runtime.openURL(targetUrl);
    })
    
	app.utils.msgBox.msg("广告" + 3 + "秒后进入直播画面……");
    
	var obj = setTimeout(function(){
		$(".video .player").css("background-image", "url("+ "images/liveBackground.png" +")"); 
		$(".video .info").show();
		console.log("图片广告播放完毕");
		app.utils.msgBox.msg("进入直播画面");
		callback();
		clearTimeout(obj); 
	}, 3000);
	
}


function playVideoAd(url, targetUrl, callback){
	console.log("开始播放视频广告");
	
	var adVideo = new plus.video.VideoPlayer('video',{
	    src: url
	    //src:'http://play.512ck.cn/live/test1.m3u8'
	    //src:'http://ivi.bupt.edu.cn/hls/lytv.m3u8'
    });
    
    $(".video").click(function(){
		plus.runtime.openURL(targetUrl);
    })    
    
    // 监听开始播放事件
	adVideo.addEventListener('play', function(e){
		//plus.nativeUI.alert('Video play');
	app.utils.msgBox.msg("正在为您播放广告片头, 稍后将看到直播画面");
		
	}, false);
	// 监听播放进度更新事件
	adVideo.addEventListener('timeupdate', function(e){
		//console.log("timeupdate:" + JSON.stringify(e));
	}, false);
	// 监听播放结束事件
	adVideo.addEventListener('ended', function(e){
		//plus.nativeUI.alert('Video ended');
		console.log("广告播放完毕");
		app.utils.msgBox.msg("进入直播画面");
		callback();
	}, false);
	adVideo.play();
}


function playVideo(url){
	console.log("开始直播:" + url)
	//视频播放器组件
	var video = new plus.video.VideoPlayer('video',{
	    src: url
	    //src:'http://play.512ck.cn/live/test1.m3u8'
	    //src:'http://ivi.bupt.edu.cn/hls/lytv.m3u8'
    });
    // 监听开始播放事件
	video.addEventListener('play', function(e){
		//plus.nativeUI.alert('Video play');
	}, false);
	// 监听播放进度更新事件
	video.addEventListener('timeupdate', function(e){
		//rconsole.log("timeupdate:" + JSON.stringify(e));
	}, false);
	// 监听播放结束事件
	video.addEventListener('ended', function(e){
		//plus.nativeUI.alert('Video ended');
	}, false);
	video.play();
}


 

window.addEventListener("asyncInfo", function(){
	console.log("informations父窗口接到回调")
	var view = plus.webview.currentWebview();
	view.reload();
})
