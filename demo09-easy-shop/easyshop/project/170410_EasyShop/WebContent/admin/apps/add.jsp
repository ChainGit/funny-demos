<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加商品</title>
<script type="text/javascript" src="base/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		$("#addBtn").click(function() {
			var goodTitle = $("#goodTitle").val();
			var goodPrice = $("#goodPrice").val();
			var goodStore = $("#goodStore").val();

			var url = "admin.do?a=add";
			var args = {
				"time" : new Date().getTime(),
				"gt" : goodTitle,
				"gp" : goodPrice,
				"gs" : goodStore
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				if (parseInt(p.res) > -1)
					$("#msg").text("增加成功");
				else
					$("#msg").text("增加失败");
			})
		});
	})
</script>
</head>
<body>

	<center>
		<div>
			<h4>增加商品</h4>
			<a href="admin/apps/index.jsp">返回管理主界面</a>
		</div>

		<br />

		<div>
			<table>
				<thead>
					<tr>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>商品名称：</td>
						<td><input type="text" id="goodTitle" /></td>
					<tr>
					<tr>
						<td>商品价格</td>
						<td><input type="text" id="goodPrice" /></td>
					<tr>
					<tr>
						<td>商品库存</td>
						<td><input type="text" id="goodStore" /></td>
					<tr>
				</tbody>
			</table>
			<br />
			<button id="addBtn">增加商品</button>
			<br />
			<p id="msg"></p>
		</div>

	</center>

</body>
</html>