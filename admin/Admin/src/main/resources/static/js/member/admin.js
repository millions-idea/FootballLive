/*!用户管理-用户列表  2018年8月27日01:05:05*/
var route = "/management/admin";
var service;
var tableIndex;
(function () {
    service = initService(route);
    // 加载数据表
    initDataTable(route + "/getAdminLimit", function (form, table, layer, vipTable, tableIns) {

    }, function (table, res, curr, count) {
        // 监听工具条
        table.on('tool(my-data-table)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent == 'edit'){ //重置密码
                service.edit({
                    userId: data.userId
                }, function(html){
                    layer.open({
                        type: 1,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['420px', 'auto'], //宽高
                        shadeClose: true,
                        content:html
                    });
                });
            }else if (layEvent == 'delete'){
                layer.confirm('真的删除吗？', function (index) {
                    $.ajax({
                        url: "/management/admin/deleteAdmin?userId=" +data.userId,
                        type: "get",
                        processData: false,
                        contentType: false,
                        cache: false,
                        success: function (data) {
                            if (data.code == 200) {
                                layer.msg("删除成功");
                                window.location.reload();
                            } else {
                                layer.msg("删除失败");
                            }
                        }
                    });
                })
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
        , {field: 'phone', title: '手机号', width: 150}
        , {field: 'type', title: '角色', width: 180, sort: true, templet: function (d) {
                var roleName = "";
                switch (d.type) {
                    case 1:
                        roleName = "<span style='color: #000000;'>管理员</span>";
                        break;
                    case 2:
                        roleName = "<span style='color: #ff0000;'>超级管理员</span>";
                        break;
                    default:
                        roleName = d.type;
                        break;
                }
                return roleName;
            }}
        , {field: 'status', title: '状态', width: 180, sort: true, templet: function (d) {
                var status = "";
                switch (d.status) {
                    case 0:
                        status = "<span style='color: #000000;'>正常</span>";
                        break;
                    case 1:
                        status = "<span style='color: #BEBEBE;'>禁用</span>";
                        break;
                    default:
                        status = d.status;
                        break;
                }
                return status;
            }}
        , {field: 'addDate', title: '注册时间', width: 180, sort: true, templet: function (d) {
                return d.addDate == null ? '' : utils.date.timestampConvert(d.addDate);
            }}
        , {field: 'editDate', title: '更新时间', width: 180, sort: true, templet: function (d) {
                return d.editDate == null ? '' : utils.date.timestampConvert(d.editDate);
            }}
        , {fixed: 'right',title: '操作', width: 150, align: 'center', templet: function(d){
                var html = "";
                html += '<a name="item-edit" class="layui-btn layui-btn layui-btn-xs" lay-event="edit">编辑</a>';

                if ($("#selecttype").val()==2){
                    html += '<a name="delete"  class="layui-btn layui-btn layui-btn-xs" lay-event="delete">删除</a>';
                }
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
