package com.group.employee.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.group.employee.dao.EmployeeDAO;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;

public class EmployeeService {
	private EmployeeDAO dao;
	private static EmployeeService service = new EmployeeService();

	private EmployeeService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream("classes.prop"));
			String className = env.getProperty("employeeDAO");
			System.out.println(className);
			/*
			 * 리플랙션 기법 이용하여 객체 생성 소스코드를 재컴파일하지 않기 위해 리플랙션 기법 이용하는 것임!
			 */
			Class c = Class.forName(className); // JVM에 로드
			dao = (EmployeeDAO) c.newInstance(); // 객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 사원 전체를 조회한다
	 * @return 사원 전체 목록
	 * @throws FindException
	 */
	public List<Employee> showAll() throws FindException {
		return dao.selectAll();
	}

	/**
	 * 부서별 사원 목록을 조회한다
	 * @param dept 특정 부서번호
	 * @return 부서별 사원 목록
	 * @throws FindException
	 */
	public List<Employee> showByDept(String dept) throws FindException {
		return dao.selectByDep(dept);
	}

	/**
	 * 사원을 검색한다
	 * @param word 검색 단어
	 * @return 특정 검색 단어에 해당하는 사원 목록
	 * @throws FindException
	 */
	public List<Employee> searchEmp(String word) throws FindException {
		return dao.selectByWord(word);
	}

	/**
	 * 특정 사원의 상세 정보를 조회한다
	 * @param emp 특정 사원의 사번 및 이름을 담고 있는 객체
	 * @return 사원의 상세 정보
	 * @throws FindException
	 * 클릭한 객체의 이름과 사번을 검색해와야함
	 */
	public Employee showDetail(Employee emp) throws FindException {
		return dao.selectInfo(emp);
	}

	public static void main(String[] args) {
		try {
			List<Employee> empList = service.showAll();
			for(Employee emp : empList) {
				System.out.println(emp);				
			}
		} catch (FindException e) {
			e.printStackTrace();
		}

//		String dept="DEV";
//		try {
//			List<Employee> empList = service.showByDept(dept);
//			for (Employee emp : empList) {
//				System.out.println(emp);
//			}
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
//		

//		String word="정";
//		try {
//			List<Employee> empList = service.searchEmp(word);
//			for (Employee emp : empList) {
//				System.out.println(emp);
//			}
//		} catch (FindException e) {
//			e.printStackTrace();
//		}

//		String name = "정연주";
//		String id = "DEV003";
//		Employee click = new Employee();
//		click.setEmployee_id(id);
//		click.setName(name);
//		try {
//			Employee emp = service.showDetail(click);
//			 System.out.println(emp.getEmployee_id() + "/" + emp.getName() +
//			 "/" + emp.getDepartment().getDepartment_id()
//			+ "/" + emp.getDepartment().getDepartment_title() + "/" + emp.getPosition().getPosition_title()
//			+ "/" + emp.getJob().getJob_title() + "/" + emp.getPhone_number() + "/" + emp.getEmail());
//
//		} catch (FindException e) {
//			e.printStackTrace();
//		}

	}
}
