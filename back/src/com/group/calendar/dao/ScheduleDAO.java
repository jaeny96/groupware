package com.group.calendar.dao;

import java.util.Date;
import java.util.List;


import com.group.calendar.dto.Schedule;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

public interface ScheduleDAO {

	/*
	 * 사원은 개인 일정 혹은 팀일정을 추가한다
	 * @param skd_share 공유여부
	 * @return 
	 * @throws AddException, DuplicatedException 일정 추가 실패시, 중복일정이 있을 경우 발생한다 
	 */
	public void insert(Schedule s) throws AddException, DuplicatedException;

	/*
	 * 사원은 개인 일정 혹은 팀일정을 수정한다
	 * @param s
	 * @return 
	 * @throws ModifyException 일정 수정 실패시 발생한다 
	 */
	public void update(Schedule s) throws ModifyException;

	/*
	 * 사원은 일정을 삭제한다
	 * @param skd_no 일정 번호
	 * @return 
	 * @throws RemoveException 일정 삭제 실패할 경우 발생한다 
	 */
	public void delete(int skd_no) throws RemoveException;

}
