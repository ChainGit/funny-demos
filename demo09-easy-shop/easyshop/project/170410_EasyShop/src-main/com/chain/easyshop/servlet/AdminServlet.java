package com.chain.easyshop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chain.easyshop.bean.GoodItem;
import com.chain.easyshop.exception.EasyShopRuntimeException;
import com.chain.easyshop.service.GoodService;
import com.chain.easyshop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		UserService us = (UserService) request.getSession().getAttribute("login_user_service");
		GoodService gs = new GoodService(us.getUserId());
		PrintWriter pw = response.getWriter();

		String action = request.getParameter("a");

		if (action == null) {
			pw.println("{}");
			return;
		}

		switch (action) {
		case "add":
			doAdd(request, response, mapper, us, gs, pw);
			break;
		case "modify":
			doModify(request, response, mapper, us, gs, pw);
			break;
		case "search":
			doSearch(request, response, mapper, us, gs, pw);
			break;
		default:
			pw.println("{}");
			break;
		}
	}

	private void doSearch(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws JsonProcessingException {
		String goodIdParam = request.getParameter("id");
		int goodId = new Integer(goodIdParam);
		GoodItem gi = gs.getGoodItem(goodId);
		Map<String, Object> map = new HashMap<>();
		if (gi == null)
			map.put("res", -1);
		else
			map.put("res", gi);
		pw.println(mapper.writeValueAsString(map));
	}

	private void doModify(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws JsonProcessingException {
		String goodIdParam = request.getParameter("gi");
		String goodTitleParam = request.getParameter("gt");
		String goodPriceParam = request.getParameter("gp");
		String goodStoreParam = request.getParameter("gs");

		double goodPrice = 0.0d;
		int goodStore = 0;
		int goodId = 0;

		Map<String, Object> map = new HashMap<>();

		try {
			goodPrice = new Double(goodPriceParam);
			goodStore = new Integer(goodStoreParam);
			goodId = new Integer(goodIdParam);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}

		gs.getGoodItemDaoImpl().modifyGood(goodId, goodTitleParam, goodPrice, goodStore);
		map.put("res", 1);
		pw.println(mapper.writeValueAsString(map));
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws JsonProcessingException {
		String goodTitleParam = request.getParameter("gt");
		String goodPriceParam = request.getParameter("gp");
		String goodStoreParam = request.getParameter("gs");

		double goodPrice = 0.0d;
		int goodStore = 0;

		Map<String, Object> map = new HashMap<>();

		try {
			goodPrice = new Double(goodPriceParam);
			goodStore = new Integer(goodStoreParam);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new EasyShopRuntimeException(e);
		}

		long res = gs.getGoodItemDaoImpl().addGood(goodTitleParam, goodPrice, goodStore);
		map.put("res", res);
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
