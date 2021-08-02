package com.group.calendar.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.calendar.dto.Schedule;
import com.group.calendar.service.ScheduleService;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;

/**
 * Servlet implementation class ShowByDate
 */
public class ShowByDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowByDate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  ObjectMapper mapper;
	      mapper = new ObjectMapper();
	      mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
	      String jsonStr = "";
	      
	      String id = request.getParameter("id");
	      String sdate = request.getParameter("start_date");
	      String edate = request.getParameter("end_date");
	      String dept = request.getParameter("dept_id");
	      ScheduleService service;
	      ServletContext sc = getServletContext();
	      ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
	      service = ScheduleService.getInstance();
	      
	      try {
	         Department dpt_id = new Department();
	         dpt_id.setDepartment_id(dept);
	         Employee em = new Employee(id, null, dpt_id, null, null, null, null, null, 1, null);
	         em.setEmployee_id(id);
	         em.setDepartment(dpt_id);
	         List<Schedule> list = service.findByDate(em, sdate, edate);

	         jsonStr = mapper.writeValueAsString(list);
	         response.setContentType("application/json;charset=utf-8");
	         response.getWriter().print(jsonStr);
	     //    System.out.println(jsonStr);
	      } catch (FindException e) {
	         e.printStackTrace();
	      }
	}

}
