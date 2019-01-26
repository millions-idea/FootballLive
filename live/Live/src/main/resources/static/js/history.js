$(function(){
    layui.use(["layer"], function(){
        //show all category begin
        $(".showAllCategory").click(function(){
            $.get("/schedule/getCategorySchedule", {
                categoryId: parseInt($(this).data("id"))
            },function (html) {
                layer.open({
                    type: 1,
                    title: "预览赛事列表",
                    shadeClose: true,
                    area: ['800px', '500px'], //宽高
                    content: html
                });
            })
        })
        //show all category end

        //hot category begin
        $(".categroy-list li").click(function(){
            $(".categroy-list li").removeClass("active");
            $(this).addClass("active");

            layer.load(1, {
                shadeClose: true
            });

            var type = $(this).data("type");

            $.get("/schedule/getScheduleList", {
                type: type == null ? null : parseInt(type)
            },function (html) {
                layer.closeAll();
                $(".schedule-scroll-container").html(html);
                bindLiveClickEvent();
            })
        })
        //hot category end

        //category begin
        $(".center-container .left .categroy .block-list li").click(function(){
            if(!$(this).hasClass("showAllCategory")){
                var gameId = $(this).data("id");
                $.get("/schedule/getScheduleList", {
                    gameId: gameId
                },function (html) {
                    layer.closeAll();
                    $(".schedule-scroll-container").html(html);
                    bindLiveClickEvent();
                })
            }
        })
        //category end

        //select date begin
        $(".select-date li").click(function(){
            $(".select-date li").removeClass("active");
            $(this).addClass("active");

            layer.load(1, {
                shadeClose: true
            });

            $.get("/schedule/getScheduleList", {
                type: parseInt($(this).data("type"))
            },function (html) {
                layer.closeAll();
                $(".schedule-scroll-container").html(html);
                bindLiveClickEvent();
            })
        })
        //select date end


        //schedule list begin
        $.get("/user/getHistoryScheduleList", function (html) {
            layer.closeAll();
            $(".schedule-scroll-container").html(html);
            bindLiveClickEvent();
        })
        //schedule list end

    })
})

function bindLiveClickEvent(){
    $(".schedule-scroll-container .schedule-list .schedule").click(function () {
        var status = $(this).find(".status").text();
        var liveId = $(this).data("liveid");
        if(liveId == null) {
            layer.msg("直播间暂无直播哦~");
            return;
        }
        if(status.indexOf("直播中") != -1){
            location.href = "/live?liveId=" + liveId
        }else{
            layer.msg("直播间暂无直播哦~");
        }
    })
}