package com.group.approval.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.approval.service.DocsWriteService;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;

/**
 * Servlet implementation class ShowApLineStaffListServlet
 */
public class ShowApLineStaffListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deptName = request.getParameter("apLineDeptName");
		System.out.println(deptName);
		DocsWriteService service;
		ServletContext sc = getServletContext();
		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DocsWriteService.getInstance();

		try {
			List<Employee> empList = service.staff(deptName);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(empList);
			System.out.println(jsonStr);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (com.group.approval.exception.FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
