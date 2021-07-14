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
 * Servlet implementation class ShowEmpDetailServlet
 */
public class ShowEmpDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("empId");
		String name = request.getParameter("empName");
		EmployeeService service;
		ServletContext sc = getServletContext();
		EmployeeService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = EmployeeService.getInstance();

		try {
			Employee empInfo = new Employee();
			empInfo.setEmployee_id(id);
			empInfo.setName(name);
			Employee emp = service.showDetail(empInfo);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(emp);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
