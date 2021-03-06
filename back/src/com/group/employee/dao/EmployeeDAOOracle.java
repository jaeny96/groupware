package com.group.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Job;
import com.group.employee.dto.Position;
import com.group.exception.FindException;
import com.group.sql.MyConnection;

public class EmployeeDAOOracle implements EmployeeDAO {
	public EmployeeDAOOracle() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("load success");
	}

	@Override
	public List<Employee> selectAll() throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectAllSQL = "SELECT *\r\n" + "FROM department d\r\n"
				+ "JOIN employee e ON d.department_id = e.department_id\r\n"
				+ "JOIN position p ON e.position_id=p.position_id JOIN job j ON e.job_id=j.job_id\r\n"
				+ "WHERE enabled=1\r\n"
				+ "ORDER BY DECODE(d.department_id,'CEO',1),d.department_title, p.position_id, employee_id";
		System.out.println(selectAllSQL);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Employee> empList = new ArrayList<Employee>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				Department d = new Department();
				d.setDepartment_id(rs.getString("department_id"));
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

				empList.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return empList;
	}

	@Override
	public List<Employee> selectByDep(String dep_id) throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectByDepSQL = "SELECT *\r\n" + "FROM department d\r\n"
				+ "JOIN employee e ON d.department_id = e.department_id\r\n"
				+ "JOIN position p ON e.position_id=p.position_id\r\n" + "JOIN job j ON e.job_id=j.job_id\r\n"
				+ "WHERE d.department_id=? AND enabled=1\r\n" + "ORDER BY p.position_id, employee_id";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Employee> empList = new ArrayList<Employee>();
		try {
			pstmt = con.prepareStatement(selectByDepSQL);
			pstmt.setString(1, dep_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				Department d = new Department();
				d.setDepartment_id(rs.getString("department_id"));
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

				empList.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return empList;

	}

	@Override
	public List<Employee> selectByWord(String word) throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		String selectByNameSQL = "SELECT *\r\n" + "FROM department d\r\n"
				+ "JOIN employee e ON d.department_id = e.department_id\r\n"
				+ "JOIN position p ON e.position_id = p.position_id " + "JOIN job j ON e.job_id=j.job_id\r\n"
				+ "WHERE name LIKE ? AND enabled=1\r\n" + "ORDER BY department_title,  p.position_id, employee_id";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Employee> empList = new ArrayList<Employee>();
		try {
			pstmt = con.prepareStatement(selectByNameSQL);
			pstmt.setString(1, "%" + word + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmployee_id(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				Department d = new Department();
				d.setDepartment_id(rs.getString("department_id"));
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

				empList.add(emp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}

		return empList;
	}

	@Override
	public Employee selectInfo(Employee emp) throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectInfoSQL = "SELECT *\r\n"
				+ "FROM employee e\r\n" + "JOIN department d ON (e.department_id = d.department_id)\r\n"
				+ "JOIN position p ON (e.position_id = p.position_id)\r\n" + "JOIN job j ON (e.job_id = j.job_id)\r\n"
				+ "WHERE name=? AND employee_id=?";
		Employee clickEmp = new Employee();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectInfoSQL);
			pstmt.setString(1, emp.getName());
			pstmt.setString(2, emp.getEmployee_id());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				clickEmp.setEmployee_id(emp.getEmployee_id());
				clickEmp.setName(emp.getName());
				Department d = new Department();
				d.setDepartment_id(rs.getString("department_id"));
				d.setDepartment_title(rs.getString("department_title"));
				clickEmp.setDepartment(d);
				Position p = new Position();
				p.setPosition_title(rs.getString("position_title"));
				clickEmp.setPosition(p);
				Job j = new Job();
				j.setJob_title(rs.getString("job_title"));
				clickEmp.setJob(j);
				clickEmp.setPhone_number(rs.getString("phone_number"));
				clickEmp.setEmail(rs.getString("email"));
			} else {
				throw new FindException("????????? ?????? ??? ????????????");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}

		return clickEmp;
	}

	public static void main(String[] args) {
//		try {
//			EmployeeDAOOracle dao = new EmployeeDAOOracle();
//			List<Employee> empList = dao.selectAll();
//			for(Employee emp : empList)
//			System.out.println(emp.getEmployee_id() + "/" 
//					+ emp.getName() + "/"
//					+ emp.getDepartment().getDepartment_id() + "/"
//					+ emp.getDepartment().getDepartment_title() + "/"
//					+ emp.getPosition().getPosition_title() + "/"
//					+ emp.getJob().getJob_title() + "/"
//					+ emp.getPhone_number() + "/" 
//					+ emp.getEmail());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			EmployeeDAOOracle dao = new EmployeeDAOOracle();
//			String dep_id="DEV";
//			List<Employee> empList = dao.selectByDep(dep_id);
//			for (Employee emp : empList)
//				System.out.println(emp.getEmployee_id() + "/" + emp.getName() + "/"
//						+ emp.getDepartment().getDepartment_id() + "/" 
//						+ emp.getDepartment().getDepartment_title() + "/"
//						+ emp.getPosition().getPosition_title() + "/" 
//						+ emp.getJob().getJob_title() + "/"
//						+ emp.getPhone_number() + "/" + emp.getEmail());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			EmployeeDAOOracle dao = new EmployeeDAOOracle();
//			String word = "???";
//			List<Employee> empList = dao.selectByWord(word);
//			for (Employee emp : empList)
//				System.out.println(emp.getEmployee_id() + "/" + emp.getName() + "/"
//						+ emp.getDepartment().getDepartment_id() + "/" + emp.getDepartment().getDepartment_title() + "/"
//						+ emp.getPosition().getPosition_title() + "/" + emp.getJob().getJob_title() + "/"
//						+ emp.getPhone_number() + "/" + emp.getEmail());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

//		try {
//			EmployeeDAOOracle dao = new EmployeeDAOOracle();
//			String name = "?????????";
//			String id = "DEV001";
//			Employee click = new Employee();
//			click.setName(name);
//			click.setEmployee_id(id);
//			Employee emp = dao.selectInfo(click);
//			System.out.println(emp.getEmployee_id() + "/" + emp.getName() + "/" + emp.getDepartment().getDepartment_id()
//					+ "/" + emp.getDepartment().getDepartment_title() + "/" + emp.getPosition().getPosition_title()
//					+ "/" + emp.getJob().getJob_title() + "/" + emp.getPhone_number() + "/" + emp.getEmail());
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
	}
}
