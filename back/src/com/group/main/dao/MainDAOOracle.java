package com.group.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.sql.MyConnection;

public class MainDAOOracle implements MainDAO {
	public MainDAOOracle() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("load success");
	}

	@Override
	public Employee selectById(String id) {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String selectByIdSQL = "SELECT employee_id, name \r\n" + "FROM employee\r\n" + "WHERE employee_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Employee emp = new Employee();
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
			} else {
				// throw exception
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return emp;
	}

	@Override
	public Leave selectLeave(String id) {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String selectLeaveSQL = "SELECT grant_days, (grant_days-remain_days), remain_days\r\n" + "FROM leave\r\n"
				+ "WHERE employee_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Leave leave = new Leave();
		try {
			pstmt = con.prepareStatement(selectLeaveSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				leave.setGrant_days(rs.getInt("grant_days"));
				leave.setUse_days(rs.getInt(2));
				leave.setRemain_days(rs.getInt("remain_days"));
			} else {
				// throw exception
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(con, pstmt, rs);
		}

		return leave;
	}

	@Override
	public List<Document> selectDocument(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//페이지 3 -> 10으로 나중에 변경
	@Override
	public List<Board> selectBoard() {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String selectBoardSQL = "SELECT bd_no,bd_title,employee_id,bd_date\r\n" + "FROM (SELECT rownum rn, b.* \r\n"
				+ "        FROM (SELECT * FROM board ORDER BY bd_date desc) b\r\n" + "        )\r\n"
				+ "WHERE rn BETWEEN 1 AND 3";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> bdList = new ArrayList<Board>();

		try {
			pstmt = con.prepareStatement(selectBoardSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board bd = new Board();
				bd.setBd_no(rs.getString("bd_no"));
				bd.setBd_title(rs.getString("bd_title"));
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				bd.setWriter(emp);
				bd.setBd_date(rs.getTimestamp("bd_date"));

				bdList.add(bd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MyConnection.close(con, pstmt, rs);
		}

		return bdList;
	}

	@Override
	public List<Schedule> selectSchedule(Employee emp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
//		try {
//			MainDAOOracle dao = new MainDAOOracle();
//			String id="DEV001";
//			Employee emp = dao.selectById(id);
//			System.out.println(emp.getEmployee_id()+"/"+emp.getName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		try {
//			MainDAOOracle dao = new MainDAOOracle();
//			String id="DEV001";
//			Leave leave = dao.selectLeave(id);
//			System.out.println(id+"/"+leave.getGrant_days()
//			+"/"+leave.getUse_days()+"/"+ leave.getRemain_days());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		try {
//			MainDAOOracle dao = new MainDAOOracle();
//			List<Board> bdList = dao.selectBoard();
//			for (Board bd : bdList) {
//				System.out.println(bd.getBd_no() + "/" + bd.getBd_title() + "/" + bd.getWriter().getEmployee_id() + "/"
//						+ bd.getBd_date());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
}
