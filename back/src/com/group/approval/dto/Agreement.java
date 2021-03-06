package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Agreement {

	private Document document_no;
	private Employee employee_id;
	private ApprovalStatus ag_ap_type;
	private Date ag_ap_date;
	private String ag_ap_comment;
	
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
	public ApprovalStatus getAg_ap_type() {
		return ag_ap_type;
	}
	public void setAg_ap_type(ApprovalStatus ag_ap_type) {
		this.ag_ap_type = ag_ap_type;
	}
	public Date getAg_ap_date() {
		return ag_ap_date;
	}
	public void setAg_ap_date(Date ag_ap_date) {
		this.ag_ap_date = ag_ap_date;
	}
	public String getAg_ap_comment() {
		return ag_ap_comment;
	}
	public void setAg_ap_comment(String ag_ap_comment) {
		this.ag_ap_comment = ag_ap_comment;
	}
	@Override
	public String toString() {
		return "Agreement [document_no=" + document_no + ", employee_id=" + employee_id + ", ag_ap_type=" + ag_ap_type
				+ ", ag_ap_date=" + ag_ap_date + ", ag_ap_comment=" + ag_ap_comment + "]";
	}
	
	
	
	
}
