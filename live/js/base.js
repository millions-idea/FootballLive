$(function(){
    //search animation begin
    $(".search input").hover(function(){
        var $that = $(this);
        $that.click(function(){
            $that.animate({width:"110px"});
            $(".search .icon-search").animate({left: "-30px"});
        })
    }, function(){
        $(this).animate({width:"80px"});
        $(".search .icon-search").animate({left: "0px"});
    })
    //search animation end

    //download pop animation begin
    $(".navigation .login-or-download .list .download").hover(function(){
        $(".pop-qrcode").show();
        $(".pop-qrcode").hover(function(){
            $(this).show();
        }, function(){
            $(this).hide();
        })
    }, function(){
        $(".pop-qrcode").hide();
        $(".pop-qrcode").hover(function(){
            $(this).show();
        }, function(){
            $(this).hide();
        })
    })
    //download pop animation end
})