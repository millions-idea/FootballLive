var mdate;

mui.init();

var liveService = {
	/**
	 * 获取直播分类信息列表 DF 2018年12月18日01:47:31
	 * @param {Object} callback
	 */
	getLiveCategoryList: function(callback){
		$.get(app.config.apiUrl + "api/home/getLiveCategoryList", function(data){
			app.logger("home", JSON.stringify(data));
			callback(data);
		});
	},
	/**
	 * 获取赛事列表 DF 2018年12月18日02:15:11
	 * @param {Object} callback
	 */
	getGameList: function(callback){
		$.get(app.config.apiUrl + "api/game/getGameList", function(data){
			app.logger("home", JSON.stringify(data));
			callback(data);
		});
	},
	
	/**
	 * 获取赛程信息列表 DF 2018年12月18日03:00:27
	 * @param {Object} callback
	 */
	getLiveGameDetailList: function(callback){
		$.get(app.config.apiUrl + "api/live/getLiveGameDetailList", function(data){
			app.logger("home", JSON.stringify(data));
			callback(data);
		});
	},
	
}

mui.plusReady(function(){ 

	//获取赛程信息列表
	liveService.getLiveGameDetailList(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载赛事列表失败");
		var html = "";
		for (var i = 0; i < res.data.length; i++) {
			
			var item = res.data[i];
			var status = "";
			switch(item.status){
				case 0: 
					status = "未开始";
					break;
				case 1: 
					status = "正在直播";
					break;
				case 2: 
					status = "已结束";
					break;
				default:
					status = item.status;
					break;
			}
			
			var dateStr = "";
			var dateTime = item.liveDate;
			dateStr = app.utils.getFormatYear(dateTime);
			dateStr += " " + app.utils.getWeek(dateTime);
			
			var gameTitle = item.gameName;
			if(item.gameName.indexOf("赛事") != -1) gameTitle = item.gameName.replaceX("赛事", "");
			var minute = app.utils.getFormatMinute(item.liveDate);
			gameTitle = gameTitle + " " + minute;

			
			html += '<li data-status="' + item.status + '" data-videourl="'+ item.sourceUrl +'" data-id="' + item.liveId + '" data-gamename="'+ item.gameName +'" data-liveTitle="'+ item.liveTitle +'" class="openLive">';
    		html += '	<div class="top">';
    		html += '		<span class="status on">' + status + '</span>';
    		html += '		<span class="time">' + dateStr + '</span>';
    		html += '		<span class="operation">';
    		html += '			<i class="icon iconfont icon-youhua"></i>';
    		html += '		</span>';
    		html += '	</div>  ';
    		html += '	<div class="content">';
    		html += '		<div class="left">';
    		html += '			<img src="images/luneng.png" alt="" />';
    		html += '			<span>'+ item.team.teamName +'</span>';
    		html += '		</div>';
    		html += '		<div class="info">';
    		html += '			<span>' + gameTitle + '</span>';
    		html += '		</div>';
    		html += '		<div class="right">';
    		html += '			<img src="images/guoan.png" alt="" />';
    		html += '			<span>'+ item.targetTeam.teamName +'</span>';
    		html += '		</div>';
    		html += '	</div> 	';
    		html += '</li>';
		} 
		$(".live ul").html(html);
		
		//打开直播间
		$(".openLive").click(function(){
			var id = $(this).data("id");
			var liveTitle = $(this).data("livetitle");
			var gameName = $(this).data("gamename");
			var videoUrl = $(this).data("videourl");
			var status = $(this).data("status");

			app.utils.openNewWindowParam("liveDetail.html", "liveDetail-" + id, {
				id: id,
				title:liveTitle,
				gameName: gameName,
				videoUrl: videoUrl,
				status: status
			})
		});
	})
})

$(function(){
	var utility = {
		getCurrentDateFormat: function(){
			var currentDate = app.utils.getCurrentDate();
			var dateOpts = {
	    		year: currentDate.year,
	    		month: currentDate.month,
	    		day: currentDate.day
	    	}
	    	var dateFormat = dateOpts.year + "-" + dateOpts.month + "-" + dateOpts.day; 
	    	return dateFormat += " " + app.utils.getWeek(dateFormat);
		}
	};
	//选择时间范围
	var currentDate = app.utils.getCurrentDate();
	
	$(".date span").text(utility.getCurrentDateFormat());
	
	mdate = new Mdate("dateSelector", {
		acceptId: "dateSelector",
		beginYear: currentDate.year,
		beginMonth: currentDate.month,
		beginDay: currentDate.day,
		endYear: currentDate.year + 6,
		endMonth: currentDate.month,
	    endDay: currentDate.day,
	    format: "-",
	    yes: function(){
	    	var dateOpts = {
	    		year: $("#dateSelector").attr("data-year"),
	    		month: $("#dateSelector").attr("data-month"),
	    		day: $("#dateSelector").attr("data-day")
	    	}
	    	var dateFormat = dateOpts.year + "-" + dateOpts.month + "-" + dateOpts.day;
	    	$(".date span").text(dateFormat += " " + app.utils.getWeek(dateFormat));
	    }
	}); 

	//显示所有分类
	$(".showCategory").click(function(){
		mui("#sheet").popover("toggle");
	});
	
	//加载直播分类列表
	liveService.getLiveCategoryList(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载热门直播数据失败");
		
		var html = "";
		
		for (var i = 0; i < res.data.length; i++) {
			html += '<li class="mui-table-view-cell">';
			html += '				<a href="javascript:void(0)" class="toCategory" data-id="' + res.data[i].categoryId + '">' + res.data[i].categoryName + '</a>';
			html += '			</li>';
		} 
		
		//选中当前直播分类
		var currentIndex = $("#currentIndex").val();
		var iconPart = '<i class="icon iconfont icon-webicon215"></i>';
		
		if(currentIndex == null) {
			$(".showCategory").html(res.data[0].categoryName + iconPart);
		}else{
			$(".showCategory").html(res.data[currentIndex].categoryName + iconPart);
		}
		
		$(".live-category").html(html); 
	})

	//加载赛事列表
	liveService.getGameList(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载赛事列表失败");
		
		var html = "";
		for (var i = 0; i < res.data.length; i++) {
			var partHtml = '';
			if(i == 0){
				partHtml = 'class="active"';
			}
			html += '<li '+ partHtml +' data-id="' + res.data[i].gameId + '">' + res.data[i].gameName + '</li>';
		} 
		$(".category ul").html(html);
	})
})
 