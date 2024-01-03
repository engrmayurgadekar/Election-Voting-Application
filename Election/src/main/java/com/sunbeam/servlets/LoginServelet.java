package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.pojos.User;

@WebServlet("/login")
public class LoginServelet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String email = req.getParameter("email");
	String password = req.getParameter("passwd");
	User user = null;
	boolean success = false;
	try (UserDao dao = new UserDaoImpl()){
		
		Optional<User> useropt = dao.findByEmail(email);
			if (useropt.isPresent()) {
				user = useropt.get();
			
				if (password.equals(user.getPassword())) {
					success = true;	
				}
			}
 		
	} catch (Exception e) {
		e.printStackTrace();
		throw new ServletException();
	}
	resp.setContentType("text/html");
	PrintWriter out = resp.getWriter();
	out.println("<html>");
	out.println("<head>");
	out.println("<title>Login</title>");
	out.println("</head>");
	out.println("<body>");
	if (success) {
		
		HttpSession session = req.getSession();
		session.setAttribute("curUser", user);
				
		String uname = user.getFirstName() + "_"+ user.getLastName();
		Cookie c = new Cookie("username", uname);
		c.setMaxAge(3600);
		resp.addCookie(c);		

		if (user.getRole().equals("voter")) {
			resp.sendRedirect(resp.encodeRedirectURL("candlist"));
		}
		else {
			resp.sendRedirect(resp.encodeRedirectURL("result"));
		}
	}
	else {
		out.println("Invalid Credentials! <br/><br/>");
		out.println("<a href='index.html'> Login Agin</a>");
		
		out.println("<body>");	
		out.println("</html>");
	}
	
	}
	
	
}
