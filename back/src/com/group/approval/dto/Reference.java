package com.group.approval.dto;

import java.util.Date;

public class Reference {

	private Document document_no;
	private Document employee_id;
	private Date re_ap_date;
	private String re_ap_comment;
	
	public Document getDocument_no() {
		return document_no;
	}
	public void setDocument_no(Document document_no) {
		this.document_no = document_no;
	}
	public Document getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Document employee_id) {
		this.employee_id = employee_id;
	}
	public Date getRe_ap_date() {
		return re_ap_date;
	}
	public void setRe_ap_date(Date re_ap_date) {
		this.re_ap_date = re_ap_date;
	}
	public String getRe_ap_comment() {
		return re_ap_comment;
	}
	public void setRe_ap_comment(String re_ap_comment) {
		this.re_ap_comment = re_ap_comment;
	}
	@Override
	public String toString() {
		return "Reference [document_no=" + document_no + ", employee_id=" + employee_id + ", re_ap_date=" + re_ap_date
				+ ", re_ap_comment=" + re_ap_comment + "]";
	}
	
	
	
	
	
}
