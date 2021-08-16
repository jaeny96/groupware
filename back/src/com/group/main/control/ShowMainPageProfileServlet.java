package com.group.main.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.main.service.MainService;

/**
 * Servlet implementation class ShowMainPageProfileServlet
 */
public class ShowMainPageProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = session.getAttribute("id").toString();

		MainService service;
		ServletContext sc = getServletContext();
		MainService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = MainService.getInstance();

		try {
			Employee emp = service.showProfile(id);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(emp);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
