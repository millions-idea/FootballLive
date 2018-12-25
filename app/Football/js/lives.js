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
	getGameList: function(callback){
		$.get(app.config.apiUrl + "api/game/getGameList", function(data){
			app.logger("lives", JSON.stringify(data));
			callback(data);
		});
	},
	
	/**
	 * 获取赛程信息列表 DF 2018年12月18日03:00:27
	 * @param {Object} callback
	 */
	getLiveGameDetailList: function(param, callback){
		$.get(app.config.apiUrl + "api/live/getLiveGameDetailList", param, function(data){
			app.logger("lives", JSON.stringify(data));
			callback(data);
		});
	},
	
}

window.addEventListener("refreshIndex", function(event){
	$("#currentIndex").val(event.detail.currentIndex);
	$("#liveCategoryId").val(event.detail.liveCategoryId);
	//initData();
	console.log("refreshIndex:initData")
	//console.log("refreshIndex:loadLiveCategoryList")
	//loadLiveCategoryList();
	
})

mui.plusReady(function(){ 
	var webview = plus.webview.currentWebview();
	plus.navigator.setStatusBarStyle("dark");
	plus.navigator.setStatusBarBackground("#F3F3F3");
	
	 
	plus.webview.currentWebview().addEventListener("show", function(){
		plus.navigator.setStatusBarStyle("dark");
		plus.navigator.setStatusBarBackground("#F3F3F3");
	 	
	 	console.log("直播大厅");
	 
	 
		console.log("lives_show");
		
		var view = plus.webview.getWebviewById("index");
		if(view != null){
			view.evalJS("createNIM()");
		}
	 
		$("#date").val("");
		
		console.log("show:initData");
		
		initData();
		
	})
	
	console.log("直播大厅");
	console.log("plusReday:initData");
	initData();  
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
		beginMonth: currentDate.month - 1,
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
	
	//显示所有分类
	$(".showCategory").click(function(){
		mui("#sheet").popover("toggle");
	});
	
	
})
 
/**
 * 获取赛程结果
 * @param {Object} param
 */
function getSchedules(param){
	liveService.getLiveGameDetailList(param, function(res){
		if(app.utils.ajax.isError(res)) {
			if(res.code == 500 || res.code == 400){
				return app.utils.msgBox.msg("加载赛事列表失败");
			}
		}
		
		if(res.code == 300){
			$(".live ul").html("");
			return;
		}
		
		var html = "";
		for (var i = 0; i < res.data.length; i++) {
			
			var item = res.data[i];
			var status = "";
			var statusStyle = "off";
			switch(item.status){
				case 0: 
					status = "未开始";
					statusStyle = "off";
					break;
				case 1: 
					status = "正在直播";
					statusStyle = "on";
					break;
				case 2: 
					status = "已结束";
					statusStyle = "over";
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
    		html += '		<span class="status ' + statusStyle + '">' + status + '</span>';
    		html += '		<span class="time">' + dateStr + '</span>';
    		html += '		<span class="operation">';
    		html += '			<i class="icon iconfont icon-youhua"></i>';
    		html += '		</span>';
    		html += '	</div>  ';
    		html += '	<div class="content">';
    		html += '		<div class="left">';
    		html += '			<img src="'  + item.team.teamIcon +'" alt="" />';
    		html += '			<span>'+ item.team.teamName +'</span>';
    		html += '		</div>';
    		html += '		<div class="info">';
    		html += '			<span>' + gameTitle + '</span>';
    		html += '		</div>';
    		html += '		<div class="right">';
    		html += '			<img src="'  + item.targetTeam.teamIcon +'" alt="" />';
    		html += '			<span>'+ item.targetTeam.teamName +'</span>';
    		html += '		</div>';
    		html += '	</div> 	';
    		html += '<div class="foot">';
			html += '	<span class="status off">' + item.liveTitle + '</span>';
			html += '</div>';
    		html += '</li>';
		} 
		$(".live ul").html(html);
		
		//打开直播间
		$(".openLive").unbind("click").bind("click", function(){
			//检测是否已经登录
			var id = $(this).data("id");
			
			var cache = plus.storage.getItem("userInfo");
			if(cache == null) {
				app.utils.openNewWindow("login.html", "login");
				plus.webview.getWebviewById("liveDetail-" + id).hide();
				return false;
			}

			var view = plus.webview.getWebviewById("liveDetail-" + id);
				
			if(view != null) plus.webview.getWebviewById("liveDetail-" + id).close();
			

			app.utils.openNewWindowParam("liveDetail.html", "liveDetail-" + id, {
				liveId: id
			})
		});
		
		
		
	})
}


function initData(){
	//获取赛程信息列表
	console.log("加载数据:" + $("#liveCategoryId").val())
	if($("#currentIndex").val() == null && $("#currentIndex").val().length <= 0){
		getSchedules({});
	}else{
		getSchedules({
			gameId: $("#gameId").val(),
			liveCategoryId: $("#liveCategoryId").val(),
			date: $("#date").val()
		});
	}

	console.log("initData:loadLiveCategoryList")

	//加载直播分类列表
	loadLiveCategoryList();
	
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
		$("#gameId").val("");
		
		$(".category ul li").unbind("click").bind("click", function(){
			var that = $(this);
			$("#gameId").val($(that).data("id"));
			$(".category ul li").removeClass("active");
			$(that).addClass("active");
			
			getSchedules({
				gameId: $("#gameId").val(),
				liveCategoryId: $("#liveCategoryId").val(),
				date: $("#date").val()
			});
		});
	})
	
}


function loadLiveCategoryList(){
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
		
		if(currentIndex == null || currentIndex.length == 0) {
			$(".showCategory").html(res.data[0].categoryName + iconPart);
		}else{
			$(".showCategory").html(res.data[currentIndex].categoryName + iconPart);
		}
		$("#currentIndex").val("");
		$("#liveCategoryId").val("");
		$(".live-category").html(html); 
		 
		//设置滚动组件
		mui('.mui-scroll-wrapper').scroll();
		
		
		$(".toCategory").unbind("click").bind("click", function(){
			var that = $(this);
			$("#liveCategoryId").val($(that).data("id"));
			
			$(".showCategory").html($(that).html() + iconPart);
			
			
			getSchedules({
				gameId: $("#gameId").val(),
				liveCategoryId: $("#liveCategoryId").val(),
				date: $("#date").val()
			});
			
			mui("#sheet").popover("toggle");
			 
			//设置滚动组件
			mui('.mui-scroll-wrapper').scroll();
		})
	})

}

window.addEventListener("asyncInfo", function(){
	console.log("lives父窗口接到回调")
	var view = plus.webview.currentWebview();
	view.show();
})
