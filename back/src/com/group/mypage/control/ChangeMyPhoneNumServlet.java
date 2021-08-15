package com.group.mypage.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group.employee.dto.Employee;
import com.group.exception.ModifyException;
import com.group.mypage.service.EmployeeLeaveService;

/**
 * Servlet implementation class ChangeMyPhoneNumServlet
 */
public class ChangeMyPhoneNumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//핸드폰 번호 변경
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = session.getAttribute("id").toString();
		String modiPhone = request.getParameter("modiPhone");
		EmployeeLeaveService service;
		ServletContext sc = getServletContext();
		EmployeeLeaveService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = EmployeeLeaveService.getInstance();

		try {
			Employee emp = new Employee();
			emp.setEmployee_id(id);
			emp.setPhone_number(modiPhone);
			service.modify(emp);
		} catch (ModifyException e) {
			e.printStackTrace();
		}
	}

}
