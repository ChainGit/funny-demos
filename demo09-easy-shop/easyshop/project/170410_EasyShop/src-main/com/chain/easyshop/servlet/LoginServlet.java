package com.chain.easyshop.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chain.easyshop.bean.User;
import com.chain.easyshop.exception.EasyShopRuntimeException;
import com.chain.easyshop.service.UserService;
import com.chain.easyshop.utils.SettingsUtils;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int adminUserId;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		adminUserId = SettingsUtils.getSetting("ADMIN_ID", Integer.class);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username == null || password == null) {
			doLogin(request, response);
			return;
		}

		try {
			UserService us = new UserService(username);
			User user = us.getUser();
			if (user != null && user.getUserName().equals(username) && user.getUserPass().equals(password)) {
				HttpSession sess = request.getSession(true);
				if (sess != null) {
					sess.setAttribute("login_status", "logined");
					sess.setAttribute("login_user_service", us);
					if (us.getUserId() == adminUserId)
						response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
					else
						response.sendRedirect(request.getContextPath() + "/index.jsp");
					return;
				}
			} else {
				doLogin(request, response);
				return;
			}
		} catch (Exception e) {
			throw new EasyShopRuntimeException(e);
		}
	}

	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/login.html");
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
