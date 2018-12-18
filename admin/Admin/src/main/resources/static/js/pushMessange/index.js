layui.use('form', function(){
    var form = layui.form;

    //监听提交
    form.on('submit(formDemo)', function(data){
        var publishMessageDetail = {};
        publishMessageDetail.message = $.trim($("#content").val());
        if(publishMessageDetail.message==null||publishMessageDetail.message==''){
            layer.msg("请输入推送内容");
            return false;
        }
        var typeResult = $('input[name="type"]:checked').val();
        if(typeResult==0){
            publishMessageDetail.type=true;
        }else {
            publishMessageDetail.type=false;
        }
        var all = $('input[name="type"]:checked').val();
        publishMessageDetail.phone = $.trim($("#phone").val());
        if((publishMessageDetail.phone==null||publishMessageDetail.phone=='')&&(all==true)){
            layer.msg("手机号或者全部必填一个");
            return false;
        }
        if((publishMessageDetail.phone!=null&&publishMessageDetail.phone!='')&&(all!=true)){
            layer.msg("手机号只能全部选一个");
            return false;
        }

        $.ajax({
            url:"/management/publishMessage/pushMessage",    //请求的url地址
            dataType:"json",   //返回格式为json
            data: publishMessageDetail,    //参数值
            type:"post",   //请求方式
            success:function(data){
                //请求成功时处理
                if(data == null || data.code == null || data.code != 200) {
                    if(data.code == 300){
                        layer.msg(data.msg, {
                            icon: 1,
                            time: 3000,
                            end: function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            }
                        });
                        return;
                    }else{
                        layer.msg("发送失败", {
                            icon: 1,
                            time: 3000,
                            end: function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            }
                        });
                        return false;
                    }
                }
                layer.msg("发送成功", {
                    icon: 1,
                    time: 3000,
                    end: function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                });
                return;
            }
        });
    });
});