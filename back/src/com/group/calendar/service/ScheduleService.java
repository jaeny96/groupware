package com.group.calendar.service;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Formatter;

import javax.xml.crypto.dsig.Transform;

import com.group.calendar.dao.ScheduleDAO;
import com.group.calendar.dto.Schedule;
import com.group.calendar.dto.ScheduleType;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;


public class ScheduleService {
	Date sdate; Date edate; 
 String startdate; String enddate; Timestamp tdate;

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
	public List<Schedule> findByDate(Employee skd_id) throws FindException{
		return dao.skdList(skd_id);
	}
	

	//일정추가 메서드
	/**
	 * @param s
	 * @throws AddException
	 */

	public void addSkd(Schedule s) throws AddException, DuplicatedException{
		dao.insert(s);
	}
	

	/**
	 * 일정업데이트 메서드
	 * @param s
	 * @throws ModifyException
	 */
	public void modifySkd(Schedule s) throws ModifyException{
		dao.update(s);
	}
	
	/**
	 * 일정삭제 메서드
	 * @param s
	 * @throws RemoveException
	 */
	public void deleteSkd(Schedule s) throws RemoveException{
	
		dao.delete(s);
	}

}
