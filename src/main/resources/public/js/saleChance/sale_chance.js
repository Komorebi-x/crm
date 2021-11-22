layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    /*数据表格的渲染*/

    /**
     * 营销机会列表展示
     */
    var  tableIns = table.render({
        elem: '#saleChanceList', // 表格绑定的ID
        url : ctx + '/sale_chance/list', // 访问数据的地址
        cellMinWidth : 95,
        page : true, // 开启分页
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "saleChanceListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'chanceSource', title: '机会来源',align:"center"},
            {field: 'customerName', title: '客户名称',  align:'center'},
            {field: 'cgjl', title: '成功几率', align:'center'},
            {field: 'overview', title: '概要', align:'center'},
            {field: 'linkMan', title: '联系人',  align:'center'},
            {field: 'linkPhone', title: '联系电话', align:'center'},
            {field: 'description', title: '描述', align:'center'},
            {field: 'createMan', title: '创建人', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'uname', title: '分配人', align:'center'},
            {field: 'assignTime', title: '分配时间', align:'center'},
            {field: 'state', title: '分配状态', align:'center',templet: function(d){
                    return formatterState(d.state);
                }},
            {field: 'devResult', title: '开发状态', align:'center',templet:function (d){
                    return formatterDevResult(d.devResult);
                }},
            {title: '操作', templet:'#saleChanceListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });


    /**
     * 格式化分配状态
     *  0 - 未分配
     *  1 - 已分配
     *  其他 - 未知
     * @param state
     * @returns {string}
     */
    function formatterState(state){
        if(state==0) {
            return "<div style='color: yellow'>未分配</div>";
        } else if(state==1) {
            return "<div style='color: green'>已分配</div>";
        } else {
            return "<div style='color: red'>未知</div>";
        }
    }

    /**
     * 格式化开发状态
     *  0 - 未开发
     *  1 - 开发中
     *  2 - 开发成功
     *  3 - 开发失败
     * @param value
     * @returns {string}
     */
    function formatterDevResult(value){
        if(value == 0) {
            return "<div style='color: yellow'>未开发</div>";
        } else if(value==1) {
            return "<div style='color: #00FF00;'>开发中</div>";
        } else if(value==2) {
            return "<div style='color: #00B83F'>开发成功</div>";
        } else if(value==3) {
            return "<div style='color: red'>开发失败</div>";
        } else {
            return "<div style='color: #af0000'>未知</div>"
        }
    }


    /*绑定搜索功能*/
    $(".search_btn").click(function (){
        //这里以搜索为例
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                customerName: $("input[name='customerName']").val(),
                creatMan: $("input[name='createMan']").val(),
                state:$("#state").val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });

        // table.reload("#saleChanceListTable",{
        //     where: { //设定异步数据接口的额外参数，任意设
        //         customerName: $("input[name='customerName']").val(),
        //         creatMan: $("input[name='createMan']").val(),
        //         state:$("#state").val()
        //     }
        //     ,page: {
        //         curr: 1 //重新从第 1 页开始
        //     }
        // })
    });

    /*绑定头部工具栏*/
    /**
     * 监听头部工具栏事件
     *   格式：table.on("toolbar(saleChances)",function (obj){})
     */
    table.on("toolbar(saleChances)",function (obj){
        //获取选中对象状态
        var checkStatus = table.checkStatus(obj.config.id);

        //判断对应的事件类型
        if(obj.event ==='add'){
            //alert("添加");
            openAddOrUpdateSaleChanceDialog();
        }else if(obj.event ==='del'){
            //alert("dels");选中数据对象
            deleteSaleChances(checkStatus.data);
        }

    });


    function  deleteSaleChances(data){
        //前端验证
        if(data.length==0){
            layer.msg("请选中数据啊?");
            return ;
        }
        //提示确定要是删除
        layer.confirm("你确定要删除这些数据吗?",{
            btn:["确认","取消"],
        },function (index){
            //关闭确认框
            layer.close(index);
            //收集收集
            var ids=[];
            //循环遍历
            for(var x in data){
               ids.push(data[x].id);
            }
            //发送ajax【1,2,3】
            $.ajax({
                type:"post",
                data:{"ids":ids.toString()},
                url:ctx+"/sale_chance/delete",
                dataType:"json",
                success:function(result){
                    //判断
                    if(result.code==200){
                        //刷新页面
                        tableIns.reload();
                    }else{
                        //提示一下
                        layer.msg(result.msg,{icon:5 });
                    }
                }

            });
        });

    }

    //定义函数
    function openAddOrUpdateSaleChanceDialog(id){
        var title="<h2>营销机会-添加</h2>";
        var url=ctx+"/sale_chance/addOrUpdateDialog";
        //判断是否有Id
        if(id){
            var title="<h2>营销机会-更新</h2>";
            url=url+"?id="+id;
        }
        console.log(url);
        //添加弹出层
        layer.layer.open({
            title:title,
            type: 2,//iframe
            area:["500px","620px"],
            //可以最大化最小化
            maxmin:true,
            content: url
        });
    }


    /*绑定行内工具栏*/
    table.on("tool(saleChances)",function (obj){
        //获取当前行对象
        var data=obj.data;

        if(obj.event==='edit'){
            //alert("编辑");
            openAddOrUpdateSaleChanceDialog(data.id);
            return ;
        }else if(obj.event ==="del"){
            //alert("删除one");
            //提示确定要是删除
            layer.confirm("你确定要删除这些数据吗?",{
                btn:["确认","取消"],
            },function (index){
                //关闭确认框
                layer.close(index);
                //发送ajax【1,2,3】
                $.ajax({
                    type:"post",
                    data:{"ids":data.id},
                    url:ctx+"/sale_chance/delete",
                    dataType:"json",
                    success:function(result){
                        //判断
                        if(result.code==200){
                            //刷新页面
                            tableIns.reload();
                        }else{
                            //提示一下
                            layer.msg(result.msg,{icon:5 });
                        }
                    }

                });
            });

        }
    });
});