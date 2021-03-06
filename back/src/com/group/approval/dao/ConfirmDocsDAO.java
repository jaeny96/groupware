package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.exception.SearchException;
import com.group.employee.dto.Employee;

public interface ConfirmDocsDAO {
	/**
	 * 1. (전체)사용자는 확인 문서를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id
	 * @throws FindException
	 */
	List<Document> selectByCheckAllOk(String employee_id) throws FindException;

	/**
	 * 1. (전체)사용자는 미확인 문서를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id
	 * @throws FindException
	 */
	List<Document> selectByCheckAllNo(String employee_id) throws FindException;

	/**
	 * 1. (진행)사용자는 확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckOkWait(String employee_id) throws FindException;
	
	/**
	 * 1. (진행)사용자는 미확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckNoWait(String employee_id) throws FindException;


	
	/**
	 * 3. 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
	 * 
	 * @param employee_id,document_no
	 * @throws FindException
	 */
	List<String> selectByMyClick(String employee_id, String document_no) throws FindException;

	/**
	 * 4. 사용자는 결재 문서를 선택했을 때, 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
	 * 
	 * @param document_no
	 * @throws FindException
	 */
	List<Document> selectByDocsDetail(String document_no) throws FindException;

	/**
	 * 4. 사용자는 결재 문서를 선택했을 때, 해당 문서의 코멘트 정보를 확인할 수 있다.
	 * 
	 * @param document_no
	 * @throws FindException
	 */
	 List<Approval> selectByComments(String document_no) throws FindException;
	
	/**
	 * 6. 문서에 대해 제목,내용으로 검색할 수 있다.
	 * 
	 * @param title,content,employee_id
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchTitle(String employee_id, String title) throws FindException, SearchException;

	List<Document> selectBySearchContent(String employee_id, String content) throws FindException, SearchException;

}
