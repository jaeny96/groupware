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
import com.group.employee.dto.Employee;
import com.group.exception.FindException;

/**
 * Servlet implementation class ShowByDetail
 */
public class ShowByDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  ObjectMapper mapper;
	      mapper = new ObjectMapper();
	      mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm"));
	      String jsonStr = "";
	      
	      int no = Integer.parseInt(request.getParameter("skd_no"));
	     
	      ScheduleService service;
	      ServletContext sc = getServletContext();
	      ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
	      service = ScheduleService.getInstance();
	      
	      try {
	    	
	         Schedule list = service.findByDetail(no);
	         jsonStr = mapper.writeValueAsString(list);
	         response.setContentType("application/json;charset=utf-8");
	         response.getWriter().print(jsonStr);
	    //     System.out.println(jsonStr);
	      } catch (FindException e) {
	         e.printStackTrace();
	      }
	
	}

}