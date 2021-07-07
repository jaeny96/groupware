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

//	public static EmployeeLeaveService getInstance() {
//		if(service==null) {
//			service = new EmployeeLeaveService();
//		}
//		return service;
//	}

	/**
	 * 로그인 한 사원의 상세정보를 마이페이지에서 조회한다
	 * @param id 로그인한 사원의 아이디
	 * @return 로그인 한 사원의 상세정보 
	 * @throws FindException
	 */
	public EmployeeLeave showDetail(String id) throws FindException {
		return dao.selectById(id);
	}

	/**
	 * 로그인 한 사원이 자신의 정보를 수정한다 (연락처, 비밀번호로 제한)
	 * @param emp 로그인한 사원이 변경한 정보를 담은 객체
	 * @throws ModifyException
	 */
	public void modify(Employee emp) throws ModifyException {
		try {
			EmployeeLeave el = showDetail(emp.getEmployee_id());
			if ((emp.getPhone_number() != null && !el.getEmployee().getPhone_number().equals(emp.getPhone_number()))
					|| (emp.getPassword() != null && !el.getEmployee().getPassword().equals(emp.getPassword()))) {
				dao.update(emp);
			} else {
				System.out.println("변경할 정보가 없습니다.");
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(service);
		String id = "DEV001";
		try {
			EmployeeLeave el = service.showDetail(id);
			System.out.println(el.getEmployee() + "/" + el.getEmployee().getDepartment() + "/"
					+ el.getEmployee().getPosition() + "/" + el.getEmployee().getJob() + el.getLeave());
		} catch (FindException e) {
			e.printStackTrace();
		}

//		Employee emp = new Employee();
//		String id = "DEV001";
//		String pwd = "DEV0011234";
//		String phoneNum = "010-1186-6485";
//		emp.setEmployee_id(id);
//		emp.setPassword(pwd);
//		emp.setPhone_number(phoneNum);
//		try {
//			service.modify(emp);
//		} catch (ModifyException e) {
//			e.printStackTrace();
//		}

	}
}
