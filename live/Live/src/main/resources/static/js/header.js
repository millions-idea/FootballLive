$(function () {

    // 判断pc浏览器是否缩放，若返回100则为默认无缩放，如果大于100则是放大，否则缩小

    function detectZoom (){

        var ratio = 0,
            screen = window.screen,

            ua = navigator.userAgent.toLowerCase();

        if (window.devicePixelRatio !== undefined) {

            ratio = window.devicePixelRatio;

        }

        else if (~ua.indexOf('msie')) {

            if (screen.deviceXDPI && screen.logicalXDPI) {

                ratio = screen.deviceXDPI / screen.logicalXDPI;}}

        else if (window.outerWidth !== undefined && window.innerWidth !== undefined) {

            ratio = window.outerWidth / window.innerWidth;}

        if (ratio){

            ratio = Math.round(ratio * 100);}

        return ratio;

    };

//window.onresize 事件可用于检测页面是否触发了放大或缩小。

    $(function(){
//alert(detectZoom())

    })

    $(window).on('resize',function(){      isScale();
    });

//判断PC端浏览器缩放比例不是100%时的情况

    function isScale(){

        var rate = detectZoom();
        if(rate != 100){

//如何让页面的缩放比例自动为100,'transform':'scale(1,1)'没有用，又无法自动条用键盘事件，目前只能提示让用户如果想使用100%的比例手动去触发按ctrl+0

            console.log(1)

            alert('当前页面不是100%显示，请按键盘ctrl+0恢复100%显示标准，以防页面显示错乱！')

        }}
//阻止pc端浏览器缩放js代码

//由于浏览器菜单栏属于系统软件权限，没发控制，我们着手解决ctrl/cammond + +/- 或 Windows下ctrl + 滚轮 缩放页面的情况，只能通过js来控制了

// jqeury version

    $(document).ready(function () {

// chrome 浏览器直接加上下面这个样式就行了，但是ff不识别

        $('body').css('zoom', 'reset');
        $(document).keydown(function (event) {

            //event.metaKey mac的command键
            if ((event.ctrlKey === true || event.metaKey === true)&& (event.which === 61 || event.which === 107 ||

                    event.which === 173 || event.which === 109 || event.which === 187  || event.which === 189)){

                event.preventDefault();    }
        });

        $(window).bind('mousewheel DOMMouseScroll', function (event) {

            if (event.ctrlKey === true || event.metaKey) {

                event.preventDefault();    }  });});





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

            $(".profile-nav li").click(function () {
                var url = $(this).data("url");
                location.href = url;
            })
            //user pop animation end
        })


    });
    //header html end


})