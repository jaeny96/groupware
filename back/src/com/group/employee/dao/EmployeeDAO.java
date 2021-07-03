package com.group.employee.dao;

import java.util.List;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;

public interface EmployeeDAO {
	/**
	 * 전체 사원을 조회한다
	 * @return 조회한 사원들
	 */
	public List<Employee> selectAll() throws FindException;
	/**
	 * 부서별 사원을 조회한다
	 * @return 조회한 사원들
	 */
	public List<Employee> selectByDep(String dep_id) throws FindException;	
	/**
	 * 사원명으로 검색한다
	 * @param word 사원명에 해당하는 단어
	 * @return 해당 단어를 포함하는 사원들
	 */
	public List<Employee> selectByWord(String word) throws FindException;
	/**
	 * 클릭한 특정 사원의 상세 정보를 조회한다 
	 * @param id 
	 * @return 클릭한 사원의 상세 정보
	 */
	public Employee selectInfo(String name) throws FindException;
	

}
