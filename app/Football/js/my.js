mui.init();

var profileService = {
	getProfile: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/getProfile"), function(data){
			app.logger("my", JSON.stringify(data));
			callback(data);
		});
	}
}

mui.plusReady(function(){ 
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	
	plus.navigator.setStatusBarStyle("dark");
	plus.navigator.setStatusBarBackground("#F3F3F3");
	
	webview.addEventListener("show", function(){
		console.log("my刷新响应")
		plus.navigator.setStatusBarStyle("dark");
		plus.navigator.setStatusBarBackground("#F3F3F3");

		if(plus.storage.getItem("userInfo") != null){
			//显示现在的信息
			profileService.getProfile({}, function(res){
				if(res == null || res.msg == null){
					plus.storage.clear();
					app.utils.openNewWindow("login.html", "login");
					return false;
				}
				app.print(JSON.stringify(res));
				if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("拉取个人信息超时");
				var info = res.msg;
				$(".user-face").attr("src", info.photo);
				$(".user-code").text(info.nickName);
				$(".logout").show();
			});	
		}
		
	})
	
	//系统设置
	$(".setting").click(function(){
		if(checkLogin())
		app.utils.openNewWindow("systemSetting.html","systemSretting");
	});
	
	//个人资料
	$(".profile").click(function(){
		if(checkLogin())
		app.utils.openNewWindow("profile.html","profile");
	});
	
	//观看历史
	$(".history").click(function(){
		if(checkLogin())
		app.utils.openNewWindow("history.html","history");
	});
	
	//个人收藏
	$(".collect").click(function(){
		if(checkLogin())
		app.utils.openNewWindow("collect.html","collect");
	});
	
	//推送消息
	$(".pushMessage").click(function(){
		if(checkLogin())
		app.utils.openNewWindowParam("systemMessage.html", "systemMessage-", {})
	});
	
	//分享我们
	$(".share").click(function(){
		app.utils.copyTo(app.config.shareUrl);
		app.utils.msgBox.msg("已为您复制宣传网址，快发给朋友吧~");
	});
	
	//联系我们
	$(".contact").click(function(){		
		app.utils.openNewWindow("contact.html","contact");
	});
	
	// 注销登录
	$("#logout").click(function(){
		logout();
	});
	
	 
})


/**
 * 更新用户名片 DF 2018年12月10日21:51:25
 * @param {Object} photo
 * @param {Object} userCode
 * @param {Object} nickname
 * @param {Object} signature
 */
function updateUserInfo(photo, userCode, nickname, signature){
	var webview = plus.webview.getWebviewById("index");
	mui.fire(webview, "updateUserInfo", {
		userCode: userCOde,
		photo: photo,
		nickname: nickname,
		signature: signature
	});
}

function logout(){
	plus.storage.clear();
	
	var webview = plus.webview.getWebviewById("index");
	if(webview != null) webview.evalJS("destroyNim()");
	
	webview = plus.webview.getWebviewById("my");
	if(webview != null) mui.fire(webview, "refreshInfo", {});
	
	app.utils.openNewWindow("login.html", "login");
}



function checkLogin(){
	var cache = plus.storage.getItem("userInfo");
	
	if(cache == null) {
		app.utils.openNewWindow("login.html", "login");
		return false;
	}

	return cache != null;
}


window.addEventListener("refreshInfo", function(){
	$(".user-face").attr("src", "images/head-default.png");
	$(".user-code").text("请登录");
	$(".logout").hide();
	
	profileService.getProfile({}, function(res){
		if(res == null || res.msg == null){
			app.utils.openNewWindow("login.html", "login");
			return false;
		}
		app.print(JSON.stringify(res));
		if(app.utils.ajax.isError(res)) return;
		var info = res.msg;
		$(".user-face").attr("src", info.photo);
		$(".user-code").text(info.nickName);
		$(".logout").show();
	});	
})


window.addEventListener("asyncInfo", function(){
	console.log("my父窗口接到回调")
	var view = plus.webview.currentWebview();
	view.show();
})
