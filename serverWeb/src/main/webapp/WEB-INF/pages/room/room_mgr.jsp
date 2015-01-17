<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
    <title>房源管理</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- basic styles -->



    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->


<%--
        <link rel="stylesheet" href="<%=contextPath%>/static/css/jquery-ui-1.10.3.full.min.css" />
--%>


    <script src="<%=contextPath%>/static/js/app/room/room_mgr.js"></script>

</head>
<body>

<div class="page-content">
<%--<div class="page-header">
    <h1>
        Tables
        <small>
            <i class="icon-double-angle-right"></i>
            Static &amp; Dynamic Tables
        </small>
    </h1>
</div>--%>
<!-- /.page-header -->
<div class="row">
<div class="col-xs-12">
<div class="row">
<div class="col-xs-12">
<div class="table-header">
    普通管理功能
</div>


<div class="table-responsive">
<!-- table begin -->
    <div id="query" class="clearfix">
        <form class="form-search" onsubmit="return false">
            <div class="row text-left">
                <div class="col-xs-8">
                    <input type="button" class="btn" id="add_btn" value="新 增" />
                    <input type="button" class="btn" id="edit_btn" value="修改" />
                    <input type="button" class="btn" id="del_btn" value="删 除" />
                </div>
                <div class="col-xs-4">
                    <div class="input-group">
                        <input id="sn" type="text" class="form-control search-query" placeholder="请输入房号">
                        <span class="input-group-btn">
                            <button id="find_btn" type="button" class="btn btn-purple btn-sm">
                                搜索
                                <i class="icon-search icon-on-right bigger-110"></i>
                            </button>
                        </span>
                    </div>
                </div>
            </div>

        </form>
    </div>

    <!-- table -->
    <div class="table-responsive">
        <table id="list">

        </table>

        <div id="pager">

        </div>
    </div>
    <!-- table end -->


</div>
</div>
</div>

<!-- PAGE CONTENT ENDS -->
</div>
<!-- /.col -->
</div>
<!-- /.row -->
</div>
<!-- /.page-content -->
</body>
</html>
