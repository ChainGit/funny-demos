<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
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
			var url = "good.do?a=page&id=totalPageAmount";
			var args = {
				"time" : new Date().getTime()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				totalPageAmount = p.tpa;
				for (var i = 0; i < totalPageAmount; i++) {
					$("#pageNums").append(
							$("<a></a>").attr("href",
									"good.do?a=page&id=" + (i + 1)).text(
									(i + 1)).click(function() {
								updatePage(this.href);
								return false;
							})).append(" ");
				}

				url = "good.do?a=page&id=currentPageIndex";
				args = {
					"time" : new Date().getTime()
				};

				$.post(url, args, function(data) {
					var p = eval("(" + data + ")");
					currentPageIndex = p.cpi;
					updatePage("good.do?a=page&id=" + currentPageIndex);
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

			url = "good.do?a=page&id=" + currentPageIndex;

			var args = {
				"time" : new Date().getTime()
			};

			$
					.post(
							url,
							args,
							function(data) {
								var p = eval("(" + data + ")");
								var lst = p.goods;
								$("#goodsList").empty();
								for (var i = 0; i < lst.length; i++) {
									var goodId = lst[i].goodId;
									var title = lst[i].goodTitle;
									var price = lst[i].goodPrice;
									var restNums = lst[i].restNums;
									var sellNums = lst[i].sellNums;
									$("#goodsList")
											.append(
													$("<tr></tr>")
															.append(
																	$(
																			"<td></td>")
																			.text(
																					title))
															.append(
																	$(
																			"<td></td>")
																			.text(
																					price))
															.append(
																	$(
																			"<td></td>")
																			.text(
																					restNums
																							+ "/"
																							+ (parseInt(sellNums) + restNums)))
															.append(
																	$(
																			"<td></td>")
																			.append(
																					$(
																							"<a></a>")
																							.attr(
																									"href",
																									"cart.do?a=add&id="
																											+ goodId)
																							.text(
																									"加入购物车")
																							.click(
																									function() {
																										addToCart(this);
																										return false;
																									}))
																			.append(
																					" ")
																			.append(
																					$(
																							"<a></a>")
																							.attr(
																									"href",
																									"good.do?a=info&id="
																											+ goodId)
																							.text(
																									"详情"))));
									$("#pageCurrent").text(currentPageIndex);
									$("#pageTotal").text(totalPageAmount);
								}
							});

		}

		function addToCart(obj) {
			var url = obj.href;
			var args = {
				"time" : new Date().getTime()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var status = p.status;
				if (status == 1)
					$("#status").text("添加到购物车成功!");
				else
					$("#status").text("添加到购物车失败!");
				refreshUser();
			});
		}

	})
</script>
</head>
<body>

	<center>
		<div>
			<jsp:include page="head.jsp"></jsp:include>
		</div>

		<div>
			<h4>商品列表</h4>
			<div>
				<form action="<%=request.getContextPath()%>/good.do?a=filter"
					method="post">
					商品价格(元)：<input type="text" name="fromPrice"
						value="${sessionScope.login_user_service.userPageFilterCache.fromPrice == 0.0 ? '' : sessionScope.login_user_service.userPageFilterCache.fromPrice }" />
					- <input type="text" name="toPrice"
						value="${sessionScope.login_user_service.userPageFilterCache.toPrice == 0.0 ? '' : sessionScope.login_user_service.userPageFilterCache.toPrice }" />
					<input type="submit" value="提交" />
				</form>
			</div>
			<br />
			<p id="status"></p>
			<table>
				<thead>
					<tr>
						<th>商品名称</th>
						<th>商品价格</th>
						<th>商品库存</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="goodsList">

				</tbody>
			</table>
		</div>

		<div>
			第 <span id="pageCurrent"></span>/<span id="pageTotal"></span> 页
		</div>
		<div id="pages" class="footer">
			<a id="first" href="good.do?a=page&id=first">首页</a> <a id="prev"
				href="good.do?a=page&id=prev">上一页</a>
			<div class="footer" id="pageNums"></div>
			<a id="next" href="good.do?a=page&id=next">下一页</a> <a id="last"
				href="good.do?a=page&id=last">尾页</a>
		</div>


	</center>

</body>
</html>