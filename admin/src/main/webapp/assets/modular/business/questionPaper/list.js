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
    var MarQuestionPaper = {
        tableId: "questionPaperTable",    //表格id
        condition: {
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MarQuestionPaper.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '活动ID'},
            {field: 'title', title: '名称'},
            {field: 'startTime', title: '开始时间'},
            {field: 'endTime', title: '结束时间'},
            {field: 'winner', title: '获胜方'},
            {field: 'winnerParticipants', title: '获胜方人数'},
            {field: 'participants', title: '总参与人数'},
            {field: 'awardCount', title: '获奖人数'},
            {field: 'awardUser', title: '获奖昵称'},
            {field: 'createTime', title: '创建日期'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 50}
        ]];
    };


    /**
     * 点击查询按钮
     */
    MarQuestionPaper.search = function () {
        var queryData = {};
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(MarQuestionPaper.tableId, {where: queryData});
    };

    /**
     * 弹出添加试卷对话框
     */
    MarQuestionPaper.openAddUser = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加活动',
            area:["560px","400px"],
            offset: '50px',
            content: Feng.ctxPath + '/questionPaper/to_add',
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
    MarQuestionPaper.onDeleteUser = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/questionPaper/delete", function () {
                table.reload(MarQuestionPaper.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除活动" + data.title + "?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MarQuestionPaper.tableId,
        url: Feng.ctxPath + '/questionPaper/list',
        page: true,
        height: "full-158",
        cellMinWidth: 80,
        cols: MarQuestionPaper.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MarQuestionPaper.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MarQuestionPaper.openAddUser();
    });

    // 工具条点击事件
    table.on('tool(' + MarQuestionPaper.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'delete') {
            MarQuestionPaper.onDeleteUser(data);
        }

    });

});
