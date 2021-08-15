package com.group.mypage.control;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.exception.FindException;
import com.group.mypage.dto.EmployeeLeave;
import com.group.mypage.service.EmployeeLeaveService;

/**
 * Servlet implementation class ShowMyProfileServlet
 */
public class ShowMyProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//프로필 정보 get
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = session.getAttribute("id").toString();

		EmployeeLeaveService service;
		ServletContext sc = getServletContext();
		EmployeeLeaveService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = EmployeeLeaveService.getInstance();

		try {
			EmployeeLeave el = service.showDetail(id);
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(el);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
