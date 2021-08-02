package com.group.approval.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
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
		String[] addApLineEmpIdArr = request.getParameterValues("addApLineEmpId[]");
		String[] addApLineStepArr = request.getParameterValues("addApLineStep[]");
		String addAgLineEmpId = request.getParameter("addAgLineEmpId");
		String addReLineEmpId = request.getParameter("addReLineEmpId");
		DocsWriteService service;
		ServletContext sc = getServletContext();
		DocsWriteService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = DocsWriteService.getInstance();

		try {
			Document document = new Document();
			document.setDocument_no(addApDocsNo);
			document.setDocument_title(addApDocsTitle);
			document.setDocument_content(addApDocsContent);
			DocumentType dtype = new DocumentType();
			dtype.setDocument_type(addApDocsType);
			document.setDocument_type(dtype);
			Employee emp = new Employee();
			emp.setEmployee_id(addApWriterId);
			document.setEmployee(emp);
			
			service.complete(document);

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
					approval.setEmployee_id(apEmp);
					approval.setAp_step(Integer.parseInt(addApLineStepArr[i]));					
					service.completeApRegister(approval);
				}
			}

			if (addAgLineEmpId != null && !"".equals(addAgLineEmpId) && !"agreementBoxBtn".equals(addAgLineEmpId)) {
				Agreement agreement = new Agreement();
				Document agDocNo = new Document();
				agDocNo.setDocument_no(addApDocsNo);
				agreement.setDocument_no(agDocNo);
				Employee agEmp = new Employee();
				agEmp.setEmployee_id(addAgLineEmpId);
				agreement.setEmployee_id(agEmp);

				service.completeAgRegister(agreement);
			}

			if (addReLineEmpId != null && !"".equals(addReLineEmpId) && !"referenceBoxBtn".equals(addReLineEmpId)) {
				Reference reference = new Reference();
				Document reDocNo = new Document();
				reDocNo.setDocument_no(addApDocsNo);
				reference.setDocument_no(reDocNo);
				Employee reEmp = new Employee();
				reEmp.setEmployee_id(addReLineEmpId);
				reference.setEmployee_id(reEmp);

				service.completeReRegister(reference);
			}
		} catch (AddException e) {
			e.printStackTrace();
		}

	}

}
