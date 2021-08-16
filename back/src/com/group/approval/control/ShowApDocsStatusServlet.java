package com.group.approval.control;

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
import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.service.SideDocsService;

/**
 * Servlet implementation class ShowApDocsStateServlet
 */
public class ShowApDocsStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	   //사이드 바를 누르면 전체/대기/반려/승인 상태에 따라 목록 보여주는 서블릿
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//현재 로그인 한 id값 받아오기
			HttpSession session = request.getSession();
			String id= session.getAttribute("id").toString();
			//현재 문서상태 값 받아오기 
			String documentStatus =request.getParameter("status");
			
			SideDocsService service;
			ServletContext sc = getServletContext();
			SideDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
			service = SideDocsService.getInstance();
			
			try {
				List<Document> apDocsList = service.findDocsStatus(id,documentStatus);
		
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				String jsonStr = mapper.writeValueAsString(apDocsList);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print(jsonStr);
			}catch(FindException e) {
				e.printStackTrace();
			}
		}

}
