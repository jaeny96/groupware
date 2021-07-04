package com.group.approval.dto;

import java.util.Date;
import java.util.List;

import com.group.employee.dto.Employee;

public class Document {

   private String document_no;
   private Employee employee_id;
   private DocumentType document_type;
   private String document_title;
   private String document_content;
   private Date draft_date;
   private String document_status;
   
   private List<Approval> approvals;
   private List<Agreement> agreements;
   private List<Reference> references;
   
   private Employee employee;
   
   
   //필요한것 : 문서번호, 문서제목, 사원번호(employee), 사원이름(employee), 기안일자, 문서타입 , 참조상태(approval,reference,agreement) 
    
    public Document(String document_no,DocumentType document_type,Employee employee_id,String document_title,String document_content){
       super();
       this.document_no=document_no;
       this.employee_id=employee_id;
       this.document_type=document_type;
       this.document_title=document_title;
       this.document_content=document_content;
          
    };

	public Document() {
		super();

	}

	public List<Approval> getApprovals() {
      return approvals;
   }
   public void setApprovals(List<Approval> approvals) {
      this.approvals = approvals;
   }
   public List<Agreement> getAgreements() {
      return agreements;
   }
   public void setAgreements(List<Agreement> agreements) {
      this.agreements = agreements;
   }
   public List<Reference> getReferences() {
      return references;
   }
   public void setReferences(List<Reference> references) {
      this.references = references;
   }
   
   public String getDocument_no() {
      return document_no;
   }
   public void setDocument_no(String document_no) {
      this.document_no = document_no;
   }
   
   public Employee getEmployee_id() {
      return employee_id;
   }
   public void setEmployee_id(Employee employee_id) {
      this.employee_id = employee_id;
   }
   public DocumentType getDocument_type() {
      return document_type;
   }
   public void setDocument_type(DocumentType document_type) {
      this.document_type = document_type;
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
   @Override
   public String toString() {
      return "Document [doucment_no=" + document_no + ", employee_id=" + employee_id + ", document_type="
            + document_type + ", document_title=" + document_title + ", document_content=" + document_content
            + ", draft_date=" + draft_date + ", document_status=" + document_status + "]";
   }
   
   
}