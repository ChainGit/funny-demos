<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品</title>
<script type="text/javascript" src="base/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {

		$("#modify").css("display", "none");

		$("#searchBtn").click(function() {
			var url = "admin.do?a=search";
			var args = {
				"time" : new Date().getTime(),
				"id" : $("#search").val()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var res = p.res;
				if (parseInt(res) != -1)
					$("#searchMsg").text("");
				else
					$("#searchMsg").text("无此商品");

				var goodId = res.goodId;
				var goodTitle = res.goodTitle;
				var goodPrice = res.goodPrice;
				var goodStore = res.restNums;

				$("#goodId").val(goodId);
				$("#goodTitle").val(goodTitle);
				$("#goodPrice").val(goodPrice);
				$("#goodStore").val(goodStore);

				$("#modify").css("display", "");
			});

		});

		$("#updateBtn").click(function() {
			var goodId = $("#goodId").val();
			var goodTitle = $("#goodTitle").val();
			var goodPrice = $("#goodPrice").val();
			var goodStore = $("#goodStore").val();

			var url = "admin.do?a=modify";
			var args = {
				"time" : new Date().getTime(),
				"gi" : goodId,
				"gt" : goodTitle,
				"gp" : goodPrice,
				"gs" : goodStore
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				if (parseInt(p.res) > -1)
					$("#msg").text("修改成功");
				else
					$("#msg").text("修改失败");
			});
		});

	})
</script>
</head>
<body>

	<center>
		<div>
			<h4>修改商品信息</h4>
			<a href="admin/apps/index.jsp">返回管理主界面</a>
		</div>

		<br />

		<div>
			<span>商品ID：</span>&nbsp;<input type="text" id="search" />&nbsp;
			<button id="searchBtn">搜索</button>
			<p id="searchMsg"></p>
		</div>

		<hr />

		<div id="modify">
			<font size="2px">因为商品的价格和用户交易的价格同属一个价格，没有作区分，所以不能修改商品的价格</font> <br />
			<input type="hidden" id="goodId" />
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
						<td><input type="text" id="goodPrice" disabled="disabled" /></td>
					<tr>
					<tr>
						<td>商品库存</td>
						<td><input type="text" id="goodStore" /></td>
					<tr>
				</tbody>
			</table>
			<br />
			<button id="updateBtn">更新信息</button>
			<p id="msg"></p>
		</div>

	</center>

</body>
</html>