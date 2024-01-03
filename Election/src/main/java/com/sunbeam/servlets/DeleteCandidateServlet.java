package com.sunbeam.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;

@WebServlet("/canddel")
public class DeleteCandidateServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String sid = req.getParameter("id");
		int id = Integer.parseInt(sid);
		int count = 0;
		try (CandidateDao dao = new CandidateDaoImpl()){
			
			count  = dao.deleteById(id);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);

	}
		req.setAttribute("message","Candidate Deleted Count is : "+ count);
		//resp.sendRedirect("result");
		RequestDispatcher rd = req.getRequestDispatcher("result");
		rd.forward(req, resp);
	
	}
}