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
import com.group.exception.RemoveException;

/**
 * Servlet implementation class deleteScheduleServlet
 */
public class deleteScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//MS: Session 가져오기 
		 HttpSession session = request.getSession();
		String targetId = session.getAttribute("id").toString();
		//String targetId = request.getParameter("removeSkdId");
		System.out.println(targetId);
//		System.out.println(request.getParameter("removeSkdno"));
		
		//MS : skd_no 변경
		int skdDeleteNo = Integer.parseInt(request.getParameter("skd_no"));
		System.out.println(targetId + "/" + skdDeleteNo);

		ScheduleService service;
		ServletContext sc = getServletContext();
		ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ScheduleService.getInstance();

		try {
			Schedule s = new Schedule();
			Employee emp = new Employee();

			emp.setEmployee_id(targetId);
			s.setSkd_id(emp);
			s.setSkd_no(skdDeleteNo);

			service.deleteSkd(s);
			//System.out.println("일정이 삭제되었습니다");
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("delete catch");
		}
	}

}
