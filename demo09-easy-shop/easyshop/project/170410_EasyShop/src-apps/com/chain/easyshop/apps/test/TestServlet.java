package com.chain.easyshop.apps.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chain.easyshop.bean.User;
import com.chain.easyshop.inter.dao.impl.GoodRemarksDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserCartDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserGoodsDaoImpl;
import com.chain.easyshop.inter.dao.impl.UserTradesDaoImpl;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");

		// int i = 1 / 0;

		PrintWriter writer = response.getWriter();
		writer.append("Served at: ").append(request.getContextPath());
		writer.println("<br/>");
		writer.println("<hr/>");
		writer.println("<br/>");

		writer.println(request.getCharacterEncoding());
		writer.println("<br/>");
		writer.println("<hr/>");
		writer.println("<br/>");

		UserDaoImpl udi = new UserDaoImpl();
		User u = udi.getUser("test");
		writer.println(u);
		writer.println("<br/>");
		writer.println("<hr/>");
		writer.println("<br/>");

		UserGoodsDaoImpl ugdi = new UserGoodsDaoImpl();
		writer.println(ugdi.getUserGoods(new UserDaoImpl().getUser("test").getUserId()));
		writer.println("<br/>");
		writer.println("<br/>");
		ugdi.doFirstPage();
		writer.println(ugdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		ugdi.doNextPage();
		ugdi.doNextPage();
		writer.println(ugdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		ugdi.doPrevPage();
		writer.println(ugdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		ugdi.doLastPage();
		writer.println(ugdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<hr/>");
		writer.println("<br/>");

		UserTradesDaoImpl urdi = new UserTradesDaoImpl(1001);
		writer.println(urdi.getUserTrades());
		writer.println("<br/>");
		writer.println("<br/>");
		urdi.doFirstPage();
		writer.println(urdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		urdi.doNextPage();
		urdi.doNextPage();
		writer.println(urdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		urdi.doPrevPage();
		writer.println(urdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		urdi.doLastPage();
		writer.println(urdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<hr/>");
		writer.println("<br/>");

		UserCartDaoImpl ucdi = new UserCartDaoImpl(1001);
		writer.println(ucdi.getUserCart());
		writer.println("<br/>");
		writer.println("<br/>");
		ucdi.doFirstPage();
		writer.println(ucdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		ucdi.doNextPage();
		ucdi.doNextPage();
		writer.println(ucdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		ucdi.doPrevPage();
		writer.println(ucdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		ucdi.doLastPage();
		writer.println(ucdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<hr/>");
		writer.println("<br/>");

		GoodRemarksDaoImpl grdi = new GoodRemarksDaoImpl(80000);
		writer.println(grdi.getGoodRemarks());
		writer.println("<br/>");
		writer.println("<br/>");
		grdi.doFirstPage();
		writer.println(grdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		grdi.doNextPage();
		grdi.doNextPage();
		writer.println(grdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		grdi.doPrevPage();
		writer.println(grdi.getCurrentPageList());
		writer.println("<br/>");
		writer.println("<br/>");
		grdi.doLastPage();
		writer.println(grdi.getCurrentPageList());
		writer.println("<br/>");

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
