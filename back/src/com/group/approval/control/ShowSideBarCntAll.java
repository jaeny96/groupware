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
		//현재 로그인 id 받아오기 
		HttpSession session = request.getSession();
		String id= session.getAttribute("id").toString();

		SideDocsService service;
		ServletContext sc = getServletContext();
		SideDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = SideDocsService.getInstance();
		
		try {
			//List로 만들어서 한꺼번에 html에 보내기 위해 만든 변수 
			List<Integer> apCntList=new ArrayList<Integer>();
			
			//0번째 인덱스에 전체 숫자값 넣기 
			apCntList.add(0, service.findCntAll(id));
			//1번째 인덱스에 대기 숫자값 넣기 
			apCntList.add(1, service.findCntWait(id));
			//2번째 인덱스에 승인 숫자값 넣기 
			apCntList.add(2, service.findCntOk(id));
			//3번째 인덱스에 반려 숫자값 넣기 
			apCntList.add(3, service.findCntNo(id));
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(apCntList);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);

		}catch(FindException e) {
			e.printStackTrace();
		}
	}

	
}
