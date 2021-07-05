package com.group.employee.dto;

public class Department {
	public String department_id;
	public String department_title;
	public String manager_id;

	public Department() {
	}

	public Department(String department_title) {
		super();
		this.department_title = department_title;
	}

	public String getDepartment_title() {
		return department_title;
	}

	public void setDepartment_title(String department_title) {
		this.department_title = department_title;
	}

	@Override
	public String toString() {
		return "Department [department_id=" + department_id + ", department_title=" + department_title + ", manager_id="
				+ manager_id + "]";
	}
	
	
}
