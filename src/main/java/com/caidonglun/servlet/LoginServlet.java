package com.caidonglun.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("login doget!!!");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("login dopost");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			// 设置浏览器是否保留获取cookie 默认好像5天 不是很建议使用
			if (subject.isRemembered()) {
				System.out.println("---isRememberMe--");
			} else {
				token.setRememberMe(true);
				System.out.println("---false---");
			}
			subject.login(token);
			resp.sendRedirect("success.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("errorInfo", "用户名或密码错误");
		}

	}

}
