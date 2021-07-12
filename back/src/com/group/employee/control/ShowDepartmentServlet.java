package com.group.employee.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.approval.dto.Document;
import com.group.employee.dto.Department;
import com.group.employee.service.DepartmentService;
import com.group.exception.FindException;

/**
 * Servlet implementation class ShowDepartmentServlet
 */
public class ShowDepartmentServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DepartmentService service;
		ServletContext sc = getServletContext();
		DepartmentService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DepartmentService.getInstance();

		try {
			List<Department> deptList = service.showDept();
//			for(Document d : docList) {
//				System.out.println(d);
//			}
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(deptList);
			System.out.println(jsonStr);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
