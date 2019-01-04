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
	 * 获取版本号  DF 2018年12月20日06:24:57
	 * @param {Object} callback
	 */
	getVersion: function(callback){
		$.get(app.config.apiUrl + "api/home/getVersion", function(data){
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
				if(list[i].key.indexOf("targetUrl") == -1){
					tempList.push(list[i]);
				}

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
	},
	
	
	getHotInformation: function(callback){
		$.get(app.config.apiUrl + "api/home/getHotInformation", function(data){
			app.logger("home", JSON.stringify(data));
			callback(data);
		});
	}
}

mui.plusReady(function(){
    //设置状态栏样式
	plus.navigator.setStatusBarStyle("dark");
	plus.navigator.setStatusBarBackground("#F3F3F3");
	
    plus.webview.currentWebview().addEventListener("show", function(){
		
		plus.navigator.setStatusBarStyle("dark");
		plus.navigator.setStatusBarBackground("#F3F3F3");
		
		console.log("home_show");
		
		var view = plus.webview.getWebviewById("index");
		if(view != null){
			view.evalJS("createNIM()");
		}
	    
	    // 检测更新版本
		//checkVersion(plus);
	    
	    // 加载初始化数据
    	initData();
    })
     
     
    initData();
    
    // 检测更新版本
	checkVersion(plus);
})
 
 
function initData(){
	//加载首页聚合数据
	homeService.getGroupInfo(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载聚合数据失败");
	
		//获取滚动图列表
		var runnerImageList = homeService.getRunnerImageList(res.data);
		

		var html = "";
		html += '<div class="mui-slider-item mui-slider-item-duplicate">' + 
			'<a href="javascript:void(0);"  data-url="'+ runnerImageList[runnerImageList.length-1].url +'"  class="runnerLink"><img src="'+ runnerImageList[runnerImageList.length-1].value +'" /></a></div>';
		console.log(html)
		for (var i = 0; i < runnerImageList.length; i++) {
			var item = runnerImageList[i];
			html += '<div class="mui-slider-item" data-url="' + item.url + '">';
		    html += '	<a href="javascript:void(0);" class="runnerLink">';
		    html += '		<img src="' + item.value + '" />';
		    html += '	</a>';
		    html += '</div>';
		}
		html += '<div class="mui-slider-item mui-slider-item-duplicate">' + 
			'<a href="javascript:void(0);"  data-url="'+ runnerImageList[0].url +'"  class="runnerLink"><img src="'+ runnerImageList[0].value +'" /></a></div>';

		//渲染滚动图列表
		$("#runnerImage").html(html);
		
		$(".runnerLink").click(function(){
			var url = $(this).data("url");
			//进入直播间
			if(url != null && url.length > 0 &&  url.toString().indexOf("http") == -1){
				var cache = plus.storage.getItem("userInfo");
				if(cache == null) {
					app.utils.openNewWindow("login.html", "login");
				}else{
					if(url != null){
						app.utils.openNewWindowParam("liveDetail.html", "liveDetail-" + url, {
							liveId: url
						});
					}	
				}
			}else if(url != null && url.length > 0){
				plus.runtime.openURL(url);
			}
		})
		
		//渲染今日头条 
		var headLineMap = homeService.getTodayHeadLine(res.data);
		if(headLineMap == null) headLineMap = { value : "加载今日头条失败"};
		$("#topTextAd").text(headLineMap.value);
		$(".mui-slider-indicator").show();
        mui('.mui-slider').slider({interval: 3000});
		
	});
	
	//加载热门直播数据
	homeService.getHotGameList(function(res){
		app.logger("getHotGameList", JSON.stringify(res))
		if(app.utils.ajax.isError(res)) {
			
			$(".left .title").text("...");
			$(".left .teamIcon").attr("src", "#");
			$(".left .teamName").text("...");
			$(".left .targetTeamIcon").attr("src", "#");
			$(".left .targetTeamName").text("...");
			$(".left").data("id", null);
			
			$(".right .title").text("...");
			$(".right .teamIcon").attr("src", "#");
			$(".right .teamName").text("...");
			$(".right .targetTeamIcon").attr("src", "#");
			$(".right .targetTeamName").text("...");
			$(".right").data("id", null);
			
			return;
		}
		
		//参赛球队信息设置
		if(res.data[0] != null){
			$(".left .title").text(res.data[0].gameName + " " + app.utils.getFormatMinute(res.data[0].liveDate));
			$(".left .teamIcon").attr("src", res.data[0].masterTeamIcon);
			$(".left .teamName").text(res.data[0].masterTeamName);
			$(".left .targetTeamIcon").attr("src", res.data[0].targetTeamIcon);
			$(".left .targetTeamName").text(res.data[0].targetTeamName);
			$(".left").data("id", res.data[0].liveId);
		}else{
			$(".left .title").text("...");
			$(".left .teamIcon").attr("src", "#");
			$(".left .teamName").text("...");
			$(".left .targetTeamIcon").attr("src", "#");
			$(".left .targetTeamName").text("...");
			$(".left").data("id", null);
		}
		
		//对战球队信息设置
		if(res.data[1] != null){
			$(".right .title").text(res.data[1].gameName + " " + app.utils.getFormatMinute(res.data[1].liveDate));
			$(".right .teamIcon").attr("src", res.data[1].masterTeamIcon);
			$(".right .teamName").text(res.data[1].masterTeamName);
			$(".right .targetTeamIcon").attr("src", res.data[1].targetTeamIcon);
			$(".right .targetTeamName").text(res.data[1].targetTeamName);
			$(".right").data("id", res.data[1].liveId);
		}else{
			$(".right .title").text("...");
			$(".right .teamIcon").attr("src", "#");
			$(".right .teamName").text("...");
			$(".right .targetTeamIcon").attr("src", "#");
			$(".right .targetTeamName").text("...");
			$(".right").data("id", null);

		}
		
		
		$(".headline .left, .headline .right").unbind("click").bind("click",function(){
			var id = $(this).data("id");
			if(id != null){
				app.utils.openNewWindowParam("liveDetail.html", "liveDetail-" + id, {
					liveId: id
				})
			}	
		});
	});
	
	//加载热门情报数据
	homeService.getHotInformation(function(res){
		app.logger("getHotInformation", JSON.stringify(res))
		if(app.utils.ajax.isError(res)) return;
		
		if(res.data == null || res.data.length == 0) return;

		var html = "";
		console.log("热门情报数量" + res.data.length)
		for (var i = 0; i < res.data.length; i++) {
			var item = res.data[i];
			
			var scheduleResult = item.scheduleGrade;
			if(item.scheduleResult != null && item.scheduleResult.length > 0){
				scheduleResult = item.scheduleResult;
			}else{
				scheduleResult = "-"
			}
			
			var param = {
				liveId: item.liveId,
				liveTitle:item.gameName,
				liveDate: app.utils.timestampToDate(item.liveDate),
				result: scheduleResult,
				masterTeamIcon: item.masterTeamIcon,
				masterTeamName: item.masterTeamName,
				targetTeamIcon: item.targetTeamIcon,
				targetTeamName: item.targetTeamName
			};
			
		console.log("热门情报胜利球队" + res.winTeamId)
			
			if(item.winTeamId == null){
				console.log("热门情报default");
				html += getHotInformation(param);
				html += '<div class="padding-top-10"></div>';
			}else{
			
				if(item.winTeamId == item.masterTeamId){
					console.log("热门情报left")
					html += getLeftHotInformation(param);
					html += '<div class="padding-top-10"></div>';
				}
				
				if(item.winTeamId == item.targetTeamId){
					console.log("热门情报right");
					html += getRightHotInformation(param);
					html += '<div class="padding-top-10"></div>';
				}	
			}
			
		} 

		$("#hotInformation").html(html);
		
		$("#hotInformation .full-headline").unbind("click").bind("click",function(){
			console.log("进入直播间")
			var id = $(this).data("id");
			console.log(id)
			
			if(id != null){
				app.utils.openNewWindowParam("liveDetail.html", "liveDetail-" + id, {
					liveId: id
				})
			}	
		});
		
		
	});
	
	//加载直播分类信息
	homeService.getLiveCategoryList(function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载热门直播数据失败");
		
		var html = "";
		html += '<div class="left">';
		for (var i = 0; i < res.data.length; i++) {
			var item = res.data[i];
			if(item.isLeft == 1 && item.isMain == 1) {
				html += '	<div  data-id="' + res.data[i].categoryId + '"  data-index="'+i+'" title="' + res.data[i].categoryName + '" id="' + res.data[i].categoryId + '" tabindex="' + i + '" class="item first" style="background-image: url('+ res.data[i].categoryBackgroundImageUrl   +')"></div>';
			}else if (item.isLeft == 1){
				html += '	<div  data-id="' + res.data[i].categoryId + '"  data-index="'+i+'" title="' + res.data[i].categoryName + '" id="' + res.data[i].categoryId + '" tabindex="' + i + '" class="item" style="background-image: url('+ res.data[i].categoryBackgroundImageUrl   +')"></div>';
			}else{
				continue;
			}
		}
		html += '</div>';
		
		html += '<div class="right">';
		for (var i = 0; i < res.data.length; i++) {
			var item = res.data[i];
			if (item.isLeft == 0){
				html += '	<div data-id="' + res.data[i].categoryId + '" data-index="'+i+'" title="' + res.data[i].categoryName + '" id="' + res.data[i].categoryId + '" tabindex="' + i + '" class="item" style="background-image: url('+ res.data[i].categoryBackgroundImageUrl   +')"></div>';
			}else{
				continue;
			}
		}
		html += '</div>';
		html += '<div class="form-placeholder-10"></div>';
		
		
		$(".category").html(html);
		$(".category .item").unbind("click").bind("click", function(){
			console.log("跳转分类");
			var that = $(this);
			var view = plus.webview.getWebviewById(app.pages[1].id);
			mui.fire(view, "refreshIndex", {
				currentIndex: $(that).attr("tabindex"),
				liveCategoryId: $(that).data("id")
			});
			plus.webview.show(app.pages[1].id);
		});
	})
		
}



function checkVersion(plugin){
	var btn = ["确定升级", "取消"];
    plugin.runtime.getProperty(plugin.runtime.appid, function (inf) {
        ver = inf.version;
        try{
        	homeService.getVersion(function(res){
        		app.logger("getVersion", JSON.stringify(res))
        		var data = res.msg;
				ver = ver.trim();
				var ua = navigator.userAgent.toLowerCase(); 
				var newVersion = parseInt(data.version.trim().toString().replace(".","").replace(".",""));
				var oldVersion = parseInt(ver.trim().toString().replace(".","").replace(".",""))
         		if (newVersion > oldVersion) {
					if (/iphone|ipad|ipod/.test(ua)) return plus.runtime.openURL(data.iosDownload);
         			console.log("update版本号" + data.update);
         			if(data.update == 0){
         				var _msg = "发现新版本:V" + data.version;
		                mui.confirm(_msg, '升级确认', btn,
		                function(e) {
		                    if (e.index == 0) { //执行升级操作
								createDownload(plugin, data.androidDownload);
		                    }
		                });
         			}else{
						if (/iphone|ipad|ipod/.test(ua)) return plus.runtime.openURL(data.iosDownload);
         				console.log("开始自动下载……");
                    	plugin.nativeUI.toast('正在为您自动下载最新版本……');
         				createDownload(plugin, data.androidDownload);
         			}
	                
	            } else {
                    //plugin.nativeUI.toast('当前版本为最新版本');
                    mui("body").progressbar().hide();
	                return;
	            }
	         })
        }catch(e){
        	//TODO handle the exception
        	console.log("自动下载抛出异常: " + JSON.stringify(e))
        }
         
    });
}

function createDownload(plugin, url){
	// 初始化下载进度
	mui("body").progressbar({progress:0}).show();
    plugin.nativeUI.toast("正在准备环境，请稍后！"); 
    var dtask = plugin.downloader.createDownload(url, {}, function(d, status) {
        console.log("下载响应码:" + status)
        if (status == 200) {
            var path = d.filename; //下载apk
            plugin.runtime.install(path); // 自动安装apk文件
			mui("body").progressbar().hide();
        } else {
            plugin.nativeUI.msg('版本更新失败:' + status);
			mui("body").progressbar().hide();
        }
    });
    dtask.addEventListener( "statechanged", onStateChanged, false );
    dtask.start();
}

// 监听下载任务状态 
function onStateChanged(download, status ) {
	switch(download.state){
		case 0:
			console.log("下载任务开始调度");
			break;
		case 1:
			console.log("下载任务开始请求");
			break;
		case 2:
			console.log("下载任务请求已经接收");                   
			break;
		case 3:
			console.log("下载任务接收数据");
			 var percent = download.downloadedSize / download.totalSize * 100;
                mui("body").progressbar().setProgress(parseInt(percent));    
			break;
		case 4:
			console.log("下载任务已完成");
			break;
		case 5:
			console.log("下载任务已暂停");
			break;
	}
	if ( download.state == 4 && status == 200 ) {
		// 下载完成 
		alert( "Download success: " + download.getFileName() );  
	}  

}


window.addEventListener("asyncInfo", function(){
	console.log("home父窗口接到回调")
	var view = plus.webview.currentWebview();
	view.show();
})


function getRightHotInformation(obj){
	var html = '<div class="full-headline" data-id="'+ obj.liveId +'" style="height: 110px;"><div class="center">';
	html += '<div class="title title-color-red">推荐情报：'+ obj.liveTitle +' '+ obj.liveDate +'</div>';
	html += '<div class="content">';
	html += '	<div class="team team-left" >';
	html += '		';
	html += '		<img class="teamIcon" src="'+ obj.masterTeamIcon +'" alt="" />';
	html += '		<span class="teamName" style="color:#c0c0c0">'+ obj.masterTeamName +'</span>';
	html += '	</div>';
	html += '	<div class="info" style="text-align:center">';
	html += '		<span>'+ obj.result +'</span>';
	html += '	</div>';
	html += '	<div class="team team-right hot" >';
	//html += '		<i class="icon iconfont icon-shoucangjiaobiao" style="position: relative;right: 1px;"></i>';
	html += '		<img class="targetTeamIcon" src="'+ obj.targetTeamIcon +'" alt="" />';
	html += '		<span class="targetTeamName" style="color:#6a6a6a">'+ obj.targetTeamName +'</span>';
	html += '	</div>';
	html += '</div>';
	html += '</div>';	
	html += '</div>';	
	return html;
}


function getLeftHotInformation(obj){
	var html = '<div class="full-headline" data-id="'+ obj.liveId +'" style="height: 110px;"><div class="center">';
	html += '<div class="title title-color-red">推荐情报：'+ obj.liveTitle +' '+ obj.liveDate +'</div>';
	html += '<div class="content">';
	html += '	<div class="team team-left hot">';
	html += '		<img class="teamIcon" src="'+ obj.masterTeamIcon +'" alt="" />';
	html += '		<span class="teamName"  style="color:#6a6a6a">'+ obj.masterTeamName +'</span>';
	html += '	</div>';
	html += '	<div class="info">';
	html += '		<span>'+ obj.result +'</span>';
	html += '	</div>';
	html += '	<div class="team team-right"> ';
	html += '		<img class="targetTeamIcon" src="'+ obj.targetTeamIcon +'" alt="" />';
	html += '		<span class="targetTeamName" style="color:#c0c0c0">'+ obj.targetTeamName +'</span>';
	html += '	</div>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	return html;
}





function getHotInformation(obj){
	var html = '<div class="full-headline" data-id="'+ obj.liveId +'" style="height: 110px;"><div class="center">';
	html += '<div class="title title-color-red">推荐情报：'+ obj.liveTitle +' '+ obj.liveDate +'</div>';
	html += '<div class="content">';
	html += '	<div class="team team-left">';
	html += '		<img class="teamIcon" src="'+ obj.masterTeamIcon +'" alt="" />';
	html += '		<span class="teamName"  style="color:#c0c0c0">'+ obj.masterTeamName +'</span>';
	html += '	</div>';
	html += '	<div class="info">';
	html += '		<span>'+ obj.result +'</span>';
	html += '	</div>';
	html += '	<div class="team team-right"> ';
	html += '		<img class="targetTeamIcon" src="'+ obj.targetTeamIcon +'" alt="" />';
	html += '		<span class="targetTeamName" style="color:#c0c0c0">'+ obj.targetTeamName +'</span>';
	html += '	</div>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	return html;
}
