package com.group.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.group.changeTime;
import com.group.board.dto.Board;
import com.group.board.dto.BoardComment;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;
import com.group.sql.MyConnection;

public class BoardCommentDAOOracle implements BoardCommentDAO {
	public BoardCommentDAOOracle() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
//		System.out.println("load success");
	}



	@Override
	public List<BoardComment> selectAll(String bd_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectAllSQL = "SELECT *" + "FROM boardcomment cm \r\n"
				+ "JOIN employee e ON cm.employee_id = e.employee_id\r\n" + "JOIN board b ON cm.bd_no = b.bd_no\r\n"
				+ "WHERE b.bd_no=?\r\n" + "ORDER BY cm_no desc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardComment> cmList = new ArrayList<BoardComment>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			pstmt.setString(1, bd_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardComment cm = new BoardComment();
				cm.setBd_no(bd_no);
				cm.setCm_no(rs.getInt("cm_no"));

				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				cm.setCm_writer(emp);
				cm.setCm_date(rs.getTimestamp("cm_date"));
				cm.setCm_content(rs.getString("cm_content"));

				cmList.add(cm);
			}
			if (cmList.size() == 0) {
				throw new FindException("댓글이 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

		return cmList;
	}

	@Override
	public void insert(BoardComment cm) throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}

		String insertSQL = "INSERT INTO boardcomment " + "(bd_no,cm_no,employee_id,cm_content) \r\n"
				+ "VALUES(?,(SELECT NVL(MAX(cm_no), 0)+1 FROM boardcomment WHERE bd_no=?),?,?)";
//		System.out.println(insertSQL);
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setString(1, cm.getBd_no());
			pstmt.setString(2, cm.getBd_no());
			pstmt.setString(3, cm.getCm_writer().getEmployee_id());
			pstmt.setString(4, cm.getCm_content());
			pstmt.executeUpdate();

			System.out.println("댓글이 추가되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	public void delete(BoardComment cm) throws RemoveException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}

		String deleteSQL = "DELETE FROM boardcomment WHERE bd_no=? AND cm_no=? AND employee_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setString(1, cm.getBd_no());
			pstmt.setInt(2, cm.getCm_no());
			pstmt.setString(3, cm.getCm_writer().getEmployee_id());
			pstmt.executeUpdate();

			System.out.println("댓글을 삭제하였습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}

	public static void main(String[] args) {
//		try {
//			BoardCommentDAOOracle dao = new BoardCommentDAOOracle();
//			String bd_no = "BD2";
//			List<BoardComment> cmList = dao.selectAll(bd_no);
//			for (BoardComment cm : cmList) {
//				System.out.println(cm.getBd_no() + "/" + cm.getCm_no() + "/" + cm.getCm_writer().getEmployee_id() + "/"
//						+ cm.getCm_writer().getName() + "/" + cm.getCm_content() + "/" + changeTime.modifyTime(cm.getCm_date()));
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			BoardCommentDAOOracle dao = new BoardCommentDAOOracle();
//			Employee emp = new Employee();
//			BoardComment cm = new BoardComment();
//			emp.setEmployee_id("DEV003");
//			cm.setCm_writer(emp);
//			cm.setBd_no("BD2");
//			cm.setCm_content("안녕하십니까~~");
//			dao.insert(cm);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			BoardCommentDAOOracle dao = new BoardCommentDAOOracle();
//			Employee emp = new Employee();
//			BoardComment cm = new BoardComment();
//			emp.setEmployee_id("DEV003");
//			cm.setCm_writer(emp);
//			cm.setBd_no("BD2");
//			cm.setCm_no(3);
//			dao.delete(cm);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
	}

}
