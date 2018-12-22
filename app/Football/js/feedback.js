mui.init();

var feedbackService = {
	addFeedback: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/addFeedback"), function(data){
			app.logger("history", JSON.stringify(data));
			callback(data);
		});
	}
}

mui.plusReady(function(){
	
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	webview.setStyle({
		titleNView: {
			autoBackButton: true,
			titleText: "反馈"
		}
	})
	
	$("#submit").click(function(){
		var content = $("#content").val();
		if(content == null || content.length <= 0){
			return app.utils.msgBox.msg("请输入反馈内容");
		}
		
		feedbackService.addFeedback({
			content: content
		}, function(res){
			if(app.utils.ajax.isError(res)) {
				if(res.code == 300) return app.utils.msgBox.msg(res.msg);
				return app.utils.msgBox.msg("发送反馈失败");
			}
			app.utils.msgBox.msg("感谢您的反馈，系统已成功收取！");
		})
	});
})
