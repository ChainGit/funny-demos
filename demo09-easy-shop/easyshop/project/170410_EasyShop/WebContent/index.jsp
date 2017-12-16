<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Loading</title>
</head>
<body>

	<c:choose>
		<c:when test="${sessionScope.login_status == 'logined' }">
			<c:out value="您已登陆,欢迎使用"></c:out>
		</c:when>
		<c:otherwise>
			<c:out value="您尚未登陆,请先登陆"></c:out>
		</c:otherwise>
	</c:choose>

	<c:redirect url="/apps/jsp/index.jsp"></c:redirect>
</body>
</html>