package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.exception.ModifyException;
import com.group.approval.exception.UpdateException;
import com.group.employee.dto.Employee;

public interface ProcessDocsDAO {

	
	 /**5.
	  * 사용자는 버튼을 클릭하면 승인or반려할지를 선택하고, 코멘트를 남길 수 있다. (결재/승인테이블만)
	  * @param d
	  * @throws UpdateException
	  */
	 void updateMyFinal(Document d) throws UpdateException;
		
	 /**9.
	  * 참조를 요청받은 참조자는 참조를 승인할지 선택한다.
	  * @param d
	  * @throws UpdateException 
	  */
	 void updateReference(Document d) throws UpdateException;
	 
	 /**10.
	  * 모두 승인처리를 내리면,최종 문서 상태의 값을 '승인'으로 바꾼다.한명이라도 반려시 '반려'로 변경한다.
	  * @param document_no
	  * @throws ModifyException
	  */
	  void documentStatus(String document_no) throws ModifyException;
	 
}
