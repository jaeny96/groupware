package com.group.calendar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Employee;
import com.group.sql.MyConnection;

public class ScheduleDAOOracle implements ScheduleDAO{

	@Override
	public void insert(Schedule s) {
	
		Connection con = null;
		try {
			con = MyConnection.getConnection();
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
		
		
		//지수야 여기 employee 객체쪽이 환장하겄어 
		String skd_type = s.getSkd_type();
		Employee skd_id = s.getSkd_id();
		String skd_title = s.getSkd_title();
		String skd_content = s.getSkd_content();
		Timestamp skd_start_date = s.getSkd_start_date();
		Timestamp skd_end_date = s.getSkd_end_date();
		String skd_share = s.getSkd_share();
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(insertSQL); // insertSQL 문 실행
			pstmt.setString(1, skd_type);
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

	@Override
	public void update(Schedule s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Schedule s, String skd_id) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		ScheduleDAOOracle dao = new ScheduleDAOOracle();
		String type="출장";
		String id = "MSD002";

		Employee emp = new Employee();
		emp.setEmployee_id(id);
		
		String title = "부산출장";
		String content ="부산회의";
		String start = "2021-08-09 09:00:00";
		Timestamp start_date=Timestamp.valueOf(start);
		System.out.println(start_date);
		String end="2021-08-09 18:00:00";
		Timestamp end_date=Timestamp.valueOf(end);
		String share="p";
		Schedule skd = new Schedule();
		skd.setSkd_type(type);
		skd.setSkd_id(emp);
		skd.setSkd_title(title);
		skd.setSkd_content(content);
		skd.setSkd_start_date(start_date);
		skd.setSkd_end_date(end_date);
		skd.setSkd_share(share);
		
		dao.insert(skd);
		
	}

}
