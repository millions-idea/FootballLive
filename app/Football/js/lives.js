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
	console.log("refreshIndex:initData");
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
			dateStr += " " + app.utils.getWeek(dateTime) + " ";
			
			var gameTitle = item.gameName;
			
			var leftHot = "",
				leftHotIcon = "",
				rightHot = "",
				rightHotIcon = "";

    		var liveTitle = gameTitle + item.liveTitle;

			if(statusStyle == "over"){
				//直播大厅，已结束的比赛显示成绩和胜利方(角标)
				
				if(item.scheduleGrade == null) item.scheduleGrade = "-/-";
				gameTitle = item.scheduleResult + " : " + item.scheduleGrade;
				if(item.scheduleResult == null || item.scheduleResult.length == 0){
					gameTitle = item.scheduleGrade;
				}
				
				//判断胜利方
				if(item.winTeamId == item.masterTeamId){
					leftHot = "left-hot";
					leftHotIcon = '<i class="icon iconfont icon-shoucangjiaobiao-copy"></i>';
				}
				
				if(item.winTeamId == item.targetTeamId){
					rightHot = "right-hot";
					rightHotIcon = '<i class="icon iconfont icon-shoucangjiaobiao"></i>';
				}
				
			}else{
				if(item.gameName.indexOf("赛事") != -1) gameTitle = item.gameName.replaceX("赛事", "");
				var minute = app.utils.getFormatMinute(item.liveDate);
				gameTitle = gameTitle + " " + minute;
			}
			
			var liStyle = "";
			if(item.cloudId != null){
				liStyle = "notvs openLive";
			}

 
			html += '<li class="'+liStyle+'" data-status="' + item.status + '" data-videourl="'+ item.sourceUrl +'" data-id="' + item.liveId + '" data-gamename="'+ item.gameName +'" data-liveTitle="'+ item.liveTitle +'" class="openLive">';
    		html += '	<div class="top">';
    		html += '		<span class="status ' + statusStyle + '">' + status + '</span>';
    		html += '		<span class="time">' + dateStr + '</span>';
    		html += '		<span class="operation">';
    		html += '			<i class="icon iconfont icon-youhua"></i>';
    		html += '		</span>';
    		html += '	</div>  ';
    		html += '	<div class="content">';
    		html += '		<div class="left '+ leftHot +'">';
    		html += leftHotIcon;
    		html += '			<img src="'  + item.masterTeamIcon +'" alt="" />';
    		html += '			<span>'+ item.masterTeamName +'</span>';
    		html += '		</div>';
    		html += '		<div class="info">';
    		html += '			<span>' + gameTitle + '</span>';
    		if(item.cloudId != null){
    			html += '<span class="red-grade">' + item.scheduleGrade + '</span>';
    		}
    		html += '		</div>';
    		html += '		<div class="right '+ rightHot +'">';
    		html += rightHotIcon;
    		html += '			<img src="'  + item.targetTeamIcon +'" alt="" />';
    		html += '			<span>'+ item.targetTeamName +'</span>';
    		html += '		</div>';
    		html += '	</div> 	';
    		html += '<div class="foot">';
    		

    		
    		if(item.cloudId != null){
    			liveTitle =  "主队角球:"+ item.masterCornerKick +" 主队黄牌:"+ item.masterYellowChess +" | 客队角球:"+ item.targetCornerKick +" 客队黄牌:"+ item.targetYellowChess +"";
    		}

    		
			html += '	<span class="status off">' + liveTitle + '</span>';
			html += '</div>';
    		html += '</li>';
		} 
		$(".live ul").html(html);
		
		//打开直播间
		$(".openLive").unbind("click").bind("click", function(){
			console.log("click")
			//检测是否已经登录
			var id = $(this).data("id");

			if(id == null) {
				app.utils.msgBox.msg("直播尚未开始");
				return;
			}

			var view = plus.webview.getWebviewById("liveDetail-" + id);
			if(view != null) view.close();
			

			app.utils.openNewWindowParam("liveDetail.html", "liveDetail-" + id, {
				liveId: id
			})
		});
		
		
		
	})
}


function initData(){
	//获取赛程信息列表
	console.log("加载数据:" + $("#liveCategoryId").val())
	
	console.log("刷新分类id:" + $("#liveCategoryId").val())
		
	getSchedules({
		gameId: $("#gameId").val(),
		liveCategoryId: $("#liveCategoryId").val(),
		date: $("#date").val()
	});
	
	console.log("initData:loadLiveCategoryList")
	//加载直播分类列表
	loadLiveCategoryList();
	

	//加载赛事列表
	app.logger("查询游戏赛事列表", $("#liveCategoryId").val())
	initGameList({
		"liveCategoryId": $("#liveCategoryId").val()
	})
	
}

function initGameList(param){
	liveService.getGameList(param,function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载赛事列表失败");
		
		var html = "";
			html += '<li class="active">' + "全部赛事" + '</li>';
		for (var i = 0; i < res.data.length; i++) {
			var partHtml = '';
			html += '<li '+ partHtml +' data-id="' + res.data[i].gameId + '">' + res.data[i].gameName + '</li>';
		} 
		$(".category ul").html(html);
		$("#gameId").val("");
		
		$(".category ul li").unbind("click").bind("click", function(){
			var that = $(this),
				title = $(that).text();
			$("#gameId").val($(that).data("id"));
			$(".category ul li").removeClass("active");
			$(that).addClass("active");
			
			if(title.indexOf("全部赛事") != -1){
				console.log("选中赛事" + $("#liveCategoryId").val())
				$("#gameId").val("");
				$("#date").val("");
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
			
			$("#gameId").val("");
			
			
			getSchedules({
				liveCategoryId: $("#liveCategoryId").val()
			});

			initGameList({
				liveCategoryId:  $("#liveCategoryId").val()
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
