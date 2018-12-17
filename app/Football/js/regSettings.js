mui.init(); 

mui.plusReady(function() {
	app.getDeviceWidth();
	
	//获取webview视图
	var webview = plus.webview.currentWebview();
	
	//初始化页面样式
	initStyle(webview);
	
	//注册选择头像事件
	$("ul[name='photo'] li").click(function(index){
		var $img = $(this).find("img");
		var selectPhotoSrc = $img.attr("src");
		$("#defaultPhoto").attr("src", selectPhotoSrc);
	})
	
	//上传自定义头像事件
	$("#defaultPhoto").click(function(){
		//mui("#sheet-photo").popover("toggle");
		
		
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
										$(".mui-backdrop .mui-active").css("opacity","0"); 
										$(".mui-backdrop .mui-active").css("display","none"); 
										$(".mui-backdrop .mui-active").remove();
										
                                        // code...
                                        var bitmap = new plus.nativeObj.Bitmap();
								        // 从本地加载Bitmap图片
								        bitmap.load(path, function() {
								            console.log('加载图片成功');
								            var data = null;
											var img = new Image();
											img.onload = function(){
												var cx = cimg.getContext('2d');
												cimg.width = img.width;
												cimg.height = img.height;
												cx.drawImage( img, 0, 0 );
												data = cimg.toDataURL();
												
												data = data.substr(data.indexOf("base64,"), data.length).replace("base64,","");
											
												$.post(app.utils.toUrl(app.config.apiUrl + "api/user/uploadAvatar"), { imageData: data }, function(res){
													app.logger("regSettings", JSON.stringify(res));
													
													if(app.utils.ajax.isError(res)) {
														app.utils.msgBox.msg("上传失败");
														return;
													}
													
													console.log("uploadAvatar:" + res.msg);
													$("#defaultPhoto").src(res.msg);
												});
											}
											img.src = path; 
								        }, function(e) {
								            console.log('加载图片失败：' + JSON.stringify(e));
								        });
								        
                                    },  
                                    function(e) {  
                            			app.logger("ChatChoosePhoto", 'Copy image fail:' + e.message);
										$(".mui-backdrop .mui-active").css("opacity","0");  
										$(".mui-backdrop .mui-active").css("display","none"); 
										$(".mui-backdrop .mui-active").remove();
										
										
                                    });  
                            });
                            
                        }, function() {  
                            //文件不存在  
                            app.logger("ChatChoosePhoto", "文件不存在");
                            entry.copyTo(root, 'youxun_choose_face.png', function(e) {  
                                    var path = e.fullPath + "?version=" + new Date().getTime(); 
                        			app.logger("ChatChoosePhoto", path);
									$(".mui-backdrop .mui-active").css("opacity","0"); 
									$(".mui-backdrop .mui-active").css("display","none"); 
									$(".mui-backdrop .mui-active").remove();
									
                        			
                                    // code...
                                    var bitmap = new plus.nativeObj.Bitmap();
								        // 从本地加载Bitmap图片
								        bitmap.load(path, function() {
								            console.log('加载图片成功');
								            var data = null;
											var img = new Image();
											img.onload = function(){
												var cx = cimg.getContext('2d');
												cimg.width = img.width;
												cimg.height = img.height;
												cx.drawImage( img, 0, 0 );
												data = cimg.toDataURL();
											
												data = data.substr(data.indexOf("base64,"), data.length).replace("base64,","");
											
												$.post(app.utils.toUrl(app.config.apiUrl + "api/user/uploadAvatar"), { imageData: data }, function(res){
													app.logger("regSettings", JSON.stringify(res));
													
													if(app.utils.ajax.isError(res)) {
														app.utils.msgBox.msg("上传失败");
														return;
													}
													
													console.log("uploadAvatar:" + res.msg);
													$("#defaultPhoto").src(res.msg);
												});
											
											}
											img.src = path; 
								        }, function(e) {
								            console.log('加载图片失败：' + JSON.stringify(e));
								        });
                                },  
                                function(e) {  
									app.logger("ChatChoosePhoto", 'Copy image fail:' + e.message);
									$(".mui-backdrop .mui-active").css("opacity","0"); 
									$(".mui-backdrop .mui-active").css("display","none"); 
									$(".mui-backdrop .mui-active").remove();
									
                                });  
                        });  
                    }, function(e) {  
                        app.logger("ChatChoosePhoto", "读取 _www 文件夹失败");
						$(".mui-backdrop .mui-active").css("opacity","0"); 
						$(".mui-backdrop .mui-active").css("display","none"); 
						$(".mui-backdrop .mui-active").remove();
							
                    })  
                }, function(e) {  
                    app.logger("ChatChoosePhoto", "读取拍照文件错误：" + e.message)
					$(".mui-backdrop .mui-active").css("opacity","0"); 
					$(".mui-backdrop .mui-active").css("display","none"); 
					$(".mui-backdrop .mui-active").remove();
					
                });
			 //read image end
	    }, function (e) {
			$(".mui-backdrop .mui-active").css("opacity","0"); 
			$(".mui-backdrop .mui-active").css("display","none");
			$(".mui-backdrop .mui-active").remove();
			
	    }, {filter:"image"} );
	    

		
	})
	
	//加载照片选择器界面
	$("#choosePhoto").click(function(){
		plus.gallery.pick(function(path){
			 //read image begin
			 plus.io.resolveLocalFileSystemURL(path, function(entry) {  
                    plus.io.resolveLocalFileSystemURL("_doc/", function(root) {  
                        root.getFile("youxun_choose_photo.png", {}, function(file) {  
                            //文件存在
                            app.logger("ChoosePhoto", "文件存在");
                            file.remove(function() {  
                                entry.copyTo(root, 'youxun_choose_photo.png', function(e) {  
                                        var path = e.fullPath + "?version=" + new Date().getTime();  
                            			app.logger("ChoosePhoto", path);
                                        $("#defaultPhoto").attr("src", path);
                                    },  
                                    function(e) {  
                            			app.logger("ChoosePhoto", 'Copy image fail:' + e.message);
                                    });  
                            });
                            
                        }, function() {  
                            //文件不存在  
                            app.logger("ChoosePhoto", "文件不存在");
                            entry.copyTo(root, 'youxun_choose_photo.png', function(e) {  
                                    var path = e.fullPath + "?version=" + new Date().getTime(); 
                        			app.logger("ChoosePhoto", path);
                                    $("#defaultPhoto").attr("src", path);
                                },  
                                function(e) {  
									app.logger("ChoosePhoto", 'Copy image fail:' + e.message);
                                });  
                        });  
                    }, function(e) {  
                        app.logger("ChoosePhoto", "读取 _www 文件夹失败") 
                    })  
                }, function(e) {  
                    app.logger("ChoosePhoto", "读取拍照文件错误：" + e.message)
                });
			 //read image end
	    }, function (e) {
	    	plus.nativeUI.toast("您已取消上传头像");
	    }, {filter:"image"} );
		
		mui("#sheet-photo").popover("toggle");
	})
	
	//跳过基本设置
	$("#breakStep").click(function(){
		mui.openWindow("index.html", "index.html");
	});
	
	//恢复下一步按钮可点击状态
	$("input[name='userCode']").click(function(){
		$("#nextStep").removeClass("btn-gray").addClass("btn-blue").attr("disabled", false);
	})

	//下一步
	$("#nextStep").click(function(){
		//数据验证
		var $userCode = $("input[name='userCode']"),
			$nickName = $("input[name='nickname']"),
			$signature = $("input[name='signature']"),
			$photo = $("#defaultPhoto");
		if($userCode.val() == null || $userCode.val().length < 5 || $userCode.val().length > 15){
			return $userCode.parent().addClass("error");
		}
		if($nickName.val() == null || $nickName.val().length < 1){
			return $nickName.parent().addClass("error");
		}
		
		//访问服务端
		$.ajax({
			type: "POST",
			url : app.utils.toUrl(app.config.apiUrl + "api/user/initSettings"),
			data: {
				photo: $photo.val(),
				userCode: $userCode.val(),
				nickName: $nickName.val(),
				signature: $signature.val()
			},
			dataType: "JSON",
			success: function(data){
				app.logger("RegSettings", JSON.stringify(data));
			
				if(app.utils.ajax.isError(data)){
					if(data.code == 300 || data.code == 400){
						return app.utils.msgBox.msg(data.msg);
					}
					return app.utils.msgBox.msg("设置失败");
				}
				
				mui.openWindow("index.html", "index.html");
			}
		});
		
		
	});
});


function initStyle(webview){
	webview.setStyle({
		top: "0px",
		bottom: "0px"
	})
}
