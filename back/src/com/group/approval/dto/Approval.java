package com.group.approval.dto;

import java.util.Date;

import com.group.employee.dto.Employee;

public class Approval {

	private Document document_no;
	private Employee employee_id;
	private ApprovalStatus ap_type;
	private int ap_step;
	private Date ap_ap_date;
	private String ap_ap_comment;

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

	public int getAp_step() {
		return ap_step;
	}

	public void setAp_step(int ap_step) {
		this.ap_step = ap_step;
	}

	public Date getAp_ap_date() {
		return ap_ap_date;
	}

	public void setAp_ap_date(Date ap_ap_date) {
		this.ap_ap_date = ap_ap_date;
	}

	public String getAp_ap_comment() {
		return ap_ap_comment;
	}

	public void setAp_ap_comment(String ap_ap_comment) {
		this.ap_ap_comment = ap_ap_comment;
	}

	@Override
	public String toString() {
		return "Approval [document_no=" + document_no + ", employee_id=" + employee_id + ", ap_type=" + ap_type
				+ ", ap_step=" + ap_step + ", ap_ap_date=" + ap_ap_date + ", ap_ap_comment=" + ap_ap_comment + "]";
	}
	
	
	
	
}
