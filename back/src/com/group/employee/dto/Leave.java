package com.group.employee.dto;

import java.util.Date;

public class Leave {
	public String employee_id;
	public int grant_days;
	public int use_days;
	public int remain_days;
	public Date grant_year;
	public Leave() {
		
	}
	
	public Leave(String employee_id, int grant_days, int use_days, int remain_days, Date grant_year) {
		super();
		this.employee_id = employee_id;
		this.grant_days = grant_days;
		this.use_days = use_days;
		this.remain_days = remain_days;
		this.grant_year = grant_year;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public int getGrant_days() {
		return grant_days;
	}

	public void setGrant_days(int grant_days) {
		this.grant_days = grant_days;
	}

	public int getUse_days() {
		return use_days;
	}

	public void setUse_days(int use_days) {
		this.use_days = use_days;
	}

	public int getRemain_days() {
		return remain_days;
	}

	public void setRemain_days(int remain_days) {
		this.remain_days = remain_days;
	}

	public Date getGrant_year() {
		return grant_year;
	}

	public void setGrant_year(Date grant_year) {
		this.grant_year = grant_year;
	}

	@Override
	public String toString() {
		return "Leave [employee_id=" + employee_id + ", grant_days=" + grant_days + ", use_days=" + use_days
				+ ", remain_days=" + remain_days + ", grant_year=" + grant_year + "]";
	}
	
}
