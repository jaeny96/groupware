package com.group.mypage.control;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.employee.dto.Employee;
import com.group.exception.CheckException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dto.EmployeeLeave;
import com.group.mypage.service.EmployeeLeaveService;

/**
 * Servlet implementation class ChangeMyPwdServlet
 */
public class ChangeMyPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = session.getAttribute("id").toString();
		String modiPwd = request.getParameter("modiPwd");
		String chkModiPwd = request.getParameter("chkModiPwd");
		EmployeeLeaveService service;
		ServletContext sc = getServletContext();
		EmployeeLeaveService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = EmployeeLeaveService.getInstance();

		try {
			if (modiPwd.equals(chkModiPwd)) {
				Employee emp = new Employee();
				emp.setEmployee_id(id);
				emp.setPassword(modiPwd);
				service.modify(emp);
			} else {
				throw new CheckException("비밀번호가 일치하지 않습니다");
			}
		} catch (ModifyException | CheckException e) {
			e.printStackTrace();
		}
	}

}
