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
	,keyEventBind: {
			backbutton: false  //关闭back按键监听
		}
});

mui.plusReady(function() {
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	//plus.navigator.setStatusBarStyle("dark");
	//plus.navigator.setStatusBarBackground(app.style.backgroundColor);
	
	/*webview.setStyle({
		bounce: 'all',//窗口回弹效果
		bounceBackground: "##4598FF",
		popGesture: true,//侧滑返回
	})*/
	//清空webSocket连接
	
	var cacheString = plus.storage.getItem("userInfo"); 
	if(cacheString != null) {
		app.logger("index","已登录，自动跳转到首页");
		
			
		var webview = plus.webview.getWebviewById("index");
		if(webview != null) webview.evalJS("newInstance()");
		
		
		app.utils.openWindow("index.html", "index");
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
			
			
			var webview = plus.webview.getWebviewById("index");
			if(webview != null) webview.evalJS("newInstance()");
			
			mui.openWindow("index.html", "index"); 			
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