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
 * Servlet Filter implementation class AdminFilter
 */
public class AdminFilter implements Filter {

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
		UserService us = (UserService) sess.getAttribute("login_user_service");
		if (us == null) {
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return;
		}

		int userId = us.getUserId();
		// 简单的验证，应该验证动态KEY
		if (userId != adminUserId) {
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return;
		} else
			// pass the request along the filter chain
			chain.doFilter(request, response);
	}

	private int adminUserId;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		adminUserId = SettingsUtils.getSetting("ADMIN_ID", Integer.class);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
