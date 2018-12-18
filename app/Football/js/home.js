mui.init();

var homeService = {
	/**
	 * 获取首页聚合数据 DF 2018年12月14日10:15:29
	 * @param {Object} callback
	 */
	getGroupInfo: function(callback){
		$.get(app.config.apiUrl + "api/home/getGroupInfo", function(data){
			app.logger("home", JSON.stringify(data));
			callback(data);
		});
	},
	
	/**
	 * 获取热门直播数据  DF 2018年12月18日00:39:14
	 * @param {Object} callback
	 */
	getHotGameList: function(callback){
		$.get(app.config.apiUrl + "api/home/getHotGameList", function(data){
			app.logger("home", JSON.stringify(data));
			callback(data);
		});
	},
	
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
	 * 查询轮播图列表 DF 2018年12月14日11:04:21
	 * @param {Object} list
	 */
	getRunnerImageList: function(list){
		var tempList = new Array();
		for (var i = 0; i < list.length; i++) {
			if(list[i].key.indexOf("banner.image") != -1){
				tempList.push(list[i]);
			}
		}
		return tempList;
	},
	
	/**
	 * 查询今日头条 DF 2018年12月14日15:49:08
	 * @param {Object} list
	 */
	getTodayHeadLine: function(list){
		var tempList = new Array();
		for (var i = 0; i < list.length; i++) {
			if(list[i].key.indexOf("home.text.ad") != -1){
				return list[i];
			}
		}
		return null;
	}
}

mui.plusReady(function(){
	//设置状态栏样式
	plus.navigator.setStatusBarStyle("light");
	plus.navigator.setStatusBarBackground("#333333");
	
	
	//获得slider插件对象
    var gallery = mui('.mui-slider');
        gallery.slider({
  	    interval:5000//自动轮播周期，若为0则不自动播放，默认为0；
    });
})

$(function(){
	//加载首页聚合数据
	homeService.getGroupInfo(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载聚合数据失败");
	
		//获取滚动图列表
		var runnerImageList = homeService.getRunnerImageList(res.data);
		
		//渲染滚动图列表
		$("#runnerImage").html(app.utils.raw(runnerImageList));
		
		//渲染今日头条 
		var headLineMap = homeService.getTodayHeadLine(res.data);
		if(headLineMap == null) headLineMap = { value : "加载今日头条失败"};
		$("#topTextAd").text(headLineMap.value);
	});
	
	//加载热门直播数据
	homeService.getHotGameList(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载热门直播数据失败");
		
		//参赛球队信息设置
		$(".left .title").text(res.data[0].gameName + " " + app.utils.getFormatMinute(res.data[0].liveDate));
		$(".left .teamIcon").attr("src", res.data[0].team.teamIcon);
		$(".left .teamName").text(res.data[0].team.teamName);
		$(".left .targetTeamIcon").attr("src", res.data[0].targetTeam.teamIcon);
		$(".left .targetTeamName").text(res.data[0].targetTeam.teamName);
		
		//对战球队信息设置
		$(".right .title").text(res.data[1].gameName + " " + app.utils.getFormatMinute(res.data[1].liveDate));
		$(".right .teamIcon").attr("src", res.data[1].team.teamIcon);
		$(".right .teamName").text(res.data[1].team.teamName);
		$(".right .targetTeamIcon").attr("src", res.data[1].targetTeam.teamIcon);
		$(".right .targetTeamName").text(res.data[1].targetTeam.teamName);
	});
	
	//加载直播分类信息
	homeService.getLiveCategoryList(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载热门直播数据失败");
		
		for (var i = 0; i < res.data.length; i++) {
			$(".item").eq(i).attr("title", res.data[i].categoryName)
			$(".item").eq(i).data("id", res.data[i].categoryId)
			$(".item").eq(i).css("background-image", "url(" + res.data[i].categoryBackgroundImageUrl + ")")
		}
	})
	
}) 