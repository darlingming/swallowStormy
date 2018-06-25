<%@ page language="java"  pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>研发中心监控管理平台</title>
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/theme/img/favicon.ico">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/theme/img/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/admin.css">
    <script src="${pageContext.request.contextPath}/theme/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/theme/js/list.js"></script>
</head>
<body>
    <!--[if lt IE 9]>
    <script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
    <script src="theme/js/amazeui.ie8polyfill.min.js"></script>
    <![endif]-->
    <!--[if (gte IE 9)|!(IE)]><!-->
    <script src="${pageContext.request.contextPath}/theme/js/jquery.min.js"></script>
    <!--<![endif]-->
    <script src="${pageContext.request.contextPath}/theme/js/amazeui.min.js"></script>
    <script src="${pageContext.request.contextPath}/theme/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/theme/js/jquery.pjax.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/thirdpart/asyncbox/skins/zcms/asyncbox.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/thirdpart/asyncbox/AsyncBox.v1.4.js"></script>
 	<link rel="stylesheet" href="${pageContext.request.contextPath}/thirdpart/validform/css/style.css">
	<script src="${pageContext.request.contextPath}/thirdpart/validform/Validform_v5.3.2.js?v=<%=System.currentTimeMillis()%>"></script>
<script>
	$(function(){
		console.log('${pageContext.request.contextPath}11');
		window.location.href="/platform";
		document.write("<form controller='/platform' method=post name=formx1 style='display:none'>");
		document.write("</form>");
		document.formx1.submit();
	});
</script>
</body>
</html>

