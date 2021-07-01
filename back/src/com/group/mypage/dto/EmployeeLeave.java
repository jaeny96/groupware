package com.group.mypage.dto;

import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;

public class EmployeeLeave {
	public Employee employee;
	public Leave leave;
	
	public EmployeeLeave() {
	}

	public EmployeeLeave(Employee employee, Leave leave) {
		this.employee = employee;
		this.leave = leave;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	@Override
	public String toString() {
		return "EmployeeLeave [employee=" + employee + ", leave=" + leave + "]";
	}
	
	
	
}
