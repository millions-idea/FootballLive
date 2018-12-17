window.nim = {};

window.app = {
	config: {
		title: "卫星体育",
		brand: "卫星体育",
		apiUrl: "http://stack-app.natapp1.cc/",
		version: "1.0.1"
	},
	
	pages: [
		{
			id: "home",
			url: "home.html",
			style: {
				top: "0px",
				bottom: "50px"
			}
		},
		{
			id: "lives",
			url: "lives.html",
			style: {
				top: "0px",
				bottom: "50px"
			}
		},
		{
			id: "informations",
			url: "informations.html",
			style: {
				top: "0px",
				bottom: "50px"
			}
			/*style: {
				top: "0px",
				bottom: "50px",
				titleNView: {                       // 窗口的标题栏控件
			      titleText:"情报中心",                // 标题栏文字,当不设置此属性时，默认加载当前页面的标题，并自动更新页面的标题
			      titleColor:"#fff",             // 字体颜色,颜色值格式为"#RRGGBB",默认值为"#000000"
			      titleSize:"15px",                 // 字体大小,默认17px
			      backgroundColor:"#8330E0",        // 控件背景颜色,颜色值格式为"#RRGGBB",默认值为"#F7F7F7"
		      	  // type:'transparent',//透明渐变样式
			    }
			}*/
		},
		{
			id: "my",
			url: "my.html",
			style: {
				top: "0px",
				bottom: "50px"
			}
		}
	],
	
	style: {
		frontBackgroundColor: "#",
		/*barBackgroundColor: "#6B1EC2",*/
		backgroundColor: "#622BAB"
	}, 
	
	logger: function(id, text){
		console.error("LOGGER => " + new Date() + " [" + id + "] ::" + text);
	},
	
	print: function(text){
		console.error("LOGGER => " + new Date() + " [" + "GLOBAL" + "] ::" + text);
	},
	
	getDeviceWidth: function(){
		console.log(document.documentElement.clientWidth);
	},
	
	utils: {
		getBase64: function(url, callback){
			$.get(app.config.apiUrl + "api/common/getBaseDecode", {
				url: url
			}, function(data){
				app.logger("main", JSON.stringify(data));
				callback(data);
			});
		},
		
		
		ajax: {
			isError: function(data){
				if(data == null) return true;
				if(data.code == 200) return false;
				return true;
			}
		},
		
		msgBox: {
			msg: function(content){
	    		plus.nativeUI.toast(content);
			}
		},
		
		openWindow: function(url,id){
			mui.openWindow({
				url: url,
				id: id,
				createNew: true,
				show:{
			      autoShow:true,
			      aniShow: "slide-in-right",
			      duration: 100
				},
			    waiting:{
			      autoShow:false,//自动显示等待框，默认为true
			      title:'正在加载...',//等待对话框上显示的提示内容
			    }
			});
		},
		
		openNewWindow: function(url,id){
			mui.openWindow({
				url: url,
				id: id,
				createNew: false,
				show:{
			      autoShow:true,
			      aniShow: "slide-in-right",
			      duration: 100
				},
			    waiting:{
			      autoShow:false,//自动显示等待框，默认为true
			      title:'正在加载...',//等待对话框上显示的提示内容
			    }
			});
		},
		
		openNewWindowParam: function(url,id,extras){
			mui.openWindow({
				url: url,
				id: id,
				createNew: false,
				show:{
			      autoShow:true,
			      aniShow: "slide-in-right",
			      duration: 100
				},
			    waiting:{
			      autoShow:false,//自动显示等待框，默认为true
			      title:'正在加载...',//等待对话框上显示的提示内容
			    },
			    extras: extras
			});
		},
		
		
		/**
		 * 获取缓存令牌
		 */
		getToken: function(){
			var data = plus.storage.getItem('userInfo');
			if(data == null) return null;
			return JSON.parse(data).token;
		},
		
		toUrl:function(url){
			return url + "?token=" + app.utils.getToken();
		},
		
		toParam: function(obj){
			obj.token = app.utils.getToken();
			return obj;
		},
		
		getCurrentDate: function(){
			var date = new Date();
		    var y = date.getFullYear();    
		    var m = date.getMonth() + 1;    
		    m = m < 10 ? ('0' + m) : m;    
		    var d = date.getDate();    
		    d = d < 10 ? ('0' + d) : d;    
		    var h = date.getHours();  
		    h = h < 10 ? ('0' + h) : h;  
		    var minute = date.getMinutes();  
		    var second = date.getSeconds();  
		    minute = minute < 10 ? ('0' + minute) : minute;    
		    second = second < 10 ? ('0' + second) : second;   
		    return {
		    	year: y,
		    	month: m,
		    	day: d
		    }
		},
		
		/**
		 * 将毫秒级时间戳转换为标准时间显示
		 * @param {Object} value
		 */
		timestampToDate: function(value){
			var date = new Date();
		    date.setTime(value);
		    var y = date.getFullYear();    
		    var m = date.getMonth() + 1;    
		    m = m < 10 ? ('0' + m) : m;    
		    var d = date.getDate();    
		    d = d < 10 ? ('0' + d) : d;    
		    var h = date.getHours();  
		    h = h < 10 ? ('0' + h) : h;  
		    var minute = date.getMinutes();  
		    var second = date.getSeconds();  
		    minute = minute < 10 ? ('0' + minute) : minute;    
		    second = second < 10 ? ('0' + second) : second;   
		    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second; 
		},
		
		
		/**
		 * 获取分钟
		 * @param {Object} value
		 */
		getFormatMinute: function(value){
			//2018-12-18 13:13:00
			var arr = value.split(" ");
			var nArr = arr[1].split(":");
			var time = nArr[0] + ":" + nArr[1];
			return time;
		},
		
		/**
		 * 获取年月日
		 * @param {Object} value
		 */
		getFormatYear: function(value){
			//2018-12-18 13:13:00
			var arr = value.split(" ");
			return arr[0];
		},
		
		/**
		 * 将毫秒级时间戳转换为分钟显示
		 * @param {Object} value
		 */
		timestampToMinute: function(value){
			var date = new Date();
		    date.setTime(value);
		    var y = date.getFullYear();    
		    var m = date.getMonth() + 1;    
		    m = m < 10 ? ('0' + m) : m;    
		    var d = date.getDate();    
		    d = d < 10 ? ('0' + d) : d;    
		    var h = date.getHours();  
		    h = h < 10 ? ('0' + h) : h;  
		    var minute = date.getMinutes();  
		    var second = date.getSeconds();  
		    minute = minute < 10 ? ('0' + minute) : minute;    
		    second = second < 10 ? ('0' + second) : second;   
		    return h + ':' + minute; 
		},
		
		getWeek: function(dateStr){
			//2008-08-08
			var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	        var myDate = new Date(Date.parse(dateStr.replace(/-/g, "/")));
	        return weekDay[myDate.getDay()];
		},
		
		
		/**
		 * 渲染HTML模板代码 DF 2018年12月14日15:32:54
		 * @param {Object} list
		 */
		raw: function(list){
			return template("runnerImageTemplate", {
				list: list
			})
		}
	}
}

String.prototype.replaceX=function(f,e){//吧f替换成e
    var reg=new RegExp(f,"g"); //创建正则RegExp对象   
    return this.replace(reg,e); 
}


String.prototype.uuid=function(){
	var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
 
    var uuid = s.join("");
    return uuid;
}
