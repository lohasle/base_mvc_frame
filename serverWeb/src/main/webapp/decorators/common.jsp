<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><decorator:title default="模板"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <!-- basic styles -->
    <link href="<%=contextPath%>/static/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=contextPath%>/static/css/font-awesome.min.css" />
    <!-- ace styles -->
<%--
    <link rel="stylesheet" href="<%=contextPath%>/static/css/jquery-ui-1.10.3.full.min.css" />
--%>
    <link rel="stylesheet" href="<%=contextPath%>/static/css/ui.jqgrid.css" />
    <link rel="stylesheet" href="<%=contextPath%>/static/css/ace.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/static/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/static/css/ace-skins.min.css" />

    <link rel="stylesheet" href="<%=contextPath%>/static/css/theme/default/main.css" type="text/css"/>
    <script type="text/javascript" src="<%=contextPath%>/Js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/lib/jquery-1.9.1.js"></script>
<%--
    <script type="text/javascript" src="<%=contextPath%>/static/js/lib/require.js"></script>
--%>
    <script type="text/javascript" src="<%=contextPath%>/static/js/main.js"></script>

    <decorator:head/>
</head>
<body>
    <decorator:body/>
</body>
</html>
