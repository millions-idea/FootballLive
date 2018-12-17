/* 
 * 作者：王者地带
 http://ask.dcloud.net.cn/article/12949
 * 基于MUI，APP
 * Date:2017-12-01
 * 
 * 增加手势控制音量，亮度，前进后退
 * Date:2017-12-07
 * 
 * 增加点击播放全屏，增加IOS系统自带播放器播放视频, 支持在浏览器上播放视频
 * Date:2018-05-17
 
 * 支持同一个页面切换视频的方法，新增可以禁止拖动调节,音量和亮度，是否显示全屏按钮, ios支持强制全屏播放
 * Date:2018-06-20
 * 
 * 修复IOS对.m3u8 视频的兼容性
 * Date:2018-09-13
 */
  //html5播放器封闭包
 (function(window,document)
            {
				var Html5video=function(getDom,options)
            	 {
					 if(!(this instanceof Html5video))return new Html5video(getDom,options);
            	 	// 参数合并  
			     this.options=this.extend({ 
			     title:"", //视频标题，当全屏时会显示
					 url:"", //视频链接
					 img:"", //视频截图封面
					 time:"00:00", //视频总时间以分钟单位，当网络缓慢时，没办法及时加载出来，如已知可以填写，可不填。
					 autoplay:false, //是否自动播放视频
					 isMobile:false, //是否开启当处于移动网络时，提示用户是否继续播放视频。
					 isFull:false, //是否点击播放就全屏显示
					 iospay:false,//是否点击播放就全屏显示
					 drag:false,//禁止拖动,调节,音量和亮度
					 isfull:true,//是否显示全屏按钮
					 prompt:function(video){} //当开启isMobile时,这里可以写提示用户的内容,
			        },options);
			         // 判断传进来的是DOM还是字符串  
			        if((typeof getDom)==="string"){  
			            this.getDom =document.querySelector(getDom);  
			        }else{  
			            this.getDom=getDom;  
			        }      
            	 	 // 初始化  
                  this.init(); 
            	}	
            	 	 Html5video.prototype={
            	 	  init:function() //初始化 
            	 	  {
                          this.event();  
                      },
                      extend:function(obj,obj2)  //参数合并 
                      {
			            for(var k in obj2){  
			                obj[k] = obj2[k];  
			            }  
			            return obj;  
			          },
			          Second:function(second)  //把时间转换成分钟
                      {
			             var hour = parseInt(second / (60* 60));
            var minute = parseInt((second/60) % 60);
            var second = parseInt(second % 60);
            return (hour > 0 ?((hour < 10 ? "0" + hour:hour) + ":") : "") + (minute < 10 ? "0" + minute:minute) + ":" + (second < 10 ? "0" + second:second); 
			          },
			          isMobile:function()
			          {
			          	    if(mui.os.plus)
			          	    {
			          	var networkTypes={};
					        networkTypes[plus.networkinfo.CONNECTION_UNKNOW] = "无网"; 
					        networkTypes[plus.networkinfo.CONNECTION_NONE] = "无网";
					        networkTypes[plus.networkinfo.CONNECTION_ETHERNET]="有线";
					        networkTypes[plus.networkinfo.CONNECTION_WIFI] ="wifi";
					        networkTypes[plus.networkinfo.CONNECTION_CELL2G] = "2G蜂窝网络";
					        networkTypes[plus.networkinfo.CONNECTION_CELL3G] = "3G蜂窝网络";
					        networkTypes[plus.networkinfo.CONNECTION_CELL4G] = "4G蜂窝网络";
					        if(networkTypes[plus.networkinfo.getCurrentType()]!="wifi" && networkTypes[plus.networkinfo.getCurrentType()]!="无网")
					        {
					        	return false;
					        }
					        }
					        return true;
			          },
								getplayUrl:function(data)
								{
									var _this=this;
									var v=_this.getDom.querySelector("video");
									      _this.getDom.querySelector("#mui-block-range").style.backgroundSize="0% 100%";  
									    v.src=data.url;
											v.poster=data.img;
											_this.getDom.querySelector(".vtitle").innerHTML=data.title;
											v.play();				
								}
								,
			          event:function()
			          {
            	 	     var _this=this; 
            	         var mui_pay_ico=_this.getDom.querySelector(".mui-pay_ico");//播放与暂停按钮
            	         var mui_block_range=_this.getDom.querySelector("#mui-block-range"); //进度条   
            	         var mui_time_current=_this.getDom.querySelector(".mui-time-current");//视频播放时间
            	         var mui_time_cout=_this.getDom.querySelector(".mui-time-cout");//视频总时间
            	         var mui_quanping=_this.getDom.querySelector(".mui-quanping");//全屏按钮
            	         var mui_videoControls=_this.getDom.querySelector(".mui-videoControls");//进度条整个区域
            	         var rprogress=_this.getDom.querySelector(".rprogress");//正在加载，提示
            	         var mui_full_back=_this.getDom.querySelector(".mui-full-back");//全屏时后退图标
            	         var vtitle=_this.getDom.querySelector(".vtitle");//全屏时显示视频标题
            	         var ptime=_this.getDom.querySelector(".ptime");//音量/亮度/进度
            	         var v_left=_this.getDom.querySelector(".v_left");
            	         var b_right=_this.getDom.querySelector(".b_right");
            	         var mui_video=document.createElement("video");//创建播放控件
            	         var mui_video_txt=_this.getDom.querySelector(".mui-video-txt"); 
            	         var fullScreenFlag=false;
            	          mui_video.setAttribute("poster",_this.options.img);
	            	         if(_this.options.url=="") 
	            	         {
	            	         	return;
	            	         }
												 if(!_this.options.isfull)
												 {
													 _this.getDom.querySelector(".mui-full-btu").style.display="none";
													 mui_quanping.style.width="48px";
													 _this.getDom.querySelector(".mui-wrap-right").style.paddingRight="115px"; 
												 }
	            	         if(_this.options.iospay && mui.os.ios)
	            	         {
	            	      mui_pay_ico.style.display="none";
					            mui_videoControls.style.display="none";
					            mui_full_back.style.display="none";
					            v_left.style.display="none";
								      b_right.style.display="none";
	            	         } 
	            	         else
	            	         {
	            	         	mui_video.setAttribute("webkit-playsinline","webkit-playsinline");
	            	         	mui_video.setAttribute("playsinline","playsinline");
	            	         }
	            	         mui_video_txt.appendChild(mui_video);  
            	         var video_url=_this.options.url;
            	         var vname=video_url.substring(video_url.lastIndexOf('.'));
            	             if(vname==".m3u8") //m3u8解码
            	             {         	             	
            	                if(Hls.isSupported())
															{
																var hls = new Hls();
																hls.loadSource(video_url);
																hls.attachMedia(mui_video);
																hls.on(Hls.Events.MANIFEST_PARSED,function(){});
									             } 	
															else if (mui_video.canPlayType('application/vnd.apple.mpegurl')) 
															{
																mui_video.src =video_url;
															}
            	             }
            	             else
            	             {
            	             mui_video.src=_this.options.url;
            	             }
            	             vtitle.innerHTML=_this.options.title; 
            	             if(_this.options.time!="")
            	             {
            	             mui_time_cout.innerHTML=_this.options.time;
            	             }
            	             mui_pay_ico.addEventListener("tap",function() 
            	             { 
            	             	 if (mui_video.paused || mui_video.ended) //当暂停或结束
                                    {
                                    if(!_this.isMobile() && _this.options.isMobile && parseInt(mui_video.currentTime)<5)
					            	{   
					            		_this.options.prompt(mui_video);
					            		return
					            	}
					            	 mui_pay_ico.style.display="none";
                                     mui_video.play();
                                     if(_this.options.isFull && !_this.options.autoplay)
                                     {
                                     	if(mui.os.ios)
				                           {//苹果需要延时才能全屏
                                     	setTimeout(function()
					                      {
                                     	isfull("pay");  
                                     	 },200);
                                     	}
				                         else{
				                         isfull("pay");	
				                         }
                                     }
                                     mui_pay_ico.querySelector("use").setAttribute("xlink:href","#icon-zanting"); //显示暂停图标
                                    }
                                    else //否则暂停播放
                                    {
                                    mui_video.pause(); 
                                    mui_pay_ico.querySelector("use").setAttribute("xlink:href","#icon-bofang1"); //显示播放图标
                                   // mui_pay_ico.style.display="none";
                                    mui_pay_ico.style.display="block"; 
                                    }
                                    
                                    setTimeout(function()
					                {
					                	mui_pay_ico.style.display="none";
					                	mui_videoControls.style.display="none";
					                	mui_full_back.style.display="none";
					                	if (mui_video.paused || mui_video.ended) //当暂停或结束
                                          {
                                          	mui_pay_ico.style.display="block";
					                	  }
					                },3000);
                                    
            	             });
						          //进度条事件，当拖动进度条时，让它在当前时间开始播放
						      mui_block_range.addEventListener("change", function()
						       {
						             var val=this.value;
						             mui_block_range.style.backgroundSize=""+val+"% 100%";
						             var time=mui_video.duration*(this.value / 100);
						             mui_video.currentTime=time;
						       });
						          //进度条事件，当点击进度条时，让它在当前位置开始播放
						      mui_block_range.addEventListener("input", function()
						       {
						            var val=this.value;
						            mui_block_range.style.backgroundSize=""+val+"% 100%";  
						       });
         
                              //媒体数据加载完成，获得视频总时间，获得总时间有点慢
					         mui_video.addEventListener("loadedmetadata", function()
					          {
					            mui_time_cout.innerText=_this.Second(mui_video.duration);
					            if(_this.options.autoplay) //是否自动播放
					            {
					            	console.log("iswifi:"+_this.isMobile());
					            	if(!_this.isMobile() && _this.options.isMobile)
					            	{
					            		_this.options.prompt(mui_video);
					            		return
					            	}
					             mui_video.play();
					             mui_pay_ico.style.display="none";
					             mui_videoControls.style.display="none";
					             rprogress.style.display="block";
					            }
					          });
                             //监测视频是否已开始播放
                           mui_video.addEventListener("playing", function()
					          {
 					             mui_pay_ico.style.display="none";
					             mui_videoControls.style.display="none";
					             rprogress.style.display="block";			            
					          });
                                //监测视频播放进度，让进度条走起来
            	              mui_video.addEventListener("timeupdate", function()
							   { 
							            var val = (100 /mui_video.duration) *mui_video.currentTime;
							                mui_block_range.value=val;
							                mui_block_range.style.backgroundSize=""+val+"% 100%"; 
							                rprogress.style.display="none";
							     //更新当前时间
							     mui_time_current.innerText=_this.Second(mui_video.currentTime);
							         
							   });
							   var arr=[];
							   var snum=0;
							  setInterval(function()
							   {   
							   	   //当网速过慢，等待缓冲超过3秒就提示加载框
							   	   if(mui_video.paused || mui_video.ended) //暂停或结束
                                    {
                                   
                                    }
                                    else //播放状态
                                    {
                                     if(snum>3)  
                                     {
                                    snum=0;	
                                     }
                                     if(snum==0)
                                      { 
                                      	arr[0]=parseInt(mui_video.currentTime); 
                                      }
                                      if(snum==1)
                                      { 
                                      	arr[1]=parseInt(mui_video.currentTime); 
                                      }
                                      if(snum==2)
                                      { 
                                      	arr[2]=parseInt(mui_video.currentTime); 
                                      }
                                      if(snum==3)
                                      { 
                                      	if(parseInt(mui_video.currentTime)==arr[0] && parseInt(mui_video.currentTime)==arr[1] && parseInt(mui_video.currentTime)==arr[2])
                                      	{
                                      		rprogress.style.display="block";
                                      		mui_pay_ico.style.display="none";
                                      	}
                                      }
                                      snum++;  
                                    } 	   
							   },1000);
							   
							    //当播放结束
						      mui_video.addEventListener("ended", function()
						       {
						       	if(_this.options.iospay && mui.os.ios)
	            	             {}else{
						         mui_video.currentTime=0;
						         mui_time_current.innerText="00:00";
						         mui_block_range.style.backgroundSize="0% 100%";
						         mui_block_range.value=0;
						         mui_pay_ico.querySelector("use").setAttribute("xlink:href","#icon-bofang1");//显示播放图标
						         mui_pay_ico.style.display="block";
						         mui_videoControls.style.display="block";
						         }
						         mui_video.pause();
						       });
						       
						         //当点击视频时，显示按钮，进度条
						      v_left.addEventListener("tap", function()
						       {
						       	
						       	   var display=rprogress.style.display;
						           if(display!="block")
						           {
						            mui_pay_ico.style.display="block";
						            if(fullScreenFlag)
                                     {
						            mui_full_back.style.display="block";
						             }
                                   }   
						            if (mui_video.paused || mui_video.ended) //当暂停或结束
                                    {
                                     mui_pay_ico.querySelector("use").setAttribute("xlink:href","#icon-bofang1"); //显示播放图标
                                     
                                    }
                                    else //否则暂停播放
                                    {
                                    mui_pay_ico.querySelector("use").setAttribute("xlink:href","#icon-zanting"); //显示暂停图标
                                    
                                    }
					                mui_videoControls.style.display="block";
					                
					                setTimeout(function()
					                {
					                	mui_pay_ico.style.display="none";
					                	mui_videoControls.style.display="none";
					                	mui_full_back.style.display="none";
					                	if (mui_video.paused || mui_video.ended) //当暂停或结束
                                          {
                                          	mui_pay_ico.style.display="block";
					                	  }
					                },3000);
						           
						       });
						       b_right.addEventListener("tap", function()
						       {
						       	  
						       	   var display=rprogress.style.display;
						           if(display!="block")
						           {
						            mui_pay_ico.style.display="block";
						            if(fullScreenFlag)
                                     {
						            mui_full_back.style.display="block";
						             }
                                   }   
						            if (mui_video.paused || mui_video.ended) //当暂停或结束
                                    {
                                     mui_pay_ico.querySelector("use").setAttribute("xlink:href","#icon-bofang1"); //显示播放图标
                                     
                                    }
                                    else //否则暂停播放
                                    {
                                    mui_pay_ico.querySelector("use").setAttribute("xlink:href","#icon-zanting"); //显示暂停图标
                                    
                                    }
					                mui_videoControls.style.display="block";
					                
					                setTimeout(function()
					                {
					                	mui_pay_ico.style.display="none";
					                	mui_videoControls.style.display="none";
					                	mui_full_back.style.display="none";
					                	if (mui_video.paused || mui_video.ended) //当暂停或结束
                                          {
                                          	mui_pay_ico.style.display="block";
					                	  }
					                },3000);
						           
						       });
						       mui_video.addEventListener("error", function()
							   {
							   	 mui.toast("~加载视频失败~");
							   });	
							    
							    var x=0,y=0;
							    //开始拖动点坐标
							    var Volume=0;
							    var Brightness=0;
							    var currentTime=0;
							    var statbool=false;
							    var drags_v=0;
							   v_left.addEventListener("dragstart", function(event)
							   {
							   	  if(mui.os.plus && !_this.options.drag)
			          	          {
							   	  //向下滑动
							   	  x=event.detail.deltaX;
							   	  y=event.detail.deltaY;
							   	  Volume=plus.device.getVolume();
							   	  currentTime=mui_video.currentTime;
                                  ptime.innerHTML="音量："+(parseInt(Volume*100))+"%";
                                  }
                                  
                                 // console.log("X:"+x);
							   });
							    //拖动中坐标
							   v_left.addEventListener("drag", function(event)
							   {
							   	    var dY=event.detail.deltaY;
							   	    var dX=event.detail.deltaX;
							   	    
							   	    if(drags_v==0 || drags_v==1)
							   	    {
							   	    if(y>dY && dX>=x-6 && dX<x+6)
							   	    {
							   	    	//上
							   	    	   if(mui.os.plus && !_this.options.drag)
			          	                    {
							   	    	    ptime.style.display="block";
							   	    		Volume=Volume+0.01;
							   	    		if(Volume<=1 && Volume>=0)
							   	    		{
							   	    		 statbool=false;
							   	    		 drags_v=1;
							   	    		 plus.device.setVolume(Volume.toFixed(2));
							   	    		 ptime.innerHTML="音量："+(parseInt(Volume*100))+"%";
							   	    		}
							   	    		}
							   	    		return;
							   	    }
							   	    else if(y<dY && dX>=x-6 && dX<x+6)
							   	    {
							   	    	   if(mui.os.plus && !_this.options.drag)
			          	                    {
							   	    		Volume=Volume-0.01;
							   	    		 ptime.style.display="block";
							   	    		if(Volume<=1 && Volume>=0)
							   	    		{
							   	    	      statbool=false;
							   	    	      drags_v=1;
							   	    		 plus.device.setVolume(Volume.toFixed(2));
							   	    		 ptime.innerHTML="音量："+(parseInt(Volume*100))+"%";
							   	    		}
							   	    		}
							   	    		return;
							   	    }
							   	   }
							   	   
							   	    if(drags_v==0 || drags_v==2)
							   	    {
							   	    if(x<dX && (dY<y+6 && dY>y-6))
							   	    {
							   	    	ptime.style.display="block";
							   	    	currentTime=currentTime+1;
							   	    	if(Math.round(currentTime)<=Math.round(mui_video.duration)  && Math.round(currentTime)>=0)
							   	    	{
							   	    	 statbool=true;
		                                  drags_v=2;
							   	    	 ptime.innerHTML="快进："+_this.Second(currentTime);
							   	    	}
	
							   	    }
							   	    else  if(x>dX && (dY<y+6 && dY>y-6))
							   	    {
							   	    	ptime.style.display="block";
							   	    	currentTime=currentTime-1;
							   	    	if(Math.round(currentTime)<=Math.round(mui_video.duration) && Math.round(currentTime)>=0)
							   	    	{
							   	    	 statbool=true;
							   	         drags_v=2;
							   	    	 ptime.innerHTML="后退："+_this.Second(currentTime);
							   	    	}
							   	    }
							   	    }
							   });
							   v_left.addEventListener("dragend", function(event)
							   {
							   	  ptime.style.display="none";
							   	  drags_v=0;
							   	  if(statbool)
							   	  {
							   	  	 mui_video.currentTime=currentTime;
							   	  	 statbool=false;
							   	  }
							   });
							   b_right.addEventListener("dragstart", function(event)
							   {
							   	 if(mui.os.plus && !_this.options.drag)
			          	          {
							   	  //向下滑动
							   	  x=event.detail.deltaX;
							   	  y=event.detail.deltaY;
							   	  Brightness=plus.screen.getBrightness();
							   	  currentTime=mui_video.currentTime;
							   	  ptime.innerHTML="亮度："+(parseInt(Brightness*100))+"%";
							   	  }
							   });
							   
							     //拖动中坐标
							   b_right.addEventListener("drag", function(event)
							   {
							   	    var dY=event.detail.deltaY;
							   	    var dX=event.detail.deltaX;
							   	     if(drags_v==0 || drags_v==1)
							   	    {
							   	    if(y>dY && dX>=x-6 && dX<x+6)
							   	    {
							   	    	//上
							   	    	   if(mui.os.plus && !_this.options.drag)
			          	                   {
							   	    	    ptime.style.display="block";
							   	    		Brightness=Brightness+0.01;
							   	    		if(Brightness<=1 && Brightness>=0)
							   	    		{ statbool=false;
							   	    			drags_v=1;
							   	    		 plus.screen.setBrightness(Brightness.toFixed(2));
							   	    		 ptime.innerHTML="亮度："+(parseInt(Brightness*100))+"%";
							   	    		}
							   	    		}
							   	    		return;
							   	    }
							   	    else if(y<dY && dX>=x-6 && dX<x+6)
							   	    {
							   	    	    if(mui.os.plus && !_this.options.drag)
			          	                   {
							   	    		Brightness=Brightness-0.01;
							   	    		 ptime.style.display="block";
							   	    		if(Brightness<=1 && Brightness>=0)
							   	    		{ statbool=false;
							   	    			drags_v=1;
							   	    		 plus.screen.setBrightness(Brightness.toFixed(2));
							   	    		 ptime.innerHTML="亮度："+(parseInt(Brightness*100))+"%";
							   	    		}
							   	    		}
							   	    		return;
							   	    } 
							   	    }
							   	    if(drags_v==0 || drags_v==2)
							   	    { 
							   	     if(x<dX  && (dY<y+6 && dY>y-6))
							   	    {
							   	    	ptime.style.display="block";
							   	    	currentTime=currentTime+1;
							   	    	if(Math.round(currentTime)<=Math.round(mui_video.duration)  && Math.round(currentTime)>=0)
							   	    	{
							   	    	 statbool=true;
							   	    	 drags_v=2;
							   	    	 ptime.innerHTML="快进："+_this.Second(currentTime);
							   	    	}
	
							   	    }
							   	    else if(x>dX  && (dY<y+6 && dY>y-6))
							   	    {
							   	    	ptime.style.display="block";
							   	    	currentTime=currentTime-1;
							   	    	if(Math.round(currentTime)<=Math.round(mui_video.duration) && Math.round(currentTime)>=0)
							   	    	{
							   	    	 statbool=true;
							   	    	 drags_v=2;
							   	    	 ptime.innerHTML="后退："+_this.Second(currentTime);
							   	    	}
							   	    }
							   	    }
							   	 
							   });
							    b_right.addEventListener("dragend", function(event)
							   {
							   	  ptime.style.display="none";
							   	  drags_v=0;
							   	  if(statbool)
							   	  {
							   	  	 mui_video.currentTime=currentTime;
							   	  	 statbool=false;
							   	  }
							   });   
					//点击全屏按钮
				mui_quanping.addEventListener("tap", function()
			      {
				       isfull("");
				});
				var isfull=function(isstat)
				{
				if(fullScreenFlag)
                {    
                	if(isstat=="pay")
                	{
                		return;
                	}
                	//退出全屏播放
                   if(document.exitFullscreen)
                   {
					    document.exitFullscreen();  
					} else if (document.webkitExitFullscreen) {  
					    document.webkitExitFullscreen();  
					} else if (document.mozCancelFullScreen) {  
					    document.mozCancelFullScreen();  
					} else if (document.msExitFullscreen) {  
					    document.msExitFullscreen();  
					}                     
                    fullScreenFlag=false;
                    mui_full_back.style.display="none";
                    if(mui.os.ios)
				     {
				     	//IOS
				       _this.getDom.style.cssText="background-color: #FFFFFF; overflow: hidden; margin: 10px; padding: 0px;";
				        if(mui.os.plus)
			          	    {
				             plus.navigator.setFullscreen(false);
										 plus.screen.lockOrientation('portrait'); //锁死屏幕方向为竖屏
				            }
				     }
		         }
		                else  //进入全屏播放
		                { 
		                   mui_full_back.style.display="block";
		                   fullScreenFlag=true;
	 if(mui.os.android)
     {
 //兼容多平台全屏         
if(_this.getDom.requestFullscreen)
{
_this.getDom.requestFullscreen();
}
else if(_this.getDom.mozRequestFullScreen){
_this.getDom.mozRequestFullScreen();
}
else if(_this.getDom.msRequestFullscreen){
_this.getDom.msRequestFullscreen();
}
else if(_this.getDom.webkitRequestFullscreen)
{
_this.getDom.webkitRequestFullScreen();
}
  
     }else{
     	//IOS
       _this.getDom.style.cssText="position:absolute; top: 0px; width:100%; height: 100%; z-index: 9999; background-color:rgba(0,0,0,0.9); overflow: hidden;margin:0px; padding: 0px;padding-top:60%;";
       if(mui.os.plus)
		      {
       plus.navigator.setFullscreen(true);
			 plus.screen.lockOrientation('landscape'); //锁死屏幕方向为横屏
          } 
      }
}
}
				 //后退出全屏播放
				mui_full_back.addEventListener("tap", function()
			     {
            	     //退出全屏播放
                   if(document.exitFullscreen)
                    {
					    document.exitFullscreen();  
					} else if (document.webkitExitFullscreen) {  
					    document.webkitExitFullscreen();  
					} else if (document.mozCancelFullScreen) {  
					    document.mozCancelFullScreen();  
					} else if (document.msExitFullscreen) {  
					    document.msExitFullscreen();  
					}                     
                    fullScreenFlag=false;
                    mui_full_back.style.display="none";
            if(mui.os.ios)
				     {
				     	//IOS
				       _this.getDom.style.cssText="background-color: #FFFFFF; overflow: hidden; margin: 10px; padding: 0px;";
				       if(mui.os.plus)
			          	    {
				       plus.navigator.setFullscreen(false);
							 plus.screen.lockOrientation('portrait'); //锁死屏幕方向为竖屏
				       }
				
				     }
            	 });
            	   //监听是否全屏
                 document.addEventListener('webkitfullscreenchange', function() 
                 {
                    var el = document.webkitFullscreenElement; //获取全屏元素
                    if(el) 
                    {
                    	if(mui.os.plus)
			          	    {
                        plus.screen.lockOrientation('landscape'); //锁死屏幕方向为横屏
                       }
											
											   if(mui.os.android)
											   {
												 _this.getDom.querySelector("video").style.height="100%";
												 _this.getDom.querySelector(".mui-videoControls").style.bottom="18px";
												 }
											 
                        fullScreenFlag=true;
                    } 
                    else
                    {   
                    	fullScreenFlag=false;
                    	if(mui.os.plus)
			          	    {
                        plus.screen.lockOrientation('portrait-primary'); //锁死屏幕方向为竖屏
                      }
											if(mui.os.android)
											{
											 	_this.getDom.querySelector("video").style.height="auto";
												_this.getDom.querySelector(".mui-videoControls").style.bottom="0px";
											 }
                    }
                }); 
								
         if(mui.os.ios)
				{
            	  //监听是否横竖屏
                 document.addEventListener('orientationchange', function() 
                 {
                    if(window.orientation==180||window.orientation==0)
                    {
					//竖屏状态
					if(plus.navigator.isFullscreen())
					{
					 _this.getDom.style.paddingTop="60%";
					 plus.screen.lockOrientation('portrait'); //锁死屏幕方向为竖屏
					}
					}
					
					if(window.orientation==90||window.orientation==-90){
					//横屏状态
					 _this.getDom.style.paddingTop="0px";
					 plus.screen.lockOrientation('landscape'); //锁死屏幕方向为横屏
					}
                }); 
               }     
            	 	  }
            	    }
window.Html5video=Html5video; 

}(window,document));
//js生成的播放器使用的图标
(function(window){var svgSprite='<svg><symbol id="icon-bofang1" viewBox="0 0 1024 1024"><path d="M511.999488 0.023536C229.232027 0.023536 0.004093 229.242078 0.004093 512.001023c0 282.755876 229.227934 511.974417 511.995395 511.974417 282.768484 0 511.996418-229.218542 511.996418-511.974417C1023.995907 229.242078 794.767973 0.023536 511.999488 0.023536zM511.999488 984.592478c-261.018243 0-472.612866-211.584929-472.612866-472.591455 0-261.009595 211.594622-472.595548 472.612866-472.595548 261.02029 0 472.613889 211.584929 472.613889 472.595548C984.613377 773.008572 773.017732 984.592478 511.999488 984.592478zM448.050464 302.141388c-3.606279-4.128017-8.896989-6.744611-14.817058-6.744611-4.333879 0-8.326984 1.412163-11.577137 3.780093-0.928177 0.655939-1.823606 1.382487-2.655588 2.217505-4.85374 4.866844-6.673253 11.624758-5.461609 17.911952l0 386.002354c-1.212668 6.287193 0.607869 13.046131 5.461609 17.915022 7.750839 7.788383 20.328811 7.788383 28.078626 0l255.766421-196.511629c3.961381-3.970428 5.890392-9.199523 5.812617-14.404058 0.077774-5.204535-1.851237-10.432607-5.812617-14.404058L448.050464 302.141388zM452.922624 668.948565 452.922624 355.668489 656.781758 512.308015 452.922624 668.948565z"  ></path></symbol><symbol id="icon-quanping" viewBox="0 0 1510 1024"><path d="M1154.213425 318.815651 1154.213425 318.815651c-12.272232 0-22.31361-10.041378-22.31361-22.31361L1131.899816 110.360093c0-12.272232 10.041378-22.31361 22.31361-22.31361l0 0c12.272232 0 22.31361 10.041378 22.31361 22.31361l0 186.141949C1176.527035 308.774273 1166.485657 318.815651 1154.213425 318.815651z"  ></path><path d="M1176.527035 110.360093 1176.527035 110.360093c0 12.272232-10.041378 22.31361-22.31361 22.31361L968.071477 132.673702c-12.272232 0-22.31361-10.041378-22.31361-22.31361l0 0c0-12.272232 10.041378-22.31361 22.31361-22.31361L1154.213425 88.046483C1166.485657 88.046483 1176.527035 98.086593 1176.527035 110.360093z"  ></path><path d="M895.973065 368.600453 895.973065 368.600453c-8.677515-8.677515-8.677515-22.877661 0-31.556443L1138.463089 94.553985c8.677515-8.677515 22.877661-8.677515 31.556443 0l0 0c8.677515 8.677515 8.677515 22.877661 0 31.556443L927.528241 368.600453C918.850726 377.277968 904.65058 377.277968 895.973065 368.600453z"  ></path><path d="M562.917974 110.360093 562.917974 110.360093c0 12.272232-10.041378 22.31361-22.31361 22.31361L354.461148 132.673702c-12.272232 0-22.31361-10.041378-22.31361-22.31361l0 0c0-12.272232 10.041378-22.31361 22.31361-22.31361l186.141949 0C552.876596 88.046483 562.917974 98.086593 562.917974 110.360093z"  ></path><path d="M354.461148 88.046483 354.461148 88.046483c12.272232 0 22.31361 10.041378 22.31361 22.31361l0 186.141949c0 12.272232-10.041378 22.31361-22.31361 22.31361l0 0c-12.272232 0-22.31361-10.041378-22.31361-22.31361L332.147539 110.360093C332.148806 98.086593 342.188917 88.046483 354.461148 88.046483z"  ></path><path d="M612.702776 368.600453 612.702776 368.600453c-8.677515 8.677515-22.877661 8.677515-31.556443 0L338.656309 126.110429c-8.677515-8.677515-8.677515-22.877661 0-31.556443l0 0c8.677515-8.677515 22.877661-8.677515 31.556443 0l242.490024 242.490024C621.380291 345.722792 621.380291 359.922938 612.702776 368.600453z"  ></path><path d="M354.461148 705.184349 354.461148 705.184349c12.272232 0 22.31361 10.041378 22.31361 22.31361l0 186.141949c0 12.272232-10.041378 22.31361-22.31361 22.31361l0 0c-12.272232 0-22.31361-10.041378-22.31361-22.31361L332.147539 727.497959C332.148806 715.225727 342.188917 705.184349 354.461148 705.184349z"  ></path><path d="M332.148806 913.639907 332.148806 913.639907c0-12.272232 10.041378-22.31361 22.31361-22.31361l186.141949 0c12.272232 0 22.31361 10.041378 22.31361 22.31361l0 0c0 12.272232-10.041378 22.31361-22.31361 22.31361L354.461148 935.953517C342.188917 935.953517 332.148806 925.913407 332.148806 913.639907z"  ></path><path d="M612.702776 655.399547 612.702776 655.399547c8.677515 8.677515 8.677515 22.877661 0 31.556443L370.212752 929.446015c-8.677515 8.677515-22.877661 8.677515-31.556443 0l0 0c-8.677515-8.677515-8.677515-22.877661 0-31.556443l242.490024-242.490024C589.823848 646.722032 604.025261 646.722032 612.702776 655.399547z"  ></path><path d="M945.757867 913.639907 945.757867 913.639907c0-12.272232 10.041378-22.31361 22.31361-22.31361L1154.213425 891.326298c12.272232 0 22.31361 10.041378 22.31361 22.31361l0 0c0 12.272232-10.041378 22.31361-22.31361 22.31361L968.071477 935.953517C955.799245 935.953517 945.757867 925.913407 945.757867 913.639907z"  ></path><path d="M1154.213425 935.953517 1154.213425 935.953517c-12.272232 0-22.31361-10.041378-22.31361-22.31361L1131.899816 727.497959c0-12.272232 10.041378-22.31361 22.31361-22.31361l0 0c12.272232 0 22.31361 10.041378 22.31361 22.31361l0 186.141949C1176.527035 925.913407 1166.485657 935.953517 1154.213425 935.953517z"  ></path><path d="M895.973065 655.399547 895.973065 655.399547c8.677515-8.677515 22.877661-8.677515 31.556443 0l242.490024 242.490024c8.677515 8.677515 8.677515 22.877661 0 31.556443l0 0c-8.677515 8.677515-22.877661 8.677515-31.556443 0L895.973065 686.95599C887.29555 678.277208 887.29555 664.077062 895.973065 655.399547z"  ></path></symbol><symbol id="icon-zanting" viewBox="0 0 1024 1024"><path d="M511.846477 0.307108C229.1025 0.307108 0.000031 229.409577 0.000031 512.153554s229.102469 511.846446 511.846446 511.846446 511.846446-229.102469 511.846446-511.846446S794.590454 0.307108 511.846477 0.307108z m0 981.107268C253.159283 981.414376 42.688024 770.840748 42.688024 512.153554S253.159283 42.995101 511.846477 42.995101s469.158452 210.471259 469.158452 469.158453-210.471259 469.260822-469.158452 469.260822z"  ></path><path d="M405.177677 341.606318c-11.772468 0-21.292812 9.622713-21.292812 21.292812v298.611217c0 11.772468 9.622713 21.292812 21.292812 21.292812s21.292812-9.622713 21.292813-21.292812V362.89913c0.102369-11.772468-9.520344-21.292812-21.292813-21.292812zM618.515276 341.606318c-11.772468 0-21.292812 9.622713-21.292812 21.292812v298.611217c0 11.772468 9.622713 21.292812 21.292812 21.292812 11.772468 0 21.292812-9.622713 21.292812-21.292812V362.89913c0-11.772468-9.622713-21.292812-21.292812-21.292812z"  ></path></symbol><symbol id="icon-houtui1" viewBox="0 0 1024 1024"><path d="M531.680256 366.199808l-6.35392 0c0-71.141376 0-126.949376 0-143.181824 0-53.538816-30.72-75.843584-68.5312-45.423616L104.966144 460.64128c-37.8112 30.429184-37.7856 80.137216 0.058368 110.503936l349.147136 280.13056c37.839872 30.343168 71.154688-0.484352 71.154688-45.543424 0-14.511104 0-74.535936 0-151.033856l52.885504 0c152.674304 0 269.123584 65.957888 340.544512 193.219584 13.995008 23.094272 28.673024 18.05824 28.673024 0C944.387072 621.167616 752.351232 366.199808 531.680256 366.199808z"  ></path></symbol></svg>';var script=function(){var scripts=document.getElementsByTagName("script");return scripts[scripts.length-1]}();var shouldInjectCss=script.getAttribute("data-injectcss");var ready=function(fn){if(document.addEventListener){if(~["complete","loaded","interactive"].indexOf(document.readyState)){setTimeout(fn,0)}else{var loadFn=function(){document.removeEventListener("DOMContentLoaded",loadFn,false);fn()};document.addEventListener("DOMContentLoaded",loadFn,false)}}else if(document.attachEvent){IEContentLoaded(window,fn)}function IEContentLoaded(w,fn){var d=w.document,done=false,init=function(){if(!done){done=true;fn()}};var polling=function(){try{d.documentElement.doScroll("left")}catch(e){setTimeout(polling,50);return}init()};polling();d.onreadystatechange=function(){if(d.readyState=="complete"){d.onreadystatechange=null;init()}}}};var before=function(el,target){target.parentNode.insertBefore(el,target)};var prepend=function(el,target){if(target.firstChild){before(el,target.firstChild)}else{target.appendChild(el)}};function appendSvg(){var div,svg;div=document.createElement("div");div.innerHTML=svgSprite;svgSprite=null;svg=div.getElementsByTagName("svg")[0];if(svg){svg.setAttribute("aria-hidden","true");svg.style.position="absolute";svg.style.width=0;svg.style.height=0;svg.style.overflow="hidden";prepend(svg,document.body)}}if(shouldInjectCss&&!window.__iconfont__svg__cssinject__){window.__iconfont__svg__cssinject__=true;try{document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>")}catch(e){console&&console.log(e)}}ready(appendSvg)})(window)		
