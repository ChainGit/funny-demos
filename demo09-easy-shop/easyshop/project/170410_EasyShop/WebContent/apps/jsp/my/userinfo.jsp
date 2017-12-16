<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的资料</title>
<style type="text/css">
.footer {
	display: inline-block
}
</style>
<script type="text/javascript" src="base/js/jquery-1.8.3.js"></script>
<script type="text/javascript"></script>
</head>
<body>

	<center>
		<div>
			<c:import url="../head.jsp"></c:import>
		</div>

		<div>
			<h4>我的资料</h4>
			<br />
			<form action="user.do?a=update" method="post">
				<label>账户名称:</label><span>${sessionScope.login_user_service.userName }</span>
				<br /> <label>账户密码:</label> <input type="password" name="password"
					value="${sessionScope.login_user_service.user.userPass }" /> <br />
				<label>账户余额:</label> <input type="text" name="account"
					value="${sessionScope.login_user_service.accountById.accountBalance }" />
				<br /> <br /> <input type="submit" value="更新账户资料" />
			</form>
		</div>

		<div>
			<span>注意：成功更新资料后将自动退出登陆!</span>
		</div>

	</center>

</body>
</html>