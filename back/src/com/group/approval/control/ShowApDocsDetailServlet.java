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
import com.group.approval.service.ConfirmDocsService;


/**
 * Servlet implementation class ShowApDocsDetail
 */
public class ShowApDocsDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//문서의 상세내용 보여주는 서블릿
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String docNo = request.getParameter("docsNo");//문서 번호
		
		ConfirmDocsService service;
		ServletContext sc = getServletContext();
		ConfirmDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ConfirmDocsService.getInstance();
		
		try {
			List<Document> apDocsList = service.findDocsDetail(docNo);
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
