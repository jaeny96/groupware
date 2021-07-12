package com.group.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;

import com.group.approval.exception.AddException;
import com.group.approval.exception.FindException;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Department;
import com.group.sql.MyConnection;

public class DocsWriteDAOOracle implements DocsWriteDAO {
	public DocsWriteDAOOracle() throws Exception {
		// JDBC드라이버로드
		Class.forName("oracle.jdbc.driver.OracleDriver");

	}

	// 1. 문서기안
	public void draft(Document d) throws AddException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
			// System.out.println("success");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}

		String draftSQL = "INSERT INTO document (document_no, document_type, employee_id, document_title, document_content)\r\n"
				+ "VALUES (?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(draftSQL);
			pstmt.setString(1, d.getDocument_no());
			pstmt.setString(2, d.getDocument_type().getDocument_type());
			pstmt.setString(3, d.getEmployee().getEmployee_id());
			pstmt.setString(4, d.getDocument_title());
			pstmt.setString(5, d.getDocument_content());
			int rowcnt = pstmt.executeUpdate();
			if (rowcnt == 1) {
				System.out.println("문서기안 완료");
			} else {
				System.out.println("문서기안 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("연결실패");
			return;
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}

	// 2-1. 사원이름을 검색해 결재선에 넣을 사원을 조회한다
	public List<Employee> searchByName(String word) throws FindException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String searchByNameSQL = "SELECT name\r\n" + "FROM employee\r\n" + "where name like ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Employee> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(searchByNameSQL);
			pstmt.setString(1, "%" + word + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Employee em = new Employee();
//				String name = rs.getString("name");
				em.setName(rs.getString("name"));

				list.add(em);
			}
			if (list.size() == 0) {
				throw new FindException("해당 사원이 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			// DB연결해제
			MyConnection.close(con, pstmt, rs);
		}
		return list; // return 구문은 try블럭 뒤에 놓든, finally 뒤에 놓든 결과는 같다.
	}

	// 2-2. 부서이름을 검색해 결재선에 넣을 사원이 속한 조직을 조회한다
	public List<Department> searchByDep(String word) throws FindException {
		// DB연결
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String searchByDepSQL = "SELECT department_title\r\n" + "FROM department \r\n"
				+ "where department_title like ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Department> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(searchByDepSQL);
			pstmt.setString(1, "%" + word + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Department dep = new Department();
				String department_title = rs.getString("department_title");
				dep.setDepartment_title(department_title);

				list.add(dep);
			}
			if (list.size() == 0) {
				throw new FindException("해당 부서가 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			// DB연결해제
			MyConnection.close(con, pstmt, rs);
		}
		return list; // return 구문은 try블럭 뒤에 놓든, finally 뒤에 놓든 결과는 같다.
	}

	public static void main(String[] args) throws Exception {
//		//1.기안하기test
//		Scanner sc = new Scanner(System.in);
//		System.out.println("문서번호: ");
//		String noValue = sc.nextLine();
//		DocumentType type = new DocumentType();
//		type.setDocument_type("휴가");
//		Employee em = new Employee();
//		em.setEmployee_id("DEV002");
//		System.out.println("제목: ");
//		String TitleValue = sc.nextLine();
//		System.out.println("내용: ");
//		String ContValue = sc.nextLine();
//		try {
//			DocsWriteDAOOracle dao = new DocsWriteDAOOracle();
//			Document d = new Document(noValue, type, em, TitleValue, ContValue);
//			dao.draft(d);
//		} catch (FindException e) {
//			e.getMessage();
//		}

		// 2-1.사원명으로 사원검색하기
		String word = "김";
		System.out.println("\"" + word + "\"단어를 포함한 사원목록");
		try {
			DocsWriteDAOOracle dao = new DocsWriteDAOOracle();
			List<Employee> list = dao.searchByName(word);
			for (Employee em : list) {
				System.out.println(em.getName());
			}
		} catch (FindException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		//2-2.부서명으로 부서검색하기
//		String word = "롸";
//		System.out.println("\"" + word + "\"단어를 포함한 부서목록");
//		try {
//			DocsWriteDAOOracle dao = new DocsWriteDAOOracle();
//			List<Department> list = dao.searchByDep(word);
//			for (Department dep : list) {
//				System.out.println(dep.department_title);
//			}
//		} catch (FindException e) {
//			System.out.println(e.getMessage());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

	}
}