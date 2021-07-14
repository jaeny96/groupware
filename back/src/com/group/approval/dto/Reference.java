package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Reference {

	private Document document_no;
	private Employee employee_id;
	private ApprovalStatus re_ap_type;
	
	
	public Document getDocument_no() {
		return document_no;
	}
	public void setDocument_no(Document document_no) {
		this.document_no = document_no;
	}

	public Employee getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Employee employee_id) {
		this.employee_id = employee_id;
	}
	public ApprovalStatus getRe_ap_type() {
		return re_ap_type;
	}
	public void setRe_ap_type(ApprovalStatus re_ap_type) {
		this.re_ap_type = re_ap_type;
	}
	@Override
	public String toString() {
		return "Reference [document_no=" + document_no + ", employee_id=" + employee_id + ", re_ap_type=" + re_ap_type
				+ "]";
	}
	


	
	
	
	
	
}
