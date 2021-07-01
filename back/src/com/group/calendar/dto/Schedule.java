package com.group.calendar.dto;

import java.security.Timestamp;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public class Schedule {

public int skd_no;
public Employee skd_id;
public ScheduleType skd_type;
public String skd_title;
public String skd_content;
public java.util.Date skd_date;
public Timestamp skd_start_date;
public Timestamp skd_end_date;
public String skd_share;
public Department department_id;

}