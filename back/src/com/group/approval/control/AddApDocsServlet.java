package com.group.approval.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.service.DocsWriteService;

import com.group.employee.dto.Employee;
import com.group.exception.AddException;

/**
 * Servlet implementation class AddApDocsServlet
 */
public class AddApDocsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addApDocNo = request.getParameter("addApDocsNo");
		String addApDocType = request.getParameter("addApDocsType");
		String addApDocTitle = request.getParameter("addApDocsTitle");
		String addApDocContent = request.getParameter("addApDocsContent");
		String addApWriter = request.getParameter("addApWriterId");
		System.out.println(addApDocNo + "/" + addApDocType + "/" + addApDocTitle + "/" + addApDocContent+"/"+addApWriter);
		DocsWriteService service;
		ServletContext sc = getServletContext();
		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DocsWriteService.getInstance();

		try {
			Document d = new Document();
			d.setDocument_no(addApDocNo);
			d.setDocument_title(addApDocTitle);
			d.setDocument_content(addApDocContent);
			DocumentType dt = new DocumentType();
			dt.setDocument_type(addApDocType);
			d.setDocument_type(dt);
			Employee emp = new Employee();
			emp.setEmployee_id(addApWriter);
			d.setEmployee(emp);
			System.out.println("기대중");
			service.complete(d);
			System.out.println("오예");
			
		} catch (AddException e) {
			e.printStackTrace();
		}
	}

}
