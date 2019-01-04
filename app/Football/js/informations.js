var mdate;

mui.init();



var liveService = {
	/**
	 * 获取直播分类信息列表 DF 2018年12月18日01:47:31
	 * @param {Object} callback
	 */
	getLiveCategoryList: function(callback){
		$.get(app.config.apiUrl + "api/home/getLiveCategoryList", function(data){
			app.logger("lives", JSON.stringify(data));
			callback(data);
		});
	},
	/**
	 * 获取赛事列表 DF 2018年12月18日02:15:11
	 * @param {Object} callback
	 */
	getGameList: function(param, callback){
		$.get(app.config.apiUrl + "api/game/getGameList", param,  function(data){
			app.logger("lives", JSON.stringify(data));
			callback(data);
		});
	},
	
	/**
	 * 获取赛程信息列表 DF 2018年12月18日03:00:27
	 * @param {Object} callback
	 */
	getInformationDetailList: function(param, callback){
		$.get(app.config.apiUrl + "api/live/getInformationDetailList", param, function(data){
			app.logger("lives", JSON.stringify(data));
			app.logger("getInformationDetailList", JSON.stringify(param));
			callback(data);
		});
	},
	
}


mui.plusReady(function(){
	
	var webview = plus.webview.currentWebview();
	plus.navigator.setStatusBarStyle("dark");
	plus.navigator.setStatusBarBackground("#F3F3F3");
	
	 
	webview.addEventListener("show", function(){
		plus.navigator.setStatusBarStyle("dark");
		plus.navigator.setStatusBarBackground("#F3F3F3");
	
		console.log("情报中心");
		
		
		console.log("informations_show");
		
		var view = plus.webview.getWebviewById("index");
		if(view != null){
			view.evalJS("createNIM()");
		}
	 
		$("#date").val("");
	 
	 
		initData();
	})
	
	console.log("情报中心");
	
	initData();
})

$(function(){
		
	//显示所有分类
	$(".showCategory").click(function(){
		mui("#sheet").popover("toggle");
	});
})
 



/**
 * 获取情报列表
 * @param {Object} param
 */
function getSchedules(param){
	plus.nativeUI.showWaiting("努力加载中", {padlock: true});
	
	liveService.getInformationDetailList(param, function(res){ 
		plus.nativeUI.closeWaiting();
		
		if(app.utils.ajax.isError(res)) {
			if(res.code == 500 || res.code == 400){
				return app.utils.msgBox.msg("加载情报信息列表失败");
			}
		}
		
		if(res.code == 300){
			$(".live ul").html("");
			return;
		}
		
		var html = "";
			
		for (var i = 0; i < res.data.length; i++) {
			
			var item = res.data[i];
		 
			var dateStr = "";
			var dateTime = item.liveDate;
			dateStr = app.utils.getFormatYear(dateTime);
			dateStr += " " + app.utils.getWeek(dateTime);
			
			var gameTitle = item.gameName;
			if(item.gameName.indexOf("赛事") != -1) gameTitle = item.gameName.replaceX("赛事", "");
			var minute = app.utils.getFormatMinute(item.liveDate);
			gameTitle = gameTitle + " " + minute;
			

			var scheduleResult = item.forecastGrade;
			if(item.forecastResult != null && item.forecastResult.length > 0){
				scheduleResult = item.forecastResult;
			}else{
				scheduleResult = "-";
			}
				


			html += '<li data-status="' + item.status + '" data-videourl="'+ item.sourceUrl +'" data-id="' + item.liveId + '" data-gamename="'+ item.gameName +'" data-liveTitle="'+ item.liveTitle +'" class="openLive"> ';
    		html += '	<div class="top">';
    		html += '		<span class="off">' + gameTitle  +  '</span>';
    		html += '		<span class="off" style="float:right; margin-right: 5px">' + dateStr +  '</span>';
    		html += '	</div> ';
    		html += '	<div class="content">';
    		//判断是否为胜利球队
			if(item.masterTeamId == item.forecastTeamId){
    			html += '		<div class="left left-hot">';
    			html += '			<i class="icon iconfont icon-shoucangjiaobiao-copy"></i>';
			}else{
    			html += '		<div class="left">';
			}
    		html += '			<img src="'  + item.masterTeamIcon +'" alt="" />';
    		if(item.masterTeamName.length > 4){
    			html += '			<span class="longText">'  + item.masterTeamName +'</span>';
    		}else{
    			html += '			<span>'  + item.masterTeamName +'</span>';	
    		}
    		html += '		</div>';
    		html += '		<div class="info">';
    		html += '			<span>' +scheduleResult+  '</span>';
    		html += '		</div>';
    		//判断是否为胜利球队
			if(item.targetTeamId == item.forecastTeamId){
    			html += '		<div class="right right-hot">';
    			html += '			<i class="icon iconfont icon-shoucangjiaobiao"></i>';
			}else{
    			html += '		<div class="right">';
			}
    		html += '			<img src="'  + item.targetTeamIcon +'" alt="" />';
    		if(item.targetTeamName.length > 4){
    			html += '			<span class="longText">'  + item.targetTeamName +'</span>';
    		}else{
    			html += '			<span>'  + item.targetTeamName +'</span>';	
    		}
    		html += '		</div>'; 
    		html += '	</div> ';
    		html += '</li>';
		} 
		$(".live ul").html(html);
		
		//打开直播间
		$(".openLive").unbind("click").bind("click", function(){
			//检测是否已经登录
			var id = $(this).data("id");
			
			var view = plus.webview.getWebviewById("liveDetail-" + id);
				
			if(view != null) plus.webview.getWebviewById("liveDetail-" + id).close();
			

			app.utils.openNewWindowParam("liveDetail.html", "liveDetail-" + id, {
				liveId: id
			})
		});
		
		
		
	})
}


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



function initData(){
	
	//选择时间范围
	reloadDate();
	
	console.log("情报加载数据:" + $("#liveCategoryId").val() + "," + $("#currentIndex").val())

	console.log("情报刷新分类id:" + $("#liveCategoryId").val())
	
	getSchedules({
		gameId: $("#gameId").val(),
		liveCategoryId: $("#liveCategoryId").val(),
		date: $("#date").val()
	});
	
	initNavgation();

	app.logger("查询游戏赛事列表", $("#liveCategoryId").val())
	
	
	initGameList({
		liveCategoryId: $("#liveCategoryId").val()
	});
}

function initNavgation(){
	
	//加载直播分类列表
	liveService.getLiveCategoryList(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载直播分类列表失败");
		
		var html = ""; 
		
		for (var i = 0; i < res.data.length; i++) {
			html += '<li class="mui-table-view-cell">';
			html += '				<a href="javascript:void(0)" class="toCategory" data-id="' + res.data[i].categoryId + '">' + res.data[i].categoryName + '</a>';
			html += '			</li>';
		} 
		
			//选中当前直播分类 
		var currentIndex = $("#currentIndex").val();
		var iconPart = '<i class="icon iconfont icon-webicon215"></i>';
		
		console.log("选中直播分类" + currentIndex);

		$("#liveCategoryId").val(res.data[0].categoryId);

		
		if(currentIndex == null || currentIndex.length == 0) {
			$(".showCategory").html(res.data[0].categoryName + iconPart);			
			$(".showCategory").data("categoryid", res.data[0].categoryId);
		}else{
			$(".showCategory").html(res.data[currentIndex].categoryName + iconPart);
			$(".showCategory").data("categoryid", res.data[currentIndex].categoryId);
		}

		$("#gameId").val("");
		$("#currentIndex").val("0");
		$(".live-category").html(html);

		//设置滚动组件
		mui('.mui-scroll-wrapper').scroll();
		 
		$(".toCategory").unbind("click").bind("click", function(){
			var that = $(this);
			
			$("#liveCategoryId").val($(that).data("id"));
			
			$(".showCategory").html($(that).html() + iconPart);
			
			
			getSchedules({
				liveCategoryId: $(that).data("id")
			});

			initGameList({
				liveCategoryId:  $(that).data("id")
			});
			
			mui("#sheet").popover("toggle");
			
			//设置滚动组件
			mui('.mui-scroll-wrapper').scroll();
		})
	})

	//加载赛事列表
	initGameList({
		liveCategoryId:  $("#liveCategoryId").val()
	});
}

function initGameList(param){
	liveService.getGameList(param, function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载赛事列表失败");
		
		var html = "";
			html += '<li class="active">' + "全部赛事" + '</li>';
		
		for (var i = 0; i < res.data.length; i++) {
			var partHtml = '';
			html += '<li '+ partHtml +' data-id="' + res.data[i].gameId + '">' + res.data[i].gameName + '</li>';
		} 
		$(".category ul").html(html);
		
		$(".category ul li").unbind("click").bind("click", function(){
			var that = $(this);
			$("#gameId").val($(that).data("id"));
			$(".category ul li").removeClass("active");
			$(that).addClass("active");
			
			
			if($(that).text().indexOf("全部赛事") != -1){
				console.log("选中赛事" + $("#liveCategoryId").val())
				$("#gameId").val("");
				$("#date").val("");
				reloadDate();
				getSchedules({
					liveCategoryId: $("#liveCategoryId").val()				
				});
			
			}else{
				getSchedules({
					gameId: $("#gameId").val(),
					liveCategoryId: $("#liveCategoryId").val(),
					date: $("#date").val()
				});
			}		
			
			
		});
	})

}

window.addEventListener("asyncInfo", function(){
	console.log("informations父窗口接到回调")
	var view = plus.webview.currentWebview();
	view.show();
})


window.addEventListener("refreshIndex", function(event){
	$("#currentIndex").val(event.detail.currentIndex);
	$("#liveCategoryId").val(event.detail.liveCategoryId);
	console.log("refreshIndex:initData");
})


function reloadDate(){
		var currentDate = app.utils.getCurrentDate();
	
	$(".date span").text(utility.getCurrentDateFormat());
	
	mdate = new Mdate("dateSelector", {
		acceptId: "dateSelector",
		beginYear: currentDate.year,
		beginMonth: currentDate.month - 1,
		beginDay: 1,//currentDate.day.replace('0',"")
		endYear: currentDate.year + 10,
		endMonth: 12,
	    endDay: app.utils.getMonthDays(currentDate.year, currentDate.month),
	    format: "-",
	    yes: function(){
	    	var dateOpts = {
	    		year: $("#dateSelector").attr("data-year"),
	    		month: $("#dateSelector").attr("data-month"),
	    		day: $("#dateSelector").attr("data-day")
	    	}
	    	
	    	if(dateOpts.month.length < 2) dateOpts.month = "0" + dateOpts.month;
	    	if(dateOpts.day.length < 2)  dateOpts.day = "0" + dateOpts.day;
	    	var dateFormat = dateOpts.year + "-" + dateOpts.month + "-" + dateOpts.day;
	    	$(".date span").text(dateFormat + " " + app.utils.getWeek(dateFormat));
			$("#date").val(dateFormat);
	    	
	    	//筛选赛事结果
	    	getSchedules({
	    		gameId: $("#gameId").val(),
	    		liveCategoryId: $("#liveCategoryId").val(),
	    		date: $("#date").val()
	    	});
	    }
	}); 
}
