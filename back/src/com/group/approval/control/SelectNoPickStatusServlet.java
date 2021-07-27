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
import com.group.approval.service.ConfirmDocsService;

/**
 * Servlet implementation class SelectNoPickStatusServlet
 */
public class SelectNoPickStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//(반려탭)사용자가 확인,미확인을 선택하면 그것에 해당하는 목록을 볼 수 있는 서블릿
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String id= session.getAttribute("id").toString();
		
	    String uCheck=request.getParameter("check");
				
		ConfirmDocsService service;
		ServletContext sc = getServletContext();
		ConfirmDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ConfirmDocsService.getInstance();
		
		try {
			List<Document> apDocsList = service.findCheckDocsNo(id,uCheck);
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			String jsonStr = mapper.writeValueAsString(apDocsList);
			System.out.println(jsonStr);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		}catch(FindException e) {
			e.printStackTrace();
		}
	}

}
