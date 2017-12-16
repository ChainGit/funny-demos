<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="base/js/jquery-1.8.3.js"></script>
<title>购买成功</title>
<style type="text/css">
.sucess {
	margin: 0 auto;
}
</style>
</head>
<body>

	<center>
		<div>
			<c:import url="../head.jsp"></c:import>
		</div>

		<div class="sucess">
			<h2>购买成功</h2>
			<a href="apps/jsp/index.jsp">返回继续购物</a>
		</div>
	</center>

</body>
</html>