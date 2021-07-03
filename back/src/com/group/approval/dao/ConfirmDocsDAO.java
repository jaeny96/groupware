package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;
import com.group.employee.dto.Employee;

public interface ConfirmDocsDAO {
	/**1.
	 * (전체)사용자는 확인/미확인 문서를 선택해서 볼 수 있다. 
	 * @param employee_id,ap_type
	 * @throws FindException
	 */
	List<Document> selectByCheckAll(Employee employee_id,String ap_type) throws FindException;

	/**1.
	 * (진행)사용자는 확인/미확인 문서를 선택해서 볼 수 있다. 
	 * @param employee_id,ap_type
	 * @throws FindException
	 */
	List<Document> selectByCheckWait(Employee employee_id,String ap_type) throws FindException;
	
	/**1.
	 * (승인)사용자는 확인/미확인 문서를 선택해서 볼 수 있다. 
	 * @param employee_id,ap_type
	 * @throws FindException
	 */
	List<Document> selectByCheckOk(Employee employee_id,String ap_type) throws FindException;
	
	/**1.
	 * (반려)사용자는 확인/미확인 문서를 선택해서 볼 수 있다. 
	 * @param employee_id,ap_type
	 * @throws FindException
	 */
	List<Document> selectByCheckNo(Employee employee_id,String ap_type) throws FindException;
	

	/**3.
	 * 사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
	 * @param employee_id,document_no
	 * @throws FindException
	 */
	List<String> selectByMyClick(Employee employee_id, String document_no) throws FindException;
	
	/**4.
	 * 사용자는 결재 문서를 선택했을 때, 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
	 * @param document_no
	 * @throws FindException
	 */
	 List<String> selectByDocsDetail(String document_no) throws FindException;
	 
	 /**6.
	  * 문서에 대해 제목,내용으로 검색할 수 있다. 
	  * @param title,content
	  * @throws FindException
	  */
	 List<Document> selectBySearch(String title,String content) throws FindException;
	 
}
