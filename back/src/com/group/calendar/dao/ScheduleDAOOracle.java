package com.group.calendar.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.group.calendar.dto.Schedule;
import com.group.calendar.dto.ScheduleType;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.sql.MyConnection;

public class ScheduleDAOOracle implements ScheduleDAO {
	public ScheduleDAOOracle() throws Exception{
		//JDBC드라이버로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");	
	}

	
	public List<Schedule> skdList(Employee skd_e) throws FindException {
		//DB연결
		Connection con = null;
		try {
			con= MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String SkdListSQL = "SELECT skd_type, skd_title,\r\n" + 
				"skd_start_date,\r\n" + 
				"skd_end_date, skd_share\r\n" + 
				"FROM schedule\r\n" + 
				"WHERE employee_id=? AND skd_share ='p'" + 
				"UNION ALL\r\n" + 
				"SELECT skd_type, skd_title,\r\n" + 
				"skd_start_date,\r\n" + 
				"skd_end_date, skd_share FROM schedule\r\n" + 
				"WHERE employee_id like ? and skd_share = 't'ORDER BY skd_start_date ASC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Schedule> list = new ArrayList<Schedule>();

		try {
			pstmt = con.prepareStatement(SkdListSQL);
			pstmt.setString(1, skd_e.getEmployee_id());
			pstmt.setString(2, skd_e.getDepartment().getDepartment_id()+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Schedule s = new Schedule();
				ScheduleType st = new ScheduleType();
				st.setSkd_type(rs.getString("skd_type"));
				s.setSkd_type(st);
				s.setSkd_title(rs.getString("skd_title"));
				s.setSkd_start_date(rs.getTimestamp("skd_start_date"));
				s.setSkd_end_date(rs.getTimestamp("skd_end_date"));
				s.setSkd_share(rs.getString("skd_share"));		
				list.add(s);
				
//			System.out.println("전체스케줄:"+s);
			}
			if(list.size() == 0) {
				throw new FindException("일정이 없습니다.");
			}
//			System.out.println(list.size());
			return list;
		}catch(SQLException e){
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	public List<Schedule> skdPeriodList(Employee skd_e, String sdate, String edate) throws FindException {
	      //DB연결
	      Connection con = null;
	      try {
	         con= MyConnection.getConnection();
	      } catch (SQLException e) {
	         e.printStackTrace();
	         throw new FindException(e.getMessage());
	      }
	      
	      String SkdListSQL = "SELECT skd_no, skd_type, skd_title, skd_content,\r\n" + 
	            "skd_start_date,\r\n" + 
	            "skd_end_date, skd_share\r\n" + 
	            "FROM schedule\r\n" + 
	            "WHERE employee_id= ? AND skd_share ='p' AND skd_start_date\r\n" + 
	            "BETWEEN ? AND ? \r\n" + 
	            "UNION ALL\r\n" + 
	            "SELECT skd_type, skd_title,\r\n" + 
	            "skd_start_date,\r\n" + 
	            "skd_end_date, skd_share FROM schedule\r\n" + 
	            "WHERE employee_id like ? and skd_share = 't' AND skd_start_date \r\n" + 
	            "BETWEEN ? AND ? ORDER BY skd_start_date ASC";   
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<Schedule> list = new ArrayList<Schedule>();
    

	      
	      try {
	         pstmt = con.prepareStatement(SkdListSQL);
	         pstmt.setString(1, skd_e.getEmployee_id());	            
	         pstmt.setString(2, sdate);
	         pstmt.setString(3, edate);
	         pstmt.setString(4, skd_e.getDepartment().getDepartment_id()+"%");
	         pstmt.setString(5, sdate);
	         pstmt.setString(6, edate);
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	Schedule s = new Schedule();
	            ScheduleType st = new ScheduleType();
	            st.setSkd_type(rs.getString("skd_type"));
	            s.setSkd_type(st);
	            s.setSkd_title(rs.getString("skd_title"));
	            s.setSkd_start_date(rs.getTimestamp("skd_start_date"));
	            s.setSkd_end_date(rs.getTimestamp("skd_end_date"));
	            s.setSkd_share(rs.getString("skd_share"));
	                  
	            list.add(s);
//	         System.out.println("전체스케줄:"+s);
	         }
	         if(list.size() == 0) {
	            throw new FindException("일정이 없습니다.");
	         }
	         return list;
	      }catch(SQLException e){
	         e.printStackTrace();
	         throw new FindException(e.getMessage());
	      }finally {
	         MyConnection.close(con, pstmt, rs);
	      }
	   
	   }
	public List<Schedule> skdByTeam(String skd_dpt_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}
		String skdByTeamSQL = "SELECT skd_type, skd_title,\r\n" + 
				"skd_start_date, 'yyyy-mm-dd hh24:mi',\r\n" + 
				"skd_end_date, 'yyyy-mm-dd hh24:mi'\r\n" + 
				" FROM SCHEDULE\r\n" + 
				"WHERE employee_id IN (SELECT employee_id FROM employee WHERE department_id =?)\r\n" + 
				"AND skd_share='t'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Schedule> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement(skdByTeamSQL);
			pstmt.setString(1, skd_dpt_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Schedule s = new Schedule();
				ScheduleType st = new ScheduleType();
				st.setSkd_type(rs.getString("skd_type"));
				String skd_title = rs.getString("skd_title");
				Timestamp skd_start_date = rs.getTimestamp("skd_start_date");
				Timestamp skd_end_date = rs.getTimestamp("skd_end_date");

			//	System.out.println("결과값:"+skd_title + skd_start_date+skd_end_date);
				s.setSkd_type(st);
				s.setSkd_title(skd_title);
				s.setSkd_start_date(skd_start_date);
				s.setSkd_end_date(skd_end_date);

				list.add(s);
				
			}
			if(list.size()==0) {
			throw new FindException(skd_dpt_id+"부서에 해당하는 일정이 없습니다.");
			}
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
			
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
		
	}
	public List<Schedule> skdPersonal(String skd_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}
		String skdPersonalSQL = "select skd_type, skd_title,\r\n" + 
				"skd_start_date, skd_end_date \r\n" + 
				"from schedule\r\n" + 
				"where employee_id= ? AND skd_share='p'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Schedule> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement(skdPersonalSQL);
			pstmt.setNString(1, skd_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Schedule s = new Schedule();
				ScheduleType st = new ScheduleType();
				st.setSkd_type(rs.getString("skd_type"));
				s.setSkd_type(st);
				s.setSkd_title(rs.getString("skd_title"));
				s.setSkd_start_date(rs.getTimestamp("skd_start_date"));
				s.setSkd_end_date(rs.getTimestamp("skd_end_date"));
				list.add(s);
//				System.out.println(s);
			}
			if(list.size()==0) {
			throw new FindException("일정이 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	public List<Schedule> skdByContent(Schedule s) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String skdByContentSQL = "SELECT skd_no, skd_type, skd_title, skd_content, skd_start_date, skd_end_date, skd_share\r\n" + 
				"FROM schedule\r\n" + 
				"where employee_id= ? AND (skd_title like ? \r\n" + 
				"OR skd_content like ?)";
		ResultSet rs = null;
		List<Schedule> list = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(skdByContentSQL);
			pstmt.setString(1, s.getSkd_id().employee_id);
			pstmt.setString(2, "%"+s.getSkd_title()+"%");
			pstmt.setString(3, "%"+s.getSkd_content()+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Schedule sc = new Schedule();
				sc.setSkd_start_date(rs.getTimestamp("skd_start_date"));
				sc.setSkd_end_date(rs.getTimestamp("skd_end_date"));
				sc.setSkd_title(rs.getString("skd_title"));
				sc.setSkd_share(rs.getString("skd_share"));
				list.add(sc);
			}
			if(list.size()==0) {
				throw new FindException("일정이 없습니다.");
			} return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	public Schedule skdDetail(int skd_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String skdByContentSQL = "SELECT skd_type,skd_title,skd_content,skd_date,skd_start_date, skd_end_date, skd_share \r\n" + 
				"FROM schedule \r\n" + 
				"WHERE skd_no=?";
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(skdByContentSQL);
			pstmt.setInt(1, skd_no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Schedule s = new Schedule();
				ScheduleType st = new ScheduleType();
				st.setSkd_type(rs.getString("skd_type"));
				s.setSkd_type(st);
				s.setSkd_title(rs.getString("skd_title"));
				s.setSkd_content(rs.getString("skd_content"));
				s.setSkd_date(rs.getTimestamp("skd_date"));
				s.setSkd_start_date(rs.getTimestamp("skd_start_date"));
				s.setSkd_end_date(rs.getTimestamp("skd_end_date"));
				return s;
			}
				throw new FindException("일정이 없습니다.");
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
		
	}

	public static void main(String[] args) {
		//1 Done
//		String skd_id = "MSD002";
//		Department dpt = new Department();
//		dpt.setDepartment_id("MSD");
//		try {
//			ScheduleDAOOracle dao = new ScheduleDAOOracle();
//			Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
//			List<Schedule> all = dao.skdList(em);
//			for(Schedule s: all) {
//				System.out.println(s.getSkd_type()+"/"+s.getSkd_title()+"/"+s.getSkd_start_date()+"/"+
//						s.getSkd_end_date());
//			}
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
		
		//2 Done
//		String skd_id = "SEC002";
//		String sdate = "2021-06-01";
//		String edate = "2021-06-30";
//		Department dpt = new Department();
//		dpt.setDepartment_id("SEC");
//		try {
//			ScheduleDAOOracle dao = new ScheduleDAOOracle();
//			Employee em = new Employee(skd_id, null, dpt, null, null, null, null, null, 1, null);
//			List<Schedule> all = dao.skdPeriodList(em, sdate, edate);
//			for(Schedule s: all) {
//				System.out.println(s.getSkd_type()+"/"+s.getSkd_title()+"/"+s.getSkd_start_date()+"/"+
//						s.getSkd_end_date());
//			}
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
		
		//3 Done
//		String skd_dpt_id = "MSD";
//		try {
//			ScheduleDAOOracle dao = new ScheduleDAOOracle();
//			List<Schedule> all = dao.skdByTeam(skd_dpt_id);
//			for(Schedule s: all) {
//				System.out.println(s.getSkd_type()+"/"+s.getSkd_title()+"/"+s.getSkd_start_date()+"/"+
//						s.skd_end_date);
//			}
//		} catch(FindException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		//4 Done
//		String skd_id = "MSD003";
//		try {
//			ScheduleDAOOracle dao = new ScheduleDAOOracle();
//			List<Schedule> all = dao.skdPersonal(skd_id);
//			for(Schedule s: all) {
//			System.out.println(s.getSkd_type()+"/"+s.getSkd_title()+"/"+
//			s.skd_start_date+"/"+s.getSkd_end_date());
//			}
//		} catch(FindException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		//5 Done
//		String skd_title = "휴가";
//		String skd_content = "휴가";
//		Employee em = new Employee();
//		em.setEmployee_id("MSD003");
//		try {
//			ScheduleDAOOracle dao = new ScheduleDAOOracle();
//			Schedule sc = new Schedule(em, skd_title, skd_content);
//			List<Schedule> all = dao.skdByContent(sc);
//			for(Schedule s: all) {
//			System.out.println(s.skd_start_date+"/"+s.getSkd_end_date()+"/"+
//			s.getSkd_title()+"/"+s.getSkd_share());
//			}
//		} catch(FindException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		//6 Done
//		int skd_no = 1;
//		try {
//			ScheduleDAOOracle dao = new ScheduleDAOOracle();
//			Schedule s = dao.skdDetail(skd_no);
//			System.out.println(s.getSkd_type()+"/"+s.getSkd_title()+"/"+
//					s.getSkd_content()+"/"+s.getSkd_date()+"/"+s.getSkd_start_date()+"/"+s.getSkd_end_date());
//		} catch(FindException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
	}
}
