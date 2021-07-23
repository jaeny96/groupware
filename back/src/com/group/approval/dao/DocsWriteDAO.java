package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public interface DocsWriteDAO {
	/**
	 * 해당 문서 종류의 최고 숫자를 출력한다
	 * @param document_type 문서 종류
	 * @return 해당 문서 종류의 최고 숫자
	 * @throws FindException
	 */
	int chkMaxNum(String document_type) throws FindException;
	/**
	 * 1-1. 사용자는 작성한 문서를 기안한다
	 * @param d
	 * @throws AddException
	 */
	void draft(Document d) throws AddException;
	/**
	 * 1-2. 문서 기안 시 결재자를 등록한다
	 * @param d
	 * @throws AddException
	 */
	void draftAp(Approval ap) throws AddException;
	/**
	 * 1-3. 문서 기안 시 합의자를 등록한다
	 * @param d
	 * @throws AddException
	 */
	void draftAg(Agreement ag) throws AddException;
	/**
	 * 1-4. 문서 기안 시 참조자를 등록한다
	 * @param d
	 * @throws AddException
	 */
	void draftRe(Reference re) throws AddException;
	/**
	 * 2-1. 결재선을 설정하는 과정에서 참여시킬 사원의 이름을 검색한다
	 * @param name
	 * @throws FindException
	 */
	List<Employee> searchByName(String word) throws FindException;
	/**
	 * 2-2. 결재선을 설정하는 과정에서 참여시킬 사원의 조직을 검색한다
	 * @param department_title
	 * @throws FindException
	 */
//	List<Department> searchByDep(String department_title) throws FindException;
	/**
	 * 3. 전체 사원의 이름, 부서 정보 갖고오기
	 * @return 조회한 사원들
	 */
	public List<Employee> searchApLineStaff() throws FindException;
}
