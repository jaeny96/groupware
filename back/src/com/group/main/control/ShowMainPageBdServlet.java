package com.group.main.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.board.dto.Board;
import com.group.exception.FindException;
import com.group.main.service.MainService;

/**
 * Servlet implementation class ShowMainPageBdServlet
 */
public class ShowMainPageBdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MainService service;
		ServletContext sc = getServletContext();
		MainService.envProp=sc.getRealPath(sc.getInitParameter("env"));
		service = MainService.getInstance();
		
		try {
			List<Board> bdList = service.showRecentBd();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

			String jsonStr = mapper.writeValueAsString(bdList);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
