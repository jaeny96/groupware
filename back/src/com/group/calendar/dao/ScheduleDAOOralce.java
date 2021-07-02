package com.group.calendar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import com.group.calendar.dto.Schedule;
import com.group.calendar.service.ScheduleService;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
import com.group.sql.MyConnection;


public class ScheduleDAOOralce implements ScheduleDAO {

	
	
	public void insert(Schedule s) throws AddException, DuplicatedException {
	
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("커넥션 연결 오류"); //이거 왜 해주더라? 
		}

		
		//SQL문 불러오기 
		String insertSQL = "INSERT INTO schedule(skd_no, skd_type, employee_id, skd_title, \r\n" + 
				"skd_content, skd_date, skd_start_date, \r\n" + 
				"skd_end_date, skd_share) \r\n" + 
				"VALUES (SKD_SEQ.NEXTVAL, ?, ?, ?, \r\n" + //skd_type, employee_id, skd_title,
				"?, sysdate, ? ,\r\n" + //skd_content,  skd_start_date,
				"?, ?)"; //skd_end_date, skd_share
		
		Employee emp = new Employee();
		
		//지수야 여기 employee 객체쪽이 환장하겄어 
		String skd_type = s.getSkd_type();
		String skd_id = s.getSkd_id(emp).employee_id;
		String skd_title = s.getSkd_title();
		String skd_content = s.getSkd_content();
		Timestamp skd_start_date = s.getSkd_start_date();
		Timestamp skd_end_date = s.getSkd_end_date();
		String skd_share = s.getSkd_share();
		
		PreparedStatement pstmt = null;
		
		
		try {
			pstmt = con.prepareStatement(insertSQL); // insertSQL 문 실행
			pstmt.setString(1, skd_type);
			pstmt.setString(2, skd_id);
			pstmt.setString(3, skd_title);
			pstmt.setString(4, skd_content);
			pstmt.setTimestamp(5, skd_start_date);
			pstmt.setTimestamp(6, skd_end_date);
			pstmt.setString(7, skd_share);
			
			int rowcnt = pstmt.executeUpdate(); //실행된 쿼리문 개수 반환
			
			if(rowcnt==1) {
				System.out.println("일정이 추가되었습니다");
			}else {
				System.out.println("일정이 추가되지 않았습니다");
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new AddException("오류");
		}finally {
			MyConnection.close(con, pstmt, null);
		}
		
		
	}

	
	public void update(Schedule s) throws ModifyException {
		
		Connection con = null;
		try {
				con = MyConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ModifyException("Connection 연결 오류");
			}
		
		String skd_type = s.getSkd_type();
		String skd_title = s.getSkd_title();
		String skd_content = s.getSkd_content();
		Timestamp skd_start_date = s.getSkd_start_date();
		Timestamp skd_end_date = s.getSkd_end_date();
		String skd_share = s.getSkd_share();
		
		String updateSQL = "UPDATE schedule SET ";
		String updateSQL1 = "WHERE skd_no = ? AND employee_id = ?";
		
		// skd_title = '7월업무보고', skd_content = '업무보고',  skd_date = sysdate, skd_start_date= '2021-08-08 09:00:00', skd_end_date='2021-08-08 18:00:00'
		//		 WHERE skd_no = 6 and employee_id = 'MSD003';
		
		//skd_type만 변경
		//skd_title만 변경
		//skd_content만 변경
		//skd_start_date만 변경
		//skd_end_date만 변경
		//skd_share만 변경
		
		boolean flag = false;
		
		if(!skd_type.equals("")) {
			updateSQL += "skd_type = ' + "+skd_type+" ' " ;
			flag = true;
		}
		
		if(!skd_title.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_title = ' "+ skd_title +" '";
			flag = true;
		}
		
		if(!skd_content.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_content = ' "+ skd_content +" '";
			flag = true;
		}
		
		//수정 필요 
		if(skd_start_date!=null) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_start_date = ' "+ skd_start_date +" '";
			flag = true;
		}
		
		if(skd_end_date!=null) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_end_date = ' "+ skd_end_date +" '";
			flag = true;
		}
		
		if(!skd_share.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_share = '"+skd_share+"'";
			flag = true;
		}
		

		//변경하지 않음 
		if(!flag) {
			throw new ModifyException("수정할 내용이 없습니다");
		}
		
		Employee emp = new Employee();
		PreparedStatement pstmt = null;
		int skd_no = s.getSkd_no();
		String skd_id = emp.getEmployee_id();
		
			try {
				pstmt = con.prepareStatement(updateSQL+updateSQL1);
				pstmt.setInt(1, skd_no);
				pstmt.setString(2, skd_id);
				
				int rowcnt = pstmt.executeUpdate();
				if(rowcnt ==1) {
					System.out.println("일정이 수정되었습니다");
				}else {
					throw new ModifyException("일정이 수정되지 않았습니다");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		
			
		
		
	}

	public void delete(Schedule s, String skd_id) throws RemoveException {
		
		
		Connection con = null;
		try {
				con = MyConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RemoveException("Connection 연결 오류");
			}
		
		 String deleteSQL = "delete from schedule \r\n" + 
		 		"where skd_no= ? AND employee_id = ?"; 
		 PreparedStatement pstmt = null;
		
		 int skd_no = s.getSkd_no();
		 
		 Employee emp = new Employee();
	//	 skd_id = emp.getEmployee_id();
		 
		 //emp.setEmployee_id("MSD002");
			//s.setSkd_id(emp);
		 
		 try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setInt(1, skd_no);
			pstmt.setObject(2, skd_id);
			//.setString(2, skd_id);

			int rowcnt = pstmt.executeUpdate();
			
			if(rowcnt == 1) {
				System.out.println("일정이 삭제되었습니다.");
			 
			}else {
				throw new RemoveException("일정을 삭제할 수 없습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException("오류");
		
		}finally {
			MyConnection.close(con, pstmt, null);
		}
	}


	public static void main(String[] args) {
	
		ScheduleDAOOralce dao = new ScheduleDAOOralce(); 
		Employee emp = new Employee();
		Schedule s = new Schedule();
		//지수야 : update 실행인데 안 되네 ^^  
				try {

					s.setSkd_no(51);
					emp.setEmployee_id("CEO001");
					s.setSkd_type("출장");
					s.setSkd_title("D사 이사점심약속");
					s.setSkd_content("강남 양식집");
					s.setSkd_start_date(dao.skdStartTime());
					s.setSkd_end_date(dao.skdEndTime());
					s.setSkd_share("t");
					dao.update(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		
				//지수야 : DELETE 실행 
		try {
			//emp.setEmployee_id("MSD002");
			s.setSkd_no(3);
			dao.delete(s, "DEV001");

		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
//Insert Test : 성공 
	/**
	 * 	try {
		
			s.setSkd_type("업무");
			s.setSkd_id("CEO001");
			s.setSkd_title("C사 대표점심약속");
			s.setSkd_content("여의도 한정식집");
			s.setSkd_start_date(dao.skdStartTime());
			s.setSkd_end_date(dao.skdEndTime());
			s.setSkd_share("p");
			dao.insert(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 */
		


		

	
	
//		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-mm-dd hh:mm");
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		String now = sdfCurrent.format(timestamp);		
//		System.out.println(now);
		
	}
	
	//시간 더하는 메서드 
	public Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}

	//일정 날짜 시간 : 00분, 시간 : 30분으로 나오게 하는 메서드 
	//to do : 만약 입력이 없다면 자동 시간 설정, 입력이 있다면 그대로 하도로 해야할듯 
	public Timestamp skdStartTime() {
		Date now = new Date();
		SimpleDateFormat test = new SimpleDateFormat("yyyy-MM-dd hh:");
		SimpleDateFormat test2 = new SimpleDateFormat("mm");
		
		String skd_time ="";
		Timestamp timestamp = null;
		
		
		//30분이 넘었을 때. ex. 3:33일 때 4:00를 보여주는 코드 
		if(Integer.parseInt(test2.format(now))>=30) {
			ScheduleDAOOralce dao = new ScheduleDAOOralce(); 
			dao.addHoursToJavaUtilDate(now, 1); //1시간을 더해주는 메서드 활용 
			//System.out.println(test.format(dao.addHoursToJavaUtilDate(now, 1))+"00:00"); 테스트
			skd_time = test.format(dao.addHoursToJavaUtilDate(now, 1))+"00:00"; //날짜 형식을 위해 더해줌. String으로 자동변환
			timestamp = Timestamp.valueOf(skd_time); // String을 timestamp로 변환
			
		//30분 미만일 때. 3:10일 때 3:30를 보여주는 코드 	
		}else if(Integer.parseInt(test2.format(now))<30){
			//System.out.println(test.format(now)+"30");
			skd_time =test.format(now)+"30:00";
			timestamp = Timestamp.valueOf(skd_time);
		}
		return timestamp;
	
	}
	
	public Timestamp skdEndTime() {
		ScheduleDAOOralce dao = new ScheduleDAOOralce(); 
		SimpleDateFormat test = new SimpleDateFormat("yyyy-MM-dd hh:");
		SimpleDateFormat test2 = new SimpleDateFormat("mm");
		
		String skd_time ="";
		Timestamp skdstarttime = skdStartTime();

		Date start = new Date(skdstarttime.getTime()); //timestamp 자료형인 일정시작시간을 date 자료형으로 변환 
		
		//start의 시간이 30분일 경우 분을 00으로 변환. start: 4:30, end: 5:00
		if(Integer.parseInt(test2.format(start))==30) {
			skd_time = test.format(dao.addHoursToJavaUtilDate(start, 1))+"00:00"; //date to String으로 format 변환 , 1시간 더하기 
		}else { //start의 시간이 00일 경우 분을 30으로 변환. start: 4:00, end: 4:30
			skd_time = test.format(start)+"30:00";
		}
	
		Timestamp timestamp = Timestamp.valueOf(skd_time); //String 타입 timestamp로 변환 
		System.out.println("일정 시작 시간: "+ skdStartTime());
		System.out.println("일정 종료 시간: "+timestamp);
		return timestamp;
	
		//	System.out.println("skdstarttime.getTime() : " + skdstarttime.getTime() +"   start" +start);
		//	System.out.println("1시간 더한거 "+dao.addHoursToJavaUtilDate(start, 1));
		//		System.out.println("skdtime"+skd_time);
		//	System.out.println("skdstarttime: "+ skdstarttime); //timestamp 자료형 
		
		
	}

}//class 




