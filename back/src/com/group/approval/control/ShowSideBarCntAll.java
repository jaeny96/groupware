package com.group.approval.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.service.SideDocsService;

/**
 * Servlet implementation class ShowSideBarCount
 */
public class ShowSideBarCntAll extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//사이드바 목록 개수 관련 서블릿 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//String loginId = request.getParameter("id");
		HttpSession session = request.getSession();
		String id= session.getAttribute("id").toString();

		SideDocsService service;
		ServletContext sc = getServletContext();
		SideDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = SideDocsService.getInstance();
		
		
		try {
			List<Integer> apCntList=new ArrayList<Integer>();
		
			apCntList.add(0, service.findCntAll(id));
			apCntList.add(1, service.findCntWait(id));
			apCntList.add(2, service.findCntOk(id));
			apCntList.add(3, service.findCntNo(id));
			System.out.println(apCntList);
			//json라이브러리로 json문자열 형태로 응답
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(apCntList);
			System.out.println(jsonStr);
			response.setContentType("application/json;charset=utf-8");
			//데이터 전달S
			response.getWriter().print(jsonStr);
		}catch(FindException e) {
			e.printStackTrace();
		}
	}

	
}
