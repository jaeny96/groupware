package com.group.approval.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.group.approval.dao.ConfirmDocsDAO;
import com.group.approval.dao.DocsWriteDAO;
import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;

public class DocsWriteService {
	private DocsWriteDAO dao;
	private static DocsWriteService service;
//	private static DocsWriteService service=new DocsWriteService();
//	private static String envProp="classes.prop";	//back에서만 테스트용
	public static String envProp;	//front테스트용

	private DocsWriteService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
//			env.load(new FileInputStream("classes.prop"));
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
	 * 결재문서 기안
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void complete(Document d) throws AddException {
		dao.draft(d);
	}
	/**
	 * 결재문서의 결재자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void completeApRegister(Approval ap) throws AddException {
		dao.draftAp(ap);
	}
	/**
	 * 결재문서의 합의자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void completeAgRegister(Agreement ag) throws AddException {
		dao.draftAg(ag);
	}
	/**
	 * 결재문서의 참조자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void completeReRegister(Reference re) throws AddException {
		dao.draftRe(re);
	}

	/**
	 * 사원이름 리스트 조회
	 * 
	 * @param name
	 * @throws FindException
	 */
	public List<Employee> staff(String word) throws FindException {
		return dao.searchByName(word);
	}

	/**
	 * 부서이름 리스트 조회
	 * 
	 * @param department_title
	 * @return
	 * @throws FindException
	 */
//	public List<Department> group(String department_title) throws FindException {
//		return dao.searchByDep(department_title);
//
//	}
	/**
	 * 사원 전체를 조회한다
	 * @return 사원 전체 목록
	 * @throws FindException
	 */
	public List<Employee> showAll() throws FindException {
		return dao.searchApLineStaff();
	}
	
	
	public static void main(String[] args) {
//		   DocsWriteService service = DocsWriteService.getInstance();
//		      try {
//				List<Employee> empList = service.staff("경영지원실");
//				for(Employee e : empList) {
//					System.out.println(e);
//				}
//			} catch (FindException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		      String word = "김";
//		      System.out.println("\"" + word + "\"단어를 포함한 사원목록");
//		      try {
//		         
//		         List<Employee> list = service.staff(word);
//		         for (Employee em : list) {
//		            System.out.println(em.getName());
//		         }
//		      } catch (FindException e) {
//		         System.out.println(e.getMessage());
//		      } catch (Exception e) {
//		         System.out.println(e.getMessage());
//		      }
		   
//		Document document = new Document();
//		document.setDocument_no("a");
//		document.setDocument_title("aa");
//		document.setDocument_content("aaaa");
//		DocumentType dtype = new DocumentType();
//		dtype.setDocument_type("지출");
//		document.setDocument_type(dtype);
//		Employee emp = new Employee();
//		emp.setEmployee_id("MSD002");
//		document.setEmployee(emp);
//		System.out.println("ac "+document);
//		
//		try {
//			service.complete(document);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		   Approval ap = new Approval();
//		   Document d = new Document();
//		   d.setDocument_no("BC-연락-20210622-0001");
//		   ap.setDocument_no(d);
//		   Employee emp = new Employee();
//		   emp.setEmployee_id("MSD002");
//		   ap.setEmployee_id(emp);
//		   ap.setAp_step(2);
//		   
//		   try {
//			service.completeApRegister(ap);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
		   
//		   Agreement ag = new Agreement();
//		   Document dag = new Document();
//		   dag.setDocument_no("BC-연락-20210622-0001");
//		   ag.setDocument_no(dag);
//		   Employee empag = new Employee();
//		   empag.setEmployee_id("MSD002");
//		   ag.setEmployee_id(empag);
//		   
//		   try {
//			service.completeAgRegister(ag);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//		   Reference re = new Reference();
//		   Document dre = new Document();
//		   dre.setDocument_no("BC-연락-20210622-0001");
//		   re.setDocument_no(dre);
//		   Employee empre = new Employee();
//		   empre.setEmployee_id("MSD002");
//		   re.setEmployee_id(empre);
//		   
//		   try {
//			service.completeReRegister(re);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
	}
}
