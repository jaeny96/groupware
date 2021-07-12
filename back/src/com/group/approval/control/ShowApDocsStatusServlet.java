package com.group.approval.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class ShowApDocsStateServlet
 */
public class ShowApDocsStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	   //사이드 바를 누르면 전체 목록 보여주는 서블릿
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			//나중에 페이지 요소 추가하기
			//int currentPage = Integer.parseInt(request.getPa)
			
			//전체 합치면 찐 id 넣기 
			String loginId = request.getParameter("id");
			String documentStatus =request.getParameter("status");
			System.out.println(documentStatus);
			//요청전달데이터 얻기 
			SideDocsService service;
			ServletContext sc = getServletContext();
			SideDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
			service = SideDocsService.getInstance();
			
			try {
				List<Document> apDocsList = service.findDocsStatus(loginId,documentStatus);
				//json라이브러리로 json문자열 형태로 응답
				ObjectMapper mapper = new ObjectMapper();
				//보낼때 데이터 포맷
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				String jsonStr = mapper.writeValueAsString(apDocsList);
				System.out.println(jsonStr);
				response.setContentType("application/json;charset=utf-8");
				//데이터 전달S
				response.getWriter().print(jsonStr);
			}catch(FindException e) {
				e.printStackTrace();
			}
		}

}
