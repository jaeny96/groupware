package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.approval.exception.ModifyException;
import com.group.employee.dto.Employee;

public interface SideDocsDAO {

	/**2.
	 * (전체)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @param employee_id
	 * @throws FindException
	 * 
	 */
	int selectByCountAll(String employee_id) throws FindException;
	
	/**2.
	 * (진행)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @param employee_id
	 * @throws FindException
	 * 
	 */
	int selectByCountWait(String employee_id) throws FindException;
	
	/**2.
	 * (승인)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @param employee_id
	 * @throws FindException
	 * 
	 */
	int selectByCountOk(String employee_id) throws FindException;
	
	/**2.
	 * (반려)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	 * @param employee_id
	 * @throws FindException
	 * 
	 */
	int selectByCountNo(String employee_id) throws FindException;
	
	 /**7.
	  * (전체)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @param employee_id
	  * @throws FindException
	  */
	 List<Document> selectByListAll(String employee_id) throws FindException;
	 
	 
	 /**8.
	  * (승인)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @param employee_id,document_status
	  * @throws FindException
	  */
	 List<Document> selectByListOk(String employee_id) throws FindException;
	 

	 
	 /**8.
	  * (진행)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @param employee_id,document_status
	  * @throws FindException
	  */
	 List<Document> selectByListWait(String employee_id) throws FindException;
	 
	 
	 /**8.
	  * (반려)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	  * @param employee_id,document_status
	  * @throws FindException
	  */
	 List<Document> selectByListNo(String employee_id) throws FindException;
	 
	
}
