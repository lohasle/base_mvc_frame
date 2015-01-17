require(["jquery", "lib/bootstrap.min", "lib/ace.min",
    "lib/respond.min","lib/jqGrid/jquery.jqGrid.min","lib/bootbox.min"],
    function ($) {
        "require:nomunge,exports:nomunge,module:nomunge";
        $(function () {
            $("#list").jqGrid({
                url: 'room/page.json',  //请求数据的url地址
                datatype: "json",  //请求的数据类型
                colNames: ['id', '房号', '状态', '价格', '创建时间'], //数据列名称（数组）
                colModel: [ //数据列各参数信息设置
                    {name: 'roomId', index: 'roomId', hidden: true, sortable: false},
                    {name: 'roomNo', index: 'roomNo', width: '25%', sorttype: "int"},
                    {name: 'roomState', index: 'roomState', width: '25%', sortable: false},
                    {name: 'roomTotalPrice', index: 'roomTotalPrice', sortable: false, width: '25%', formatter: 'integer', formatoptions: {thousandsSeparator: ','}},
                    {name: 'createTime', index: 'createTime', sortable: false, sorttype: "date", unformat: 'pickDate'}
                ],
                altRows: true,
                rowNum: 10, //每页显示记录数
                rowList: [10, 20, 30], //分页选项，可以下拉选择每页显示记录数
                pager: '#pager',  //表格数据关联的分页条，html元素
                autowidth: true, //自动匹配宽度
                width: "auto", //自动匹配宽度
                height: 'auto',   //设置高度
                gridview: false, //加速显示
                shrinkToFit:true,
                viewrecords: true,  //显示总记录数
                multiselect: true,  //可多选，出现多选框
                multiselectWidth: 25, //设置多选列宽度
                sortable: true,  //可以排序
                sortname: 'roomNo',  //排序字段名
                sortorder: "desc", //排序方式：倒序，本例中设置默认按id倒序排序
                loadComplete: function (data) { //完成服务器请求后，回调函数
                    if (data.records == 0) { //如果没有记录返回，追加提示信息，删除按钮不可用
                        $("p").appendTo($("#list")).addClass("nodata").html('找不到相关数据！');
                        $("#del_btn").attr("disabled", true);
                    } else { //否则，删除提示，删除按钮可用
                        $("p.nodata").remove();
                        $("#del_btn").removeAttr("disabled");
                    }
                }
            });


            $("#find_btn").click(function () {
                var title = $("#title").val();
                var sn = $("#sn").val();
                $("#list").jqGrid('setGridParam', {
                    url: "room/page.json",
                    postData: {'roomNo': sn}, //发送数据
                    page: 1
                }).trigger("reloadGrid"); //重新载入
            });

            $("body").on('click', '#add_btn',function () {
                //新增
                location.href = "room/add";
            }).on('click', '#edit_btn',function () {
                //修改
                var ids = $('#list').jqGrid('getGridParam', 'selarrrow');
                if (!ids || ids.length === 0) {
                    diyalert("请先选中");
                    return;
                }
                if (!ids || ids.length > 1) {
                    diyalert("亲，不能选中多项编辑哦");
                    return;
                }
                var rowData = $('#list').jqGrid('getRowData', ids[0]);
                location.href = "room/edit/" + rowData.roomId;
            }).on('click', '#del_btn', function () {
                var ids = $('#list').jqGrid('getGridParam', 'selarrrow');
                if (!ids || ids.length === 0) {
                    diyalert("请先选中");
                    return;
                }
                if (confirm("确定要删除吗?")) {
                    var roomids = [];
                    for (var i in ids) {
                        roomids.push($('#list').jqGrid('getRowData', ids[i]).roomId);
                    }
                    //删除数据
                    $.ajax({
                        url: "room/delete",
                        type: "post",
                        dataType: "json",
                        data: {"ids": roomids.join(",")},
                        success: function (rep) {
                            //刷新
                            $("#list").jqGrid('setGridParam', {
                                url: "room/page.json",
                                page: 1
                            }).trigger("reloadGrid"); //重新载入
                        }
                    });
                }
            });
        });
    });


function diyalert(msg) {
    bootbox.dialog({
        message: msg,
        buttons: {
            "success": {
                "label": "确定",
                "className": "btn-sm btn-primary"
            }
        }
    });
}


