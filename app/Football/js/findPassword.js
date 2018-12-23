var utils = app.utils;

mui.init();

mui.plusReady(function() {
	// 自定义webview样式
	var webview = plus.webview.currentWebview();
	webview.setStyle({
		titleNView: {
			autoBackButton: true
		}
	})
	
	// 发送验证码倒计时器
	var internalTimer = null,
		timeCount = 60;
	document.getElementById("sendSmsCode").addEventListener("tap", function(){
		document.getElementById("sendSmsCode").disabled = true;
		//发送验证码begin
		$.get(app.config.apiUrl + "api/user/sendResetPwdSmsCode", {
			phone: $("input[name='phone']").val()
		}, function(data){
			app.logger("ResetSmsCode", JSON.stringify(data));
			
			if(utils.ajax.isError(data)) {
				timeCount = 60;
				document.getElementById("sendSmsCode").disabled = false;
				document.getElementById("sendSmsCode").innerHTML = "重新发送验证码";
				return utils.msgBox.msg("发送验证码失败");
			}
			
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
	
	//重置密码
	$("#resetPassword").click(function(){
		var $phone = $("input[name='phone']"),
			$password = $("input[name='password']"),
			$smsCode = $("input[name='smsCode']");
			
		if($phone == null || $phone.val().length != 11) return utils.msgBox.msg("请输入正确的手机号(11位)");
		if($password == null || $password.val().length <= 6) return utils.msgBox.msg("请输入密码(最短6位)");
		if($smsCode == null || $smsCode.val().length != 6) return utils.msgBox.msg("请输入正确的验证码");
		
		
		$.post(app.config.apiUrl + "api/user/resetPassword", {
			phone: $phone.val(),
			password: $password.val(),
			smsCode: $smsCode.val()
		}, function(data){
			app.logger("FindPassword", JSON.stringify(data));
			
			if(utils.ajax.isError(data)) {
				return utils.msgBox.msg("重置失败，可联系客服处理！");
			}
			
			utils.msgBox.msg("重置密码成功，请妥善保管您的新密码！");
			
			mui.openWindow("login.html", "login");
		});
		
	});
}); 