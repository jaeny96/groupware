package com.group.board.dto;

import java.sql.Timestamp;
import java.util.List;
import com.group.employee.dto.Employee;

public class Board {
	public String bd_no;

	// 게시판은 작성자를 가질 수 있다
	private Employee writer;
	private String bd_title;
	private String bd_content;
	private Timestamp bd_date;

	// 게시판은 여러개의 댓글을 가질 수 있다
	public List<BoardComment> bd_comments;

	public Board() {
	}

	public Board(String bd_no, Employee writer) {
		this(bd_no, writer, null, null, null, null);
	}

	public Board(String bd_no, Employee writer, String bd_title, Timestamp bd_date) {
		this(bd_no, writer, bd_title, null, bd_date, null);
	}

	public Board(String bd_no, Employee writer, String bd_title, String bd_content, Timestamp bd_date,
			List<BoardComment> bd_comments) {
		super();
		this.bd_no = bd_no;
		this.writer = writer;
		this.bd_title = bd_title;
		this.bd_content = bd_content;
		this.bd_date = bd_date;
		this.bd_comments = bd_comments;
	}

	public String getBd_no() {
		return bd_no;
	}

	public void setBd_no(String bd_no) {
		this.bd_no = bd_no;
	}

	public Employee getWriter() {
		return writer;
	}

	public void setWriter(Employee writer) {
		this.writer = writer;
	}

	public String getBd_title() {
		return bd_title;
	}

	public void setBd_title(String bd_title) {
		this.bd_title = bd_title;
	}

	public String getBd_content() {
		return bd_content;
	}

	public void setBd_content(String bd_content) {
		this.bd_content = bd_content;
	}

	public Timestamp getBd_date() {
		return bd_date;
	}

	public void setBd_date(Timestamp bd_date) {
		this.bd_date = bd_date;
	}

	public List<BoardComment> getBd_comments() {
		return bd_comments;
	}

	public void setBd_comments(List<BoardComment> bd_comments) {
		this.bd_comments = bd_comments;
	}

	@Override
	public String toString() {
		return "Board [bd_no=" + bd_no + ", writer=" + writer + ", bd_title=" + bd_title + ", bd_content=" + bd_content
				+ ", bd_date=" + bd_date + ", bd_comments=" + bd_comments + "]";
	}

}
