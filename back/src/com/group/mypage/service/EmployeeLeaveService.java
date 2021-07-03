package com.group.mypage.service;

import java.io.FileInputStream;
import java.util.Properties;

import com.group.employee.dto.Employee;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.mypage.dao.EmployeeLeaveDAO;
import com.group.mypage.dto.EmployeeLeave;

public class EmployeeLeaveService {
	private EmployeeLeaveDAO dao;
	private static EmployeeLeaveService service = new EmployeeLeaveService();

	private EmployeeLeaveService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream("classes.prop"));
			String className = env.getProperty("employeeLeaveDAO");
			System.out.println(className);
			/*
			 * 리플랙션 기법 이용하여 객체 생성 소스코드를 재컴파일하지 않기 위해 리플랙션 기법 이용하는 것임!
			 */
			Class c = Class.forName(className); // JVM에 로드
			dao = (EmployeeLeaveDAO) c.newInstance(); // 객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public EmployeeLeave showDetail(String id) throws FindException{
		return null;
	}
	public void update(Employee emp) throws ModifyException{
		
	}
	
	public static void main(String[] args) {
		System.out.println(service);
	}
}
