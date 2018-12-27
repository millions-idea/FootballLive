mui.init();

var profileService = {
	getProfile: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/getProfile"), function(data){
			app.logger("profile", JSON.stringify(data));
			callback(data);
		});
	}
}

mui.plusReady(function(){
	//显示现在的信息
	profileService.getProfile({}, function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("拉取个人信息超时");
		var info = res.msg;
		$("#face").attr("src", info.photo);
		$(".nickname span").after(info.nickName);
		
		
		//更改昵称
		$(".nickname").click(function(){
			app.utils.openNewWindowParam("setData.html","setData-nickname", {
					map: {
						title: "修改昵称",
						default: info.nickName,
						placeholder: "请输入您新的昵称",
						webview: "profile",
						method: "onUpdateNickname"
					}
				})
		});

		
	});


	//从相册里选择图片
	$("#link_myface").click(function(){
		plus.gallery.pick(function(path){
			 //read image begin
			 plus.io.resolveLocalFileSystemURL(path, function(entry) {  
                    plus.io.resolveLocalFileSystemURL("_doc/", function(root) {  
                        root.getFile("tvchoose_face.png", {}, function(file) {  
                            //文件存在
                            app.logger("ChatChoosePhoto", "文件存在");		
                            file.remove(function() {  
                                entry.copyTo(root, 'tvchoose_face.png', function(e) {  
                                        var path = e.fullPath + "?version=" + new Date().getTime();  
                            			app.logger("ChatChoosePhoto", path);
										$(".mui-backdrop.mui-active").css("opacity","0"); 
										$(".mui-backdrop.mui-active").css("display","none"); 
										
                                        // code...
                                        var bitmap = new plus.nativeObj.Bitmap();
								        // 从本地加载Bitmap图片
								        bitmap.load(path, function() {
								            console.log('加载图片成功');
											app.utils.msgBox.msg("更新头像中……");
								            var data = null;
											var img = new Image();
											img.onload = function(){
												var cx = cimg.getContext('2d');
												cimg.width = img.width;
												cimg.height = img.height;
												cx.drawImage( img, 0, 0 );
												data = cimg.toDataURL();
												uploadPhoto(data);
											}
											img.src = path; 
								        }, function(e) {
								            console.log('加载图片失败：' + JSON.stringify(e));
								        });
								        
                                    },  
                                    function(e) {  
                            			app.logger("ChatChoosePhoto", 'Copy image fail:' + e.message);
										$(".mui-backdrop.mui-active").css("opacity","0");  
										$(".mui-backdrop.mui-active").css("display","none"); 
										
                                    });  
                            });
                            
                        }, function() {  
                            //文件不存在  
                            app.logger("ChatChoosePhoto", "文件不存在");
                            entry.copyTo(root, 'tvchoose_face.png', function(e) {  
                                    var path = e.fullPath + "?version=" + new Date().getTime(); 
                        			app.logger("ChatChoosePhoto", path);
									$(".mui-backdrop.mui-active").css("opacity","0"); 
									$(".mui-backdrop.mui-active").css("display","none"); 
									
                        			
                                    // code...
                                    var bitmap = new plus.nativeObj.Bitmap();
								        // 从本地加载Bitmap图片
								        bitmap.load(path, function() {
								            console.log('加载图片成功');
											app.utils.msgBox.msg("更新头像中……");
								            var data = null;
											var img = new Image();
											img.onload = function(){
												var cx = cimg.getContext('2d');
												cimg.width = img.width;
												cimg.height = img.height;
												cx.drawImage( img, 0, 0 );
												data = cimg.toDataURL();  
												uploadPhoto(data);
											}
											img.src = path; 
								        }, function(e) {
								            console.log('加载图片失败：' + JSON.stringify(e));
								        });
                                },  
                                function(e) {  
									app.logger("ChatChoosePhoto", 'Copy image fail:' + e.message);
									$(".mui-backdrop.mui-active").css("opacity","0"); 
									$(".mui-backdrop.mui-active").css("display","none"); 
									
                                });  
                        });  
                    }, function(e) {  
                        app.logger("ChatChoosePhoto", "读取 _www 文件夹失败");
						$(".mui-backdrop.mui-active").css("opacity","0"); 
						$(".mui-backdrop.mui-active").css("display","none"); 
							
                    })  
                }, function(e) {  
                    app.logger("ChatChoosePhoto", "读取拍照文件错误：" + e.message)
					$(".mui-backdrop.mui-active").css("opacity","0"); 
					$(".mui-backdrop.mui-active").css("display","none"); 
                });
			 //read image end
	    }, function (e) {
			$(".mui-backdrop.mui-active").css("opacity","0"); 
			$(".mui-backdrop.mui-active").css("display","none"); 
	    }, {filter:"image"} );
	    
		mui("#sheet-photo").popover("toggle");
	});

  
})



/**
 * 上传头像回调事件 DF 2018年12月11日02:05:15
 */
window.addEventListener("onAvatar", function(event){
	app.utils.msgBox.msg("更新成功");
	$("#face").attr("src", event.detail.avatar);
})

/**
 * 上传头像 DF 2018年12月10日22:09:16
 * @param {Object} data
 */
function uploadPhoto(data){
	if(data == null) {
		app.utils.msgBox.msg("读取头像失败");
		return;
	}
	
	data = data.substr(data.indexOf("base64,"), data.length).replace("base64,","");

	$.post(app.utils.toUrl(app.config.apiUrl + "api/user/uploadAvatar"), { imageData: data }, function(res){
		app.logger("my", JSON.stringify(res));
		
		if(app.utils.ajax.isError(res)) {
			app.utils.msgBox.msg("上传失败");
			return;
		}
		
		console.log("uploadAvatar:" + res.msg);
		$("#face").attr("src", res.msg);
		/*var webview = plus.webview.getWebviewById("index");
		console.log(webview);
		mui.fire(webview, "uploadAvatar", {
			avatar: res.msg
		});*/
	}); 
}




/**
 * 修改个人昵称 DF 2018年12月11日04:17:14
 * @param {Object} inputValue
 */
function onUpdateNickname(inputValue){
	$.post(app.utils.toUrl(app.config.apiUrl + "api/user/updateInfo"), {
		nickname: inputValue
	}, function(data){
		app.logger("onUpdateNickname", JSON.stringify(data));
		
		if(app.utils.ajax.isError(data)) {
			if(data.code == 300) {
				app.utils.msgBox.msg(data.msg);
				return;
			}
			app.utils.msgBox.msg("修改失败");
			return;
		}

		app.utils.msgBox.msg("修改成功");
		
		plus.webview.getWebviewById("setData-nickname").close();
		$(".nickname div").html("");
		$(".nickname div").html('<span>昵称</span>&nbsp;&nbsp;&nbsp;&nbsp;' + inputValue);
	})
}


