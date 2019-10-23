/**
 * 角色详情对话框
 */
var RoleInfoDlg = {
    data: {
        pid: "",
        pName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    var ajax = new $ax(Feng.ctxPath + "/questionPaper/all");
    var qIds = ajax.start();
    console.log(qIds);
    $.each(qIds, function(index,item) {
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

    //初始化角色的详情数据
    var ajax = new $ax(Feng.ctxPath + "/topic/get?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('topicForm',result.data);

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/topic/edit", function (data) {
            Feng.success("修改成功!");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
    });
});