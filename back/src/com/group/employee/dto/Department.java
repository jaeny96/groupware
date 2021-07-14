package com.group.employee.dto;

public class Department {
	private String department_id;
	private String department_title;
	private String manager_id;
	private int count;
	
	public Department() {
	}

	public Department(String department_id,String department_title,int count) {
		this(department_id,department_title,null,count);
	}
	
	public Department(String department_id, String department_title, String manager_id, int count) {
		this.department_id = department_id;
		this.department_title = department_title;
		this.manager_id = manager_id;
		this.count = count;
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_title() {
		return department_title;
	}

	public void setDepartment_title(String department_title) {
		this.department_title = department_title;
	}

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Department [department_id=" + department_id + ", department_title=" + department_title + ", manager_id="
				+ manager_id + ", count=" + count + "]";
	}	
}
