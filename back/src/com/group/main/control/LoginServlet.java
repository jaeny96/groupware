package com.group.main.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.main.service.MainService;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ObjectMapper mapper;
		mapper = new ObjectMapper();
		String jsonStr = "";

		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		System.out.println(id+"/"+pwd);
//		String id = "DEV003";
//		String pwd = "DEV0031234";

		ServletContext sc = getServletContext();
		MainService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		MainService service = MainService.getInstance();

		HttpSession session = request.getSession();
		session.removeAttribute("loginInfo");
		Map<String, Object> map = new HashMap<>();
		
		try {
			Employee loginInfo = service.login(id, pwd);
			session.setAttribute("id", loginInfo.getEmployee_id());
			session.setAttribute("dept", loginInfo.getDepartment().getDepartment_id());
			session.setAttribute("pwd", loginInfo.getPassword());
			map.put("status", 1);
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}

		jsonStr = mapper.writeValueAsString(map);
		response.setContentType("application/json;charset=utf-8"); //응답형식지정
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
	}

}