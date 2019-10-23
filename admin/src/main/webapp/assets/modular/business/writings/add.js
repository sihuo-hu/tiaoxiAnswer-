/**
 * 用户详情对话框
 */
layui.use(['layer', 'form', 'admin', 'laydate', 'ax'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;


    var ajax = new $ax(Feng.ctxPath + "/questionPaper/all");
    var result = ajax.start();
    console.log(result);
    $.each(result, function(index,item) {
        $('#qids').append('<option value="'+item.id+'">'+item.title+'</option>');
    });
    form.render('select');

// 让当前iframe弹层高度适应
    admin.iframeAuto();


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/writings/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });


});