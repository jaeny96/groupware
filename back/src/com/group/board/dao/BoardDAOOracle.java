package com.group.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group.changeTime;
import com.group.board.dto.Board;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
import com.group.sql.MyConnection;

public class BoardDAOOracle implements BoardDAO {
	public BoardDAOOracle() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("load success");
	}
	public void alterFormat() {
		
	}

	@Override
	public List<Board> selectAll() throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectAllSQL = "select*\r\n" + "FROM (SELECT rownum rn, a.* \r\n" + "    FROM ( SELECT *\r\n"
				+ "            FROM board b \r\n" + "            JOIN employee e ON b.employee_id = e.employee_id\r\n"
				+ "            ORDER BY bd_no desc\r\n" + "        ) a\r\n" + "    )\r\n";
//		System.out.println(selectAllSQL);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> bdList = new ArrayList<Board>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board bd = new Board();

				bd.setBd_no(rs.getString("bd_no"));

				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);

				bd.setBd_title(rs.getString("bd_title"));
				bd.setBd_date(rs.getTimestamp("bd_date"));
				bdList.add(bd);
			}
			if (bdList.size() == 0) {
				throw new FindException("게시글이 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return bdList;
	}

	@Override
	public List<Board> selectAll(int currentPage) throws FindException {
		int cnt_per_page = 3;
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectAllPageSQL = "select * \r\n" + "FROM (SELECT rownum rn, a.* \r\n" + "    FROM ( SELECT *\r\n"
				+ "            FROM board b \r\n" + "            JOIN employee e ON b.employee_id = e.employee_id\r\n"
				+ "            ORDER BY bd_no desc\r\n" + "        ) a\r\n" + "    )\r\n"
				+ "WHERE rn BETWEEN start_row(?,?) AND end_row(?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> bdList = new ArrayList<Board>();
		try {
			pstmt = con.prepareStatement(selectAllPageSQL);
			pstmt.setInt(1, currentPage);
			pstmt.setInt(2, cnt_per_page);
			pstmt.setInt(3, currentPage);
			pstmt.setInt(4, cnt_per_page);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board bd = new Board();
				bd.setBd_no(rs.getString("bd_no"));
				bd.setBd_title(rs.getString("bd_title"));
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);
				bd.setBd_date(rs.getTimestamp("bd_date"));

				bdList.add(bd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return bdList;
	}

	@Override
	public List<Board> selectByWord(String category, String word) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectByWordSQL = "SELECT * \r\n" + "FROM board b \r\n"
				+ "JOIN employee e ON b.employee_id = e.employee_id\r\n" + "WHERE " + category
				+ " LIKE ? ORDER BY bd_no desc";
//		System.out.println(selectByWordSQL);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> bdList = new ArrayList<Board>();
		try {
			pstmt = con.prepareStatement(selectByWordSQL);
			pstmt.setString(1, "%" + word + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board bd = new Board();
				bd.setBd_no(rs.getString("bd_no"));
				bd.setBd_title(rs.getString("bd_title"));
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);
				bd.setBd_date(rs.getTimestamp("bd_date"));
				bdList.add(bd);
			}
			if (bdList.size() == 0) {
				throw new FindException("일치하는 내용이 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return bdList;
	}

	@Override
	public Board selectBdInfo(String bd_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectBdInfoSQL = "SELECT *\r\n" + "FROM board b \r\n"
				+ "JOIN employee e ON b.employee_id = e.employee_id\r\n" + "WHERE b.bd_no=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board bd = new Board();
		try {
			pstmt = con.prepareStatement(selectBdInfoSQL);
			pstmt.setString(1, bd_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				bd.setBd_no(rs.getString("bd_no"));
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				bd.setWriter(emp);
				bd.setBd_title(rs.getString("bd_title"));
				bd.setBd_content(rs.getString("bd_content"));
				bd.setBd_date(rs.getTimestamp("bd_date"));
//				System.out.println(bd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}
		return bd;
	}

	@Override
	public void insert(Board bd) throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}

		String insertSQL = "INSERT INTO " + "board(bd_no,employee_id,bd_title,bd_content) "
				+ "VALUES('BD'||BD_SEQ.NEXTVAL,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setString(1, bd.getWriter().getEmployee_id());
			pstmt.setString(2, bd.getBd_title());
			pstmt.setString(3, bd.getBd_content());
			pstmt.executeUpdate();

			System.out.println("게시글이 추가되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	public void update(Board bd) throws ModifyException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}

		String str = "";

		if (bd.getBd_title() != null) {
			str += " bd_title='" + bd.getBd_title() + "',";
		}

		if (bd.getBd_content() != null) {
			str += " bd_content='" + bd.getBd_content() + "',";
		}
		String updateSQL = "UPDATE board SET " + str.substring(0, str.length() - 1)
				+ " ,bd_date=SYSTIMESTAMP WHERE bd_no=? AND employee_id=?";
//		System.out.println(updateSQL);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1, bd.getBd_no());
			pstmt.setString(2, bd.getWriter().getEmployee_id());
			int rowcnt = pstmt.executeUpdate();
			if (rowcnt == 1) {
				System.out.println("내용이 변경되었습니다");
			} else {
				throw new ModifyException("내용이 변경되지 않았습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}

	}

	@Override
	public void delete(Board bd) throws RemoveException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}

		String deleteSQL = "DELETE FROM board WHERE bd_no=? AND employee_id=?";
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setString(1, bd.getBd_no());
			pstmt.setString(2, bd.getWriter().getEmployee_id());
			pstmt.executeUpdate();

			System.out.println("게시글을 삭제하였습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}

	public static void main(String[] args) {
		try {
			BoardDAOOracle dao = new BoardDAOOracle();
			List<Board> bdList = dao.selectAll();
			for (Board bd : bdList) {
				System.out.println(bd.getBd_no() + "/" + bd.getBd_title() + "/" + bd.getWriter().getEmployee_id() + "/"
						+ bd.getWriter().getName() + "/" + changeTime.modifyTime(bd.getBd_date()));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		try {
//			BoardDAOOracle dao = new BoardDAOOracle();
//			int currentPage=1;
//			List<Board> bdList = dao.selectAll(currentPage);
//			for(Board bd : bdList) {
//				System.out.println(bd.getBd_no()+"/"+bd.getBd_title()+
//						"/"+bd.getWriter().getEmployee_id()+"/"+bd.getWriter().getName()+
//						"/"+bd.getBd_date());
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			BoardDAOOracle dao = new BoardDAOOracle();
//			List<Board> bdList = dao.selectByWord("name","권");
//			for (Board bd : bdList) {
//				System.out.println(bd.getBd_no() + "/" + bd.getBd_title() + "/" + bd.getWriter().getEmployee_id() + "/"
//						+ bd.getWriter().getName() + "/" + bd.getBd_date());
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			BoardDAOOracle dao = new BoardDAOOracle();
//			int currentPage = 1;
//			List<Board> bdList = dao.selectAll(currentPage);
//			Board bd = dao.selectBdInfo(bdList.get(1).getBd_no());
//			System.out.println(bd.getBd_no() + "/" + bd.getWriter().getEmployee_id() + "/" + bd.getWriter().getName()
//					+ "/" + bd.getBd_title() + "/" + bd.getBd_content() + "/" + bd.getBd_date());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			BoardDAOOracle dao = new BoardDAOOracle();
//			Employee emp = new Employee();
//			Board bd = new Board();
//			emp.setEmployee_id("DEV001");
//			bd.setWriter(emp);
//			bd.setBd_title("책임을 맡게된 임창균입니다.");
//			bd.setBd_content("앞으로 잘 부탁드립니다 여러분");
//			dao.insert(bd);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			BoardDAOOracle dao = new BoardDAOOracle();
//			int currentPage = 1;
//			List<Board> bdList = dao.selectAll(currentPage);
//			Board bd = dao.selectBdInfo(bdList.get(1).getBd_no());
//			bd.setBd_title("");
////			bd.setBd_title("하하");
////			bd.setBd_content("");
//			bd.setBd_content("놀랐지?ㅋㅋㅋㅋ");
//			dao.update(bd);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			BoardDAOOracle dao = new BoardDAOOracle();
//			Employee emp = new Employee();
//			Board bd = new Board();
//			bd.setBd_no("BD7");
//			emp.setEmployee_id("DEV001");
//			bd.setWriter(emp);
//			dao.delete(bd);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

	}
}
