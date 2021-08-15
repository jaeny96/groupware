package com.group.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group.employee.dto.Department;
import com.group.exception.FindException;
import com.group.sql.MyConnection;

public class DepartmentDAOOracle implements DepartmentDAO {
	@Override
	public List<Department> selectDepAll() throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}

		String selectDepAllSQL = "SELECT d.department_id,department_title,count(*)\r\n"
				+ "FROM employee e JOIN department d ON (e.department_id = d.department_id)\r\n"
				+ "WHERE enabled=1 \r\n" + "GROUP BY d.department_id,department_title\r\n"
				+ "ORDER BY DECODE(department_id,'CEO',1),department_title";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Department> depList = new ArrayList<Department>();
		try {
			pstmt = con.prepareStatement(selectDepAllSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Department dep = new Department();
				dep.setDepartment_id(rs.getString("department_id"));
				dep.setDepartment_title(rs.getString("department_title"));
				dep.setCount(rs.getInt(3));

				depList.add(dep);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		return depList;
	}
}
