$(function(){
    //scroll begin
    $(".today-game-list .next").click(function(){
        var left = parseInt($(".today-game-list ul").data("left"));
        left += 120;
        $(".today-game-list ul").animate({scrollLeft: "+=" + left}, "normal");
        $(".today-game-list ul").data("left", left);
    })

    $(".today-game-list .prev").click(function(){
        var left = parseInt($(".today-game-list ul").data("left"));
        left -= 120;
        $(".today-game-list ul").animate({scrollLeft: "-=" + left}, "normal");
        $(".today-game-list ul").data("left", left);
    })
    //scroll end
})