$(function(){
    //chat list begin
    var containerHeight = $(".right-navigation").height();
    $(".chat-list-container").height(containerHeight - 158);
    //chat list end

    //chat list content begin
    var html = "";
    for(var i=0; i<1000; i++){
        html += getRecvMessageHtml("178***5414", "测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本测试短消息文本");
    }
    $(".chat-list").html(html);
    scrollListToBottom();

    var disabledTimer = null;
    $(".editor-button").click(function(){
        var that = $(this);
        var msg = $(".editor-box textarea").val();
        if(msg == null || msg.length == 0) return;
        $(that).attr("disabled", true);
        $(that).css("background-color", "#b7b7b7");
        setRecvMessageHtml("1234",msg);
        $(".editor-box textarea").val("");
        disabledTimer = setTimeout(function(){
            $(that).css("background-color", "#5626D3");
            $(that).attr("disabled", false);
            clearTimeout(disabledTimer);
        }, 1000);

    })

    setOfficialMessageHtml("官方声明: 此直播间的任何评论、预测等观点均为个人观点，不代表卫星体育意见。")
    //chat list content end
})


function getRecvMessageHtml(phone, message) {
    var html = "";
    html += '<li>';
    html += '    <p class="text-cont">';
    html += '        <span class="phone">' + phone + '</span>';
    html += '        <span class="content">' + message + '</span>';
    html += '    </p>';
    html += '</li>';
    return html;
}

function setRecvMessageHtml(phone, message) {
    var html = "";
    html += '<li>';
    html += '    <p class="text-cont">';
    html += '        <span class="phone">' + phone + '</span>';
    html += '        <span class="content">' + htmlEncode(message) + '</span>';
    html += '    </p>';
    html += '</li>';
    $(".chat-list").append(html);
    scrollListToBottom();
}


function setOfficialMessageHtml(message) {
    var html = "";
    html += '<li class="official">';
    html += '    <p class="text-cont">';
    html += '        <span class="phone">' + "官方" + '</span>';
    html += '        <span class="content">' + htmlEncode(message) + '</span>';
    html += '    </p>';
    html += '</li>';
    $(".chat-list").append(html);
    scrollListToBottom();
}

function scrollListToBottom(){
    var areaMsgList = document.getElementById("chat-list");
    areaMsgList.scrollTop = areaMsgList.scrollHeight + areaMsgList.offsetHeight;
}


//转义  元素的innerHTML内容即为转义后的字符
function htmlEncode ( str ) {
    var ele = document.createElement('span');
    ele.appendChild( document.createTextNode( str ) );
    return ele.innerHTML;
}
//解析
function htmlDecode ( str ) {
    var ele = document.createElement('span');
    ele.innerHTML = str;
    return ele.textContent;
}
