package com.group.board.dto;

import java.sql.Timestamp;
import com.group.employee.dto.Employee;

public class BoardComment {
	public String bd_no;
	public String cm_no;
	public Employee cm_writer_id;
	public Timestamp cm_date;
	public String cm_content;
}
