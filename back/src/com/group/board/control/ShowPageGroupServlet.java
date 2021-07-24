package com.group.board.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.board.dto.Board;
import com.group.board.service.BoardService;
import com.group.board.service.PageBeanService;
import com.group.exception.FindException;

/**
 * Servlet implementation class ShowPageGroupServlet
 */
public class ShowPageGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int nthPageGroup = Integer.parseInt(request.getParameter("nthPageGroup"));
		PageBeanService service;
		ServletContext sc = getServletContext();
		PageBeanService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = PageBeanService.getInstance();
		System.out.println("n "+nthPageGroup);
		List<Integer> pageList= service.selectPageGroup(nthPageGroup);
		for(int i : pageList) {
			System.out.println(i);
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(pageList);
		System.out.println(jsonStr);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);

	}

}
