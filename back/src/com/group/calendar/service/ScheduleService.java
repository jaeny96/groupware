package com.group.calendar.service;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import com.group.calendar.dao.ScheduleDAO;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;


public class ScheduleService {
	private ScheduleDAO dao;
	private static ScheduleService service; //선언만해두기
	public static String envProp; //
	private ScheduleService() {
		Properties env = new Properties();
		
		try {
			//리팩토링작업
			env.load(new FileInputStream("classes.prop"));
			String className = env.getProperty("scheduleDAO");
			System.out.println(className);
			Class c = Class.forName(className);//JVM에로드
			System.out.println(c);
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
	 * @param skd_id
	 * @throws FindException
	 */
	public List<Schedule> findSkdAll(Employee skd_id) throws FindException{
		return dao.skdList(skd_id);
	}
	/**
	 * 
	 * @param skd_id
	 * @throws FindException
	 */
	public List<Schedule> findSkdTeam(String skd_id) throws FindException{
		return dao.skdByTeam(skd_id);
	}
	
	/**
	 * 
	 * @param skd_share
	 * @throws FindException
	 */
	public List<Schedule> findSkdPersonal(String skd_share) throws FindException{
		return dao.skdPersonal(skd_share);
	}
	
	/**
	 * 
	 * @param s
	 * @throws FindException
	 */
	public  List<Schedule> findByContent(Schedule s) throws FindException{
		return dao.skdByContent(s);
	}
	

	/**
	 * 
	 * @param skd_no
	 * @throws FindException
	 */
	public Schedule findByDetail(int skd_no) throws FindException{
		return dao.skdDetail(skd_no);
	}
	
	/**
	 * 
	 * @param skd_id
	 * @throws FindException
	 */
	public List<Schedule> findByDate(Employee skd_id, Date start_date) throws FindException{
		return dao.skdList(skd_id);
	}
	public static void main(String[] args) {
//		ScheduleService service = ScheduleService.getInstance();
//		String skd_id = "MSD003";
//		Department dpt = new Department();
//		dpt.setDepartment_id("MSD");
//
//		try {
//			Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
//			List<Schedule> all = service.findSkdAll(em);
//			for(Schedule s: all) {
//			System.out.println("전체스케줄:"+s);
//			}
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
		
//		ScheduleService service = ScheduleService.getInstance();
//		String skd_id = "MSD003";
//		try {
//			List<Schedule> all = service.findSkdPersonal(skd_id);
//			for(Schedule s:all) {
//				System.out.println(s);
//			}
//		} catch (FindException e) {
//
//			e.printStackTrace();
//		}
		
//		ScheduleService service = ScheduleService.getInstance();
//		String skd_title = "회의";
//		String skd_content = "회의";
//		Employee em = new Employee();
//		em.setEmployee_id("MSD003");
//		try {
//		Schedule s = new Schedule(em, skd_title, skd_content);
//		List<Schedule> all = service.findByContent(s);
//		for(Schedule sc: all) {
//			System.out.println(sc);
//		}
//		}catch(FindException e){
//			e.printStackTrace();
//		}
		
//		ScheduleService service = ScheduleService.getInstance();
//		int skd_no = 9;
//		try {
//			Schedule s = service.findByDetail(skd_no);
//			System.out.println(s);
//		}
//		catch(FindException e){
//			e.printStackTrace();
//		}
		
		//미완성
		ScheduleService service = ScheduleService.getInstance();
		String skd_id = "MSD003";
		Department dpt = new Department();
		dpt.setDepartment_id("MSD");
		Timestamp start_date = new Timestamp(0);
		try {
		Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
		Schedule sc = new Schedule(em, start_date);
		List<Schedule> all = service.findSkdAll(em);
		for(Schedule s: all) {
		System.out.println("전체스케줄:"+s);
		}
	} catch (FindException e) {
		e.printStackTrace();
	}
		
	}
}
