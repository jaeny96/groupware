package com.group.approval.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.group.approval.dao.ConfirmDocsDAO;
import com.group.approval.dao.DocsWriteDAO;
import com.group.approval.dto.Document;
import com.group.approval.exception.AddException;
import com.group.approval.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public class DocsWriteService {
	private DocsWriteDAO dao;
	private static DocsWriteService service;

	private static String envProp="classes.prop";	//back에서만 테스트용
//	public static String envProp;	//front테스트용


	private DocsWriteService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("DocsWriteDAO");
			System.out.println(className);
			Class c = Class.forName(className);
			dao = (DocsWriteDAO) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DocsWriteService getInstance() {
		if (service == null) {
			service = new DocsWriteService();
		}
		return service;
	}

	/**
	 * 결재문서 기안완료
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void complete(Document d) throws AddException {
		dao.draft(d);
	}

	/**
	 * 사원이름 리스트 조회
	 * 
	 * @param name
	 * @throws FindException
	 */
	public List<Employee> staff(String name) throws FindException {
		return dao.searchByName(name);
	}

	/**
	 * 부서이름 리스트 조회
	 * 
	 * @param department_title
	 * @return
	 * @throws FindException
	 */
	public List<Department> group(String department_title) throws FindException {
		return dao.searchByDep(department_title);

	}
	public static void main(String[] args) {
		   DocsWriteService service = DocsWriteService.getInstance();
		      
		      String word = "김";
		      System.out.println("\"" + word + "\"단어를 포함한 사원목록");
		      try {
		         
		         List<Employee> list = service.staff(word);
		         for (Employee em : list) {
		            System.out.println(em.getName());
		         }
		      } catch (FindException e) {
		         System.out.println(e.getMessage());
		      } catch (Exception e) {
		         System.out.println(e.getMessage());
		      }
	}
}
