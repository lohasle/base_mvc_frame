$(function () {
    $("#list").jqGrid({
        url:'page.json',  //请求数据的url地址
        datatype: "json",  //请求的数据类型
        colNames:['id','房号','状态','价格','创建时间'], //数据列名称（数组）
        colModel:[ //数据列各参数信息设置
            {name:'roomId',index:'roomId',hidden:true},
            {name:'roomNo',index:'roomNo', width:60, sorttype:"int"},
            {name:'roomState',index:'roomState',width:90},
            {name:'roomTotalPrice',index:'roomTotalPrice', width:150,formatter:'integer',formatoptions:{thousandsSeparator:','}},
            {name:'createTime',index:'createTime', width:150,sorttype:"date",unformat: 'pickDate'}
        ],
        altRows: true,
        rowNum:10, //每页显示记录数
        rowList:[10,20,30], //分页选项，可以下拉选择每页显示记录数
        pager: '#pager',  //表格数据关联的分页条，html元素
        autowidth: true, //自动匹配宽度
        height:'auto',   //设置高度
        gridview:true, //加速显示
        viewrecords: true,  //显示总记录数
        multiselect: true,  //可多选，出现多选框
        multiselectWidth: 25, //设置多选列宽度
        sortable:true,  //可以排序
        sortname: 'roomNo',  //排序字段名
        sortorder: "desc", //排序方式：倒序，本例中设置默认按id倒序排序
        loadComplete:function(data){ //完成服务器请求后，回调函数
            if(data.records==0){ //如果没有记录返回，追加提示信息，删除按钮不可用
                $("p").appendTo($("#list")).addClass("nodata").html('找不到相关数据！');
                $("#del_btn").attr("disabled",true);
            }else{ //否则，删除提示，删除按钮可用
                $("p.nodata").remove();
                $("#del_btn").removeAttr("disabled");
            }
        }
    });



    $("#find_btn").click(function(){
        var title = $("#title").val();
        var sn = $("#sn").val();
        $("#list").jqGrid('setGridParam',{
            url:"page.json",
            postData:{'roomNo':sn}, //发送数据
            page:1
        }).trigger("reloadGrid"); //重新载入
    });

    $("body").on('click','#add_btn',function(){
        //新增
        location.href = "add";
    }).on('click','#edit_btn',function(){
        //修改
        var ids=$('#list').jqGrid('getGridParam','selarrrow');
        if(!ids||ids.length===0){
            alert("请先选中");
            return;
        }
        if(!ids||ids.length>1){
            alert("亲，不能选中多项编辑哦");
            return;
        }
        var rowData = $('#list').jqGrid('getRowData',ids[0]);
        location.href = "edit/"+rowData.roomId;
    }).on('click','#del_btn',function(){
        var ids=$('#list').jqGrid('getGridParam','selarrrow');
        if(!ids||ids.length===0){
            alert("请先选中");
            return;
        }
        //删除数据
        $.ajax({
            url:""
        });

        //刷新表格
    });


});
