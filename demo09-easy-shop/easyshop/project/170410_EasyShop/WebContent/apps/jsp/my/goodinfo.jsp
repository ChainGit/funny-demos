<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品详情</title>
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

		var goodId = getParameter(window.location.search.substr(1), "id");

		$("#pages > a").click(function() {
			updatePage(this.href);
			return false;
		});

		function init0() {
			var url = "good.do?a=getRemarks&id=totalPageAmount";
			var args = {
				"time" : new Date().getTime(),
				"gd" : goodId
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				totalPageAmount = p.tpa;
				for (var i = 0; i < totalPageAmount; i++) {
					$("#pageNums").append(
							$("<a></a>").attr(
									"href",
									"good.do?a=getRemarks&gd=" + goodId
											+ "&id=" + (i + 1)).text((i + 1))
									.click(function() {
										updatePage(this.href);
										return false;
									})).append(" ");
				}

				url = "good.do?a=getRemarks&id=currentPageIndex";
				args = {
					"time" : new Date().getTime(),
					"gd" : goodId
				};

				$.post(url, args, function(data) {
					var p = eval("(" + data + ")");
					currentPageIndex = p.cpi;
					updatePage("good.do?a=getRemarks&id=" + currentPageIndex);
				});

			});
		}

		function init() {
			var url = "good.do?a=singleInfo";
			var args = {
				"time" : new Date().getTime(),
				"goodId" : goodId
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var g = p.good;
				var goodTitle = g.goodTitle;
				var goodPrice = g.goodPrice;
				var sellNums = g.sellNums;
				var restNums = g.restNums;
				$("#goodTitle").text(goodTitle);
				$("#goodPrice").text(goodPrice);
				$("#sellNums").text(sellNums);
				$("#restNums").text(restNums);
			})
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

			url = "good.do?a=getRemarks&id=" + currentPageIndex;

			var args = {
				"time" : new Date().getTime(),
				"gd" : goodId
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var lst = p.remarks;
				var uis = [];
				$("#remarksList").empty();
				for (var i = 0; i < lst.length; i++) {
					var remarkDate = lst[i].remarkDate;
					var remarkContent = lst[i].remarkContent;
					var remarkUserId = lst[i].remarkUserId;
					var j = 0;
					for (; j < uis.length; j++)
						if (uis[j] == remarkUserId)
							break;
					if (j == uis.length)
						uis[j] = remarkUserId;
					$("#remarksList").append(
							$("<tr></tr>").append(
									$("<td></td>").text(remarkDate)).append(
									$("<td></td>").attr("name", remarkUserId)
											.text(remarkUserId)).append(
									$("<td></td>").text(remarkContent)));
					$("#pageCurrent").text(currentPageIndex);
					$("#pageTotal").text(totalPageAmount);
				}
				var urlx = "user.do?a=info&id=query";
				for (var i = 0; i < uis.length; i++) {
					var argsx = {
						"time" : new Date().getTime(),
						"ui" : uis[i]
					};
					$.post(urlx, argsx, function(data) {
						var p = eval("(" + data + ")");
						var unx = p.un;
						var uix = p.ui;
						$("td[name=" + uix + "]").text(unx);
					})
				}
			});
		}

		init0();
		init();
	})
</script>
</head>
<body>

	<center>
		<div>
			<c:import url="../head.jsp"></c:import>
		</div>

		<div>
			<h4>商品详情</h4>
			<table>
				<tr>
					<td><label>商品名称：</label></td>
					<td><span id="goodTitle"></span></td>
				</tr>
				<tr>
					<td><label>商品价格：</label></td>
					<td><span id="goodPrice"></span></td>
				</tr>
				<tr>
					<td><label>商品已售：</label></td>
					<td><span id="sellNums"></span></td>
				</tr>
				<tr>
					<td><label>商品库存：</label></td>
					<td><span id="restNums"></span></td>
				</tr>
			</table>
		</div>

		<div>
			<h4>商品评价</h4>
			<table>
				<thead>
					<tr>
						<th>时间</th>
						<th>用户</th>
						<th>内容</th>
					</tr>
				</thead>
				<tbody id="remarksList">
				</tbody>
			</table>
		</div>

		<div>
			第 <span id="pageCurrent"></span>/<span id="pageTotal"></span> 页
		</div>
		<div id="pages" class="footer">
			<a id="first" href="good.do?a=getRemarks&id=first">首页</a> <a
				id="prev" href="good.do?a=getRemarks&id=prev">上一页</a>
			<div class="footer" id="pageNums"></div>
			<a id="next" href="good.do?a=getRemarks&id=next">下一页</a> <a id="last"
				href="good.do?a=getRemarks&id=last">尾页</a>
		</div>

		<div>
			<h4>添加评价</h4>
			<label>${sessionScope.login_user_service.userName }:</label><br />
			<form action="good.do?a=addRemark" method="post">
				<textarea cols="20" rows="3" name="addRemark"></textarea>
				<br /> <input type="submit" value="提交评价" />
			</form>
		</div>

	</center>
</body>
</html>