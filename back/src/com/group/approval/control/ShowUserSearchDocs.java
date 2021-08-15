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
import com.group.approval.exception.SearchException;
import com.group.approval.service.ConfirmDocsService;

/**
 * Servlet implementation class ShowUserSearchDocs
 */
public class ShowUserSearchDocs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//검색 관련 서블릿 
		HttpSession session = request.getSession();
		String id= session.getAttribute("id").toString();//id값
		String uCategory = request.getParameter("searchCategory");//제목/내용 구분란
		String uSearch = request.getParameter("searchWord");//사용자가 검색한 값 
		String uStatus = request.getParameter("status");//대기/반려/승인/전체 구분란
		System.out.println(uSearch);
	
		ConfirmDocsService service;
		ServletContext sc = getServletContext();
		ConfirmDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ConfirmDocsService.getInstance();
		
		try {
			System.out.println(uCategory);
			if(uCategory.equals("content")) {//내용 검색시
				List<Document> apDocsList = service.findMySearchContent(id,uSearch,uStatus);
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				String jsonStr = mapper.writeValueAsString(apDocsList);
				//System.out.println(jsonStr);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print(jsonStr);
			}else if(uCategory.equals("title")){//제목 검색시
				List<Document> apDocsList = service.findMySearchTitle(id,uSearch,uStatus);
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				String jsonStr = mapper.writeValueAsString(apDocsList);
				//System.out.println(jsonStr);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print(jsonStr);
			}
		
		}catch(FindException e) {
			e.printStackTrace();
		}catch(SearchException e) {
			e.printStackTrace();
		}
	}

}
