$(function(){
    //scroll begin
    $('.am-slider').flexslider({
        slideshow: false,
        itemWidth: 120,                   // Integer: slide 宽度，多个同时滚动时设置
        itemMargin: 0,                  // Integer: slide 间距
        minItems: 9,                    // Integer: 最少显示 slide 数, 与 `itemWidth` 相关
        maxItems: 0,                    // Integer: 最多显示 slide 数, 与 `itemWidth` 相关
        move: 9,                        // Integer: 一次滚动移动的 slide 数量，0 - 滚动可见的 slide
    });

    $(".today-game-list .next").click(function(){
        $('.am-slider').flexslider('next');
    })

    $(".today-game-list .prev").click(function(){
        $('.am-slider').flexslider('prev');
    })
    //scroll end

})


