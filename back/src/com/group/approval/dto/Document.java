package com.group.approval.dto;

import java.util.Date;
import java.util.List;

import com.group.employee.dto.Employee;

public class Document {
	private String state;//결재서류,기안서류 구분용
	private String document_no;
	private String document_title;
	private String document_content;
	private Date draft_date;
	private String document_status;
	
	private Employee employee;
	private DocumentType document_type;
	private Approval approval;
	private Agreement agreement;
	private Reference reference;
	private List<Approval> approvals;
	
	public Document() {
	}

	//++지수 추가
	public Document(String document_no, String document_title, Date draft_date) {
		this(null,document_no, document_title, null, draft_date, null,null, null, null, null, null,null);
	}
	// ++

	public Document(String state,String document_no,  String document_title,String document_content, Date draft_date, String document_status,Employee employee, DocumentType document_type,
			 Approval approval,
			Agreement agreement, Reference reference, List<Approval> approvals) {
		this.state=state;
		this.document_no = document_no;
		this.document_title = document_title;
		this.document_content = document_content;
		this.draft_date = draft_date;
		this.document_status = document_status;
		this.employee = employee;
		this.document_type = document_type;
		this.approval = approval;
		this.agreement = agreement;
		this.reference = reference;
		this.approvals = approvals;
	}


	public List<Approval> getApprovals() {
		return approvals;
	}
	public void setApprovals(List<Approval> approvals) {
		this.approvals = approvals;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDocument_no() {
		return document_no;
	}
	public void setDocument_no(String document_no) {
		this.document_no = document_no;
	}
	public String getDocument_title() {
		return document_title;
	}
	public void setDocument_title(String document_title) {
		this.document_title = document_title;
	}
	public String getDocument_content() {
		return document_content;
	}
	public void setDocument_content(String document_content) {
		this.document_content = document_content;
	}
	public Date getDraft_date() {
		return draft_date;
	}
	public void setDraft_date(Date draft_date) {
		this.draft_date = draft_date;
	}
	public DocumentType getDocument_type() {
		return document_type;
	}
	public void setDocument_type(DocumentType document_type) {
		this.document_type = document_type;
	}
	public String getDocument_status() {
		return document_status;
	}
	public void setDocument_status(String document_status) {
		this.document_status = document_status;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public Approval getApproval() {
		return approval;
	}
	public void setApproval(Approval approval) {
		this.approval = approval;
	}
	public Agreement getAgreement() {
		return agreement;
	}
	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}
	public Reference getReference() {
		return reference;
	}
	public void setReference(Reference reference) {
		this.reference = reference;
	};
	@Override
	public String toString() {
		return "Document [state=" + state + ", document_no=" + document_no + ", document_title=" + document_title
				+ ", document_content=" + document_content + ", draft_date=" + draft_date + ", document_status="
				+ document_status + ", employee=" + employee + ", document_type=" + document_type + ", approval="
				+ approval + ", agreement=" + agreement + ", reference=" + reference + ", approvals=" + approvals + "]";
	}

	
	
}
