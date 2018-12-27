var utils = app.utils;

mui.init();

mui.plusReady(function() {
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	webview.setStyle({
		titleNView: {
			titleText: "系统设置",
			autoBackButton: true,
			backgroundColor: "#FBFBFB",
			titleColor: "#000"
		}
	})
	
	//系统反馈
	$(".feedback").click(function(){
		app.utils.openNewWindow("feedback.html","feedback");
	})
	
	// 清除缓存
	$(".clean").click(function(){
		app.utils.msgBox.msg("清空成功");
		
	});
	
}); 