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
}); 