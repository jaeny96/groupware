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
import com.group.exception.ModifyException;

/**
 * Servlet implementation class modifyScheduleServlet
 */
public class modifyScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		String targetId = session.getAttribute("id").toString();
		
		String targetId= "MSD002";
		String skdUpdateType = request.getParameter("calendarType");
		System.out.println("type: "+skdUpdateType);
		String skdUpdateTitle = request.getParameter("title");
		System.out.println("title : "+skdUpdateTitle);
		System.out.println("start : "+request.getParameter("start")+":00");
		System.out.println("end : "+request.getParameter("end")+":00");
		Timestamp skdUpdateStart= Timestamp.valueOf(request.getParameter("start")+":00");
		Timestamp skdUpdateEnd = Timestamp.valueOf(request.getParameter("end")+":00");
		String skdUpdateContent = request.getParameter("content");
		String skdUpdateShare = request.getParameter("teamOrPersonal");
		int skdUpdateNo = 32;
				//Integer.parseInt(request.getParameter("updateSkdno"));
				System.out.println(skdUpdateType + "/" + skdUpdateTitle + "/" + skdUpdateStart
						+ "/"+skdUpdateEnd+ "/" + skdUpdateContent + "/" +skdUpdateShare );
	
		ScheduleService service;
		ServletContext sc = getServletContext();
		ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ScheduleService.getInstance();
		

		try {
			Schedule s = new Schedule();
			Employee emp = new Employee();
			ScheduleType st = new ScheduleType();
			emp.setEmployee_id(targetId);
			st.setSkd_type(skdUpdateType);
			
			s.setSkd_id(emp);
			s.setSkd_type(st);
			s.setSkd_title(skdUpdateTitle);
			s.setSkd_start_date(skdUpdateStart);
			s.setSkd_end_date(skdUpdateEnd);
			s.setSkd_content(skdUpdateContent);
			s.setSkd_share(skdUpdateShare);
			s.setSkd_no(skdUpdateNo);
			service.modifySkd(s);
		} catch (ModifyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
