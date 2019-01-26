
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
 //滚动图1地址上传
    $("#btnUrl1").click(function () {
        var bannerUrl1=$("#bannerUrl1").val();
        /*if (bannerUrl1==""||bannerUrl1.length==0){
            layer.msg("地址不能为空");
            return false;
        }*/
        uploadConfig("banner.image1.targetUrl",bannerUrl1);
    })
    //滚动图2地址上传
    $("#btnUrl2").click(function () {
        var bannerUrl2=$("#bannerUrl2").val();
        /*if (bannerUrl2==""||bannerUrl2.length==0){
            layer.msg("地址不能为空");
            return false;
        }*/
        uploadConfig("banner.image2.targetUrl",bannerUrl2);
    })
    //滚动图3地址上传
    $("#btnUrl3").click(function () {
        var bannerUrl3=$("#bannerUrl3").val();
        /*if (bannerUrl3==""||bannerUrl3.length==0){
            layer.msg("地址不能为空");
            return false;
        }*/
        uploadConfig("banner.image3.targetUrl",bannerUrl3);
    })
    //全站广告图
    var uploadInst = upload.render({
        elem: '#total'
        , url: '/management/file/cosUpload'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#station').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            $("#station").val(res.msg);
            var value=$("#station").val();
            uploadConfig("webapp.image.ad.url",value);
        }
    });
    //全站广告图链接
    $("#totalUrl").click(function () {
        var stationUrl=$("#stationUrl").val();
        if (stationUrl==""||stationUrl.length==0){
            layer.msg("地址不能为空");
            return false;
        }
        uploadConfig("webapp.image.ad.target.url",stationUrl);
    })


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
 //修改公告管理
    $("#title").click(function () {
        var announcement=$("#announcement").val();
        if (announcement==""||announcement.length==0){
            layer.msg("公告不能为空");
            return false;
        }
        uploadConfig("home.text.ad",announcement);
    })
   //修改版本信息
    $("#ver").click(function () {
        var version=$("#version").val();
        var ios=$("#ios").val();
        var android=$("#android").val();

        if (version==""||version.length==0){
            layer.msg("版本不能为空");
            return false;
        }else if (ios==""||ios.length==0){
            layer.msg("ios不能为空");
            return false;
        }else if (android==""||android.length==0){
            layer.msg("android不能为空");
            return false;
        }
        uploadConfig("version",version);
        uploadConfig("iosDownload",ios);
        uploadConfig("androidDownload",android);
    })


    $("#saveWebsite").click(function () {
        var title=$("#website-title").val();
        var keywords=$("#website-keywords").val();
        var description=$("#website-description").val();
        var links=$("#website-links").val();
        var copyright=$("#website-copyright").val();

        if (title==""||title.length==0){
            layer.msg("网站标题不能为空");
            return false;
        }else if (keywords==""||keywords.length==0){
            layer.msg("网站关键词不能为空");
            return false;
        }else if (description==""||description.length==0){
            layer.msg("网站描述不能为空");
            return false;
        }else if (links==""||links.length==0){
            layer.msg("友情链接不能为空");
            return false;
        }else if (copyright==""||copyright.length==0){
            layer.msg("底部版权不能为空");
            return false;
        }
        uploadConfig("pc.header.title",title);
        uploadConfig("pc.header.keywords",keywords);
        uploadConfig("pc.header.description",description);
        uploadConfig("pc.footer.html",links);
        uploadConfig("pc.footer.about",copyright);
    })
})
