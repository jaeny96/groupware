package com.group.approval.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.service.DocsWriteService;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;

/**
 * Servlet implementation class AddAgreementLineEmpServlet
 */
public class AddAgreementLineEmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addApDocsNo = request.getParameter("addApDocsNo");
		String addAgLineEmpId = request.getParameter("addAgLineEmpId");
		System.out.println(addApDocsNo);
		System.out.println(addAgLineEmpId);
		DocsWriteService service;
		ServletContext sc = getServletContext();
		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DocsWriteService.getInstance();

		try {
			Agreement ag = new Agreement();
			Document d = new Document();
			d.setDocument_no(addApDocsNo);
			Employee emp = new Employee();
			emp.setEmployee_id(addAgLineEmpId);
			d.setEmployee(emp);
			ag.setDocument_no(d);
			ag.setEmployee_id(emp);
			System.out.println("기대중");
			service.completeAgRegister(ag);
			System.out.println("오예");
			
		} catch (AddException e) {
			e.printStackTrace();
		}
	}

}
