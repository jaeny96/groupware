package com.group.calendar.dao;

import java.util.Date;
import java.util.List;


import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

public interface ScheduleDAO {
	
	/**
	 * 개인 및 팀 일정을 조회한다
	 * @return 해당 월 일정
	 * @throws FindException 일정이 없을 경우 또는 찾기를 싱패한 경우 발생한다.
	 */
	public List<Schedule> skdList(Employee skd_e) throws FindException;
	
	
	/**
	 * 주어진 기간에 대한 일정을 조회한다
	 * @param  skd_share 공유여부
	 * @return 기간 일정
	 * @throws FindException 일정이 없을 경우 또는 상품찾기를 실패한 경우 발생한다
	 */
	public List<Schedule> skdPeriodList(Employee skd_e, String sdate, String edate) throws FindException;
	
	/**
	 * 팀 일정을 조회한다
	 * @param  skd_dpt_id 부서명
	 * @return 해당 월 팀 일정
	 * @throws FindException 일정이 없을 경우 또는 상품찾기를 실패한 경우 발생한다
	 */
	public List<Schedule> skdByTeam(String skd_dpt_id) throws FindException;
	
	/**
	 * 개인 일정을 조회한다
	 * @param skd_id 사원번호
	 * @return 해당 월 개인 일정
	 * @throws FindException 일정이 없을 경우 또는 일정찾기를 실패한 경우 발생한다
	 */
	public List<Schedule> skdPersonal(String skd_id) throws FindException;
	
	/**
	 * 제목 혹은 내용으로 검색한다
	 * @param  s 스케줄
	 * @return 일정내용에 해당하는 일정
	 * @throws FindException 일정이 없을 경우 또는 일정찾기를 실패한 경우 발생한다
	 */
	public List<Schedule> skdByContent(Schedule s) throws FindException;

	
	/*
	 * 등록된 일정 클릭 시 일정 상세내용을 확인한다
	 * @param skd_no 일정번호
	 * @return 일정 상세내용
	 * @throws FindException 일정 추가 실패시, 중복일정이 있을 경우 발생한다 
	 */
	public Schedule skdDetail(int skd_no) throws FindException;
	

	
}
