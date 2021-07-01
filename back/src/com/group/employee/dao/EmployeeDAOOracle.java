package com.group.employee.dao;

import java.util.List;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public class EmployeeDAOOracle implements EmployeeDAO{
	public EmployeeDAOOracle() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("load success");
	}
	
	@Override
	public List<Employee> selectAll() {
		
		return null;
	}

	@Override
	public int selectAllCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Department> selectDepAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> selectByName(String word) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
