package com.chain.easyshop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chain.easyshop.apps.pc.jsp.UserPageFilterCache;
import com.chain.easyshop.exception.EasyShopRuntimeException;
import com.chain.easyshop.service.GoodService;
import com.chain.easyshop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		UserService us = (UserService) request.getSession().getAttribute("login_user_service");
		GoodService gs = new GoodService(us.getUserId());
		PrintWriter pw = response.getWriter();

		String action = request.getParameter("a");

		if (action == null) {
			pw.println("{}");
			return;
		}

		UserPageFilterCache ugfc = us.getUserPageFilterCache();
		gs.getUserGoodsDaoImpl().setFilter(
				gs.getUserGoodsFilterDaoImpl().GetFilterByPriceRange(ugfc.getFromPrice(), ugfc.getToPrice()));

		switch (action) {
		case "add":
			doAdd(request, response, mapper, us, gs, pw);
			break;
		case "delete":
			doDelete(request, response, mapper, us, gs, pw);
			break;
		case "page":
			doPage(request, response, mapper, us, gs, pw);
			break;
		case "changeAmount":
			doChangeAmount(request, response, mapper, us, gs, pw);
			break;
		default:
			pw.println("{}");
			break;
		}
	}

	private void doChangeAmount(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper,
			UserService us, GoodService gs, PrintWriter pw) throws IOException {
		String goodIdParam = request.getParameter("id");
		String newGoodAmountParam = request.getParameter("nga");
		Map<String, Object> map = new HashMap<>();

		int goodId = new Integer(goodIdParam);
		int newGoodAmount = new Integer(newGoodAmountParam);

		if (newGoodAmount <= 0) {
			doDelete(request, response, mapper, us, gs, pw);
			return;
		}

		us.getUserCartDaoImpl().updateInCart(us.getUserId(), goodId, newGoodAmount);
		map.put("res", goodId);
		pw.println(mapper.writeValueAsString(map));
	}

	private void doDelete(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws IOException {
		String goodIdParam = request.getParameter("id");
		int goodId = new Integer(goodIdParam);
		Map<String, Object> map = new HashMap<>();
		us.getUserCartDaoImpl().deleteFromCart(us.getUserId(), goodId);
		map.put("res", 1);
		pw.println(mapper.writeValueAsString(map));
	}

	private void doPage(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws JsonProcessingException {
		String details = request.getParameter("id");
		Map<String, Object> map = new HashMap<>();
		us.getUserCartDaoImpl().setUserId(us.getUserId());
		switch (details) {
		case "totalPageAmount":
			map.put("tpa", us.getUserCartDaoImpl().getTotalPageAmount());
			break;
		case "currentPageIndex":
			map.put("cpi", us.getUserCartDaoImpl().getCurrentPageIndex());
			break;
		default:
			int pageIndex = new Integer(details);
			us.getUserCartDaoImpl().setCurrentPageIndex(pageIndex);
			us.getUserCartDaoImpl().doNewPageList();
			map.put("carts", us.getUserCartDaoImpl().getCurrentPageList());
			break;
		}
		pw.println(mapper.writeValueAsString(map));
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		String goodIdParam = request.getParameter("id");
		int goodId = 0;
		try {
			goodId = new Integer(goodIdParam);
		} catch (Exception e) {
			pw.println(mapper.writeValueAsString(map));
			throw new EasyShopRuntimeException(e);
		}

		int status = 0;

		long old = us.getTotalCartsAmount();
		us.getUserCartDaoImpl().insertToCart(us.getUserId(), goodId);
		us.getUserCartDaoImpl().setTotalItemsNumber(us.getUserCartDaoImpl().getUserCartSize(us.getUserId()));
		long now = us.getTotalCartsAmount();
		if (now >= old)
			status = 1;

		map.put("status", status);
		pw.println(mapper.writeValueAsString(map));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
