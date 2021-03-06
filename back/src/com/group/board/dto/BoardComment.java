package com.group.board.dto;

import java.sql.Timestamp;
import com.group.employee.dto.Employee;

public class BoardComment {
	private String bd_no;
	private int cm_no;
	private Employee cm_writer;
	private Timestamp cm_date;
	private String cm_content;

	public BoardComment() {
	}

	public BoardComment(String bd_no, Employee cm_writer, String cm_content) {
		this(bd_no, 0, cm_writer, null, cm_content);
	}

	public BoardComment(String bd_no, int cm_no, Employee cm_writer, Timestamp cm_date, String cm_content) {
		super();
		this.bd_no = bd_no;
		this.cm_no = cm_no;
		this.cm_writer = cm_writer;
		this.cm_date = cm_date;
		this.cm_content = cm_content;
	}

	public String getBd_no() {
		return bd_no;
	}

	public void setBd_no(String bd_no) {
		this.bd_no = bd_no;
	}

	public int getCm_no() {
		return cm_no;
	}

	public void setCm_no(int cm_no) {
		this.cm_no = cm_no;
	}

	public Employee getCm_writer() {
		return cm_writer;
	}

	public void setCm_writer(Employee cm_writer) {
		this.cm_writer = cm_writer;
	}

	public Timestamp getCm_date() {
		return cm_date;
	}

	public void setCm_date(Timestamp cm_date) {
		this.cm_date = cm_date;
	}

	public String getCm_content() {
		return cm_content;
	}

	public void setCm_content(String cm_content) {
		this.cm_content = cm_content;
	}

	@Override
	public String toString() {
		return "BoardComment [bd_no=" + bd_no + ", cm_no=" + cm_no + ", cm_writer=" + cm_writer + ", cm_date=" + cm_date
				+ ", cm_content=" + cm_content + "]";
	}

}
