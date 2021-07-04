package com.group.approval.dao;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.Reference;
import com.group.approval.exception.ModifyException;
import com.group.approval.exception.UpdateException;

public interface ProcessDocsDAO {

	/**
	 * 5-1. 사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (결재승인테이블)
	 * 
	 * @param d
	 * @throws UpdateException
	 */
	void updateApproval(Approval ap) throws UpdateException;

	/**
	 * 5-2. 사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (합의승인테이블)
	 * 
	 * @param d
	 * @throws UpdateException
	 */
	void updateAgreement(Agreement ag) throws UpdateException;

	/**
	 * 9. 참조를 요청받은 참조자는 참조를 승인할지 선택한다.
	 * 
	 * @param d
	 * @throws UpdateException
	 */
	void updateReference(Reference R) throws UpdateException;

	/**
	 * 10. 모두 승인처리를 내리면,최종 문서 상태의 값을 '승인'으로 바꾼다.
	 * 
	 * @param document_no
	 * @throws ModifyException
	 */
	void documentAudmit(String document_no) throws ModifyException;

	/**
	 * 11.한명이라도 반려시 '반려'로 변경한다.
	 * 
	 * @param document_no
	 * @throws ModifyException
	 */
	void documentRefuse(String document_no) throws ModifyException;
}
