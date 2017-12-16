package com.chain.easyshop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chain.easyshop.service.GoodService;
import com.chain.easyshop.service.TradeService;
import com.chain.easyshop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class TradeServlet
 */
public class TradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TradeServlet() {
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
		TradeService ts = new TradeService(us.getUserId(), us, new GoodService(us.getUserId()));
		PrintWriter pw = response.getWriter();

		String action = request.getParameter("a");

		if (action == null) {
			pw.println("{}");
			return;
		}

		switch (action) {
		case "check":
			doCheck(request, response, mapper, us, ts, pw);
			break;
		case "confirm":
			doConfirm(request, response, mapper, us, ts, pw);
			break;
		case "cash":
			doCash(request, response, mapper, us, ts, pw);
			break;
		default:
			pw.println("{}");
			break;
		}
	}

	private void doConfirm(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper,
			UserService us, TradeService ts, PrintWriter pw) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		map.put("res", ts.confirm(mapper));
		map.put("tm", ts.getTotalMoney());
		pw.println(mapper.writeValueAsString(map));
	}

	private void doCash(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			TradeService ts, PrintWriter pw) throws IOException {
		Map<String, Object> map = new HashMap<>();
		ts.cash();
		map.put("res", 1);
		pw.println(mapper.writeValueAsString(map));
	}

	private void doCheck(HttpServletRequest request, HttpServletResponse response, ObjectMapper mapper, UserService us,
			TradeService ts, PrintWriter pw) throws IOException {
		Map<String, Object> map = new HashMap<>();

		String details = request.getParameter("id");

		switch (details) {
		case "cart":
			String userCartParam = request.getParameter("uc");
			Map<String, Object> cc = ts.checkCart(mapper, userCartParam);
			if (cc.containsKey("cc") && cc.get("cc") instanceof Integer && (Integer) cc.get("cc") == 1)
				map.put("res", 1);
			else
				map.put("res", cc);
			break;
		case "account":
			String pass = request.getParameter("ps");
			map.put("res", ts.checkAccount(pass));
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
