layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;

    /**
     * 试卷管理
     */
    var WritingsPaper = {
        tableId: "writingsTable"    //表格id
    };

    /**
     * 初始化表格的列
     */
    WritingsPaper.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '章节ID'},
            {field: 'title', title: '活动名称'},
            {field: 'wName', title: '章节名称'},
            {field: 'userCount', title: '需要答对人数'},
            {field: 'sortNumber', title: '排序号'},
            {field: 'createTime', title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 50}
        ]];
    };


    /**
     * 弹出添加试卷对话框
     */
    WritingsPaper.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加章节',
            area:["400px","300px"],
            offset: '50px',
            content: Feng.ctxPath + '/writings/to_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(MarQuestionPaper.tableId);
            }
        });
    };

    /**
     * 点击删除用户按钮
     *
     * @param data 点击按钮时候的行数据
     */
    WritingsPaper.onDeleteUser = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/writings/delete", function () {
                table.reload(WritingsPaper.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除章节" + data.wName + "?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + WritingsPaper.tableId,
        url: Feng.ctxPath + '/writings/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: WritingsPaper.initColumn()
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        WritingsPaper.openAddUser();
    });

    // 工具条点击事件
    table.on('tool(' + WritingsPaper.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'delete') {
            WritingsPaper.onDeleteUser(data);
        }

    });

});
