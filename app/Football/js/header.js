mui.init(); 

mui.plusReady(function() {
	// 设置状态栏样式
  	plus.navigator.setStatusBarStyle("light");
	plus.navigator.setStatusBarBackground(app.style.backgroundColor);
	
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	webview.setStyle({
		titleNView: {
			backgroundColor: app.style.backgroundColor,
			titleColor: "#fff"
		}
	}) 
}); 