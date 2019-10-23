/**
 * 角色详情对话框
 */

layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    var ajax = new $ax(Feng.ctxPath + "/questionPaper/all");
    var result = ajax.start();
    console.log(result);
    $.each(result, function(index,item) {
        $('#qIds').append('<option value="'+item.id+'">'+item.title+'</option>');
    });
    form.render('select','huodong');


    form.on('select(huodongSelect)', function(data){
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象
        var ajax = new $ax(Feng.ctxPath + "/writings/getQId", function (data) {

        }, function (data) {
            Feng.error("获取失败！" + data.responseJSON.message)
        });
        ajax.set("qId",data.value);
        var wIds = ajax.start();
        $.each(wIds, function(index,item) {
            $('#wIds').append('<option value="'+item.id+'">'+item.wName+'</option>');
        });
        form.render('select','zhangjie');
    });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/topic/add", function (data) {
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