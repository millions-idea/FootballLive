mui.init();

mui.plusReady(function(){
	//接收动态参数
	var self = plus.webview.currentWebview();
	
	var dynamicObject = self.map;
	
	$("#title").text(dynamicObject.title); 
	
	$("#content").text(dynamicObject.content);
})
