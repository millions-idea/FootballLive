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
	var webview = plus.webview.currentWebview();
	
	webview.setStyle({
		titleNView: {
			titleText: "系统消息",
			autoBackButton: true,
			backgroundColor: "#FBFBFB",
			titleColor: "#000"
		}
	})
	
	refreshHeight();
	
	plus.nativeUI.closeWaiting();
	
	webview.addEventListener("show", function(){
		refreshHeight();
		plus.nativeUI.closeWaiting();
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
			var typeText= "文字";
			
			if(item.type){
				part = "type-link";
				message = "请点击查看消息详情";
				typeText = "链接";
			}	
			
			var timeText = app.utils.timestampToDate(item.addDate);
			if(item.addDate == null) {
				timeText = "近期";
			}
		
			var color = "color: #000;";
			if(item.isRead == 1) color = "color: #BEBEBE;";
		
			html +='<li data-message="'+ item.message +'" data-id="'+ item.relationId +'" class="mui-media mui-table-view-cell mui-indexed-list-item '+ part +'" style="padding: 8px 10px; background-color:#fff">';
			html +='	<img style="width: 32px !important;height: 32px !important;position: relative;top: 5px;"  class="mui-media-object mui-pull-left" src="images/icon_verify_remind.png"/>';
			html +='	<div style="line-height: 35px;position: relative;left: 10px; font-size: 15px;' + color + '" class="mui-media-body">' + message + '</div>';
			html +='	<span style="float: left; position: relative; color: #BEBEBE; left: 4px; line-height: 30px;">' + typeText + '</span>';
			html +='	<span style="float: right; position: relative; color: #BEBEBE; right: 5px; line-height: 30px;">' + timeText +'</span>';
			html +='</li>';
		}
		$("#list").html(html);
		
		
		$(".type-text").click(function(){
			var that = $(this);
			
			messageService.signMessage({
				relationId: $(that).data("id")
			}, function(res){
				app.logger("systemMessage", JSON.stringify(res));
			})
			
			app.utils.openNewWindowParam("previewMessage.html","previewMessage", {
				map: {
					title: "短消息",
					content: $(that).data("message"),
				}
			})
		});
		
		
		$(".type-link").click(function(){
			var that = $(this);
			
			messageService.signMessage({
				relationId: $(that).data("id")
			}, function(res){
				app.logger("systemMessage", JSON.stringify(res));
			})
			
			plus.runtime.openURL($(this).data("message"));
		});
	})
})
 



function refreshHeight() {
	var list = document.getElementById('list');
	list.style.height = (document.body.offsetHeight) + "px";
}
