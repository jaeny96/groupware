package com.group.employee.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.group.employee.dao.DepartmentDAO;
import com.group.employee.dto.Department;
import com.group.exception.FindException;

public class DepartmentService {
	private DepartmentDAO dao;
	private static DepartmentService service = new DepartmentService();

	private DepartmentService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream("classes.prop"));
			String className = env.getProperty("departmentDAO");
			System.out.println(className);
			/*
			 * 리플랙션 기법 이용하여 객체 생성 소스코드를 재컴파일하지 않기 위해 리플랙션 기법 이용하는 것임!
			 */
			Class c = Class.forName(className); // JVM에 로드
			dao = (DepartmentDAO) c.newInstance(); // 객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 부서목록 및 부서별 사원수를 조회한다
	 * @return 부서목록 및 부서별 사원수
	 * @throws FindException
	 */
	public List<Department> showDept() throws FindException{
		return dao.selectDepAll();
	}
	
	public static void main(String[] args) {
//		try {
//			List<Department> deptList = service.showDept();
//			for (Department dept : deptList) {
//				System.out.println(dept.getDepartment_id() + "/" + dept.getDepartment_title() + "/" + dept.getCount());
//			}
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
	}
}
