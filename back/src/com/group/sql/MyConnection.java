package com.group.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MyConnection {
	public static Connection getConnection() throws SQLException{
		
		String url ="jdbc:oracle:thin:@localhost:1521:xe";
		String user = "groupware";
		String password = "1234";
		
		Connection con = null;
		con = DriverManager.getConnection(url, user, password);		
	
		//url,user,passwrod 정보를 담은 con을 반환 
		return con;
	
//		try {
//		con = DriverManager.getConnection(url, user, password);	
//} catch (Exception e) {
//	e.printStackTrace();
//}
	}
	 
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			
			if(stmt !=null) {
			try {
				stmt.close();
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
