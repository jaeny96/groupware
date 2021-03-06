package com.group.employee.dao;

import java.util.List;

import com.group.employee.dto.Department;
import com.group.exception.FindException;

public interface DepartmentDAO {
	/**
	 * 부서의 목록을 조회한다
	 * @return 조회한 모든 부서들
	 */
	public List<Department> selectDepAll() throws FindException;
}
