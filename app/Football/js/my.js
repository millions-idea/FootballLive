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
		
		plus.navigator.setStatusBarStyle("dark");
		plus.navigator.setStatusBarBackground("#F3F3F3");
	
		//显示现在的信息
		profileService.getProfile({}, function(res){
			app.print(JSON.stringify(res));
			if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("拉取个人信息超时");
			var info = res.msg;
			$(".user-face").attr("src", info.photo);
			$(".user-code").text(info.nickName)
		});
	})
	
	//系统设置
	$(".setting").click(function(){
		app.utils.openNewWindow("systemSetting.html","systemSretting");
	});
	
	//个人资料
	$(".profile").click(function(){
		app.utils.openNewWindow("profile.html","profile");
	});
	
	//观看历史
	$(".history").click(function(){
		app.utils.openNewWindow("history.html","history");
	});
	
	//个人收藏
	$(".collect").click(function(){
		app.utils.openNewWindow("collect.html","collect");
	});
	
	//推送消息
	$(".pushMessage").click(function(){
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
	plus.storage.removeItem("userInfo");
	var webview = plus.webview.getWebviewById("index");
	webview.evalJS("destroyNim()");
	app.utils.openWindow("login.html", "login");
}

