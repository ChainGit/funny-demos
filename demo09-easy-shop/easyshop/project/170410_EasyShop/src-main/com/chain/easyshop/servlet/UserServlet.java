package com.chain.easyshop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chain.easyshop.bean.User;
import com.chain.easyshop.exception.EasyShopRuntimeException;
import com.chain.easyshop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
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
		PrintWriter pw = response.getWriter();

		String action = request.getParameter("a");

		if (action == null) {
			pw.println("{}");
			return;
		}

		switch (action) {
		case "add":
			doAdd(request, response, mapper, us, pw);
			break;
		case "exit":
			doExit(request, response, mapper, us, pw);
			break;
		case "info":
			doInfo(request, response, mapper, us, pw);
			break;
		case "update":
			doUpdate(request, response, mapper, us, pw);
			break;
		case "getTrades":
			doGetTrades(request, response, mapper, us, pw);
			break;
		default:
			pw.println("{}");
			break;
		}
	}

	private void doAdd(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			PrintWriter pw) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String balance = request.getParameter("balance");
		double bal = new Double(balance);
		long res = UserService.addUser(username, password, bal);
		response.sendRedirect(request.getContextPath() + "/sign.html?res=" + res);
	}

	private void doExit(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			PrintWriter pw) {
		request.getSession().invalidate();
	}

	private void doGetTrades(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper,
			UserService us, PrintWriter pw) throws JsonProcessingException {
		String details = request.getParameter("id");
		Map<String, Object> map = new HashMap<>();
		us.getUserTradesDaoImpl().setUserId(us.getUserId());
		switch (details) {
		case "totalPageAmount":
			map.put("tpa", us.getUserTradesDaoImpl().getTotalPageAmount());
			break;
		case "currentPageIndex":
			map.put("cpi", us.getUserTradesDaoImpl().getCurrentPageIndex());
			break;
		default:
			int pageIndex = new Integer(details);
			us.getUserTradesDaoImpl().setCurrentPageIndex(pageIndex);
			us.getUserTradesDaoImpl().doNewPageList();
			map.put("trades", us.getUserTradesDaoImpl().getCurrentPageList());
			break;
		}
		pw.println(mapper.writeValueAsString(map));
	}

	private void doUpdate(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			PrintWriter pw) throws IOException {
		String passWordParam = request.getParameter("password");
		String accountBalanceParam = request.getParameter("account");

		if (passWordParam == null || accountBalanceParam == null) {
			response.sendRedirect(request.getContextPath() + "/apps/jsp/my/userinfo.jsp");
			return;
		}

		double accountBalance = 0.0d;
		try {
			accountBalance = new Double(accountBalanceParam);
			us.updateUserPassword(passWordParam);
			us.updateUserAccountBalance(accountBalance);
			response.sendRedirect(request.getContextPath() + "/apps/jsp/exit.jsp");
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/apps/jsp/my/userinfo.jsp");
			throw new EasyShopRuntimeException(e);
		}
	}

	private void doInfo(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			PrintWriter pw) throws JsonProcessingException {
		String details = request.getParameter("id");
		String userIdParam = request.getParameter("ui");
		Map<String, Object> map = new HashMap<>();
		switch (details) {
		case "getTotalTradesAmount":
			map.put("tta", us.getTotalTradesAmount());
			break;
		case "getTotalCartsAmount":
			map.put("tca", us.getTotalCartsAmount());
			break;
		case "query":
			int userId = new Integer(userIdParam);
			User user = us.getUserById(userId);
			map.put("un", user.getUserName());
			map.put("ui", user.getUserId());
			break;
		default:
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
