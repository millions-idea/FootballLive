mui.init();

var historyService = {
	getHistoryList: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/getLiveHistoryList"), function(data){
			app.logger("history", JSON.stringify(data));
			callback(data);
		});
	},
	
	cleanHistorys: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/cleanHistorys"), function(data){
			app.logger("history", JSON.stringify(data));
			callback(data);
		});
	}
}

mui.plusReady(function(){
	//清空
	$(".clean").click(function(){
		mui.confirm("您确定要清空所有记录吗", "清空历史记录", ["确定","取消"], function(e){
			//确定
			if(e.index == 0){
				historyService.cleanHistorys({}, function(res){
					if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("清空失败");
					app.utils.msgBox.msg("清空成功");
					$(".live ul").html("");
				});
			}
		})
	});
	
	//获取观看历史记录
	historyService.getHistoryList({}, function(res){
		if(app.utils.ajax.isError(res)) {
			if(res.msg == "空的集合"){
				return app.utils.msgBox.msg("您还没有观看过直播哦");
			}
			return app.utils.msgBox.msg("加载观看历史记录失败");
		}
		
		var html = "";
		var list = res.data;
		for (var i = 0; i < list.length; i++) {
			var item = list[i];
			
			
			var dateStr = "";
			var dateTime = item.liveDate;
			dateStr = app.utils.getFormatYear(dateTime);
			dateStr += " " + app.utils.getWeek(dateTime);
				
			var gameTitle = item.gameName;
			if(item.gameName.indexOf("赛事") != -1) gameTitle = item.gameName.replaceX("赛事", "");
			var minute = app.utils.getFormatMinute(item.liveDate);
			gameTitle = gameTitle + " " + minute;
	
			var result = item.scheduleResult + ": " + item.scheduleGrade;
			if(item.scheduleResult == null) result = item.scheduleGrade;
			
			html += '<li>';
    		html += '	<div class="top">';
    		html += '		<span class="off">' + gameTitle + '</span>';
    		html += '	</div>';
    		html += '	<div class="content">';
    		html += '		<div class="left">';
    		html += '			<img src="' + item.team.teamIcon + '" alt="" />';
    		html += '			<span>' + item.team.teamName + '</span>';
    		html += '		</div>';
    		html += '		<div class="info">';
    		html += '			<span>' + result + '</span>';
    		html += '		</div>';
    		html += '		<div class="right">';
    		html += '			<img src="' + item.targetTeam.teamIcon + '" alt="" />';
    		html += '			<span>' + item.targetTeam.teamName + '</span>';
    		html += '		</div>';
    		html += '	</div>';
    		html += '</li>';
		}
		
		$(".live ul").html(html);
		
	})	
})

$(function(){
	
})
