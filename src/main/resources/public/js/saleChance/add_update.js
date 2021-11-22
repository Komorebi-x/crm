layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /*添加*/
    /**
     * 监听表单submit事件
     */
    form.on("submit(addOrUpdateSaleChance)",function (obj){
        /*数据加载层*/
        var index = layer.msg("数据提交中,请稍后...",{
            icon:16, // 图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });
        var url=ctx+"/sale_chance/save";

        //默认是添加操作，判断是否有隐藏域，来决定是否做修改操作
        if($("input[name=id]").val()){

            url=ctx+"/sale_chance/update"
        }
        /*发送ajax添加数据 */
        $.post(url,obj.field,function(data){
            //判断
            if(data.code==200){
                //成功提示信息
                layer.msg("操作成功了");
                //关闭加载层
                layer.close(index);
                //关闭所有的弹出层
                layer.closeAll("iframe");
                //加载页面信息
                window.parent.location.reload();
            }else{
                //提示信息
                layer.msg(data.msg);
            }
        },"json");
        //取消默认跳转（阻止表单提交）
        return false;
    });

    /*取消*/
    $("#closeBtn").click(function (){
        //关闭弹出层
        //假设这是iframe页
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    /*发送ajax填充分配人*/

    $.ajax({
        type:"post",
        url:ctx+"/user/sales",
        dataType:"json",
        success:function (data){
            //指派人ID
            var assignMan=$("#man").val();
            alert(assignMan);
            //循环遍历
            for(var x in data){
                //追加option
                if(assignMan==data[x].id){
                    $("#assignMan").append("<option selected value='"+data[x].id+"'>"+data[x].uname+"</option>");
                }else{
                    $("#assignMan").append("<option  value='"+data[x].id+"'>"+data[x].uname+"</option>");
                }
            }
            //重新渲染下拉框
            layui.form.render("select");
        }
    });

});