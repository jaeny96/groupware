package com.group.calendar.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.group.calendar.dao.ScheduleDAO;
import com.group.calendar.dto.Schedule;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.ModifyException;

public class ScheduleService {
	private ScheduleDAO dao;
	private static ScheduleService service;
	public static String classes;
	
	//Todo : prop 오류 해결 
	public ScheduleService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(classes));
			
			String className = env.getProperty("ScheduleDAO"); 
			Class c = Class.forName(className);
			dao = (ScheduleDAO)c.newInstance();
			
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
	
}
