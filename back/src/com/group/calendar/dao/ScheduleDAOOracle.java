package com.group.calendar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import com.group.calendar.dto.Schedule;
import com.group.calendar.dto.ScheduleType;
import com.group.calendar.service.ScheduleService;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.DuplicatedException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
import com.group.sql.MyConnection;


public class ScheduleDAOOracle implements ScheduleDAO {

	public ScheduleDAOOracle() throws Exception{
		//JDBC드라이버로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");	
	}
	
	
	
	public void insert(Schedule s) throws AddException, DuplicatedException {
		   
	      Connection con = null;
	      try {
	         con = MyConnection.getConnection();
	         con.setAutoCommit(false);
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }

	      
	      //SQL문 불러오기 
	      String insertSQL = "INSERT INTO schedule(skd_no, skd_type, employee_id, skd_title, \r\n" + 
	            "skd_content, skd_date, skd_start_date, \r\n" + 
	            "skd_end_date, skd_share) \r\n" + 
	            "VALUES (SKD_SEQ.NEXTVAL, ?, ?, ?, \r\n" + //skd_type, employee_id, skd_title,
	            "?, sysdate, ? ,\r\n" + //skd_content,  skd_start_date,
	            "?, ?)"; //skd_end_date, skd_share


	      ScheduleType skd_type = s.getSkd_type();
	      Employee skd_id = s.getSkd_id();
	      String skd_title = s.getSkd_title();
	      String skd_content = s.getSkd_content();
	      Timestamp skd_start_date = s.getSkd_start_date();
	      Timestamp skd_end_date = s.getSkd_end_date();
	      String skd_share = s.getSkd_share();
	      
	      PreparedStatement pstmt = null;
	      
	      try {
	         pstmt = con.prepareStatement(insertSQL); // insertSQL 문 실행
	         pstmt.setString(1, skd_type.getSkd_type());
	         pstmt.setString(2, skd_id.getEmployee_id());
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
	      }finally {
	         MyConnection.close(con, pstmt, null);
	      }
		
		
	}

	
	public void update(Schedule s) throws ModifyException {
		
		Connection con = null;
		try {
				con = MyConnection.getConnection();
				   con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ModifyException("Connection 연결 오류");
			}
		
		
		String updateSQL = "UPDATE schedule SET ";
		String updateSQL1 = " WHERE skd_no = ? AND employee_id = ?";
		
		ScheduleType skd_type = s.getSkd_type();
		String skd_title = s.getSkd_title();
		String skd_content = s.getSkd_content();
		Timestamp skd_start_date = s.getSkd_start_date();
		Timestamp skd_end_date = s.getSkd_end_date();
		String skd_share = s.getSkd_share();
		

		boolean flag = false;
	
		if(!skd_type.getSkd_type().equals("")) {
			updateSQL += "skd_type = '"+skd_type+"'" ;
			flag = true;
		}
		
		if(!skd_title.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_title = '"+skd_title+"'";
			flag = true;
		}
		
		if(!skd_content.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_content = '"+skd_content+"'";
			flag = true;
		}
		
		//시간 미변경 시 필요 조건 
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String timeformat = time.format(skd_start_date);
		String timeformat2 = time.format(skd_end_date);
		
		//timestamp 미변경 시 코드 보충 필요... 
		if(!timeformat.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_start_date = '"+skd_start_date+"'";
			flag = true;
		}
		
		if(!timeformat2.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_end_date = '"+skd_end_date +"'";
			flag = true;
		}
		
		if(!skd_share.equals("")) {
			if(flag) {
				updateSQL +=",";
			}
			updateSQL += "skd_share = '"+skd_share+"'";
			flag = true;
		}
		

		System.out.println("변경된 내용 " + skd_type + skd_title + skd_content + skd_start_date + skd_end_date + skd_share);
		System.out.println(updateSQL+updateSQL1);
		
		//변경하지 않음 
		if(!flag) {
			throw new ModifyException("수정할 내용이 없습니다");
		}
		
		
		PreparedStatement pstmt = null;
		//?에 들어갈 변수들 
		int skd_no = s.getSkd_no();
		Employee skd_id = s.getSkd_id();
		
		//end 시간이 start보다 빠르지 않도록 제약 설정
		String skd_start_datestr = new SimpleDateFormat("yyyyMMddHHmmss").format(skd_start_date);	
		String skd_end_datestr = new SimpleDateFormat("yyyyMMddHHmmss").format(skd_end_date);
		
			try {
				//end시간이 더 빠른 경우 없도록 하는 조건절 
				if(Double.parseDouble(skd_end_datestr)-Double.parseDouble(skd_start_datestr)>0) {
					
				pstmt = con.prepareStatement(updateSQL+updateSQL1);
				pstmt.setInt(1, skd_no);
				pstmt.setString(2, skd_id.getEmployee_id());
				
				int rowcnt = pstmt.executeUpdate();
				if(rowcnt ==1) {
					System.out.println("일정이 수정되었습니다");
				}else {
					System.out.println(updateSQL+updateSQL1);
					throw new ModifyException("일정이 수정되지 않았습니다");
				}
				}else {//end-start if문의 else 
					throw new ModifyException("정상적인 시간을 입력하세요");
				}//end start if 문의 닫기 괄호 
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

	public void delete(Schedule s) throws RemoveException {
		
		
		Connection con = null;
		try {
				con = MyConnection.getConnection();
				con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RemoveException("Connection 연결 오류");
			}
		
		 String deleteSQL = "delete from schedule \r\n" + 
		 		" where skd_no= ? AND employee_id = ?"; 
		 PreparedStatement pstmt = null;
		 Employee emp = new Employee();
		 
		 int skd_no = s.getSkd_no();
		 Employee skd_id = s.getSkd_id();
		
		 
	//	 skd_id = emp.getEmployee_id();
		 
		 //emp.setEmployee_id("MSD002");
			//s.setSkd_id(emp);
		 
		 try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setInt(1, skd_no);
			pstmt.setObject(2, skd_id.getEmployee_id());
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


	public static void main(String[] args) throws Exception {
	
//insert 실행
//		ScheduleDAOOralce dao = new ScheduleDAOOralce(); 
//	      String type="출장";
//	      String id = "MSD002";
//
//	      Employee emp = new Employee();
//	      emp.setEmployee_id(id);
//	      
//	      String title = "포천출장";
//	      String content ="포천회의";
//	      String start = "2021-08-09 09:00:00";
//	      Timestamp start_date=Timestamp.valueOf(start);
//	      System.out.println(start_date);
//	      String end="2021-08-09 18:00:00";
//	      Timestamp end_date=Timestamp.valueOf(end);
//	      String share="p";
//	      
//	      Schedule skd = new Schedule();
//	      skd.setSkd_type(type);
//	      skd.setSkd_id(emp);
//	      skd.setSkd_title(title);
//	      skd.setSkd_content(content);
//	      skd.setSkd_start_date(start_date);
//	      skd.setSkd_end_date(end_date);
//	      skd.setSkd_share(share);
//	      
//	      try {
//			dao.insert(skd);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DuplicatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	      
		
		
//	DELETE 실행 
//		try {
//			//emp.setEmployee_id("MSD002");
//			s.setSkd_no(3);
//			dao.delete(s, "DEV001");
//
//		} catch (RemoveException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


// Update 실행

	//	ScheduleDAOOralce dao = new ScheduleDAOOralce(); 
		//skd_id 설정.
//		Employee emp = new Employee(); //id 입력 위한 employee 객체 부르기	
//		//skd_type 설정. scheduleType 객체의 skd_type 변수에 값을 저장  
//		ScheduleType st = new ScheduleType();
//	
//		//변수넣기 
//		int skd_no = 41; //스케쥴번호 입력 
//		String skd_title = "업데이트테스트4";
//		String skd_skd_content = "";
//		String start = "2021-07-22 15:00:00";
//		String end = "2021-07-22 16:00:00";
//		String skd_share = "p";
//		Timestamp skd_start_date = Timestamp.valueOf(start);
//		Timestamp skd_end_date = Timestamp.valueOf(end);
//
//		String skd_id = "CEO001"; //id 설정 
//		emp.setEmployee_id(skd_id);//skd_id를 emp 객체에 넣어주기 
//		String type ="";
//		st.setSkd_type(type); //type 넣어주기 
//
//		
//		
//		Schedule s = new Schedule();
//		//pstmt의 ? 값 설정 
//		s.setSkd_id(emp); //id에 emp 객체 넣어주기 
//		s.setSkd_no(skd_no); //skd_no 변경 		
//
//		//update 구문들 
//		s.setSkd_type(st); //skd_type 변수에 값이 저장된 ScheduleType 객체를 s 객체에 넣음
//		s.setSkd_title(skd_title);
//		s.setSkd_content(skd_skd_content);
//		s.setSkd_start_date(skd_start_date);
//		s.setSkd_end_date(skd_end_date);
//		s.setSkd_share(skd_share);
//		
//		//test
//		s.setSkd_type(st); //skd_type 변수에 값이 저장된 ScheduleType 객체를 s 객체에 넣음
//		s.setSkd_title(skd_title);
//		s.setSkd_content(skd_skd_content);
//		s.setSkd_start_date(skd_start_date);
//		s.setSkd_end_date(skd_end_date);
//		s.setSkd_share(skd_share);
//		
//		try {
//			dao.update(s);
//		} catch (ModifyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		ScheduleService service = new ScheduleService();
//		Schedule s = new Schedule();
//		ScheduleType st = new ScheduleType();
//		Employee emp = new Employee();

		//update test2 
//		//변수넣기 
//		int skd_no = 1; //스케쥴번호 입력 
//		String skd_title = "업데이트테스트2";
//		String skd_skd_content = "테스트2";
//		String start = "2021-07-03 17:00:00";
//		String end = "2021-07-03 18:00:00";
//		String skd_share = "p";
//		Timestamp skd_start_date = Timestamp.valueOf(start);
//		Timestamp skd_end_date = Timestamp.valueOf(end);
//		
//		//변수, 객체 넣기 
//		s.setSkd_no(skd_no);
//		s.setSkd_type(st); //skd_type 변수에 값이 저장된 ScheduleType 객체를 s 객체에 넣음
//		s.setSkd_title(skd_title);
//		s.setSkd_content(skd_skd_content);
//		s.setSkd_start_date(skd_start_date);
//		s.setSkd_end_date(skd_end_date);
//		s.setSkd_share(skd_share);
//		s.setSkd_id(emp);
//		
//		//id, type 설정 
//		String skd_id = "CEO001"; //id 설정 
//		emp.setEmployee_id(skd_id);//skd_id를 emp 객체에 넣어주기 
//		String type ="업무";
//		st.setSkd_type(type); //type 넣어주기 	
//		service.modifySkd(s);
		
//delete service test
//		s.setSkd_id(emp);
//		emp.setEmployee_id("CEO001");
//		s.setSkd_no(24);
//		service.deleteSkd(s);
//		
//insert service test 		
//		s.setSkd_type(st);
//		s.setSkd_id(emp);
//		
//	
//		st.setSkd_type("업무");
//		emp.setEmployee_id("CEO001");
//		s.setSkd_title("서비스테스트");
//		s.setSkd_content("서비스");
//		s.setSkd_start_date(skdStartTime());
//		s.setSkd_end_date(skdEndTime());
//		s.setSkd_share("p");
//
//		try {
//			service.addSkd(s);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DuplicatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
	}
	
	
	//시간 더하는 메서드 
	public Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}

	//시간 빼는 메서드
	public Date subtractHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}


	//일정 날짜 시간 : 00분, 시간 : 30분으로 나오게 하는 메서드 
	//to do : 만약 입력이 없다면 자동 시간 설정, 입력이 있다면 그대로 하도로 해야할듯 
	public static Timestamp skdStartTime() throws Exception {
		Date now = new Date();
		SimpleDateFormat test = new SimpleDateFormat("yyyy-MM-dd hh:");
		SimpleDateFormat test2 = new SimpleDateFormat("mm");
		
		String skd_time ="";
		Timestamp timestamp = null;
		
		
		//30분이 넘었을 때. ex. 3:33일 때 4:00를 보여주는 코드 
		if(Integer.parseInt(test2.format(now))>=30) {
			ScheduleDAOOracle dao = new ScheduleDAOOracle(); 
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
	
	public static Timestamp skdEndTime() throws Exception {
		ScheduleDAOOracle dao = new ScheduleDAOOracle(); 
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
		//System.out.println("일정 시작 시간: "+ skdStartTime());
		System.out.println("일정 종료 시간: "+timestamp);
		return timestamp;
	
		//	System.out.println("skdstarttime.getTime() : " + skdstarttime.getTime() +"   start" +start);
		//	System.out.println("1시간 더한거 "+dao.addHoursToJavaUtilDate(start, 1));
		//		System.out.println("skdtime"+skd_time);
		//	System.out.println("skdstarttime: "+ skdstarttime); //timestamp 자료형 
		
		
	}



	//미정언니
//
//	@Override
//	public List<Schedule> skdList(Timestamp skd_start_date, Timestamp skd_end_date) throws FindException {
//		//DB연결
//		Connection con = null;
//		try {
//			con= MyConnection.getConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}
//		
//		String SkdListSQL = "SELECT skd_type, skd_title,\r\n" + 
//				"skd_start_date,\r\n" + 
//				"skd_end_date, skd_share\r\n" + 
//				"FROM schedule\r\n" + 
//				"WHERE employee_id= ? AND skd_share ='p' AND skd_start_date\r\n" + 
//				"BETWEEN ? AND ? \r\n" + 
//				"UNION ALL\r\n" + 
//				"SELECT skd_type, skd_title,\r\n" + 
//				"skd_start_date,\r\n" + 
//				"skd_end_date, skd_share FROM schedule\r\n" + 
//				"WHERE employee_id like ? and skd_share = 't' AND skd_start_date \r\n" + 
//				"BETWEEN ? AND ?";	
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<Schedule> list = new ArrayList<Schedule>();
//		
//		Schedule s = new Schedule();
//		Employee emp = new Employee();
//		
////		SimpleDateFormat test = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
////		//시작 날짜 매개변수로 받아오기 
////		Date start = new Date(skd_start_date.getTime());
////		Timestamp skdstart = test.format(start);
////		System.out.println(skdstart);
////		//끝나는 날짜 매개변수로 받아오기 
////		Date end = new Date(skd_end_date.getTime());
////		String skdend = test.format(end);
////		System.out.println(skdend);
//		
//		try {
//			
//			pstmt = con.prepareStatement(SkdListSQL);
//			pstmt.setString(1, s.getSkd_id().getEmployee_id());
//			pstmt.setTimestamp(2, skd_start_date);
//			pstmt.setTimestamp(3, skd_end_date);
//			pstmt.setString(4, s.getDepartment_id().getDepartment_id()+"%");
//			pstmt.setTimestamp(5, skd_start_date);
//			pstmt.setTimestamp(6, skd_end_date);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				ScheduleType st = new ScheduleType();
//				st.setSkd_type(rs.getString("skd_type"));
//				s.setSkd_type(st);
//				System.out.println(st);
//				s.setSkd_title(rs.getString("skd_title"));
//				s.setSkd_start_date(rs.getTimestamp("skd_start_date"));
//				s.setSkd_end_date(rs.getTimestamp("skd_end_date"));
//				emp.setEmployee_id(rs.getString("employee_id"));
//				s.setSkd_id(emp);
//				s.setSkd_share(rs.getString("skd_share"));
//						
//				list.add(s);
////			System.out.println("전체스케줄:"+s);
//			}
//			if(list.size() == 0) {
//				throw new FindException("일정이 없습니다.");
//			}
////			System.out.println(list.size());
//			return list;
//		}catch(SQLException e){
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		}finally {
//			MyConnection.close(con, pstmt, rs);
//		}
//	
//	}

}//class 




