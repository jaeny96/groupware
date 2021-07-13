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
import com.group.approval.exception.SearchException;
import com.group.approval.service.ConfirmDocsService;

/**
 * Servlet implementation class ShowUserSearchDocs
 */
public class ShowUserSearchDocs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uId = request.getParameter("id");
		String uCategory = request.getParameter("searchCategory");
		String uSearch = request.getParameter("search");
		System.out.println(uSearch);
		//요청전달데이터 얻기 
				
		ConfirmDocsService service;
		ServletContext sc = getServletContext();
		ConfirmDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ConfirmDocsService.getInstance();
		
		try {
			System.out.println(uCategory);
			if(uCategory.equals("content")) {
				List<Document> apDocsList = service.findMySearchContent(uId,uSearch);
				//json라이브러리로 json문자열 형태로 응답
				ObjectMapper mapper = new ObjectMapper();
				//보낼때 데이터 포맷
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				String jsonStr = mapper.writeValueAsString(apDocsList);
				System.out.println(jsonStr);
				response.setContentType("application/json;charset=utf-8");
				//데이터 전달S
				response.getWriter().print(jsonStr);
			}else {
				List<Document> apDocsList = service.findMySearchTitle(uId,uSearch);
				//json라이브러리로 json문자열 형태로 응답
				ObjectMapper mapper = new ObjectMapper();
				//보낼때 데이터 포맷
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				String jsonStr = mapper.writeValueAsString(apDocsList);
				System.out.println(jsonStr);
				response.setContentType("application/json;charset=utf-8");
				//데이터 전달S
				response.getWriter().print(jsonStr);
			}
		
		}catch(FindException e) {
			e.printStackTrace();
		}catch(SearchException e) {
			e.printStackTrace();
		}
	}

}
