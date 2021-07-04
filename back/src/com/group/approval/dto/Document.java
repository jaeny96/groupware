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
	private DocumentType documentType;
	private Approval approval;
	private Agreement agreement;
	private Reference reference;
	private List<Approval> approvals;
	
	public List<Approval> getApprovals() {
		return approvals;
	}
	public void setApprovals(List<Approval> approvals) {
		this.approvals = approvals;
	}
	public Document(){
		super();
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
	public DocumentType getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
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
				+ document_status + ", employee=" + employee + ", documentType=" + documentType + ", approval="
				+ approval + ", agreement=" + agreement + ", reference=" + reference + ", approvals=" + approvals + "]";
	}

	
	
}
