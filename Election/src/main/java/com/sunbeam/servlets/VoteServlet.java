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

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.pojos.User;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		processExcute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		processExcute(req, resp);
	}

	protected void processExcute(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<center>");
		out.println("<head>");
		out.println("<title>Voted</title>");
		out.println("</head>");
		out.println("<body>");
		String uname = "";
		Cookie[] arr = req.getCookies();
		if (arr != null) {
			for (Cookie c : arr) {
				if (c.getName().equals("username")) {
					uname = c.getValue();
					break;
				}
			}
		}

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("curUser");
		if (user.getStatus() != 0) {
			out.println("You have already Voted ! Don't Cheat");
			
		} else {
				   String canddateID = req.getParameter("candidate");
			int id = Integer.parseInt(canddateID);
			try (CandidateDao dao = new CandidateDaoImpl()){
				dao.incrementVotes(id);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException();
			}
			user.setStatus(1);
			try (UserDao u = new UserDaoImpl()){
				u.updateStatus(user.getId(), true);
				out.printf(" %S your vote is registered successfully. <br/><br/>", uname);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException();			}
		}
		
		out.println("<br/><br/>");
		String url = resp.encodeRedirectURL("logout");
		out.printf("<a href='%s'>Sign Out</a>",url);
		out.println("</body>");
		out.println("</center>");
		out.println("</html>");

	}
}
