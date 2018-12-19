/*!赛事管理  2018年8月27日01:05:05*/
var route = "/management";
var service;
var tableIndex;
(function () {
    service = initService(route);
    // 加载数据表
    initDataTable(route+"/schedule/getScheduleLimit", function (form, table, layer, vipTable, tableIns) {

    }, function (table, res, curr, count) {
        // 监听工具条
        table.on('tool(my-data-table)', function(obj){
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if (layEvent=='add'){
                service.add({

                }, function(html){
                    layer.open({
                        type: 1,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['420px', 'auto'], //宽高
                        shadeClose: true,
                        content:html
                    });
                });
            }else if (layEvent=='edit'){
                service.edit({
                    scheduleId: data.scheduleId
                }, function(html){
                    layer.open({
                        type: 1,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['auto', 'auto'], //宽高
                        shadeClose: true,
                        content:html
                    });
                });

            }else if (layEvent == 'team'){
                $.ajax({
                    url: "/management/schedule/queryTeam?teamId=" +data.teamId,
                    type: "get",
                    processData: false,
                    contentType: false,
                    cache: false,
                    success: function (data) {
                        if (data.code == 200) {
                            layer.msg(data.msg,{
                                time:20000,
                                btn:['知道了']
                            });
                        }else {
                            layer.msg("失败了~~~");
                        }
                    }
                });
            }else if (layEvent == 'delete'){
                layer.confirm('真的删除吗？', function (index) {
                    $.ajax({
                        url: "/management/schedule/deleteSchedule?scheduleId=" +data.scheduleId,
                        type: "get",
                        processData: false,
                        contentType: false,
                        cache: false,
                        success: function (data) {
                            if (data.code == 200) {
                                location.reload();
                            }else {
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
        add: function (param, callback) {
            $.get(route+"/schedule/add", param, function (data) {
                callback(data);
            });
        },
        edit:function (param,callback) {
            $.get(route+"/schedule/edit", param, function (data) {
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
        , {field: 'scheduleId', title: '赛程ID', width: 180, sort: true}
        , {field: 'gameName', title: '赛事', width: 180}
        , {field: 'teamId', title: '球队', width: 180,templet:function (d) {
                var html = "";
                html += '<a name="check"  class="layui-btn layui-btn layui-btn-xs" lay-event="team">查看球队</a>';
                return html;
        }}
        , {field: 'gameDate', title: '开始时间', width: 180,templet:function (d) {
             return d.gameDate == null ? '' : utils.date.timestampConvert(d.gameDate);
        }}
        , {field: 'gameDuration', title: '比赛时长', width: 150 }
        , {field: 'status', title: '状态', width: 150, sort: true, templet: function (d) {
                return d.status == null ? '' : utils.scheduleStatus.scheduleStatusInfo(d.status);
        }}
        , {field: 'scheduleResult', title: '比赛结果', width: 150, templet: function (d) {
                if (d.scheduleResult==null||d.scheduleResult.length==0){
                    return '——'
                }
                return d.scheduleResult;
    }}
        , {field: 'scheduleGrade', title: '比赛成绩', width: 150, templet: function (d) {
                if (d.scheduleGrade==null||d.scheduleGrade.length==0){
                    return '——'
                }
                return d.scheduleGrade;
            }}
        , {field: 'teamName', title: '胜利方', width: 150, templet: function (d) {
                if (d.teamName==null||d.teamName.length==0){
                    return '——'
                }
                return d.teamName;
            }}
        , {fixed: 'right',title: '操作', width: 200, align: 'center', templet: function(d){
                var html = "";
                html += '<a name="delete"  class="layui-btn layui-btn layui-btn-xs" lay-event="delete">删除</a>';
                html += '<a name="edit"  class="layui-btn layui-btn layui-btn-xs" lay-event="edit">编辑</a>';
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
