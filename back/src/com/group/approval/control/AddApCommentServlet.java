package com.group.approval.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.exception.AddException;
import com.group.approval.exception.UpdateException;
import com.group.approval.service.ProcessDocsService;
import com.group.employee.dto.Employee;

/**
 * Servlet implementation class AddApCommentServlet
 */
public class AddApCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String updateApType = request.getParameter("updateApType");
		String updateApComment = request.getParameter("updateApComment");
		String updateDocsNo = request.getParameter("updateDocsNo");
		String updateEmpId = request.getParameter("updateEmpId");
		System.out.println(updateApType + "/" + updateApComment + "/" + updateDocsNo + "/" + updateEmpId);
		ProcessDocsService service;
		ServletContext sc = getServletContext();
		ProcessDocsService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = ProcessDocsService.getInstance();	
		
		try {
			ApprovalStatus aps = new ApprovalStatus();
			aps.setApStatus_type(updateApType);
			Approval ap = new Approval();
			ap.setAp_ap_comment(updateApComment);
			Employee emp = new Employee();
			emp.setEmployee_id(updateEmpId);
			Document d = new Document();
			d.setDocument_no(updateDocsNo);
			service.decisionAp(ap);
		} catch (com.group.exception.UpdateException e) {
			e.printStackTrace();
		}
	}

}