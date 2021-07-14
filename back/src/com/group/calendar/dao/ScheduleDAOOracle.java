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

	
	public List<Schedule> skdList(Employee skd_e) throws FindException {
		//DB연결
		Connection con = null;
		try {
			con= MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String SkdListSQL = "SELECT skd_no, skd_type, skd_title,\r\n" + 
				"skd_start_date,\r\n" + 
				"skd_end_date, skd_share\r\n" + 
				"FROM schedule\r\n" + 
				"WHERE employee_id=? AND skd_share ='p'" + 
				"UNION ALL\r\n" + 
				"SELECT skd_no, skd_type, skd_title,\r\n" + 
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
				s.setSkd_no(rs.getInt("skd_no"));
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
	      
	      String SkdListSQL = "SELECT skd_type, skd_title, skd_content,\r\n" + 
	            "skd_start_date,\r\n" + 
	            "skd_end_date, skd_share\r\n" + 
	            "FROM schedule\r\n" + 
	            "WHERE employee_id= ? AND skd_share ='p' AND skd_start_date\r\n" + 
	            "BETWEEN ? AND ? \r\n" + 
	            "UNION ALL\r\n" + 
	            "SELECT skd_type, skd_title,skd_content,\r\n" + 
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
	            s.setSkd_content(rs.getNString("skd_content"));
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
				ScheduleType st = new ScheduleType();
				st.setSkd_type(rs.getString("skd_type"));
				sc.setSkd_type(st);
				sc.setSkd_content(rs.getString("skd_content"));
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
	      
	       
	   //    skd_id = emp.getEmployee_id();
	       
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
