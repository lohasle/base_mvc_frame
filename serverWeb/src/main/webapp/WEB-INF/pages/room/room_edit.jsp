<%@include file="/decorators/includeTag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>房源管理</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>

<div class="page-content">
    <!-- /.page-header -->
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <form id="form" class="form-horizontal" method="post" role="form" action="${action}">

                <fieldset>
                    <legend>房屋信息编辑</legend>


                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 房号 </label>
                    <div class="col-sm-9">
                        <input type="text" value="${data.roomNo}" name="roomNo" id="form-field-1" placeholder="请输入房号" class="col-xs-10 col-sm-5">
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right"> 楼层 </label>
                    <div class="col-sm-9">
                        <input type="text" value="${data.roomFloor}" name="roomFloor" placeholder="请输入房号" class="col-xs-10 col-sm-5">
                    </div>
                </div>
                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 状态 </label>
                    <div class="col-sm-9">
                        <select name="roomState" class="col-xs-10 col-sm-5" id="form-field-2">
                            <option value="">请选择</option>
                            <option value="1" <c:if test="${data.roomState==1}">selected="selected" </c:if>>正常</option>
                            <option value="0" <c:if test="${data.roomState==0}">selected="selected" </c:if>>失效</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right"> 房屋类型 </label>
                    <div class="col-sm-9">
                        <select name="roomTypeId" class="col-xs-10 col-sm-5">
                            <option value="">请选择</option>
                            <option value="1">住宅</option>
                            <option value="2">商业</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right"> 房屋面积 </label>

                    <div class="col-sm-9">
                        <input value="${data.roomArea}"  name="roomArea" type="number" class="col-xs-10 col-sm-5">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right"> 创建时间 </label>
                    <div class="col-sm-9">
                        <div class="input-group">
                            <span class="input-group-addon">
                                 <i class="icon-calendar"></i>
                            </span>
                            <input value="<fmt:formatDate value="${data.createTime}"  pattern="yyyy-MM-dd "/>"  name="createTime" data-date-format="yyyy-mm-dd" class="col-xs-10 col-sm-5 form_datetime" id="id-date-picker-1" type="text"/>
                        </div>

                    </div>
                </div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button id="subBtn" class="btn btn-info" type="submit">
                            <i class="icon-ok bigger-110"></i>
                            提交
                        </button>
                        &nbsp; &nbsp; &nbsp;
                        <button class="btn" type="reset">
                            <i class="icon-undo bigger-110"></i>
                            重置
                        </button>
                    </div>
                </div>
                <div class="hr hr-24"></div>
                </fieldset>
            </form>


        </div>
        <!-- /.row -->
    </div>
    <!-- /.page-content -->
</div>

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='<%=contextPath%>/static/js/lib/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>


<!-- inline scripts related to this page -->

<script type="text/javascript">

    require(["jquery", "lib/bootstrap.min", "lib/ace.min",
        "lib/respond.min","lib/date-time/bootstrap-datetimepicker.zh-CN",
        "lib/jquery.form","lib/jquery.validate.additional-methods","lib/bootbox.min"],
            function ($) {
                $(function () {
                    $("#id-date-picker-1").datetimepicker({"language":"zh-CN","minView":2});
                    $('#form').validate({
                        errorElement: 'div',
                        errorClass: 'help-block',
                        focusInvalid: false,
                        rules: {
                            roomNo: 'required',
                            roomFloor: {
                                required: true,
                                number: true
                            }
                        },

                        messages: {
                            roomNo: {
                                required: "房号不能为空"
                            },
                            roomFloor: {
                                required: "楼层号不能为空",
                                number: "楼层号必须为数字"
                            }

                        },

                        invalidHandler: function (event, validator) { //display error alert on form submit
                            alert("表单错误");
                        },

                        highlight: function (e) {
                            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                        },

                        success: function (e) {
                            $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                            $(e).remove();
                        },

                        errorPlacement: function (error, element) {
                            if(element.is(':checkbox') || element.is(':radio')) {
                                var controls = element.closest('div[class*="col-"]');
                                if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                            }
                            else if(element.is('.select2')) {
                                error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                            }
                            else if(element.is('.chosen-select')) {
                                error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                            }
                            else error.insertAfter(element.parent());
                        },

                        submitHandler: function (form) {
                            $('#form').ajaxSubmit(function(data){
                                bootbox.dialog({
                                    message: data.detail,
                                    buttons: {
                                        "success" : {
                                            "label" : "确定",
                                            "className" : "btn-sm btn-primary",
                                            "callback":function(){
                                                location.href="<%=contextPath%>/room" ;//添加成功后跳回到列表
                                            }
                                        }
                                    }
                                });
                            });
                        },
                        invalidHandler: function (form) {
                        }
                    });
                });
    });


</script>
</body>
</html>
