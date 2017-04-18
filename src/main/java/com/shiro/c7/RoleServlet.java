package com.shiro.c7;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@SuppressWarnings("serial")
@WebServlet(name="roleServlet",urlPatterns = "/role")
public class RoleServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Subject subject = SecurityUtils.getSubject();
		subject.checkRole("admin");
		req.getRequestDispatcher("/WEB-INF/jsp/hasRole.jsp").forward(req, resp);
	}

	
}
