package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		process(req, resp);	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);		
	}
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<center>");	
		out.println("<head>");
		out.println("<title>Logout</title>");
		out.println("</head>");
		out.println("<body>");
		String uname = "";
		Cookie []arr = req.getCookies();
		if (arr != null) {
			for (Cookie c : arr) {
				if (c.getName().equals("username")) {
					uname = c.getValue();
					break;
				}
			}
		}
		HttpSession session = req.getSession();
		session.invalidate();
		out.printf("Thank you %s. <br/><br/>",uname);
		out.println("<a href='index.html'>Login Again</a>");
		out.println("</body>");
		out.println("</center>");
		out.println("</html>");	
	}
	
}
