var mdate;

mui.init();

mui.plusReady(function(){
	
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
		beginMonth: currentDate.month,
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
	    	$(".date span").text(dateFormat += " " + app.utils.getWeek(dateFormat));
	    }
	}); 
	
	//显示所有分类
	$(".showCategory").click(function(){
		mui("#sheet").popover("toggle");
	});
})
 