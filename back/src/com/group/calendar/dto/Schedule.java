package com.group.calendar.dto;



import java.sql.Timestamp;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public class Schedule {

public int skd_no;
public Employee skd_id;
public ScheduleType skd_type;
public String skd_title;
public String skd_content;
public Timestamp skd_date;
public Timestamp skd_start_date;
public Timestamp skd_end_date;
public String skd_share;




public Schedule() {
	super();
	// TODO Auto-generated constructor stub
}



public Schedule(Employee skd_id, String skd_title, String skd_content) {		
	super();
	this.skd_id = skd_id;
	this.skd_title = skd_title;
	this.skd_content = skd_content;
}



public Schedule(Employee skd_id, Timestamp skd_start_date) {
	super();
	this.skd_id = skd_id;
	this.skd_start_date = skd_start_date;
}



public Schedule(int skd_no, Employee skd_id, ScheduleType skd_type, String skd_title, String skd_content,
		Timestamp skd_date, Timestamp skd_start_date, Timestamp skd_end_date, String skd_share) {
	super();
	this.skd_no = skd_no;
	this.skd_id = skd_id;
	this.skd_type = skd_type;
	this.skd_title = skd_title;
	this.skd_content = skd_content;
	this.skd_date = skd_date;
	this.skd_start_date = skd_start_date;
	this.skd_end_date = skd_end_date;
	this.skd_share = skd_share;

}

public int getSkd_no() {
	return skd_no;
}

public void setSkd_no(int skd_no) {
	this.skd_no = skd_no;
}

public Employee getSkd_id() {
	return skd_id;
}

public void setSkd_id(Employee skd_id) {
	this.skd_id = skd_id;
}

public ScheduleType getSkd_type() {
	return skd_type;
}

public void setSkd_type(ScheduleType skd_type) {
	this.skd_type = skd_type;
}

public String getSkd_title() {
	return skd_title;
}

public void setSkd_title(String skd_title) {
	this.skd_title = skd_title;
}

public String getSkd_content() {
	return skd_content;
}

public void setSkd_content(String skd_content) {
	this.skd_content = skd_content;
}

public Timestamp getSkd_date() {
	return skd_date;
}

public void setSkd_date(Timestamp skd_date) {
	this.skd_date = skd_date;
}

public Timestamp getSkd_start_date() {
	return skd_start_date;
}

public void setSkd_start_date(Timestamp skd_start_date) {
	this.skd_start_date = skd_start_date;
}

public Timestamp getSkd_end_date() {
	return skd_end_date;
}

public void setSkd_end_date(Timestamp skd_end_date) {
	this.skd_end_date = skd_end_date;
}

public String getSkd_share() {
	return skd_share;
}

public void setSkd_share(String skd_share) {
	this.skd_share = skd_share;
}

@Override
public String toString() {
	return "Schedule [skd_no=" + skd_no + ", skd_id=" + skd_id + ", skd_type=" + skd_type + ", skd_title=" + skd_title
			+ ", skd_content=" + skd_content + ", skd_date=" + skd_date + ", skd_start_date=" + skd_start_date
			+ ", skd_end_date=" + skd_end_date + ", skd_share=" + skd_share + "]";
}











}