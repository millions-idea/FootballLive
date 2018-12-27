/*!财务模块-会计账簿  2018年8月27日01:05:05*/
var route = "/management/chatRoomUser";
var service;
var tableIndex;
(function () {
    service = initService(route);

    // 加载数据表
    initDataTable(route + "/getchatRoomLimit", function (form, table, layer, vipTable, tableIns) {

    }, function (table, res, curr, count) {
        //预览图片
        $(".gameIcon").click(function () {
            var photo = {
                title: $(this).attr("data-nick"),
                id: $(this).attr("data-id"),
                start: 0,
                data: [{
                    alt: $(this).attr("data-nick"),
                    pid: $(this).attr("data-id"),
                    src: $(this).attr("src"),
                    thumb: $(this).attr("src")
                }]
            };
            layer.photos({
                photos: photo
                ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        })
        // 监听工具条
        table.on('tool(my-data-table)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'delete'){ //删除
                layer.confirm('您确定要在此直播间拉黑这个用户吗？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    data.isEnable = 0;
                    data.isDelete = 1;
                    service.delete(data, function (data) {
                        if(utils.response.isErrorByCode(data)) return layer.msg("操作失败");
                        if(utils.response.isException(data)) return layer.msg(data.msg);
                        tableIndex.reload();
                        layer.msg("操作成功");
                    })
                });
            }
        });
    });
})()

/**
 * 加载模块
 * @param r
 * @returns
 */
function initService(r) {
    return {
        /**
         * 编辑用户 DF 2018年12月16日19:22:24
         * @param param
         * @param callback
         */
        edit: function (param, callback) {
            $.get(r + "/edit", param, function (data) {
                callback(data);
            });
        },
        delete: function (param, callback) {
            $.get(r + "/shieldingUser", param, function (data) {
                callback(data);
            });
        },
        details: function (param, callback) {
            $.get(r + "/getLiveDetailByLiveId", param, function (data) {
                callback(data);
            });
        },
        add:function (param, callback) {
            $.get(r + "/add", param, function (data) {
                callback(data);
            });
        }
    }
}

/**
 * 加载数据表
 * @param url
 * @param callback
 * @param loadDone
 */
function initDataTable(url, callback, loadDone) {
    var $queryButton = $("#my-data-table-query"),
        $queryCondition = $("#my-data-table-condition"),
        $tradeTypeInput = $("select[name='trade_type']"),
        $tradeDateBeginInput = $("input[name='trade_date_begin']"),
        $tradeDateEndInput = $("input[name='trade_date_end']");

    var cols = getTableColumns();
    function GetQueryString(name)

    {

        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");

        var r = window.location.search.substr(1).match(reg);

        if(r!=null)return  unescape(r[2]); return null;

    }
    var roomId = GetQueryString("roomId");
    url+='?roomId='+roomId+'&';
    // 注册查询事件
    $queryButton.click(function () {
        $queryButton.attr("disabled",true);
        var condition = $queryCondition.val();
        if(condition.indexOf("+") != -1) condition = condition.replace("+", "[add]");
        if(condition.indexOf("-") != -1) condition = condition.replace("-", "[reduce]");
        var param =  "?condition=" + encodeURI(condition);
        /*param += "&state=" + $tradeTypeInput.val();*/
        param += "&beginTime=" + $tradeDateBeginInput.val();
        param += "&endTime=" + $tradeDateEndInput.val();

        loadTable(tableIndex,"my-data-table", "#my-data-table", cols, url + param, function (res, curr, count) {
            $queryButton.removeAttr("disabled");
        });
    })

    layui.use(['table', 'form', 'layer', 'vip_table', 'layedit', 'tree','element'], function () {
        // 操作对象
        var form = layui.form
            , table = layui.table
            , layer = layui.layer
            , vipTable = layui.vip_table
            , $ = layui.jquery
            , layedit = layui.layedit
            , element = layui.element;
        
        // 表格渲染
        tableIndex = table.render({
            elem: '#my-data-table'                  //指定原始表格元素选择器（推荐id选择器）
            , height: 720    //容器高度
            , cols: cols
            , id: 'my-data-table'
            , url: url
            , method: 'get'
            , page: true
            , limits: [30, 60, 90, 150, 300]
            , limit: 30 //默认采用30
            , loading: true
            , even: true
            , done: function (res, curr, count) {
                loadDone(table, res, curr, count);
            }
        });

        // 刷新
        $('#btn-refresh-my-data-table').on('click', function () {
            tableIndex.reload();
        });

        // you code ...
        callback(form, table, layer, vipTable, tableIndex);
    });
}

/**
 * 获取表格列属性
 * @returns {*[]}
 */
function getTableColumns() {
    return [[
        {type: "numbers", fixed: 'left'}
        , {field: 'userId', title: 'ID', width: 80, sort: true}
        , {field: 'nickName', title: '用户名', width: 150}
        , {field: 'photo', title: '用户头像', width: 180, templet: function(d){
                var part = 'data-id="' + d.photo + '" data-nick="' + d.nickName + '"';
                return '<img ' + part + '  width="27px" class="gameIcon" src="' + d.photo + '"/>';
            }}
        , {field: 'signature', title: '签名', width: 150}
        , {fixed: 'right',title: '操作', width: 160, align: 'center', templet: function(d){
                var html = "";
                html+='<a name="item-edit" class="layui-btn layui-btn layui-btn-xs layui-btn-primary" lay-event="delete">拉黑</a>';
                return html;
            }}
    ]];
}

/**
 * 加载表格数据
 * @param tableIns
 * @param id
 * @param elem
 * @param cols
 * @param url
 * @param loadDone
 */
function loadTable(index,id,elem,cols,url,loadDone) {
    index.reload({
        elem: elem
        , height: 720    //容器高度
        , cols: cols
        , id: id
        , url: url
        , method: 'get'
        , page: true
        , limits: [30, 60, 90, 150, 300]
        , limit: 30 //默认采用30
        , loading: true
        , even: true
        , done: function (res, curr, count) {
            resetPager();
            loadDone(res, curr, count);
        }
    });
}

function resetPager() {
    $(".layui-table-body.layui-table-main").each(function (i, o) {
        $(o).height(640);
    });
}
