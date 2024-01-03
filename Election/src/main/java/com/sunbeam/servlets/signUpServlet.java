package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.pojos.User;

@WebServlet("/signUp")
public class signUpServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
		
	}
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String email =req.getParameter("email");
		String password = req.getParameter("passwd");
		
		String date = req.getParameter("dob");
		SimpleDateFormat sdef = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate=null;
		java.sql.Date sqlDate=null;
		try {
			utilDate = sdef.parse(date);
	 sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try (UserDao dao = new UserDaoImpl()){
			User usr = new User();
			usr.setFirstName(fname);
			usr.setLastName(lname);
			usr.setEmail(email);
			usr.setPassword(password);
			usr.setBirth(sqlDate);
			int row = dao.save(usr);
			if (row > 0) {
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>SignUp</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<center>");
				out.println("<br/><br/>");
				out.println("<h1>Candidate Updated Successfully!</h1>");
				out.println("</center>");
				out.println("</body>");
				out.println("</html>");				
			}
			else {
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>SignUp</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<center>");
				out.println("<br/><br/>");
				out.println("<h1>Candidate Not Updated !</h1>");
				out.println("</center>");
				out.println("</body>");
				out.println("</html>");				
			}
 					
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
	}
	
}
