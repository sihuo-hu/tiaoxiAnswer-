layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax','upload'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var upload = layui.upload;
    var admin = layui.admin;

    /**
     * 试卷管理
     */
    var MarTopic = {
        tableId: "topicTable",
        condition: {
            id: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MarTopic.initColumn = function () {
        return [[
            {field: 'id', hide: true,title: '题目ID'},
            {field: 'qName', title: '活动'},
            {field: 'wName', title: '章节'},
            {field: 'title', title: '题目'},
            {field: 'options', title: '选项'},
            {field: 'answer', title: '答案'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 50}
        ]];
    };


    /**
     * 弹出添加题目对话框
     */
    MarTopic.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加题目',
            area:["400px","400px"],
            offset: '50px',
            content: Feng.ctxPath + '/topic/toAdd',
            end: function () {
                admin.getTempData('formOk') && table.reload(MarTopic.tableId);
            }
        });
    };

    /**
     * 弹出导入题目对话框
     */
    MarTopic.openImport = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '导入题目',
            area:["400px","300px"],
            offset: '50px',
            content: Feng.ctxPath + '/topic/toImport',
            end: function () {
                admin.getTempData('formOk') && table.reload(MarTopic.tableId);
            }
        });
    };

    /**
     * 弹出编辑题目对话框
     */
    MarTopic.onEditUser = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑题目',
            area:["400px","400px"],
            offset: '50px',
            content: Feng.ctxPath + '/topic/toEdit?id=' + data.id,
            end: function () {
                admin.getTempData('formOk') && table.reload(MarTopic.tableId);
            }
        });
    };



    /**
     * 下载模板
     */
    MarTopic.exportExcelUser = function () {
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,closeBtn: false
                ,area: '300px;'
                ,shade: 0.8
                ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                ,btn: ['知道了', '取消']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">注意事项：<br>填写方式请参考标题下第一行记录；<br><br>请不要删除标题及第一行记录；<br><br>导入数据从行号3开始扫描！<br><br></div>'
                ,success: function(layero){
                    var btn = layero.find('.layui-layer-btn');
                    btn.find('.layui-layer-btn0').attr({
                        href:Feng.ctxPath + "/topic/exportExcel"
                    });

                }
            });
    };

    /**
     * 点击删除用户按钮
     *
     * @param data 点击按钮时候的行数据
     */
    MarTopic.onDeleteUser = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/topic/delete", function () {
                table.reload(MarTopic.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除题目" + data.title + "?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MarTopic.tableId,
        url: Feng.ctxPath + '/topic/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MarTopic.initColumn()
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MarTopic.openAddUser();
    });

    // 导入按钮点击事件
    $('#btnImport').click(function () {
        MarTopic.openImport();
    });

    // 下载模板
    $('#btnDownload').click(function () {
        MarTopic.exportExcelUser();
    });

    // 工具条点击事件
    table.on('tool(' + MarTopic.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'delete') {
            MarTopic.onDeleteUser(data);
        }else if(layEvent === 'edit'){
            MarTopic.onEditUser(data);
        }

    });

});
