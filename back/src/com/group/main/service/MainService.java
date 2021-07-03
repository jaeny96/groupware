package com.group.main.service;

import java.util.List;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;

public class MainService {
	public Employee showProfile(String id) throws FindException{
		return null;
	}
	public Employee login(String id) throws FindException {
		return null;
	}
	public List<Document> showDocExpected(String id) throws FindException{
		return null;
	}
	public List<Board> showRecentBd() throws FindException{
		return null;
	}
	public Leave showLeave(String id) throws FindException{
		return null;
	}
	public List<Schedule> showTodaySkd(Employee emp) throws FindException{
		return null;
	}

}
