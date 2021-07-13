package com.group.calendar.control;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group.calendar.dto.Schedule;
import com.group.calendar.dto.ScheduleType;
import com.group.calendar.service.ScheduleService;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;

/**
 * Servlet implementation class AddScheduleServlet
 */
public class AddScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		String targetId = session.getAttribute("id").toString();
		String targetId = "MSD002";
		String skdInsertType = request.getParameter("calendarType");
		System.out.println(skdInsertType);
		String skdInsertTitle = request.getParameter("title");
		System.out.println(skdInsertTitle);
		System.out.println(request.getParameter("start"));
		Timestamp skdInsertStart= Timestamp.valueOf(request.getParameter("start")+":00");
		System.out.println(request.getParameter("start"));
		System.out.println(request.getParameter("end"));
		Timestamp skdInsertEnd = Timestamp.valueOf(request.getParameter("end")+":00");
		System.out.println(request.getParameter("end"));
		String skdInsertContent = request.getParameter("content");
		String skdInsertShare = request.getParameter("teamOrPersonal");
		System.out.println(skdInsertType + "/"+skdInsertTitle+ "/" +skdInsertStart+ 
				"/"+skdInsertEnd + "/" +skdInsertContent+ "/"+skdInsertShare);
		System.out.println(request.getParameter("teamOrPersonal"));
		
		ScheduleService service;
		ServletContext sc = getServletContext();
		ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ScheduleService.getInstance();
		
		
		try {
			Schedule s = new Schedule();
			Employee emp = new Employee();
			ScheduleType st = new ScheduleType();
			emp.setEmployee_id(targetId);
			st.setSkd_type(skdInsertType);
			
			s.setSkd_id(emp);
			s.setSkd_type(st);
			s.setSkd_title(skdInsertTitle);
			s.setSkd_start_date(skdInsertStart);
			s.setSkd_end_date(skdInsertEnd);
			s.setSkd_content(skdInsertContent);
			s.setSkd_share(skdInsertShare);
			service.addSkd(s);
			System.out.println("servlet s " +s);
			System.out.println(s);
		} catch (AddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
