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
		HttpSession session = request.getSession();
		String targetId = session.getAttribute("id").toString();
		String skdInsertType = request.getParameter("calendarType");	
		String skdInsertTitle = request.getParameter("title");
		Timestamp skdInsertStart= Timestamp.valueOf(request.getParameter("start")+":00");
		Timestamp skdInsertEnd = Timestamp.valueOf(request.getParameter("end")+":00");
		String skdInsertContent = request.getParameter("content");
		String skdInsertShare = request.getParameter("teamOrPersonal");
		
		ScheduleService service;
		ServletContext sc = getServletContext();
		ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ScheduleService.getInstance();

		System.out.println("아이디 :"+targetId);
		System.out.println("팀or개인일정 :"+skdInsertShare);
		System.out.println("일정종류 :"+skdInsertType);
		System.out.println("일정 내용 :"+skdInsertContent);
		System.out.println("일정 제목 :"+skdInsertTitle);
		System.out.println("일정 시작 시간 :"+skdInsertStart);
		System.out.println("일정 종료 시간"+ skdInsertEnd);
	
		
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
			System.out.println("AddScheduleServlet / 일정이 추가되었습니다.");
		} catch (AddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
