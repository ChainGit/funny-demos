<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物结账</title>
<script type="text/javascript" src="base/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		init();

		function init() {
			var url = "trade.do?a=confirm";
			var args = {
				"time" : new Date().getTime()
			}

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var r = p.res;
				var tm = p.tm;
				$("#totalMoney").text(tm.toFixed(2));
				for (var i = 0; i < r.length; i++) {
					var goodTitle = r[i].gt;
					var goodPrice = r[i].gp;
					var goodAmount = r[i].ga;
					$("#cartsList").append(
							$("<tr></tr>").append(
									$("<td></td>").text(goodTitle)).append(
									$("<td></td>").text(goodPrice)).append(
									$("<td></td>").text(goodAmount)));
				}
			});
		}

		$("#buy").click(function() {
			var url = "trade.do?a=check";
			var args = {
				"time" : new Date().getTime(),
				"id" : "account",
				"ps" : $("#ps").val()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var r = p.res;
				if (parseInt(r) == 1) {
					var rs = confirm("确定要支付吗?");
					if (rs)
						cash();
				} else if (processMsg(parseInt(r)) == "账户余额不足") {
					$("#buyMsg").empty().text("账户余额不足");
				} else if (processMsg(parseInt(r)) == "账户密码错误") {
					$("#buyMsg").empty().text("账户密码错误");
				}
			});
		});

		function cash() {
			$("#buyMsg").empty().text("处理中...");

			var url = "trade.do?a=cash";
			var args = {
				"time" : new Date().getTime()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				if (parseInt(p.res) == 1)
					window.location.href = "apps/jsp/my/myresult.jsp";
			});
		}
	})
</script>
</head>
<body>

	<center>
		<div>
			<c:import url="../head.jsp"></c:import>
		</div>

		<div>
			<h4>结账付款</h4>
		</div>

		<div>
			<table>
				<thead>
					<tr>
						<th>商品名称</th>
						<th>商品价格</th>
						<th>购买数量</th>
					</tr>
				</thead>
				<tbody id="cartsList">

				</tbody>
			</table>
			<p>
				合计 <font color="red" id="totalMoney">0.00</font> 元
			</p>
		</div>

		<hr />

		<div>
			<label>输入密码：</label><input type="password" id="ps" />&nbsp;
			<button id="buy">确认购买</button>
			<p id="buyMsg"></p>
		</div>
	</center>

</body>
</html>