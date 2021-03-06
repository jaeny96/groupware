package com.group.board.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.board.service.PageBeanService;

/**
 * Servlet implementation class ShowTotalPageServlet
 */
public class ShowTotalPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageBeanService service;
		ServletContext sc = getServletContext();
		PageBeanService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = PageBeanService.getInstance();

		int totalPage= service.selectTotalPage();
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(totalPage);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
	}

}
