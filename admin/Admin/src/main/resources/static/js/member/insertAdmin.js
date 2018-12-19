$(function () {
    register();

});
    function register() {

        $("#submit").click(function () {
            var phone = $("#phone").val();
            var password = $("#password").val();
            var rpassword = $("#rpassword").val();

            if (phone==""||phone==null) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('手机号不能为空', {icon: 5,time: 2000});
                });
                return false
            }
            if (password==""||password==null) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('密码不能为空', {icon: 5,time: 2000});
                });
                return false
            }

            if (password!=rpassword) {
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('两次密码不一致', {icon: 5,time: 2000});
                });
                return false
            }

            var type=$("input[type='radio']:checked").val()
            var formData = new FormData();

            formData.append("phone",phone);
            formData.append("password",password);
            formData.append("type",type);

                    $.ajax({
                        url:"/management/admin/insertAdmin ",
                        type:"POST",
                        data:formData,
                        processData:false,
                        contentType :false,
                        cache:false,
                        success:function (data) {
                            if (data.msg=="添加成功"){
                                layer.msg("添加成功");
                            } else if (data.msg=="该账号已经存在") {
                                layer.msg("该管理员已经被注册过了");
                            }else {
                                layer.msg("添加失败！");
                            }
                        }
                    });
        });
    };
