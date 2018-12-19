$(function () {

    $("#reset").click(function () {
        $("#phone").val("");
        $("#password").val("");
        $("#newpwd").val("");
        $("#affirmpwd").val("");
    })
    $("#subpwd").click(function () {
        var phone=$.trim($("#phone").val());
        var oldpassword=$.trim($("#password").val());
        var newpassword=$.trim($("#newpwd").val());
        var rpassword=$.trim($("#affirmpwd").val());


        if (phone==""||phone==null) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg('手机号不能为空', {icon: 5,time: 2000});
            });
            return false
        }
        if (oldpassword==""||oldpassword==null) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg('密码不能为空', {icon: 5,time: 2000});
            });
            return false
        }
        if (newpassword==""||newpassword==null||rpassword==""||rpassword==null) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg('请输入新密码', {icon: 5,time: 2000});
            });
            return false
        }
        if (newpassword!=rpassword) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg('密码不一致', {icon: 5,time: 2000});
            });
            return false
        }
        var formData=new FormData();
        formData.append("phone",phone);
        formData.append("password",oldpassword);
        formData.append("newpwd",newpassword);

        $.ajax({
            url:"/management/admin/updateuserpwd",
            data:formData,
            type:"post",
            processData:false,
            contentType:false,
            cache:false,
            success:function (data) {
                if (data.code==200){
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.msg('修改成功', {icon: 6,time: 2000});
                    });
                } else {
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.msg('账号或密码错误', {icon: 5,time: 2000});
                    });
                }
            }
        })

    })
})


