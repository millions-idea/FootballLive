var utils = app.utils;

mui.init({
  preloadPages:[
    {
      url:'findPassword.html',
      id:'findPassword'
    }
    ,{
      url:'reg.html',
      id:'reg'
    }
    ,{
      url:'regPact.html',
      id:'regPact'
    }]
});

mui.plusReady(function() {
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	plus.navigator.setStatusBarStyle("light");
	plus.navigator.setStatusBarBackground(app.style.backgroundColor);
	
	plus.webview.currentWebview().addEventListener("show", function(){
		plus.navigator.setStatusBarStyle("light");
		plus.navigator.setStatusBarBackground(app.style.backgroundColor);
	})
	
	//首页返回键处理,处理逻辑：1秒内,连续两次按返回键，则退出应用
	var first = null;
	mui.back = function() {
		console.log("回退")
			return false;
	};
	
	$(".closeButton").click(function(){
		plus.navigator.setStatusBarStyle("dark");
		plus.navigator.setStatusBarBackground("#F3F3F3");
    plus.webview.currentWebview().close();
	})
	
	var cacheString = plus.storage.getItem("userInfo"); 
	if(cacheString != null) {
		app.logger("index","已登录，自动跳转到首页");
		app.utils.msgBox.msg("已为您自动登录……")


		var parentWebview = plus.webview.currentWebview().opener();
		if(parentWebview != null){
				console.log("调用父窗口刷新函数")
				mui.fire(parentWebview, "asyncInfo", {});
		}


		plus.navigator.setStatusBarStyle("dark");
		plus.navigator.setStatusBarBackground("#F3F3F3");
		
		plus.webview.currentWebview().close();
		return;
	}
	
	
	
	
	
	//刷新标题
	$("span[class='title']").text(app.config.brand);
	
	//显示版本号
	$("#version").text("v" + app.config.version);
	
	//登录
	$("#login").click(function(){
		var $phone = $("input[name='phone']"),
				$password = $("input[name='password']");
			
		if($phone == null || $phone.val().length != 11) return utils.msgBox.msg("请输入正确的手机号(11位)");
		if($password == null || $password.val().length < 6) return utils.msgBox.msg("请输入密码(6位)");
		
		app.logger("Login", app.config.apiUrl + "api/user/signIn");
			
		$.post(app.config.apiUrl + "api/user/signIn", {
			phone: $phone.val(),
			password: $password.val()
		}, function(data){
			app.logger("Login", JSON.stringify(data));
			 
			if(utils.ajax.isError(data)) {
				if(data.code == 400 || data.code == 300){
					return utils.msgBox.msg(data.msg);
				}
				return utils.msgBox.msg("登录失败");
			}
			
			// 记录缓存信息
			plus.storage.setItem('userInfo', JSON.stringify(data.msg));
			
			
			var parentWebview = plus.webview.currentWebview().opener();
			if(parentWebview != null){
					console.log("调用父窗口刷新函数")
					mui.fire(parentWebview, "asyncInfo", {});
			}
 
			
			plus.navigator.setStatusBarStyle("dark");
			plus.navigator.setStatusBarBackground("#F3F3F3");
			
			var subView = plus.webview.getWebviewById("reg");
			if(subView != null) subView.close();
			
			subView = plus.webview.getWebviewById("findPassword");
			if(subView != null) subView.close();
			
			plus.webview.currentWebview().close();
				
		});
		
	});

	//忘记密码
	$("#findPassword").click(function(){
		app.utils.openNewWindow("findPassword.html","findPassword");
	})
	
	//手机号注册
	$("#reg").click(function(){
		app.utils.openNewWindow("reg.html","reg");
	})
});