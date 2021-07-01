package com.group.employee.dto;

import java.util.Date;

public class Employee {
	public String employee_id;
	public String name;

	public Department department;
	public Job job;
	public Position position;

	public String phone_number;
	public String email;
	public Date hire_date;
	public int employee_status;
	public String password;

	public Employee() {
	}

	public Employee(String employee_id, int employee_status, String password) {
		this(employee_id, null, null, null, null, null, null, null, 1, password);
	}

	public Employee(String employee_id, String phone_number, int employee_status) {
		this(employee_id, null, null, null, null, phone_number, null, null, 1, null);
	}

	public Employee(String employee_id, String name, Department department, Job job, Position position,
			String phone_number, String email, String password) {
		this(employee_id, name, department, job, position, phone_number, email, null, 1, password);
	}

	public Employee(String employee_id, String name, Department department, Job job, Position position,
			String phone_number, String email, Date hire_date, String password) {
		this(employee_id, name, department, job, position, phone_number, email, hire_date, 1, password);

	}

	public Employee(String employee_id, String name, Department department, Job job, Position position,
			String phone_number, String email, Date hire_date, int employee_status, String password) {
		this.employee_id = employee_id;
		this.name = name;
		this.department = department;
		this.job = job;
		this.position = position;
		this.phone_number = phone_number;
		this.email = email;
		this.hire_date = hire_date;
		this.employee_status = employee_status;
		this.password = password;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public int getEmployee_status() {
		return employee_status;
	}

	public void setEmployee_status(int employee_status) {
		this.employee_status = employee_status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Employee [employee_id=" + employee_id + ", name=" + name + ", department=" + department + ", job=" + job
				+ ", position=" + position + ", phone_number=" + phone_number + ", email=" + email + ", hire_date="
				+ hire_date + ", employee_status=" + employee_status + ", password=" + password + "]";
	}

}
