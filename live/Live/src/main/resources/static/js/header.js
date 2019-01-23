$(function () {
    layui.use(["form", "layer"], function () {
        //header html begin
        var page = ["/","/schedule"];
        var url = location.pathname;
        var index = 0;
        for(var i=0; i < page.length; i++){
            if(page[i] == url){
                index = i;
            }
        }
        $.get("/header", {
            index :  index
        }, function (html) {
            if ($(".header-container").html() != html) {
                $(".header-container").html(html);
            }

            //search animation begin
            $(".search input").hover(function () {
                var $that = $(this);
                $that.click(function () {
                    $that.animate({width: "110px"});
                    $(".search .icon-search").animate({left: "-30px"});
                })
            }, function () {
                $(this).animate({width: "80px"});
                $(".search .icon-search").animate({left: "0px"});
            })
            //search animation end

            //login pop begin
            $(".open-login-pop").click(function () {
                $.get("/user/loginPop", function (html) {
                    layer.open({
                        title: false,
                        type: 1,
                        closeBtn: 1, //不显示关闭按钮
                        anim: 2,
                        area: ["400px", "auto"],
                        shadeClose: true, //开启遮罩关闭
                        content: html
                    });
                })
            })
            //login pop end


            //download pop animation begin
            $(".navigation .login-or-download .list .download").hover(function () {
                $(".pop-qrcode").show();
                $(".pop-qrcode").removeClass("bounceIn animated").addClass("bounceIn animated");
                $(".pop-qrcode").hover(function () {
                    $(this).show();
                    $(this).removeClass("bounceIn animated");
                }, function () {
                    $(this).removeClass("bounceIn animated");
                    $(this).hide();
                })
            }, function () {
                $(".pop-qrcode").removeClass("bounceIn animated");
                $(".pop-qrcode").hide();
                $(".pop-qrcode").hover(function () {
                    $(this).show();
                    $(this).removeClass("bounceIn animated");
                }, function () {
                    $(this).removeClass("bounceIn animated");
                    $(".pop-qrcode").hide();
                })
            })
            //download pop animation end


            //user pop animation begin
            $(".navigation .login-or-download .list .profile").hover(function(){
                $(".pop-profile").show();
                $(".pop-profile").removeClass("bounceIn animated").addClass("bounceIn animated");
                $(".pop-profile").hover(function(){
                    $(this).show();
                    $(this).removeClass("bounceIn animated");
                }, function(){
                    $(this).removeClass("bounceIn animated");
                    $(this).hide();
                })
            }, function(){
                $(".pop-profilepop-profile").removeClass("bounceIn animated");
                $(".pop-profile").hide();
                $(".pop-profile").hover(function(){
                    $(this).show();
                    $(this).removeClass("bounceIn animated");
                }, function(){
                    $(this).removeClass("bounceIn animated");
                    $(".pop-profile").hide();
                })
            })
            //user pop animation end
        })


    });
    //header html end


})