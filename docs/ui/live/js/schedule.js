$(function(){
    layui.use(["layer"], function(){
        //show all category begin
        $(".showAllCategory").click(function(){
            $.get("./schedule_show_category.html", function (html) {
                layer.open({
                    type: 1,
                    title: "全部分类",
                    shadeClose: true,
                    area: ['440px', 'auto'], //宽高
                    content: html
                });
            })
        })
        //show all category end

        //hot category begin
        $(".categroy-list li").click(function(){
            $(".categroy-list li").removeClass("active");
            $(this).addClass("active");
        })
        //hot category end

        //category begin
        $(".center-container .left .categroy .block-list li").click(function(){
            $(".center-container .left .categroy .block-list li").removeClass("active");
            $(this).addClass("active");
        })
        //category end

        //select date begin
        $(".select-date li").click(function(){
            $(".select-date li").removeClass("active");
            $(this).addClass("active");
        })
        //select date end
    })
})