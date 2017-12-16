package com.chain.easyshop.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chain.easyshop.apps.base.ConnectionContext;
import com.chain.easyshop.utils.DBUtils;

/**
 * Servlet Filter implementation class TransactionFilter
 */
public class TransactionFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public TransactionFilter() {
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
		Connection conn = null;

		try {
			conn = DBUtils.getConnection();

			conn.setAutoCommit(false);

			ConnectionContext.getInstance().bindConnection(conn);

			// pass the request along the filter chain
			chain.doFilter(request, response);

			conn.commit();
		} catch (Exception e) {
			// 在应用中其他地方发生异常简单处理后再全部抛出，异常在这里输出，回滚操作
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				// throw new EasyShopRuntimeException(e);
			} finally {
				HttpServletRequest req = (HttpServletRequest) request;
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendRedirect(req.getContextPath() + "/error.action");
			}

		} finally {
			ConnectionContext.getInstance().removeConnection();
			DBUtils.release(conn);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
