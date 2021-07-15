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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.service.SideDocsService;

/**
 * Servlet implementation class ShowSideBarCount
 */
public class ShowSideBarCntAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	showdeptempservlet
//
//	department id 


  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//전체 합치면 찐 id 넣기 
		String loginId = request.getParameter("id");
	
		//요청전달데이터 얻기 
		SideDocsService service;
		ServletContext sc = getServletContext();
		SideDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = SideDocsService.getInstance();
		
		
		try {
			int n = service.findCntAll("id");

			List<Integer> apCntList=new ArrayList<Integer>();
			System.out.println(n);
			apCntList.add(0, service.findCntAll(loginId));
			apCntList.add(1, service.findCntWait(loginId));
			apCntList.add(2, service.findCntOk(loginId));
			apCntList.add(3, service.findCntNo(loginId));
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
