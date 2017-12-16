<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的购物车</title>
<style type="text/css">
.footer {
	display: inline-block
}
</style>
<script type="text/javascript" src="base/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="base/js/json2.js"></script>
<script type="text/javascript">
	$(function() {
		var currentPageIndex = 1;
		var totalPageAmount = 1;

		//Json对象
		var selm = eval("({s:[],p:0})");

		init();

		$("#pages > a").click(function() {
			updatePage(this.href);
			return false;
		});

		function init() {
			var url = "cart.do?a=page&id=totalPageAmount";
			var args = {
				"time" : new Date().getTime()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				totalPageAmount = p.tpa;
				for (var i = 0; i < totalPageAmount; i++) {
					$("#pageNums").append(
							$("<a></a>").attr("href",
									"cart.do?a=page&id=" + (i + 1)).text(
									(i + 1)).click(function() {
								updatePage(this.href);
								return false;
							})).append(" ");
				}

				url = "cart.do?a=page&id=currentPageIndex";
				args = {
					"time" : new Date().getTime()
				};

				$.post(url, args, function(data) {
					var p = eval("(" + data + ")");
					currentPageIndex = p.cpi;
					updatePage("cart.do?a=page&id=" + currentPageIndex);
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

			url = "cart.do?a=page&id=" + currentPageIndex;

			var args = {
				"time" : new Date().getTime()
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				var cs = p.carts;
				$("#cartsList").empty();
				var uis = [];
				for (var i = 0; i < cs.length; i++) {
					var goodId = cs[i].goodId;
					var goodAmount = cs[i].goodAmount;
					var k = 0;
					for (; k < uis.length; k++)
						if (uis[k] == goodId)
							break;
					if (k == uis.length)
						uis[k] = goodId;
					$("#cartsList").append(
							$("<tr></tr>").append(
									$("<td></td>").append(
											$("<input type='checkbox'>").attr(
													"name", "c" + goodId)
													.click(function() {
														select(this);
													}))).append(
									$("<td></td>").attr("name", "n" + goodId)
											.text("NaN")).append(
									$("<td></td>").attr("name", "p" + goodId)
											.text("NaN")).append(
									$("<td></td>").append(
											$("<input type='text'>").attr(
													"name", "a" + goodId).val(
													goodAmount).change(
													function() {
														changeAmount(this);
													})).append(
											$("<input type='hidden'>").val(
													goodAmount))).append(
									$("<td></td>").append(
											$("<a></a>").attr("name",
													"d" + goodId).attr("href",
													"#").click(function() {
												deleteFromCart(this.href);
												return false;
											}).text("删除"))).append(
									$("<td></td>").attr("name", "changeMsg")));
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
						$("td[name=n" + goodId + "]").empty().append(
								$("<a></a>").attr("href",
										"good.do?a=info&id=" + goodId).text(
										goodTitle));
						$("td[name=p" + goodId + "]").text(goodPrice);
						$("a[name=d" + goodId + "]").attr(
								"href",
								encodeURIComponent("cart.do?a=delete&id="
										+ goodId + "&goodTitle=" + goodTitle));
					})
				}

				$("#pageCurrent").text(currentPageIndex);
				$("#pageTotal").text(totalPageAmount);

				for (var i = 0; i < selm.s.length; i++)
					$(":checkbox[name=c" + selm.s[i].goodId + "]").attr(
							"checked", "checked");
			});
		}

		function deleteFromCart(url) {
			url = decodeURIComponent(url);
			var goodName = getParameter(url, "goodTitle");
			var goodId = getParameter(url, "id");

			var res = confirm("确实要删除 \"" + goodName + "\" 吗?");
			if (res == false)
				return;

			url = "cart.do?a=delete";
			var args = {
				"time" : new Date().getTime(),
				"id" : goodId
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				if (parseInt(p.res) == 1) {
					$("#deleteMsg").text("删除成功");
					location.reload(true);
				}
			});
		}

		function changeAmount(obj) {
			var $obj = $(obj);
			var newGoodAmount = parseInt($obj.val());
			if (newGoodAmount < 1) {
				$obj.parent().siblings(":last").text("选购数量最低为1");
				$obj.val($obj.siblings(":hidden").val());
				return;
			} else
				$obj.parent().siblings(":last").text("");

			var goodIdx = $obj.attr("name");
			var goodId = goodIdx.substring(1, goodIdx.length);

			var res = confirm("确定要修改 \""
					+ $("#cartsList td[name=n" + goodId + "] a").text()
					+ "\" 的数目为 " + newGoodAmount + " ?");

			if (res == false)
				return;

			var url = "cart.do?a=changeAmount";
			var args = {
				"time" : new Date().getTime(),
				"id" : goodId,
				"nga" : newGoodAmount
			};

			$.post(url, args, function(data) {
				var p = eval("(" + data + ")");
				if (parseInt(p.res) == 1) {
					location.reload(true);
					return;
				}
				if (parseInt(p.res) == goodId) {
					$obj.parent().siblings(":last").text("修改成功");
					$obj.siblings(":hidden").val(newGoodAmount);
					updateSelMoney(goodId, newGoodAmount);
				} else
					$obj.parent().siblings(":last").text("修改失败");
			});
		}

		function select(obj) {
			var $obj = $(obj);
			var goodIdx = $obj.attr("name");
			var goodId = goodIdx.substring(1, goodIdx.length);

			if (selm.s.length > 4) {
				obj.checked = false;
				alert("一次最多支持只购买5种商品!");
				return;
			}

			if (obj.checked == true) {
				addSelMoney(goodId);
			} else {
				subSelMoney(goodId);
			}

			$("#selectedGoodsSize").text(selm.s.length);
		}

		function updateSelMoney(goodId, goodAmount) {
			var p = selm.s;
			for (var i = 0; i < p.length; i++)
				if (p[i].goodId == goodId) {
					p[i].goodAmount = goodAmount;
					break;
				}

			updateTotalMoney();
		}

		function addSelMoney(goodId) {
			var p = selm.s;
			for (var i = 0; i < p.length; i++)
				if (p[i].goodId == goodId) {
					updateSelMoney(goodId, $(
							"#cartsList input[name=a" + goodId + "]").val());
					return;
				}

			var tmp = {
				"goodId" : goodId,
				"goodPrice" : $("#cartsList td[name=p" + goodId + "]").text(),
				"goodAmount" : $("#cartsList input[name=a" + goodId + "]")
						.val()
			};

			p.push(tmp);
			updateTotalMoney();
		}

		function subSelMoney(goodId) {
			var p = selm.s;
			var i = 0;
			var found = false;
			for (; i < p.length; i++)
				if (p[i].goodId == goodId) {
					found = true;
					break;
				}

			if (found)
				p.splice(i, 1);

			updateTotalMoney();
		}

		function updateTotalMoney() {
			var s = selm.s;

			var t = 0.0;
			for (var i = 0; i < s.length; i++)
				t += s[i].goodPrice * s[i].goodAmount;

			selm.p = t.toFixed(2);
			$("#selectGoodsMoney").text(selm.p);
		}

		$("#cashBtn")
				.click(
						function() {
							if (selm.s.length < 1)
								return;

							$(this).attr("disabled", true);
							$("#cartsList td[name=changeMsg]").text("");
							$("#cashMsg").empty("");

							var s = selm.s;
							var ns = eval("({s:[]})");
							for (var i = 0; i < s.length; i++) {
								var gi = s[i].goodId;
								var ga = s[i].goodAmount;
								var nsitem = {
									"goodId" : gi,
									"goodAmount" : ga
								};
								ns.s.push(nsitem);
							}

							var url = "trade.do?a=check";
							var args = {
								"time" : new Date().getTime(),
								"uc" : JSON.stringify(ns),
								"id" : "cart"
							};

							$
									.post(
											url,
											args,
											function(data) {
												console.log(data);
												var p = eval("(" + data + ")");
												var r = p.res
												if (parseInt(r) == 1)
													window.location.href = "apps/jsp/my/mycash.jsp";
												else {
													var cc = r.cc;
													for (var i = 0; i < cc.length; i++) {
														var mid = processMsg(cc[i].mid);
														if (mid == "商品库存不足") {
															$("#cashMsg")
																	.append(
																			$(
																					"<p></p>")
																					.text(
																							"\""
																									+ cc[i].gt
																									+ "\" 库存不足,仅剩 "
																									+ cc[i].gsn
																									+ " 件"));
														}
													}
												}
												$("#cashBtn").attr("disabled",
														false);
											});
						});

	})
</script>
</head>
<body>

	<center>
		<div>
			<c:import url="../head.jsp"></c:import>
		</div>

		<div>
			<h4>购物车列表</h4>
			<p id="deleteMsg"></p>
			<table>
				<thead>
					<tr>
						<th>选择</th>
						<th>商品名称</th>
						<th>商品价格</th>
						<th>选购数量</th>
						<th>操作</th>
						<th></th>
					</tr>
				</thead>
				<tbody id="cartsList">

				</tbody>
			</table>
		</div>

		<div>
			第 <span id="pageCurrent"></span>/<span id="pageTotal"></span> 页
		</div>
		<div id="pages" class="footer">
			<a id="first" href="cart.do?a=page&id=first">首页</a> <a id="prev"
				href="cart.do?a=page&id=prev">上一页</a>
			<div class="footer" id="pageNums"></div>
			<a id="next" href="cart.do?a=page&id=next">下一页</a> <a id="last"
				href="cart.do?a=page&id=last">尾页</a>
		</div>

		<div>
			<h4>购物车操作</h4>
			<p>
				您的购物车内共有 ${sessionScope.login_user_service.userCartSize } 种商品,已经选择了
				<span id="selectedGoodsSize">0</span> 种商品
			</p>
			<p>
				所选商品的总价格为 <span id="selectGoodsMoney">0</span> 元
			</p>
			<button id="cashBtn">提交订单</button>
			<p id="cashMsg"></p>
		</div>

	</center>

</body>
</html>