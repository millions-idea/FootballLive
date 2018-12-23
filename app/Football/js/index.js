/*! framework page by DF 2018-11-24 09:57:41*/
mui.init();

var service = {
	/**
	 * 获取消息列表 DF 2018年12月19日06:30:15
	 * @param {Object} param
	 * @param {Object} callback
	 */
	getMessageList: function(param, callback){
		$.get(app.utils.toUrl(app.config.apiUrl + "api/profile/getMessageList"), param, function(data){
			app.logger("profile", JSON.stringify(data));
			callback(data);
		});
	}
}


var data = {};
var nim = {};

mui.plusReady(function(){
	app.logger("framework", "plusReady");
	
  	//plus.navigator.setStatusBarStyle("light");
	//plus.navigator.setStatusBarBackground(app.style.barBackgroundColor);
    
    //手动关闭启动页
    plus.navigator.closeSplashscreen();
    
    plus.webview.currentWebview().addEventListener("show", function(){
		// 拉取短消息列表
		service.getMessageList({}, function(res){
			if(app.utils.ajax.isError(res)) {
				app.utils.msgBox.msg("加载消息列表失败");
				return;
			}
	
			for (var i = 0; i < res.data.length; i++) {
				var item = res.data[i];
				var message = item.message;
				if(item.isRead==1) continue;
				if(item.type) message = "点击查看详情";
				plus.push.createMessage("您有一条未读消息: " + message);			
			}
		})
    })
    
    		
	plus.push.addEventListener("receive", function(){
		console.log("推送消息被点击")
		clickPushMessage();
	})
	
	plus.push.addEventListener("click", function(){
		console.log("推送消息被点击")
		clickPushMessage();
	});
    
    
    /**
     * 获取本地存储中launchFlag的值
     * http://www.html5plus.org/doc/zh_cn/storage.html#plus.storage.getItem
     * 若存在，说明不是首次启动，直接进入首页；
     * 若不存在，说明是首次启动，进入引导页；
     */
    var launchFlag = plus.storage.getItem("launchFlag");
    if(launchFlag) {
    	console.log("不是首次启动，直接进入首页")
		//app.utils.call("asyncLoginNIM", {});

    } else {
        mui.openWindow({
            url: "guide.html",
            id: "guide"
        });
    }
    
    
	// 禁止返回到登录注册页面
	/*mui.back = function() {
		return false;
	}*/
	
	//首页返回键处理,处理逻辑：1秒内,连续两次按返回键，则退出应用
    var first = null;
    mui.back = function() {
        if (!first) {//首次按键，提示‘再按一次退出应用’
            first = (new Date()).getTime();
            mui.toast('再按一次退出应用');
            setTimeout(function() {
                first = null;
            }, 1000);
        } else {
            if ((new Date()).getTime() - first < 1000) {
                plus.runtime.quit();
            }
        }
    };
	
	// 对网络连接进行事件监听
	hookNetwork();
	
	// 获取当前的webview对象
	var webView = plus.webview.currentWebview()

		// 向当前的主页webview追加子页的webview对象
		for (var i = 0 ; i < app.pages.length ; i ++) {
			var page = plus.webview.create(app.pages[i].url, app.pages[i].id, app.pages[i].style);
			// 隐藏webview窗口
			page.hide();
			// 注册webview窗口的show事件
	        plus.webview.getWebviewById(app.pages[i].id).addEventListener('show',showCallback.bind(page, app.pages[i].id)); 
			
			// 追加每一个子页面到当前主页面
			webView.append(page);
		}
		// 默认显示第一个webview
		plus.webview.show(app.pages[0].id);
		
		
		// 批量绑定tap事件，展示不同的页面
		mui(".mui-bar-tab").on("tap", "a", function() {
			var tabindex = this.getAttribute("tabindex");
			
			// 显示点击的tab选项所对应的页面
			plus.webview.show(app.pages[tabindex].id, "fade-in", 200);
			
			// 隐藏其他的不需要的页面
			for (var i = 0 ; i < app.pages.length ; i ++) {
				if (i != tabindex) {
					plus.webview.hide(app.pages[i].id, "fade-out", 200);
				}
			}
			
			// 播放动画点击效果
			// code...
		})
	 


	newInstance();
	
	
	// 延时加载
	// setTimeout("initData()", "1000");
	
});

window.addEventListener("asyncLoginNIM", function(){
	console.log("同步登录云信服务器：" + nim);
	if(nim == null){
		newInstance();	
	}else{
		console.log("已登录云信服务器：" + nim);
	}

	

})

function newInstance(){
	app.logger("index", "创建云信服务")
	
	// 设置激活图标
	var items = document.getElementsByClassName("mui-tab-item");
	for (var i = 0; i < items.length; i++) {
		items[i].classList.remove("mui-active");
	}
	items[0].classList.add("mui-active");
	plus.webview.show(app.pages[0].id);
	
	// 获取缓存信息 
	var cacheString = plus.storage.getItem("userInfo"); 
	if(cacheString == null) {
		app.logger("index","请先登录");
		app.utils.msgBox.msg("请先登录");
		app.utils.openWindow("login.html", "login");
		return;
	}
	var cache = JSON.parse(cacheString);

	app.logger("index", "实例化云信服务")

	// 实例化网易云信服务
	nim = NIM.getInstance({
	    appKey: "c58b616a2dbfa83bf7857364f77ae13b",
	    account: cache.cloudAccid,
	    token: cache.cloudToken,
	    customTag: 'TV',
	    db: true,
	    onconnect: onConnect,
	    onerror: onError,
	    onwillreconnect: onWillReconnect,
	    ondisconnect: onDisconnect,
	    // 多端登录
	    onloginportschange: onLoginPortsChange,
	    // 用户关系
	    onblacklist: onBlacklist,
	    onsyncmarkinblacklist: onMarkInBlacklist,
	    onmutelist: onMutelist,
	    onsyncmarkinmutelist: onMarkInMutelist,
	    // 好友关系
	    onfriends: onFriends,
	    onsyncfriendaction: onSyncFriendAction,
	    // 用户名片
	    onmyinfo: onMyInfo,
	    onupdatemyinfo: onUpdateMyInfo,
	    onusers: onUsers,
	    onupdateuser: onUpdateUser,
	    // 机器人列表的回调
	    onrobots: onRobots,
	    // 群组
	    onteams: onTeams,
	    onsynccreateteam: onCreateTeam,
	    onUpdateTeam: onUpdateTeam,
	    onteammembers: onTeamMembers,
	    // onsyncteammembersdone: onSyncTeamMembersDone,
	    onupdateteammember: onUpdateTeamMember,
	    // 群消息业务已读通知
	    onTeamMsgReceipt: onTeamMsgReceipt,
	    // 会话
	    onsessions: onSessions,
	    onupdatesession: onUpdateSession,
	    syncSessionUnread: true,
	    autoMarkRead: true,
	    // 消息
	    onroamingmsgs: onRoamingMsgs,
	    onofflinemsgs: onOfflineMsgs,
	    onmsg: onMsg,
	    // 系统通知
	    onofflinesysmsgs: onOfflineSysMsgs,
	    onsysmsg: onSysMsg,
	    onupdatesysmsg: onUpdateSysMsg,
	    onsysmsgunread: onSysMsgUnread,
	    onupdatesysmsgunread: onUpdateSysMsgUnread,
	    onofflinecustomsysmsgs: onOfflineCustomSysMsgs,
	    oncustomsysmsg: onCustomSysMsg,
	    // 收到广播消息
	    onbroadcastmsg: onBroadcastMsg,
	    onbroadcastmsgs: onBroadcastMsgs,
	    // 同步完成
	    onsyncdone: onSyncDone
	});
	
	app.logger("index", "实例化云信服务成功" + nim)
	

}

function clickPushMessage(){
	app.print("推送消息被单击");
	
	if(plus.webview.getWebviewById("systemMessage") != null) plus.webview.getWebviewById("systemMessage").close();
	mui.openWindow({
        url: "systemMessage.html",
        id: "systemMessage",
        preload: false,
        createNew: true
    });
}


// 监听网络状态更改
function hookNetwork(){
	document.addEventListener("netchange", function(){
		// 网络状态获取和判断
		var connectionStatus = plus.networkinfo.getCurrentType();
		if (connectionStatus != 0 && connectionStatus != 1) {
			// 重新打开网络连接
			var muxin_title = document.getElementById("muxin_title");
			muxin_title.innerHTML = "<b>" + app.config.brand + "</b>";
		} else {
			// 关闭网络连接
			var muxin_title = document.getElementById("muxin_title");
			muxin_title.innerHTML = "<b>" + app.config.brand + "(未连接)</b>";
		}
	});
}

// 预加载
function initData() {
	var chatlist = plus.webview.getWebviewById("home.html");
	mui.fire(imooc_chatlist, "refresh");
	
	var me = plus.webview.getWebviewById("my.html");
	mui.fire(imooc_me, "refresh");
}


// webview窗口的show事件回调函数
function showCallback(id){
	if(id == "home"){
		setActiveBarIcon(0);
	}else if(id == "lives"){
		setActiveBarIcon(1);
	}else if(id == "informations"){
		setActiveBarIcon(2);
	}else if(id == "my"){
		setActiveBarIcon(3);
	}
}

// 给指定bar图标设置active激活状态
function setActiveBarIcon(index){
	var items = document.getElementsByClassName("mui-tab-item");
		for (var i = 0; i < items.length; i++) {
			items[i].classList.remove("mui-active");
		}
		items[index].classList.add("mui-active");
}



function onConnect() {
    console.log('连接成功');
}
function onWillReconnect(obj) {
    // 此时说明 `SDK` 已经断开连接, 请开发者在界面上提示用户连接已断开, 而且正在重新建立连接
    console.log('即将重连');
    console.log(obj.retryCount);
    console.log(obj.duration);
    app.utils.msgBox.msg("您已断开连接，正在尝试重新连接……");
}
function destroyNim(disconnect){ 
	// 清除实例
    nim.destroy({
      done: function (err) {
        console.log('实例已被完全清除')
      }
    })
}
function onDisconnect(error) {
    // 此时说明 `SDK` 处于断开状态, 开发者此时应该根据错误码提示相应的错误信息, 并且跳转到登录页面
    console.log('丢失连接');
    console.log(JSON.stringify(error));
    if (error) {
        switch (error.code) {
        // 账号或者密码错误, 请跳转到登录页面并提示错误
        case 302:
        	destroyNim(false);
        	app.logger("IM_onDisconnect","账号或密码错误");
        	app.utils.msgBox.msg("账号或密码错误");
        	app.utils.openNewWindow("login.html", "login");
        	return;
            break;
        // 被踢, 请提示错误后跳转到登录页面
        case 'kicked':
        	destroyNim(false);
        	app.logger("IM_onDisconnect","您的账号在别处登录,被迫下线");
        	app.utils.msgBox.msg("您的账号在别处登录,被迫下线");
        	app.utils.openNewWindow("login.html", "login");
        	return;
        	
            break;
        default:
        	destroyNim(false);
        	app.logger("IM_onDisconnect","服务器出现连接问题,被迫下线");
        	app.utils.msgBox.msg("服务器出现连接问题,被迫下线");
        	app.utils.openNewWindow("login.html", "login");
        	return;
            break;
        }
    }
	return;
}
function onError(error) {
    console.log(error);
}

function onLoginPortsChange(loginPorts) {
    console.log('当前登录帐号在其它端的状态发生改变了', loginPorts);
}

function onBlacklist(blacklist) {
    console.log('收到黑名单', blacklist);
    data.blacklist = nim.mergeRelations(data.blacklist, blacklist);
    data.blacklist = nim.cutRelations(data.blacklist, blacklist.invalid);
    refreshBlacklistUI();
}
function onMarkInBlacklist(obj) {
    console.log(obj);
    console.log(obj.account + '被你在其它端' + (obj.isAdd ? '加入' : '移除') + '黑名单');
    if (obj.isAdd) {
        addToBlacklist(obj);
    } else {
        removeFromBlacklist(obj);
    }
}
function addToBlacklist(obj) {
    data.blacklist = nim.mergeRelations(data.blacklist, obj.record);
    refreshBlacklistUI();
}
function removeFromBlacklist(obj) {
    data.blacklist = nim.cutRelations(data.blacklist, obj.record);
    refreshBlacklistUI();
}
function refreshBlacklistUI() {
    // 刷新界面
}
function onMutelist(mutelist) {
    console.log('收到静音列表', mutelist);
    data.mutelist = nim.mergeRelations(data.mutelist, mutelist);
    data.mutelist = nim.cutRelations(data.mutelist, mutelist.invalid);
    refreshMutelistUI();
}
function onMarkInMutelist(obj) {
    console.log(obj);
    console.log(obj.account + '被你' + (obj.isAdd ? '加入' : '移除') + '静音列表');
    if (obj.isAdd) {
        addToMutelist(obj);
    } else {
        removeFromMutelist(obj);
    }
}
function addToMutelist(obj) {
    data.mutelist = nim.mergeRelations(data.mutelist, obj.record);
    refreshMutelistUI();
}
function removeFromMutelist(obj) {
    data.mutelist = nim.cutRelations(data.mutelist, obj.record);
    refreshMutelistUI();
}
function refreshMutelistUI() {
    // 刷新界面
}

function onFriends(friends) {
    console.log('收到好友列表', friends);
    data.friends = nim.mergeFriends(data.friends, friends);
    data.friends = nim.cutFriends(data.friends, friends.invalid);
    refreshFriendsUI();
}

function handleSysMsgs(sysMsgs) {
    if (!Array.isArray(sysMsgs)) {sysMsgs=[sysMsgs];}
    sysMsgs.forEach(function(sysMsg) {
        var idServer = sysMsg.idServer;  
        switch (sysMsg.type) {
        case 'addFriend':
        	console.log("系统通知：" + "申请加某个用户为好友");
            onAddFriend(sysMsg.friend);
            break;
        case 'applyFriend':
        	console.log("系统通知：" + "申请加为好友"); 
            break;
        case 'passFriendApply':
        	console.log("系统通知：" + "通过好友申请");
            onAddFriend(sysMsg.friend);
            
            break;
        case 'rejectFriendApply':
        	console.log("系统通知：" + "拒绝好友申请");
            break;
        case 'deleteFriend':
        	console.log("系统通知：" + "删除好友");
            onDeleteFriend(sysMsg.from);
            break;
        case 'applyTeam':
        	console.log("系统通知：" + "申请加入高级群");
            break;
        case 'rejectTeamApply':
        	console.log("系统通知：" + "拒绝申请");
            break;
        case 'teamInvite':
        	console.log("系统通知：" + "入群邀请");
            break;
        case 'rejectTeamInvite':
        	console.log("系统通知：" + "拒绝入群邀请");
            break;
        default:
            break;
        }
    });
}

function onSyncFriendAction(obj) {
    console.log(obj);
    switch (obj.type) {
    case 'addFriend':
        console.log('你在其它端直接加了一个好友' + obj.account + ', 附言' + obj.ps);
        onAddFriend(obj.friend);
        break;
    case 'applyFriend':
        console.log('你在其它端申请加了一个好友' + obj.account + ', 附言' + obj.ps);
        break;
    case 'passFriendApply':
        console.log('你在其它端通过了一个好友申请' + obj.account + ', 附言' + obj.ps);
        onAddFriend(obj.friend);
        break;
    case 'rejectFriendApply':
        console.log('你在其它端拒绝了一个好友申请' + obj.account + ', 附言' + obj.ps);
        break;
    case 'deleteFriend':
        console.log('你在其它端删了一个好友' + obj.account);
        onDeleteFriend(obj.account);
        break;
    case 'updateFriend':
        console.log('你在其它端更新了一个好友', obj.friend);
        onUpdateFriend(obj.friend);
        break;
    }
}
function onAddFriend(friend) {
    data.friends = nim.mergeFriends(data.friends, friend);
    refreshFriendsUI();
}
function onDeleteFriend(account) {
    data.friends = nim.cutFriendsByAccounts(data.friends, account);
    refreshFriendsUI();
}
function onUpdateFriend(friend) {
    data.friends = nim.mergeFriends(data.friends, friend);
    refreshFriendsUI();
}
function refreshFriendsUI() {
    // 刷新界面
}

function onMyInfo(user) {
    console.log('收到我的名片', user);
    data.myInfo = user;
    updateMyInfoUI();
}
function onUpdateMyInfo(user) {
    console.log('我的名片更新了', user);
    data.myInfo = NIM.util.merge(data.myInfo, user);
    updateMyInfoUI();
}
function updateMyInfoUI() {
    // 刷新界面
    //var webview = plus.webview.getWebviewById("my");
    //if(webview != null) webview.evalJS("updateMyInfoUI('" + JSON.stringify(data.myInfo) + "')");
}
function onUsers(users) {
    console.log('收到用户名片列表', users);
    data.users = nim.mergeUsers(data.users, users);
}
function onUpdateUser(user) {
    console.log('用户名片更新了', user);
    data.users = nim.mergeUsers(data.users, user);
}
function onRobots (robots) {
    // 客户私有化方案不支持
    console.log('收到机器人列表', robots);
    data.robots = robots;
}
function onTeams(teams) {
    console.log('群列表', teams);
    data.teams = nim.mergeTeams(data.teams, teams);
    onInvalidTeams(teams.invalid);
}
function onInvalidTeams(teams) {
    data.teams = nim.cutTeams(data.teams, teams);
    data.invalidTeams = nim.mergeTeams(data.invalidTeams, teams);
    refreshTeamsUI();
}
function onCreateTeam(team) {
    console.log('你创建了一个群', team);
    data.teams = nim.mergeTeams(data.teams, team);
    refreshTeamsUI();
    onTeamMembers({
        teamId: team.teamId,
        members: owner
    });
}
function refreshTeamsUI() {
    // 刷新界面
}
function onTeamMembers(teamId, members) {
    console.log('群id', teamId, '群成员', members);
    var teamId = obj.teamId;
    var members = obj.members;
    data.teamMembers = data.teamMembers || {};
    data.teamMembers[teamId] = nim.mergeTeamMembers(data.teamMembers[teamId], members);
    data.teamMembers[teamId] = nim.cutTeamMembers(data.teamMembers[teamId], members.invalid);
    refreshTeamMembersUI();
}
// function onSyncTeamMembersDone() {
//     console.log('同步群列表完成');
// }
function onUpdateTeam (team) {
    console.log('群状态更新', team)
}
function onUpdateTeamMember(teamMember) {
    console.log('群成员信息更新了', teamMember);
    onTeamMembers({
        teamId: teamMember.teamId,
        members: teamMember
    });
}
function refreshTeamMembersUI() {
    // 刷新界面
}
function onTeamMsgReceipt (teamMsgReceipts) {
    console.log('群消息已读通知', teamMsgReceipts)
}

function onSessions(sessions) {
    console.log('收到会话列表', sessions);
    data.sessions = nim.mergeSessions(data.sessions, sessions);
    updateSessionsUI();
}
function onUpdateSession(session) {
    console.log('会话更新了', session);
    data.sessions = nim.mergeSessions(data.sessions, session);
    updateSessionsUI(); 
}
function updateSessionsUI() {
    // 刷新session界面 
    console.log("刷新session界面" + data.sessions);
    var webview = plus.webview.getWebviewById("chatList");
    if(webview != null && data.sessions != null) {
    	webview.evalJS("updateSessionsUI('" + JSON.stringify(data.sessions) + "')");
    }
}
 

function onRoamingMsgs(obj) {
    console.log('漫游消息', obj);
    pushMsg(obj.msgs);
}
function onOfflineMsgs(obj) {
    console.log('离线消息', obj);
    pushMsg(obj.msgs);
}
function onMsg(msg) {
    console.log('收到消息', msg.scene, msg.type, msg);
    pushMsg(msg);
    pushMsgForSend(msg);
}
function onBroadcastMsg(msg) {
    console.log('收到广播消息', msg);
}
function onBroadcastMsgs(msgs) {
    console.log('收到广播消息列表', msgs);
}
function pushMsg(msgs) {
    if (!Array.isArray(msgs)) { msgs = [msgs]; }
    var sessionId = msgs[0].sessionId;
    data.msgs = data.msgs || {};
    data.msgs[sessionId] = nim.mergeMsgs(data.msgs[sessionId], msgs);
    
    var user = JSON.parse(plus.storage.getItem("userInfo"));
    
	for (var i = 0; i < data.sessions.length; i++) {
		var id = data.sessions[i].id.replaceX("p2p-","");
		if(id.indexOf(user.phone) != -1){
			nim.sendMsgReceipt({
			    msg: data.sessions[i].lastMsg,
			    done: function(error, obj){
			    	console.log('发送消息已读回执' + (!error?'成功':'失败'), error, obj);
			    }
			}); 
		}
	}
}


function pushMsgForSend(msgs) {
    if (!Array.isArray(msgs)) { msgs = [msgs]; }
    var sessionId = msgs[0].sessionId;
    data.msgs = data.msgs || {};
    data.msgs[sessionId] = nim.mergeMsgs(data.msgs[sessionId], msgs);
    
    var user = JSON.parse(plus.storage.getItem("userInfo"));
    
	for (var i = 0; i < data.sessions.length; i++) {
		var id = data.sessions[i].id.replaceX("p2p-","");
		if(id.indexOf(user.phone) != -1){
			nim.sendMsgReceipt({
			    msg: data.sessions[i].lastMsg,
			    done: function(error, obj){
			    	console.log('发送消息已读回执' + (!error?'成功':'失败'), error, obj);
			    }
			}); 
		}
	}
    
    console.log("本次消息类型" + JSON.stringify(msgs));
    console.log("本次消息编号" + sessionId.replaceX("p2p-",""));
    
    // 刷新个人会话页面
    webview = plus.webview.getTopWebview();
    if(webview != null) {
    	console.log("updateHistoryMsgsUI")
	    mui.fire(webview, "updateHistoryMsgsUI", {
	    	accid: sessionId.replaceX("p2p-",""),
	    	obj: msgs
	    })
    }
}

function onOfflineSysMsgs(sysMsgs) {
    console.log('收到离线系统通知', sysMsgs);
    pushSysMsgs(sysMsgs);
}
function onSysMsg(sysMsg) {
    console.log('收到系统通知', sysMsg)
     nim.markSysMsgRead({
	    sysMsgs: sysMsg, // or [someSysMsg]
	    done: function(error, obj){
	    	console.log(error);
		    console.log(obj);
		    console.log('标记系统通知为已读状态' + (!error?'成功':'失败'));
	    }
	});
    
    pushSysMsgs(sysMsg);
}
function onUpdateSysMsg(sysMsg) {
    pushSysMsgs(sysMsg);
}
function pushSysMsgs(sysMsgs) {
    data.sysMsgs = nim.mergeSysMsgs(data.sysMsgs, sysMsgs);
    refreshSysMsgsUI(sysMsgs); 
}
function onSysMsgUnread(obj) {
    console.log('收到系统通知未读数', obj);
    data.sysMsgUnread = obj;
    refreshSysMsgsUI(obj);
}
function onUpdateSysMsgUnread(obj) {
    console.log('系统通知未读数更新了', obj);
    data.sysMsgUnread = obj;
    refreshSysMsgsUI(obj);
}
function refreshSysMsgsUI(sysMsgs) {
    // 刷新界面
    console.log('系统通知刷新界面', sysMsgs);
    var webview = plus.webview.getWebviewById("index");
	 if(webview != null) {
	 	mui.fire(webview, "getLocalSysMsgs", {});
	 }
}
function onOfflineCustomSysMsgs(sysMsgs) {
    console.log('收到离线自定义系统通知', sysMsgs);
}
function onCustomSysMsg(sysMsg) {
    console.log('收到自定义系统通知', sysMsg);
}

function onSyncDone() {
    console.log('同步完成');
}


 
/**
 * 查询用户资料事件 DF 2018年12月8日18:47:26
 */
window.addEventListener("getUsers", function(event){
	nim.getUsers({
		accounts: event.detail.accounts,
		done: function(error, users){
			var webview = plus.webview.getWebviewById("chatList");
			mui.fire(webview, "onUsers", {
				error: error,
				users: users,
				model: event.detail.model
			})
		}
	});
})



 
/**
 * 查询历史聊天记录事件 DF 2018年12月9日18:18:01
 */
window.addEventListener("getHistoryMsgs", function(event){
	nim.getHistoryMsgs({
	    scene: 'p2p',
	    to: event.detail.to,
	    asc: true,
	    limit: 30,
	    done: function(error, obj){
			var user = JSON.parse(plus.storage.getItem("userInfo"));
	    	
	    	for (var i = 0; i < data.sessions.length; i++) {
	    		var id = data.sessions[i].id.replaceX("p2p-","");
	    		if(id.indexOf(event.detail.to) != -1){
	    			
	    			console.log("重置未读数：" + event.detail.to + "," + data.sessions[i].id); 
	    			
					nim.resetSessionUnread(data.sessions[i].id);
	    			
	    			console.log("已读回执：" + event.detail.to + "," + id); 
	    			nim.sendMsgReceipt({
					    msg: data.sessions[i].lastMsg,
					    done: function(error, obj){
					    	console.log('发送消息已读回执' + (!error?'成功':'失败'), error, obj);
					    }
					}); 
	    		}
	    	}
	    	 
	    	
	    	nim.getUsers({
				accounts: [event.detail.to, user.phone],
				done: function(error, users){
					var webview = plus.webview.getWebviewById("chat-" + event.detail.to);
					mui.fire(webview, "getHistoryMsgsDone", {
						error: error,
						obj: obj,
						model: obj.msgs,
						to: event.detail.to,
						toUserInfo: users[0],
						myUserInfo: users[1]
					})
				}
			});
	    	
	    	
	    }
	});
})


window.addEventListener("sendText", function(event){
	var msg = nim.sendText({
	    scene: 'team',
	    to: event.detail.to,
	    text: event.detail.text,
	    done: function(error, msg){
	    	
	    	/*var webview = plus.webview.getWebviewById("chat-" + event.detail.to);
			mui.fire(webview, "sendMsgDone", {
				error: error,
				msg: msg
			})*/
	   		pushMsg(msg);
	    }
	}); 
	pushMsg(msg);
})


window.addEventListener("sendFile", function(event){
	nim.sendFile({
	    scene: 'p2p',
	    to: event.detail.to,
	    type: 'image',
	    dataURL: event.detail.data,	
	    beginupload: function(upload) {
	        // - 如果开发者传入 fileInput, 在此回调之前不能修改 fileInput
	        // - 在此回调之后可以取消图片上传, 此回调会接收一个参数 `upload`, 调用 `upload.abort();` 来取消文件上传
	    },
	    uploadprogress: function(obj) {
	        console.log('文件总大小: ' + obj.total + 'bytes');
	        console.log('已经上传的大小: ' + obj.loaded + 'bytes');
	        console.log('上传进度: ' + obj.percentage);
	        console.log('上传进度文本: ' + obj.percentageText);
	    },
	    uploaddone: function(error, file) {
	        console.log(error);
	        console.log(file);
	        console.log('上传' + (!error?'成功':'失败'));
	    },
	    beforesend: function(msg) {
	        console.log('正在发送p2p image消息, id=' + msg.idClient);
	        pushMsg(msg);
	    },
	    done: function(error, msg){
	    	var webview = plus.webview.getWebviewById("chat-" + event.detail.to);
			mui.fire(webview, "sendMsgDone", {
				error: error,
				msg: msg
			})
	   		pushMsg(msg);
	    }	
	});
})

/**
 * 更新头像 DF 2018年12月11日02:12:33
 */
window.addEventListener("uploadAvatar", function(event){
	console.log(event.detail.avatar);
	nim.updateMyInfo({
	    avatar: event.detail.avatar,
	    done: function(error, user){
	    	console.log('更新我的名片' + (!error?'成功':'失败'));
		    console.log(error);
		    console.log(user);
		    if (!error) {
		        onUpdateMyInfo(user);
		        var webview = plus.webview.getWebviewById("my");
				mui.fire(webview, "onAvatar", {
					error: error,
					avatar: event.detail.avatar,
					user: user
				})
		    }
	    }
	});
})


window.addEventListener("getFriends", function(){
	nim.getFriends({
    	done: function(error, friends){
    		console.log('获取好友列表');
		    if (error == null) {
				onFriends(friends);

		        // 查询用户详细资料
		        var profileList = new Array();
		        for (var i = 0; i < friends.length; i++) {
		        	profileList.push(friends[i].account);
		        }
		        nim.getUsers({
					accounts: profileList,
					done: function(error, users){
						var webview = plus.webview.getWebviewById("addressBook");
						mui.fire(webview, "onFriends", {
							error: error,
							friends: friends,
							profiles: users
						})
					}
				});
		    }
    	}
	}); 
})


window.addEventListener("applyFriend", function(event){
	nim.applyFriend({
	    account: event.detail.account,
	    ps: event.detail.ps,
	    done: function(error,obj){
	    	console.log(error);
		    console.log(obj);
		    console.log('申请加为好友' + (!error?'成功':'失败'));
		    var webview = plus.webview.getWebviewById("addFriend");
			mui.fire(webview, "applyFriendDone", {
				error: error,
				obj: obj
			})
	    }
	});
})
 


window.addEventListener("applyTeam", function(event){	
	nim.applyTeam({
	    teamId: event.detail.teamId,
	    ps: event.detail.ps,
	    done: function(error,obj){
	    	console.log(error);
		    console.log(obj);
		    console.log('申请加群' + (!error?'成功':'失败'));
		     
		    var webview = plus.webview.getWebviewById("liveDetail-" + event.detail.id);
			mui.fire(webview, "applyTeamDone", {
				error: error,
				obj: obj
			})
		    
	    }
	});
})
 
 


window.addEventListener("getLocalSysMsgs", function(){
		nim.getLocalSysMsgs({
	    limit: 100,
	    done: function(error, obj){
	    	console.log(error);
		    console.log(obj);
		    console.log('获取本地系统通知' + (!error?'成功':'失败'));
		    if (!error) {
		        var webview = plus.webview.getWebviewById("systemMessage");
		        if(webview != null){
		        	mui.fire(webview, "onMessage", {
		        		sysMsgs: obj.sysMsgs
		        	});
		        }
		    }
	    }
	});
})



window.addEventListener("passFriendApply", function(event){
	var idServer = event.detail.idServer;
	var account = event.detail.account;
	var sysMsgs = event.detail.sysMsgs;
	
	nim.passFriendApply({
    idServer: idServer,
    account: account,
    done: function(error, obj){
    	console.log(error);
	    console.log(obj);
	    console.log('通过好友申请' + (!error?'成功':'失败'));
	    if (!error) {
	        onAddFriend(obj.friend); 
	        nim.deleteLocalSysMsg({
			    idServer: idServer,
			    done: function(error, obj){
			    	console.log(error);
				    console.log(obj);
				    console.log('删除本地系统通知' + (!error?'成功':'失败'));
			    }
			}); 
	    }
	    
	    var webview = plus.webview.getWebviewById("systemMessage");
	        if(webview != null){
	        	mui.fire(webview, "passFriendApplyDone", {
		        	error: error,
		        	obj: obj
		        });
	        }
    }
})
	    });

	


window.addEventListener("rejectFriendApply", function(event){
	var idServer = event.detail.idServer;
	var account = event.detail.account;
	var sysMsgs = event.detail.sysMsgs;
	
	nim.rejectFriendApply({
    idServer: idServer,
    account: account,
    done: function(error, obj){
    	console.log(JSON.stringify(error));
    	console.log(JSON.stringify(obj));
	    console.log('拒绝好友申请' + (!error?'成功':'失败'));
	    if (!error) {
	        onAddFriend(obj.friend); 
	        nim.deleteLocalSysMsg({
			    idServer: idServer,
			    done: function(error, obj){
			    	console.log(error);
				    console.log(obj);
				    console.log('删除本地系统通知' + (!error?'成功':'失败'));
			    }
			}); 
	    }
	    
	    var webview = plus.webview.getWebviewById("systemMessage");
	        if(webview != null){
	        	mui.fire(webview, "rejectFriendApplyDone", {
		        	error: error,
		        	obj: obj
		        });
	        }
    }
})
	    });




window.addEventListener("getTeams", function(event){
	nim.getTeams({
	  done: function(error, teams){
	  	console.log(error);
		  console.log(teams);
		  console.log('获取群列表' + (!error?'成功':'失败'));
		  console.log(JSON.stringify(teams))
		  if (!error) {
		    onTeams(teams);
		    var webview = plus.webview.getWebviewById("groups");
		    if(webview != null) {
		    	mui.fire(webview, "getTeamsDone", {
		    		error: error,
		    		teams: teams
		    	});
		    }
		  }
	  }
	}); 
})



window.addEventListener("leaveTeam", function(event){
	nim.leaveTeam({
	  teamId: event.detail.teamId,
	  done: function(error, obj){
		  console.log(error);
		  console.log(obj);
		  console.log('主动退群' + (!error?'成功':'失败'));
	  }
	});
})