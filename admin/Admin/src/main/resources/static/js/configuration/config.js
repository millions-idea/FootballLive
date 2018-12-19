
layui.use('upload', function () {
    var $ = layui.jquery
        , upload = layui.upload;
    //banner1图片上传
    var uploadInst = upload.render({
        elem: '#banner1'
        , url: '/management/file/cosUpload'
        , before: function (obj) {
            obj.preview(function (index, file, result) {

                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            $("#demo1").val(res.msg);
            var value=$("#demo1").val();
            uploadConfig("banner.image1",value);
        }
    });

//banner2图片上传
    var uploadInst = upload.render({
        elem: '#banner2'
        , url: '/management/file/cosUpload'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo2').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            $("#demo2").val(res.msg);
            var value=$("#demo2").val();
            uploadConfig("banner.image2",value);
        }
    });

//banner3图片上传
    var uploadInst = upload.render({
        elem: '#banner3'
        , url: '/management/file/cosUpload'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo3').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            $("#demo3").val(res.msg);
            var value=$("#demo3").val();
            uploadConfig("banner.image3",value);
        }
    });
//APP启动图片1上传
    var uploadInst = upload.render({
        elem: '#app1'
        , url: '/management/file/cosUpload'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#appone').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            $("#appone").val(res.msg);
            var value=$("#appone").val();
            uploadConfig("bootstrap.image1",value);
        }
    });

//APP启动图片2上传
    var uploadInst = upload.render({
        elem: '#app2'
        , url: '/management/file/cosUpload'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#apptwo').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            $("#apptwo").val(res.msg);
            var value=$("#apptwo").val();
            uploadConfig("bootstrap.image2",value);
        }
    });

//APP启动图片3上传
    var uploadInst = upload.render({
        elem: '#app3'
        , url: '/management/file/cosUpload'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#appthree').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            $("#appthree").val(res.msg);
            var value=$("#appthree").val();
            uploadConfig("bootstrap.image3",value);
        }
    });

})
