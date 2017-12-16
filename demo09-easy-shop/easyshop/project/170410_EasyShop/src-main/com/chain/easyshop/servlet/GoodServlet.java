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
import com.chain.easyshop.bean.GoodItem;
import com.chain.easyshop.exception.EasyShopRuntimeException;
import com.chain.easyshop.inter.dao.impl.GoodRemarksDaoImpl;
import com.chain.easyshop.service.GoodService;
import com.chain.easyshop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GoodServlet
 */
public class GoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoodServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		UserService us = (UserService) request.getSession().getAttribute("login_user_service");
		GoodService gs = (GoodService) request.getSession().getAttribute("login_good_service");
		if (gs == null) {
			gs = new GoodService(us.getUserId());
			request.getSession().setAttribute("login_good_service", gs);
		}
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
		case "page":
			doPage(request, response, mapper, us, gs, pw);
			break;
		case "filter":
			doFilter(request, response, mapper, us, gs, pw);
			break;
		case "info":
			doInfo(request, response, mapper, us, gs, pw);
			break;
		case "singleInfo":
			doSingleInfo(request, response, mapper, us, gs, pw);
			break;
		case "getRemarks":
			doGetRemark(request, response, mapper, us, gs, pw);
			break;
		case "addRemark":
			doAddRemark(request, response, mapper, us, gs, pw);
			break;
		default:
			pw.println("{}");
			break;
		}
	}

	private void doGetRemark(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper,
			UserService us, GoodService gs, PrintWriter pw) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		String details = request.getParameter("id");
		String goodIdParam = request.getParameter("gd");
		int goodId = new Integer(goodIdParam);
		if (gs.getGoodRemarksDaoImpl() == null)
			gs.setGoodRemarksDaoImpl(new GoodRemarksDaoImpl(goodId));
		else if (gs.getGoodRemarksDaoImpl().getGoodId() != goodId)
			gs.getGoodRemarksDaoImpl().setGoodId(goodId);
		switch (details) {
		case "totalPageAmount":
			map.put("tpa", gs.getGoodRemarksDaoImpl().getTotalPageAmount());
			break;
		case "currentPageIndex":
			map.put("cpi", gs.getGoodRemarksDaoImpl().getCurrentPageIndex());
			break;
		default:
			int pageIndex = new Integer(details);
			gs.getGoodRemarksDaoImpl().setCurrentPageIndex(pageIndex);
			gs.getGoodRemarksDaoImpl().doNewPageList();
			map.put("remarks", gs.getGoodRemarksDaoImpl().getCurrentPageList());
			break;
		}
		pw.println(mapper.writeValueAsString(map));
	}

	private void doAddRemark(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper,
			UserService us, GoodService gs, PrintWriter pw) throws IOException {
		String remarkParam = request.getParameter("addRemark");
		gs.getGoodRemarksDaoImpl().addGoodRemarkItem(us.getUserId(), gs.getGoodId(), remarkParam);
		gs.setGoodId(gs.getGoodId());
		response.sendRedirect(request.getContextPath() + "/apps/jsp/my/goodinfo.jsp?id=" + gs.getGoodId());
	}

	private void doSingleInfo(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper,
			UserService us, GoodService gs, PrintWriter pw) throws JsonProcessingException {
		String goodIdParam = request.getParameter("goodId");
		int goodId = new Integer(goodIdParam);
		GoodItem gi = gs.getGoodItem(goodId);
		Map<String, Object> map = new HashMap<>();
		map.put("good", gi);
		pw.println(mapper.writeValueAsString(map));
	}

	private void doInfo(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws IOException {
		String goodIdParam = request.getParameter("id");
		int goodId = 0;
		try {
			goodId = new Integer(goodIdParam);
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			throw new EasyShopRuntimeException(e);
		}

		response.sendRedirect(request.getContextPath() + "/apps/jsp/my/goodinfo.jsp?id=" + goodId);
	}

	private void doFilter(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws IOException {
		String fromPriceParam = request.getParameter("fromPrice");
		String toPriceParam = request.getParameter("toPrice");
		double fromPrice = 0.0d;
		double toPrice = 0.0d;
		try {
			fromPrice = new Double(fromPriceParam);
		} catch (Exception e) {
			fromPrice = 0.0d;
		}
		try {
			toPrice = new Double(toPriceParam);
		} catch (Exception e) {
			toPrice = 0.0d;
			throw new EasyShopRuntimeException(e);
		}
		us.getUserPageFilterCache().setFromPrice(fromPrice);
		us.getUserPageFilterCache().setToPrice(toPrice);
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	private void doPage(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			GoodService gs, PrintWriter pw) throws JsonProcessingException {
		String details = request.getParameter("id");
		Map<String, Object> map = new HashMap<>();
		gs.getUserGoodsDaoImpl().setUserId(us.getUserId());
		switch (details) {
		case "totalPageAmount":
			map.put("tpa", gs.getTotalPageAmout());
			break;
		case "currentPageIndex":
			map.put("cpi", gs.getCurrentPageIndex());
			break;
		default:
			int pageIndex = new Integer(details);
			gs.getUserGoodsDaoImpl().setCurrentPageIndex(pageIndex);
			gs.getUserGoodsDaoImpl().doNewPageList();
			map.put("goods", gs.getUserGoodsDaoImpl().getCurrentPageList());
			break;
		}
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
