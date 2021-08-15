package com.group.employee.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.employee.dto.Department;
import com.group.employee.service.DepartmentService;
import com.group.exception.FindException;

/**
 * Servlet implementation class ShowDepartmentServlet
 */
public class ShowDepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//부서 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DepartmentService service;
		ServletContext sc = getServletContext();
		DepartmentService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DepartmentService.getInstance();

		try {
			List<Department> deptList = service.showDept();
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(deptList);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
