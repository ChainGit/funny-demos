<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<a href="apps/jsp/my/userinfo.jsp">${sessionScope.login_user_service.user.userName }</a>
,您好！ &nbsp;
<a href="apps/jsp/my/mytrades.jsp">查看已购买(<span
	id="totalTradesAmount"></span>)
</a>
&nbsp;
<a href="apps/jsp/my/mycarts.jsp">我的购物车(<span id="totalCartsAmount"></span>)
</a>
&nbsp;
<a href="apps/jsp/exit.jsp">退出登陆 </a>
<br />
<br />
<a href="apps/jsp/index.jsp">显示商品列表</a>

<script type="text/javascript">
	function getParameter(url, name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = url.match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

	function getTotalTradesAmount() {
		var url = "user.do?a=info&id=getTotalTradesAmount";
		var args = {
			"time" : new Date().getTime()
		};
		$.post(url, args, function(data) {
			var p = eval("(" + data + ")");
			var totalTradesAmount = p.tta;
			$("#totalTradesAmount").text(totalTradesAmount);
		});
	}

	function getTotalCartsAmount() {
		var url = "user.do?a=info&id=getTotalCartsAmount";
		var args = {
			"time" : new Date().getTime()
		};
		$.post(url, args, function(data) {
			var p = eval("(" + data + ")");
			var totalCartsAmount = p.tca;
			$("#totalCartsAmount").text(totalCartsAmount);
		});
	}

	function refreshUser() {
		getTotalTradesAmount();
		getTotalCartsAmount();
	}

	window.onload = function() {
		refreshUser();
	}

	function processMsg(mid) {
		var msg;
		switch (parseInt(mid)) {
		case 1001:
			msg = "商品库存不足";
			break;
		case 1002:
			msg = "账户余额不足";
			break;
		case 1003:
			msg = "账户密码错误";
			break;
		default:
			break;
		}

		return msg;
	}
</script>

