
    function  disableAdmin() {
        $("a[name='delete']").click(function () {
            var that = $(this);
            layer.confirm('真的删除吗？',function (index) {
                var userid = $(that).data("userid");

                $.ajax({
                    url:"/users/deleteAdmin?userid="+userid,
                    type:"get",
                    processData:false,
                    contentType:false,
                    cache:false,
                    success:function (data) {
                        if (data.code==200){
                            layer.msg("禁用成功");
                            window.location.reload();
                        } else {
                            layer.msg("不好意思，失败了");
                        }
                    }
                });
            })
        })

    }

