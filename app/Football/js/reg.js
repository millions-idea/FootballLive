var utils = app.utils;

mui.init();

mui.plusReady(function() {
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	webview.setStyle({
		titleNView: {
			autoBackButton: true,
			backgroundColor: app.style.backgroundColor
		}
	})
	
	// 展示注册协议 窗口
	document.getElementById("regPact").addEventListener("tap", function(){
		document.activeElement.blur();
		mui.openWindow({
			url: "regPact.html",
			id: "regPact",
			preload: false,
			waiting: {
				autoShow: true,
				title: '...'
			},
			createNew: true
		});
	})
	

	// 同意注册协议
	document.getElementById("agreeRegPact").addEventListener("tap", function(){
		var selectState = document.getElementById("regPactState").value;
		if(selectState == 0){
			document.getElementById("regPactState").value = 1;
			document.getElementById("agreeRegPact").style.backgroundImage = "url(images/check-box.png)";
			// 恢复下一步状态
			document.getElementById("nextStep").classList.remove("btn-gray");
			document.getElementById("nextStep").classList.add("btn-blue");
			document.getElementById("nextStep").disabled = false;
		}else{
			document.getElementById("regPactState").value = 0;
			document.getElementById("agreeRegPact").style.backgroundImage = "url(images/check-box_outline.png)";
			// 恢复下一步状态
			document.getElementById("nextStep").classList.remove("btn-blue");
			document.getElementById("nextStep").classList.add("btn-gray");
			document.getElementById("nextStep").disabled = true;
		}
	})
	
	// 发送验证码倒计时器
	var internalTimer = null,
		timeCount = 60;
	document.getElementById("sendSmsCode").addEventListener("tap", function(){
		document.getElementById("sendSmsCode").disabled = true;
		//发送验证码begin
		$.get(app.config.apiUrl + "api/user/sendSmsCode", {
			phone: $("input[name='phone']").val()
		}, function(data){
			app.logger("SmsCode", JSON.stringify(data));
			
			if(utils.ajax.isError(data)) return utils.msgBox.msg("发送验证码失败");
			
			//再次获取验证码倒计时器
			internalTimer = setInterval(function(){
				timeCount--;
				if(timeCount < 0) {
					timeCount = 60;
					document.getElementById("sendSmsCode").disabled = false;
					document.getElementById("sendSmsCode").innerHTML = "重新发送验证码";
					clearInterval(internalTimer);
					return;
				}
				document.getElementById("sendSmsCode").innerHTML = "已发送(" + timeCount + "s)";
			},1000)
		});
		//发送验证码end
	})
	
	//下一步
	$("#nextStep").click(function(){
		var $phone = $("input[name='phone']"),
			$password = $("input[name='password']"),
			$smsCode = $("input[name='smsCode']"),
			$select = $("#regPactState");
			
		if($phone == null || $phone.val().length != 11) return utils.msgBox.msg("请输入正确的手机号(11位)");
		if($password == null || $password.val().length <= 0) return utils.msgBox.msg("请输入密码");
		if($smsCode == null || $smsCode.val().length != 6) return utils.msgBox.msg("请输入正确的验证码");
		
		if($select.val() == "0") return utils.msgBox.msg("请先同意注册协议");
		
			
        $.ajax({
			type: "POST",
			url : app.config.apiUrl + "api/user/signUp",
			data: {
				phone: $phone.val(),
				password: $password.val(),
				smsCode: $smsCode.val()
			},
			xhrFields: {
                withCredentials: true 
            },
			dataType: "JSON",
			success: function(data){
				app.logger("Register", JSON.stringify(data));
			
				if(utils.ajax.isError(data)) {
					if(data.code == 400){
						if(data.msg == "已存在"){
							data.msg = "该手机号已在平台注册!";
						}
						return utils.msgBox.msg(data.msg);
					}
					return utils.msgBox.msg("注册失败");
				}
				
				utils.msgBox.msg("恭喜您注册成功");
				
				// 记录缓存信息
				plus.storage.setItem('userInfo', JSON.stringify(data.msg));
				
				mui.openWindow("index.html", "index");
			}
		});
		 
		
	});
}); 