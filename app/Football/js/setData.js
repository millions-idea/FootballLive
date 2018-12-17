mui.init();

mui.plusReady(function(){
	//接收动态参数
	var self = plus.webview.currentWebview();
	
	var dynamicObject = self.map;
	
	$("#title").text(dynamicObject.title); 
	
	$("#input").attr("placeholder", dynamicObject.placeholder);
	
	
	if(dynamicObject.default != null) {
		$("#input").val(dynamicObject.default);
	}
	
	$("#finish").click(function(){
		if($("#input").val() == null || $("#input").val().length == 0) {
			app.utils.msgBox.msg("请先填写信息");
			return;
		}
		 
		var webview = plus.webview.getWebviewById(dynamicObject.webview);
		
		var method = dynamicObject.method + "('" + $("#input").val() + "')";
		
		if(webview != null) webview.evalJS(method);
	});
})
