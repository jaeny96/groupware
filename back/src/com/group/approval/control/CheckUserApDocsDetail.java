package com.group.approval.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.group.approval.service.SideDocsService;

/**
 * Servlet implementation class CheckUserApDocsDetail
 */
public class CheckUserApDocsDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//내가 승인할 부분 확인하는 서블릿 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String id= session.getAttribute("id").toString();
		
		String docsNo = request.getParameter("docsNo");
	
		ConfirmDocsService service;
		ServletContext sc = getServletContext();
		ConfirmDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ConfirmDocsService.getInstance();
		Map<String,String>map = new HashMap<>();
		
		try {
			List<String> apDocsList = service.findDocsMyCheck(id,docsNo);
			map.put("loginName", apDocsList.get(1).toString());
			System.out.println(apDocsList.get(1).toString());
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			String jsonStr = mapper.writeValueAsString(map);
			System.out.println(jsonStr);
			response.setContentType("application/json;charset=utf-8");

			response.getWriter().print(jsonStr);
		}catch(FindException e) {
			e.printStackTrace();
		}
	}


}
