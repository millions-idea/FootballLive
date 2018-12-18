/********************************************************************************
 * jquery ajax 扩展方法
 * 任洪威
 * 2017年9月21日11:49:17
 ********************************************************************************/
(function($){
    //首先备份下jquery的ajax方法
    var _get = $.get;
    var _post = $.post;
    var paras = new Object();

    //重写jquery的ajax方法
    $.fn.extend({
        ajax : function(opt){
            var $object = $(this);
            //备份opt中error和success方法
            var fn = {
                error:function(XMLHttpRequest, textStatus, errorThrown){},
                success:function(data, textStatus){}
            }
            if(opt.error){
                fn.error=opt.error;
            }
            if(opt.success){
                fn.success=opt.success;
            }
            //扩展增强处理
            var _opt = $.extend(opt,{
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    fn.error(XMLHttpRequest, textStatus, errorThrown);
                },
                success:function(data, textStatus){
                    fn.success(data, textStatus);
                },
                beforeSend:function(XHR,obj){
                    //提交前回调方法
                    console.log("增强post");
                    
                    XMLHttpRequest.setRequestHeader("token", app.utils.getToken());
                },
                complete:function(XHR, TS){
                    //请求完成后回调函数 (请求成功或失败之后均调用)。
                }
            });
            return _ajax(_opt);
        }
    });
})(jQuery);

