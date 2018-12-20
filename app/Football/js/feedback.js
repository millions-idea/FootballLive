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
	$("#submit").click(function(){
		var content = $("#content").text();
		if(content == null || content.length <= 0){
			return app.utils.msgBox.msg("请输入反馈内容");
		}
		
		feedbackService.addFeedback({
			content: content
		}, function(res){
			if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("发送反馈失败");
			app.utils.msgBox.msg("感谢您的反馈，系统已成功收取！");
		})
	});
})
