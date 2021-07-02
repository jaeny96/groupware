package com.group.approval.dto;

import java.sql.Timestamp;

import com.group.employee.dto.Employee;

public class Reference {
	public Document document_no;
	public Employee employee_id;
	public ApprovalStatus ap_type;
	
	public Reference() {
	}

	public Reference(Document document_no, Employee employee_id, ApprovalStatus ap_type) {
		this.document_no = document_no;
		this.employee_id = employee_id;
		this.ap_type = ap_type;
	}

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

	public ApprovalStatus getAp_type() {
		return ap_type;
	}

	public void setAp_type(ApprovalStatus ap_type) {
		this.ap_type = ap_type;
	}

	@Override
	public String toString() {
		return "Reference [document_no=" + document_no + ", employee_id=" + employee_id + ", ap_type=" + ap_type + "]";
	}
	
}
