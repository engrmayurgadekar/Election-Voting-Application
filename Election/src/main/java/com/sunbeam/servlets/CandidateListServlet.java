package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.group.Response;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.pojos.Candidate;

@WebServlet("/candlist")
public class CandidateListServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Candidate> list = new ArrayList<>();

		try (CandidateDao dao = new CandidateDaoImpl()) {

			list = dao.findAll();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<center>");
		out.println("<head>");
		out.println("<title>Candidates</title>");
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
		out.printf("Hello, Voter %s <hr/>", uname);

		ServletContext ctx = req.getServletContext();
		String msg = (String) ctx.getAttribute("announcement");
		if (msg!=null) {
			out.println("Announcement :"+msg +"<br/><br/>");
		}

		String url = resp.encodeURL("vote");
		out.printf("<form method='post' action='%s'>", url);
		for (Candidate c : list)
			out.printf("<input type='radio' name='candidate' value='%s'/> %s -- %s <br/>\n", c.getId(), c.getName(),
					c.getParty());
		out.println("<input type='submit' value='vote'/>");
		out.println("</form>");
		out.println("</body>");
		out.println("</center>");
		out.println("</html>");

	}
}
