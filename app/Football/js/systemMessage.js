mui.init();

var messageService = {
	/**
	 * 获取消息列表 DF 2018年12月19日06:30:15
	 * @param {Object} param
	 * @param {Object} callback
	 */
	getMessageList: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/getMessageList"), param, function(data){
			app.logger("profile", JSON.stringify(data));
			callback(data);
		});
	},
	
	
	/**
	 * 签收消息 DF 2018年12月19日06:30:15
	 * @param {Object} param
	 * @param {Object} callback
	 */
	signMessage: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/signMessage"), param, function(data){
			app.logger("profile", JSON.stringify(data));
			callback(data);
		});
	}
}

mui.plusReady(function(){
	webview.setStyle({
		titleNView: {
			titleText: "系统设置",
			autoBackButton: true,
			backgroundColor: "#FBFBFB",
			titleColor: "#000"
		}
	})
	
	messageService.getMessageList({}, function(res){
		if(app.utils.ajax.isError(res)) {
			app.utils.msgBox.msg("加载消息列表失败");
			return;
		}
		
		var html = "";
		for (var i = 0; i < res.data.length; i++) {
			var item = res.data[i];
			var message = item.message; 
			var part = "type-text";
			if(!item.type){
				part = "type-link";
			}
			html +='<li data-message="'+ item.message +'" data-id="'+ item.relationId +'" class="mui-media mui-table-view-cell mui-indexed-list-item '+ part +'" style="padding: 8px 10px;">';
			html +='	<img class="mui-media-object mui-pull-left" src="images/icon_verify_remind.png"/>';
			html +='	<div class="mui-media-body" style="line-height: 35px;">' + message + '</div>';
			html +='</li>';
		}
		$("#list").html(html);
		
		
		$(".type-text").click(function(){
			app.utils.openNewWindowParam("previewMessage.html","previewMessage", {
				map: {
					title: "预览消息",
					content: $(this).data("message"),
				}
			})
		});
		
		
		$(".type-text").click(function(){
			plus.runtime.openURL($(this).data("message"));
		});
	})
})
 