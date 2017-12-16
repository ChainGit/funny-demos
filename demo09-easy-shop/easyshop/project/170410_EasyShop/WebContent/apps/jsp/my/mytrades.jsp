<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的所有订单</title>
<style type="text/css">
.footer {
	display: inline-block
}
</style>
<script type="text/javascript" src="base/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
	$(function() {
		var currentPageIndex = 1;
		var totalPageAmount = 1;

		init();

		$("#pages > a").click(function() {
			updatePage(this.href);
			return false;
		});

		function init() {
			var url = "user.do?a=getTrades&id=totalPageAmount";
			var args = {
				"time" : new Date().getTime()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				totalPageAmount = p.tpa;
				for (var i = 0; i < totalPageAmount; i++) {
					$("#pageNums").append(
							$("<a></a>").attr("href",
									"user.do?a=getTrades&id=" + (i + 1)).text(
									(i + 1)).click(function() {
								updatePage(this.href);
								return false;
							})).append(" ");
				}

				url = "user.do?a=getTrades&id=currentPageIndex";
				args = {
					"time" : new Date().getTime()
				};

				$.post(url, args, function(data) {
					var p = eval("(" + data + ")");
					currentPageIndex = p.cpi;
					updatePage("user.do?a=getTrades&id=" + currentPageIndex);
				});

			});
		}

		function updatePage(url) {
			var index = getParameter(url, "id");

			if (index == "first")
				currentPageIndex = 1;
			else if (index == "last")
				currentPageIndex = totalPageAmount;
			else if (index == "prev")
				currentPageIndex = currentPageIndex == 1 ? currentPageIndex
						: currentPageIndex - 1;
			else if (index == "next")
				currentPageIndex = currentPageIndex == totalPageAmount ? currentPageIndex
						: parseInt(currentPageIndex) + 1;
			else
				currentPageIndex = index;

			url = "user.do?a=getTrades&id=" + currentPageIndex;

			var args = {
				"time" : new Date().getTime()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var ts = p.trades;
				$("#trades").empty();
				var uis = [];
				for (var i = 0; i < ts.length; i++) {
					var trade = ts[i].trade;
					var tradeTime = ts[i].tradeTime;
					$("#trades").append($("<p></p>").text(tradeTime));
					$("#trades").append($("<table></table>").attr("name",tradeTime).append($("<tr></tr>").append($("<th></th>").text("商品名称")).append($("<th></th>").text("商品价格")).append($("<th></th>").text("购买数量")))).append("<br/>");
					for(var j = 0;j<trade.length;j++){
						var goodId = trade[j].goodId;
						var goodQuantity = trade[j].goodQuantity;
						var k = 0;
						for (; k < uis.length; k++)
							if (uis[k] == goodId)
								break;
						if (k == uis.length)
							uis[k] = goodId;
						$("table[name='"+tradeTime+"'] > tbody").append($("<tr></tr>").append($("<td></td>").append($("<a></a>").attr("href","good.do?a=info&id="+ goodId).attr("name",goodId).text(goodId))).append($("<td></td>").attr("name","p"+goodId).text("NaN")).append($("<td></td>").text(goodQuantity)));						
					}
				}
				var urlx = "good.do?a=singleInfo";
				for (var i = 0; i < uis.length; i++) {
					var argsx = {
						"time" : new Date().getTime(),
						"goodId" : uis[i]
					};
					$.post(urlx, argsx, function(data) {
						var p = eval("(" + data + ")");
						var good = p.good;
						var goodTitle = good.goodTitle;
						var goodId = good.goodId;
						var goodPrice = good.goodPrice;
						$("a[name=" + goodId + "]").text(goodTitle);
						$("td[name=p" + goodId + "]").text(goodPrice);
					})
				}
				$("#pageCurrent").text(currentPageIndex);
				$("#pageTotal").text(totalPageAmount);
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
			<h4>所有订单列表</h4>
		</div>

		<div id="trades"></div>

		<div>
			第 <span id="pageCurrent"></span>/<span id="pageTotal"></span> 页
		</div>
		<div id="pages" class="footer">
			<a id="first" href="user.do?a=getTrades&id=first">首页</a> <a id="prev"
				href="user.do?a=getTrades&id=prev">上一页</a>
			<div class="footer" id="pageNums"></div>
			<a id="next" href="user.do?a=getTrades&id=next">下一页</a> <a id="last"
				href="user.do?a=getTrades&id=last">尾页</a>
		</div>

	</center>

</body>
</html>