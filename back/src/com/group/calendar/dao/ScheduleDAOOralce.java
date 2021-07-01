package com.group.calendar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

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
		
		String skd_type = s.getSkd_type();
		Employee employee_id = s.getSkd_id();
		String skd_title = s.getSkd_title();
		String skd_content = s.getSkd_content();
		Timestamp skd_start_date = s.getSkd_start_date();
		Timestamp skd_end_date = s.getSkd_end_date();
		String skd_share = s.getSkd_share();
		
		PreparedStatement pstmt = null;
		
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		
		
		try {
			pstmt = con.prepareStatement(insertSQL); // insertSQL 문 실행
			pstmt.setString(1, skd_type);
			pstmt.setString(2, s.getSkd_id().employee_id);
			pstmt.setString(3, skd_title);
			pstmt.setString(4, skd_content);
			pstmt.setTimestamp(5, skd_start_date);
			pstmt.setTimestamp(6, skd_end_date);
			pstmt.setString(7, skd_share);
			
			int rs = pstmt.executeUpdate(); //실행된 쿼리문 개수 반환
			
			if(rs==1) {
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
		// TODO Auto-generated method stub
		
	}

	public void delete(int skd_no) throws RemoveException {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		
		SimpleDateFormat sd =new SimpleDateFormat("yyyy-mm-dd hh:mm");
		String start_date = "2021-07-02 03:30";
		String end_date = "2021-07-02 04:30";
		
		
		
		ScheduleService service = new ScheduleService();
		Schedule s= new Schedule();
		s.setSkd_type("업무");
		//s.setSkd_id();
		s.setSkd_title("자바테스트");
		s.setSkd_content("자바테스트");
	//	s.setSkd_start_date("");
		//s.setSkd_end_date("");
		s.setSkd_share("p");
		
		//service.skdInsert(s);
	
		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-mm-dd hh:mm");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String now = sdfCurrent.format(timestamp);		
		System.out.println(now);
		
	}
}
