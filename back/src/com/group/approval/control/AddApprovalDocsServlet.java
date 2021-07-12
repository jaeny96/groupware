package com.group.approval.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.exception.AddException;
import com.group.approval.service.DocsWriteService;
import com.group.employee.dto.Employee;

/**
 * Servlet implementation class AddApprovalDocsServlet
 */
public class AddApprovalDocsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String addApDocsNo = request.getParameter("addApDocsNo");
		String addApDocsType = request.getParameter("addApDocsType");
		String addApWriterId = request.getParameter("addApWriterId");
		String addApDocsTitle = request.getParameter("addApDocsTitle");
		String addApDocsContent = request.getParameter("addApDocsContent");
		System.out.println(addApDocsNo + "/" + addApDocsType + "/" + addApWriterId + "/" + addApDocsTitle
				 + "/" + addApDocsContent);
		DocsWriteService service;
		ServletContext sc = getServletContext();
		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		System.out.println(DocsWriteService.envProp);
		service = DocsWriteService.getInstance();
		System.out.println(service);
		
		try {
			DocumentType dtype = new DocumentType();
			dtype.setDocument_type(addApDocsType);
			Employee emp = new Employee();
			emp.setEmployee_id(addApWriterId);
			Document d = new Document();
			d.setDocument_no(addApDocsNo);
			d.setDocument_title(addApDocsTitle);
			d.setDocument_content(addApDocsContent);
			d.setEmployee(emp);
			d.setDocument_type(dtype);
			service.complete(d);
		} catch (AddException e) {
			e.printStackTrace();
		}
		
	}

}
