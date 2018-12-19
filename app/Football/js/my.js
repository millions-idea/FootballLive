mui.init();

mui.plusReady(function(){ 
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	
	 
	webview.addEventListener("show", function(){
		var webview = plus.webview.getWebviewById("index");
		webview.evalJS("updateMyInfoUI()");
	})
	 
	//更改昵称
	$(".nickname").click(function(){
		app.utils.openNewWindowParam("setData.html","setData-nickname", {
				map: {
					title: "修改昵称",
					default: myInfo.nick,
					placeholder: "请输入您新的昵称",
					webview: "my",
					method: "onUpdateNickname"
				}
			})
	});
	
	//更改签名
	$(".signature").click(function(){
		app.utils.openNewWindowParam("setData.html","setData-signature", {
				map: {
					title: "修改个性签名",
					default: myInfo.sign,
					placeholder: "请输入您新的签名",
					webview: "my",
					method: "onUpdateSignature"
				}
			})
	});
	
	//系统设置
	$(".setting").click(function(){
		app.utils.openNewWindow("systemSetting.html","systemSretting");
	});
	
	//推送消息
	$(".pushMessage").click(function(){
		app.utils.openNewWindow("systemMessage.html","systemMessage");
	});
	
	
	// 清除缓存
	$("#cleanCache").click(function(){
		logout();
	});
	
	// 注销登录
	$("#logout").click(function(){
		logout();
	});
	
	//从相册里选择图片
	$("#link_myface").click(function(){
		plus.gallery.pick(function(path){
			 //read image begin
			 plus.io.resolveLocalFileSystemURL(path, function(entry) {  
                    plus.io.resolveLocalFileSystemURL("_doc/", function(root) {  
                        root.getFile("youxun_choose_face.png", {}, function(file) {  
                            //文件存在
                            app.logger("ChatChoosePhoto", "文件存在");		
                            file.remove(function() {  
                                entry.copyTo(root, 'youxun_choose_face.png', function(e) {  
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
                            entry.copyTo(root, 'youxun_choose_face.png', function(e) {  
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
 * 更新用户名片 DF 2018年12月10日21:51:25
 * @param {Object} photo
 * @param {Object} userCode
 * @param {Object} nickname
 * @param {Object} signature
 */
function updateUserInfo(photo, userCode, nickname, signature){
	var webview = plus.webview.getWebviewById("index");
	mui.fire(webview, "updateUserInfo", {
		userCode: userCOde,
		photo: photo,
		nickname: nickname,
		signature: signature
	});
}


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

	$.post(app.config.apiUrl + "api/user/uploadAvatar", { imageData: data }, function(res){
		app.logger("my", JSON.stringify(res));
		
		if(app.utils.ajax.isError(res)) {
			app.utils.msgBox.msg("上传失败");
			return;
		}
		
		console.log("uploadAvatar:" + res.msg);
		var webview = plus.webview.getWebviewById("index");
		console.log(webview);
		mui.fire(webview, "uploadAvatar", {
			avatar: res.msg
		});
	}); 
}


function logout(){
	plus.storage.clear();
	var webview = plus.webview.getWebviewById("index");
	webview.evalJS("destroyNim()");
	app.utils.openWindow("login.html", "login");
}
