package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.exception.AddException;
import com.group.approval.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public interface DocsWriteDAO {
	/**
	 * 1. 사용자는 작성한 문서를 기안한다
	 * @param d
	 * @throws AddException
	 */
	void draft(Document d) throws AddException;
	/**
	 * 2-1. 결재선을 설정하는 과정에서 참여시킬 사원의 이름을 검색한다
	 * @param name
	 * @throws FindException
	 */
	List<Employee> searchByName(String name) throws FindException;
	/**
	 * 2-2. 결재선을 설정하는 과정에서 참여시킬 사원의 조직을 검색한다
	 * @param department_title
	 * @throws FindException
	 */
	List<Department> searchByDep(String department_title) throws FindException;

}