package com.group.calendar.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.group.board.dto.Employee;
import com.group.calendar.dao.ScheduleDAO;
import com.group.calendar.dto.Schedule;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

public class ScheduleService {
	private ScheduleDAO dao;
	private static ScheduleService service;
	//public static String classes;
	
	//Todo : prop 오류 해결 
	public ScheduleService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream("classes.prop"));
		
			String className = env.getProperty("scheduleDAO"); 
			//System.out.println("클래스네임" + className);
			Class c = Class.forName(className);
			dao = (ScheduleDAO) c.newInstance();
			//System.out.println("dao"+dao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	//service 객체를 가져올 수 있도록 하는 메서드 
	public static ScheduleService getInstance() {
		if(service == null) {
			service = new ScheduleService();		
		}
		return service;
	}
	
	//일정추가 메서드
	/**
	 * @param s
	 * @throws AddException
	 */

	public void skdInsert(Schedule s) throws AddException, DuplicatedException{
		dao.insert(s);
	}
	

	/**
	 * 일정업데이트 메서드
	 * @param s
	 * @throws ModifyException
	 */
	public void skdUpdate(Schedule s) throws ModifyException{
		dao.update(s);
	}
	
	/**
	 * 일정삭제 메서드
	 * @param s
	 * @throws RemoveException
	 */
	public void skdDelete(Schedule s, String skd_id) throws RemoveException{
	
		dao.delete(s, skd_id);
	}
	
	public static void main(String[] args) {
		System.out.println("서비스"+service);
		System.out.println("서비스스케쥴객체"+ScheduleService.getInstance());

		
	}
	
}
