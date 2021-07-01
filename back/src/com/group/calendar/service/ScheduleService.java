package com.group.calendar.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;


import com.group.calendar.dao.ScheduleDAO;
import com.group.calendar.dto.Schedule;
import com.group.exception.FindException;


public class ScheduleService {
	private ScheduleDAO dao;
	private static ScheduleService service; //선언만해두기
	public static String envProp; //
	private ScheduleService() {
		Properties env = new Properties();
		try {
			//리팩토링작업
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("scheduleDAO");
			Class c = Class.forName(className);//JVM에로드
			dao = (ScheduleDAO)c.newInstance();//객체생성 (위에 주석처럼 직접 하드코딩하지 않고 //이렇게 객체생성=구체화된 클라스를 사용하지 않고 일반화된 인터페이스를 사용) 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static ScheduleService getInstance() {
		if(service == null) {//getItance가 호출되기전에는 생성 x 호출되면 서비스객체가 생성 = 안전한방법
			service = new ScheduleService();
		}
		return service;
	}
	
	
	/**
	 * 
	 * @param 
	 * @throws FindException
	 */
	public List<Schedule> findSkdAll() throws FindException{
		return dao.skdList();
	}
	/**
	 * 
	 * @param skd_share
	 * @throws FindException
	 */
	public Schedule findSkdTeam(String skd_share) throws FindException{
		return dao.skdByTeam(skd_share);
	}
	
	/**
	 * 
	 * @param skd_share
	 * @throws FindException
	 */
	public Schedule findSkdPersonal(String skd_share) throws FindException{
		return dao.skdPersonal(skd_share);
	}
	
	/**
	 * 
	 * @param skd_title, skd_content
	 * @throws FindException
	 */
	public Schedule findByDetail(String skd_title, String skd_content) throws FindException{
		return dao.skdByDetail(skd_title, skd_content);
	}
	/**
	 * 
	 * @param 
	 * @throws FindException
	 */
	public List<Schedule> findByDate() throws FindException{
		return dao.skdList();
	}
	
}
