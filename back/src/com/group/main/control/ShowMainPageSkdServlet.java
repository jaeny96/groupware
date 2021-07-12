package com.group.main.control;

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
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.main.service.MainService;

/**
 * Servlet implementation class ShowMainPageSkdServlet
 */
public class ShowMainPageSkdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = session.getAttribute("id").toString();
		String dept = session.getAttribute("dept").toString();
		
		MainService service;
		ServletContext sc = getServletContext();
		MainService.envProp=sc.getRealPath(sc.getInitParameter("env"));
		service = MainService.getInstance();
		
		try {
			Employee emp = new Employee();
			emp.setEmployee_id(id);
			Department deptObj = new Department();
			deptObj.setDepartment_id(dept);
			emp.setDepartment(deptObj);
			
			List<Schedule> skdList = service.showTodaySkd(emp);
			for(Schedule s :skdList) {
				System.out.println(s);
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("hh:mm"));

			String jsonStr = mapper.writeValueAsString(skdList);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
