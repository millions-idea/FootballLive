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
            })
        })
        //hot category end

        //category begin
       /* $(".center-container .left .categroy .block-list li").click(function(){
            $(".center-container .left .categroy .block-list li").removeClass("active");
            $(this).addClass("active");
        })*/
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
            })
        })
        //select date end


        //schedule list begin
        layer.ready(function(){
            layer.load(0, {shade: false});
        });
        $.get("/schedule/getScheduleList" ,function (html) {
            layer.closeAll();
            $(".schedule-scroll-container").html(html);
        })
        //schedule list end
    })
})