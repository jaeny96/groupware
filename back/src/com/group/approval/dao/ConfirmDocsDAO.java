package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.exception.SearchException;
import com.group.employee.dto.Employee;

public interface ConfirmDocsDAO {
	/**
	 * 1. (전체탭)사용자는 확인 문서를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id
	 * @throws FindException
	 */
	List<Document> selectByCheckAllOk(String employee_id) throws FindException;

	/**
	 * 1. (전체탭)사용자는 미확인 문서를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id
	 * @throws FindException
	 */
	List<Document> selectByCheckAllNo(String employee_id) throws FindException;

	/**
	 * 1. (대기탭)사용자는 확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckWaitOk(String employee_id) throws FindException;
	
	/**
	 * 1. (대기탭)사용자는 미확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckWaitNo(String employee_id) throws FindException;

	/**
	 * 1. (반려탭)사용자는 확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckNoOk(String employee_id) throws FindException;
	
	/**
	 * 1. (반려탭)사용자는 미확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckNoNo(String employee_id) throws FindException;

	/**
	 * 1. (승인탭)사용자는 확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckOkOk(String employee_id) throws FindException;
	
	/**
	 * 1. (승인탭)사용자는 미확인를 선택해서 볼 수 있다.
	 * 
	 * @param employee_id,document_status
	 * @throws FindException
	 */
	List<Document> selectByCheckOkNo(String employee_id) throws FindException;

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
	 *  6 (전체)문서에 대해 제목으로 검색할 수 있다.
	 * @param employee_id
	 * @param title
	 * @return 검색 내용
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchTitle(String employee_id, String title) throws FindException, SearchException;

	/**
	 * 6 (전체)문서에 대해 내용으로 검색할 수 있다.
	 * @param employee_id
	 * @param content
	 * @return title
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchContent(String employee_id, String content) throws FindException, SearchException;
	
	/**
	 *  6 (대기)문서에 대해 제목으로 검색할 수 있다.
	 * @param employee_id
	 * @param title
	 * @return 검색 내용
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchTitleWait(String employee_id, String title) throws FindException, SearchException;

	/**
	 * 6 (대기)문서에 대해 내용으로 검색할 수 있다.
	 * @param employee_id
	 * @param content
	 * @return title
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchContentWait(String employee_id, String content) throws FindException, SearchException;

	/**
	 *  6 (반려)문서에 대해 제목으로 검색할 수 있다.
	 * @param employee_id
	 * @param title
	 * @return 검색 내용
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchTitleNo(String employee_id, String title) throws FindException, SearchException;

	/**
	 * 6 (반려)문서에 대해 내용으로 검색할 수 있다.
	 * @param employee_id
	 * @param content
	 * @return title
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchContentNo(String employee_id, String content) throws FindException, SearchException;

	/**
	 *  6 (승인)문서에 대해 제목으로 검색할 수 있다.
	 * @param employee_id
	 * @param title
	 * @return 검색 내용
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchTitleOk(String employee_id, String title) throws FindException, SearchException;

	/**
	 * 6 (승인)문서에 대해 내용으로 검색할 수 있다.
	 * @param employee_id
	 * @param content
	 * @return title
	 * @throws FindException
	 * @throws SearchException
	 */
	List<Document> selectBySearchContentOk(String employee_id, String content) throws FindException, SearchException;

}
