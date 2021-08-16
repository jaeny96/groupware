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
 * Servlet implementation class ShowPageGroupServlet
 */
public class ShowPageGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//페이지 그룹 반환
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int nthPageGroup = Integer.parseInt(request.getParameter("nthPageGroup"));
		PageBeanService service;
		ServletContext sc = getServletContext();
		PageBeanService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = PageBeanService.getInstance();
		List<Integer> pageList= service.selectPageGroup(nthPageGroup);

		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(pageList);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);

	}

}
