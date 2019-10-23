/**
 * 角色详情对话框
 */

layui.use(['layer', 'form', 'admin', 'ax','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var upload = layui.upload;

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

    /**
     * 导入
     */
    //指定允许上传的文件类型
    upload.render({
        elem: '#importFile'
        ,url: Feng.ctxPath +'/topic/readExcel'
        ,auto: false
        ,exts: 'xlsx' //普通文件
        ,bindAction: '#saveFile'
        ,data: {
            wId: function(){
                return $('#wIds').val();
            },
            qId: function(){
                return $('#qIds').val();
            }
        }
        ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            layer.load(); //上传loading
        }
        ,done: function(res){
            layer.closeAll('loading'); //关闭loading
            if(res.success){
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }else{
                layer.open({
                    title: '失败'
                    ,content: res.message
                });
            }
            console.log(res);
        }
        ,error: function(index, upload){
            layer.closeAll('loading'); //关闭loading
            layer.open({
                title: '异常'
                ,content: '请检查上传的文件是否符合规定'
            });
        }
    });

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