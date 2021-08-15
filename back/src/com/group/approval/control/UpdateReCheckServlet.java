package com.group.approval.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.approval.dto.Document;
import com.group.approval.dto.Reference;
import com.group.approval.service.ProcessDocsService;
import com.group.employee.dto.Employee;

/**
 * Servlet implementation class UpdateReCheckServlet
 */
public class UpdateReCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String DocsNo = request.getParameter("docsNo");//문서번호 
		String LoginId = request.getParameter("id");// 로그인 아이디
		System.out.println(DocsNo + "/" + LoginId );
		ProcessDocsService service;
		ServletContext sc = getServletContext();
		ProcessDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ProcessDocsService.getInstance();

		try {
			Reference re = new Reference();
			Document d = new Document();
			d.setDocument_no(DocsNo);
			Employee emp = new Employee();
			emp.setEmployee_id(LoginId);
			re.setEmployee_id(emp);
			re.setDocument_no(d);
			service.decisionRe(re);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
