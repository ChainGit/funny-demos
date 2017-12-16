package com.chain.easyshop.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chain.easyshop.service.UserService;
import com.chain.easyshop.utils.SettingsUtils;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession sess = req.getSession();
		if (sess != null) {
			String status = (String) sess.getAttribute("login_status");
			if (status != null && status.equals("logined")) {
				UserService us = (UserService) sess.getAttribute("login_user_service");
				int userId = us.getUserId();
				String location = req.getServletPath();
				if (userId != adminUserId && location.contains("/admin"))
					res.sendRedirect(req.getContextPath() + "/index.jsp");
				else
					// pass the request along the filter chain
					chain.doFilter(request, response);
			} else
				doLogin(req, res);
		} else
			doLogin(req, res);

	}

	public void doLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.sendRedirect(req.getContextPath() + "/login.html");
	}

	private int adminUserId;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		adminUserId = SettingsUtils.getSetting("ADMIN_ID", Integer.class);
	}

}
