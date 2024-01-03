package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.pojos.Candidate;

@WebServlet("/candedit")
public class CandidateEditServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uid = req.getParameter("id");
		int id = Integer.parseInt(uid);
		Optional<Candidate> candidateopt = null;

		try (CandidateDao dao = new CandidateDaoImpl()) {
			candidateopt = dao.findById(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		Candidate cand = candidateopt.get();

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Edit</title>");
		out.println("</head>");
		out.println("<body><br/><br/>");
		out.println("<center>");
		out.println("<form method=\"post\" action=\"candedit\">");
		out.printf("<input type=\"hidden\"  name=\"id\" value=\"%s\"> <br/><br/>", cand.getId());
		out.printf("Name    :<input type=\"text\"  name=\"name\" value=\"%s\"><br/><br/>", cand.getName());
		out.printf("Party   :<input type=\"text\"  name=\"party\" value=\"%s\"><br/><br/>", cand.getParty());
		out.printf("Votes   :<input type=\"text\"  name=\"votes\" value=\"%s\" readonly><br/><br/>", cand.getVotes());
		out.println("<input type=\"submit\" value=\"Update Candidate\"/>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int count = 0;
		String candId = req.getParameter("id");
		String name = req.getParameter("name");
		String party = req.getParameter("party");
		String votes = req.getParameter("votes");

		Candidate cand = new Candidate(Integer.parseInt(candId), name, party, Integer.parseInt(votes));

		try (CandidateDao dao = new CandidateDaoImpl()) {
			count = dao.update(cand);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		req.setAttribute("message", "Candidate Edited Count is : " + count);
		RequestDispatcher rd = req.getRequestDispatcher("result");
		rd.forward(req, resp);
	}

}
