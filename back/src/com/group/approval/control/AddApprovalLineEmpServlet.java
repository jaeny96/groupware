package com.group.approval.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.service.DocsWriteService;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;

/**
 * Servlet implementation class AddApprovalLineEmpServlet
 */
public class AddApprovalLineEmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addApDocsNo = request.getParameter("addApDocsNo");
		String[] addApLineEmpIdArr = request.getParameterValues("addApLineEmpId[]");
		String[] addApLineStepArr = request.getParameterValues("addApLineStep[]");
		DocsWriteService service;
		ServletContext sc = getServletContext();
		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DocsWriteService.getInstance();
		
		for (int i = 0; i < addApLineEmpIdArr.length; i++) {
			if("staffOne".equals(addApLineEmpIdArr[i]) || "staffTwo".equals(addApLineEmpIdArr[i]) ||"staffThree".equals(addApLineEmpIdArr[i])) {
				System.out.println(i+"번째 결재자가 없습니다");
			}else {
				Approval approval = new Approval();
				Document apDocNo = new Document();
				apDocNo.setDocument_no(addApDocsNo);
				approval.setDocument_no(apDocNo);
				Employee apEmp = new Employee();
				apEmp.setEmployee_id(addApLineEmpIdArr[i]);
				approval.setAp_step(Integer.parseInt(addApLineStepArr[i]));					
				try {
					System.out.println("또 한번 기대");
					service.completeApRegister(approval);
					System.out.println("됐음 ㅜㅜ");
				} catch (AddException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
