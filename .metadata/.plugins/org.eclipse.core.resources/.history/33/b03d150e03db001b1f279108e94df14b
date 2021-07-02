package com.group.mypage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Leave;
import com.group.employee.dto.Position;
import com.group.mypage.dto.EmployeeLeave;
import com.group.sql.MyConnection;

public class EmployeeLeaveDAOOracle implements EmployeeLeaveDAO {
//	public EmployeeLeaveDAOOracle() throws Exception {
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		System.out.println("load success");
//	}

	public EmployeeLeave selectById(String id) throws Exception {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String selectSQL = "SELECT *\r\n" + "FROM employee e \r\n"
				+ "JOIN department d ON e.department_id = d.department_id\r\n"
				+ "JOIN position p ON e.position_id = p.position_id\r\n" + "JOIN job j ON e.job_id = j.job_id\r\n"
				+ "JOIN leave l ON l.employee_id = e.employee_id \r\n" + "WHERE e.employee_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeLeave empleave = null;
		try {
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empleave = new EmployeeLeave();

				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				Department d = new Department();
				d.setDepartment_title(rs.getString("department_title"));
				emp.setDepartment(d);
				Position p = new Position();
				p.setPosition_title(rs.getString("position_title"));
				emp.setPosition(p);
				Job j = new Job();
				j.setJob_title(rs.getString("job_title"));
				emp.setJob(j);
				emp.setPhone_number(rs.getString("phone_number"));
				emp.setEmail(rs.getString("email"));
				emp.setHire_date(rs.getDate("hire_date"));
				emp.setPassword(rs.getString("password"));

				Leave leave = new Leave();
				leave.setGrant_days(rs.getInt("grant_days"));
				leave.setRemain_days(rs.getInt("remain_days"));
				leave.setUse_days(rs.getInt("grant_days") - rs.getInt("remain_days"));

				empleave.setEmployee(emp);
				empleave.setLeave(leave);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return empleave;
	}

	public void update(Employee emp) throws Exception{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
//			System.out.println("connection success");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String str = "";

		if (!"".equals(emp.getPhone_number())) {
			str += " phone_number='"+emp.getPhone_number()+"'";
		}

		if (!"".equals(emp.getPassword())) {
			str += " password='"+emp.getPassword()+"'";
		}

		String updateSQL = "UPDATE employee SET" + str + " WHERE employee_id=?";
//		System.out.println(updateSQL);
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1, emp.getEmployee_id());
			int rowcnt = pstmt.executeUpdate();
			if (rowcnt == 1 && emp.employee_status == 1) {
				System.out.println(emp.getEmployee_id() + "의 정보가 변경되었습니다.");
			} else {
				// throw exception
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}

	public static void main(String[] args) {
//		try {
//			EmployeeLeaveDAOOracle dao = new EmployeeLeaveDAOOracle();
//			String id = "DEV001";
//			EmployeeLeave el = dao.selectById(id);
//			System.out.println(el.getEmployee().getEmployee_id() + "/" + el.getEmployee().getName() + "/"
//					+ el.getEmployee().getDepartment().getDepartment_title() + "/"
//					+ el.getEmployee().getPosition().getPosition_title() + "/" + el.getEmployee().getJob().job_title + "/"
//					+ el.getEmployee().getPhone_number() + "/" + el.getEmployee().getEmail() + "/" + el.getLeave().getGrant_days()
//					+ "/" + el.getLeave().getUse_days() + "/" + el.getLeave().getRemain_days());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			EmployeeLeaveDAOOracle dao = new EmployeeLeaveDAOOracle();
//			String id="DEV001";
//			EmployeeLeave el = dao.selectById(id);
////			el.getEmployee().setPhone_number("010-1111-2222");
//			el.getEmployee().setPhone_number("");
////			el.getEmployee().setPassword("");
//			el.getEmployee().setPassword("1234");
//			dao.update(el.getEmployee());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
