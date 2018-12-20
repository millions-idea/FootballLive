mui.init();


var service = {
	getContact: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/getContact"), function(data){
			app.logger("contact", JSON.stringify(data));
			callback(data);
		});
	}
}


mui.plusReady(function(){
	
	service.getContact({}, function(res){
		if(app.utils.ajax.isError(res)) return app.utils.msgBox.msg("加载失败");
		$("#content").text(res.msg);
	})
	

})
