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
 * Servlet implementation class ShowAllEmployeeServlet
 */
public class ShowAllEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//사원 전체 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EmployeeService service;
		ServletContext sc = getServletContext();
		EmployeeService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = EmployeeService.getInstance();

		try {
			List<Employee> empList = service.showAll();
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(empList);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
