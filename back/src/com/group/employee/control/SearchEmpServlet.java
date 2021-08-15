package com.group.employee.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.employee.dto.Employee;
import com.group.employee.service.EmployeeService;
import com.group.exception.FindException;

/**
 * Servlet implementation class SearchEmpServlet
 */
public class SearchEmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//사원명 검색
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String word = request.getParameter("word");
		EmployeeService service;
		ServletContext sc = getServletContext();
		EmployeeService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = EmployeeService.getInstance();

		try {
			List<Employee> empList = service.searchEmp(word);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(empList);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
