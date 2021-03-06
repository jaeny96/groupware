package com.group.approval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.approval.exception.FindException;
import com.group.approval.exception.SearchException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.sql.MyConnection;

public class ConfirmDocsDAOOracle implements ConfirmDocsDAO{
	
	public ConfirmDocsDAOOracle() throws Exception{
		try {
		//JDBC드라이버로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공 ");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//(전체)사용자는 확인 문서를 선택해서 볼 수 있다. 
	@Override
	public List<Document> selectByCheckAllOk(String employee_id) throws FindException {

		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		String sql="SELECT j.document_no, j.document_title, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type, j.ap_type\r\n" + 
					"from employee e join ( \r\n" + 
					"SELECT * FROM (select a.*\r\n" + 
					"FROM ((SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
					"FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" + 
					"WHERE  a.ap_type<>'대기' AND a.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
					"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
					"WHERE r.ap_type<>'대기' AND r.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
					"FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" + 
					"WHERE ag.ap_type<>'대기' AND ag.employee_id=?))a\r\n" + 
					"JOIN document d ON a.document_no= d.document_no )) j on e.employee_id = j.employee_id\r\n" + 
					"ORDER BY draft_date ASC";
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			rs=pstmt.executeQuery();
		
		    while(rs.next()) {
		    	Document d=new Document();
		    	Employee emp=new Employee();
		    	DocumentType dt= new DocumentType();
		    	Approval a = new Approval();
		    	ApprovalStatus ap = new ApprovalStatus();
		    	
			    d.setDocument_no(rs.getString("document_no"));
		    	d.setDocument_title(rs.getString("document_title"));
		    	emp.setEmployee_id(rs.getString("employee_id"));
		    	emp.setName(rs.getString("name"));
		    	d.setEmployee(emp);
		    	d.setDraft_date(rs.getDate("dt"));
		    	dt.setDocument_type(rs.getString("employee_id"));
		    	d.setDocument_type(dt);
		    	String s=rs.getString("ap_type");
		    	ap.setApStatus_type(rs.getString("ap_type"));
		    	a.setAp_type(ap);
		    	d.setApproval(a);
		    	
		  		list.add(d);
				
			}
		    
		    if(list.size()==0) {
			throw new FindException("목록이 존재하지 않습니다.");
		    }
		   
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}

	}
	//(전체)사용자는 미확인 문서를 선택해서 볼 수 있다. 
	@Override
	public List<Document> selectByCheckAllNo(String employee_id) throws FindException {

		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		String sql="SELECT j.document_no, j.document_title, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type, j.ap_type\r\n" + 
					"from employee e join (\r\n" + 
					"SELECT * FROM (select a.*\r\n" + 
					"FROM ((SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
					"FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" + 
					"WHERE a.ap_type='대기' AND a.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
					"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
					"WHERE r.ap_type='대기' AND r.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
					"FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" + 
					"WHERE ag.ap_type='대기' AND ag.employee_id=?))a\r\n" + 
					"JOIN document d ON a.document_no= d.document_no )) j on e.employee_id = j.employee_id\r\n" + 
					"ORDER BY draft_date ASC";
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			rs=pstmt.executeQuery();
		
		    while(rs.next()) {
		    	Document d=new Document();
		    	Employee emp=new Employee();
		    	DocumentType dt= new DocumentType();
		    	Approval a = new Approval();
		    	ApprovalStatus ap = new ApprovalStatus();
		    	
			    d.setDocument_no(rs.getString("document_no"));
		    	d.setDocument_title(rs.getString("document_title"));
		    	emp.setEmployee_id(rs.getString("employee_id"));
		    	emp.setName(rs.getString("name"));
		    	d.setEmployee(emp);
		    	d.setDraft_date(rs.getDate("dt"));
		    	dt.setDocument_type(rs.getString("employee_id"));
		    	d.setDocument_type(dt);
		    	String s=rs.getString("ap_type");
		    	ap.setApStatus_type(rs.getString("ap_type"));
		    	a.setAp_type(ap);
		    	d.setApproval(a);
		    	
		  		list.add(d);
				
			}
		    
		    if(list.size()==0) {
			throw new FindException("목록이 존재하지 않습니다.");
		    }
		   
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}

	}
	
	//(대기)사용자는 미확인 문서를 선택해서 볼 수 있다. 
	@Override
	public List<Document> selectByCheckNoWait(String employee_id) throws FindException {
	

		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		String sql="SELECT j.document_no, j.document_title, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type, j.ap_type\r\n" + 
				"from employee e join ( \r\n" + 
				"SELECT * FROM (select a.*\r\n" + 
				"FROM ((SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
				"FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" + 
				"WHERE  a.ap_type='대기' AND a.employee_id='DEV001')\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
				"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
				"WHERE r.ap_type='대기'  AND r.employee_id='DEV001')\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
				"FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" + 
				"WHERE ag.ap_type='대기' AND ag.employee_id='DEV001'))a\r\n" + 
				"JOIN document d ON a.document_no= d.document_no  WHERE document_status='대기')) j on e.employee_id = j.employee_id\r\n" + 
				"ORDER BY draft_date ASC";
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			rs=pstmt.executeQuery();
		
		    while(rs.next()) {
		    	Document d=new Document();
		    	Employee emp=new Employee();
		    	DocumentType dt= new DocumentType();
		    	Approval a = new Approval();
		    	ApprovalStatus ap = new ApprovalStatus();
		    	
			    d.setDocument_no(rs.getString("document_no"));
		    	d.setDocument_title(rs.getString("document_title"));
		    	emp.setEmployee_id(rs.getString("employee_id"));
		    	emp.setName(rs.getString("name"));
		    	d.setEmployee(emp);
		    	d.setDraft_date(rs.getDate("dt"));
		    	dt.setDocument_type(rs.getString("employee_id"));
		    	d.setDocument_type(dt);
		    	String s=rs.getString("ap_type");
		    	ap.setApStatus_type(rs.getString("ap_type"));
		    	a.setAp_type(ap);
		    	d.setApproval(a);
		    	
		  		list.add(d);
				
			}
		    
		    if(list.size()==0) {
			throw new FindException("목록이 존재하지 않습니다.");
		    }
		   
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	//(대기)사용자는 확인 문서를 선택해서 볼 수 있다. 
	@Override
	public List<Document> selectByCheckOkWait(String employee_id) throws FindException {

		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		String sql="SELECT j.document_no, j.document_title, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type, j.ap_type\r\n" + 
				"from employee e join ( \r\n" + 
				"SELECT * FROM (select a.*\r\n" + 
				"FROM ((SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
				"FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" + 
				"WHERE  a.ap_type='대기' AND a.employee_id='DEV001')\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
				"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
				"WHERE r.ap_type='대기'  AND r.employee_id='DEV001')\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n" + 
				"FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" + 
				"WHERE ag.ap_type='대기' AND ag.employee_id='DEV001'))a\r\n" + 
				"JOIN document d ON a.document_no= d.document_no  WHERE document_status='대기')) j on e.employee_id = j.employee_id\r\n" + 
				"ORDER BY draft_date ASC";
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			rs=pstmt.executeQuery();
		
		    while(rs.next()) {
		    	Document d=new Document();
		    	Employee emp=new Employee();
		    	DocumentType dt= new DocumentType();
		    	Approval a = new Approval();
		    	ApprovalStatus ap = new ApprovalStatus();
		    	
			    d.setDocument_no(rs.getString("document_no"));
		    	d.setDocument_title(rs.getString("document_title"));
		    	emp.setEmployee_id(rs.getString("employee_id"));
		    	emp.setName(rs.getString("name"));
		    	d.setEmployee(emp);
		    	d.setDraft_date(rs.getDate("dt"));
		    	dt.setDocument_type(rs.getString("employee_id"));
		    	d.setDocument_type(dt);
		    	String s=rs.getString("ap_type");
		    	ap.setApStatus_type(rs.getString("ap_type"));
		    	a.setAp_type(ap);
		    	d.setApproval(a);
		    	
		  		list.add(d);
				
			}
		    
		    if(list.size()==0) {
			throw new FindException("목록이 존재하지 않습니다.");
		    }
		   
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}

	}
	
	/*
	 * //(반려)사용자는 미확인 문서를 선택해서 볼 수 있다.
	 * 
	 * @Override public List<Document> selectByCheckAllNo(String employee_id) throws
	 * FindException {
	 * 
	 * Connection con = null; try { con = MyConnection.getConnection();
	 * }catch(SQLException e) { e.printStackTrace(); System.out.println("db연동 실패");
	 * throw new FindException(e.getMessage()); } String
	 * sql="SELECT j.document_no, j.document_title, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type, j.ap_type\r\n"
	 * + "from employee e join (\r\n" + "SELECT * FROM (select a.*\r\n" +
	 * "FROM ((SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" +
	 * "WHERE a.ap_type='대기' AND a.employee_id=?)\r\n" + "UNION ALL\r\n" +
	 * "(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" +
	 * "WHERE r.ap_type='대기' AND r.employee_id=?)\r\n" + "UNION ALL\r\n" +
	 * "(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" +
	 * "WHERE ag.ap_type='대기' AND ag.employee_id=?))a\r\n" +
	 * "JOIN document d ON a.document_no= d.document_no )) j on e.employee_id = j.employee_id\r\n"
	 * + "ORDER BY draft_date ASC";
	 * 
	 * PreparedStatement pstmt=null; ResultSet rs= null; List list = new
	 * ArrayList<>(); try {
	 * 
	 * pstmt = con.prepareStatement(sql); pstmt.setString(1, employee_id);
	 * pstmt.setString(2, employee_id); pstmt.setString(3, employee_id);
	 * rs=pstmt.executeQuery();
	 * 
	 * while(rs.next()) { Document d=new Document(); Employee emp=new Employee();
	 * DocumentType dt= new DocumentType(); Approval a = new Approval();
	 * ApprovalStatus ap = new ApprovalStatus();
	 * 
	 * d.setDocument_no(rs.getString("document_no"));
	 * d.setDocument_title(rs.getString("document_title"));
	 * emp.setEmployee_id(rs.getString("employee_id"));
	 * emp.setName(rs.getString("name")); d.setEmployee(emp);
	 * d.setDraft_date(rs.getDate("dt"));
	 * dt.setDocument_type(rs.getString("employee_id")); d.setDocument_type(dt);
	 * String s=rs.getString("ap_type");
	 * ap.setApStatus_type(rs.getString("ap_type")); a.setAp_type(ap);
	 * d.setApproval(a);
	 * 
	 * list.add(d);
	 * 
	 * }
	 * 
	 * if(list.size()==0) { throw new FindException("목록이 존재하지 않습니다."); }
	 * 
	 * return list; }catch(SQLException e) { e.printStackTrace(); throw new
	 * FindException(e.getMessage()); }finally { MyConnection.close(con, pstmt, rs);
	 * }
	 * 
	 * }
	 * 
	 * //(대기)사용자는 미확인 문서를 선택해서 볼 수 있다.
	 * 
	 * @Override public List<Document> selectByCheckAllNo(String employee_id) throws
	 * FindException {
	 * 
	 * Connection con = null; try { con = MyConnection.getConnection();
	 * }catch(SQLException e) { e.printStackTrace(); System.out.println("db연동 실패");
	 * throw new FindException(e.getMessage()); } String
	 * sql="SELECT j.document_no, j.document_title, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type, j.ap_type\r\n"
	 * + "from employee e join (\r\n" + "SELECT * FROM (select a.*\r\n" +
	 * "FROM ((SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" +
	 * "WHERE a.ap_type='대기' AND a.employee_id=?)\r\n" + "UNION ALL\r\n" +
	 * "(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" +
	 * "WHERE r.ap_type='대기' AND r.employee_id=?)\r\n" + "UNION ALL\r\n" +
	 * "(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" +
	 * "WHERE ag.ap_type='대기' AND ag.employee_id=?))a\r\n" +
	 * "JOIN document d ON a.document_no= d.document_no )) j on e.employee_id = j.employee_id\r\n"
	 * + "ORDER BY draft_date ASC";
	 * 
	 * PreparedStatement pstmt=null; ResultSet rs= null; List list = new
	 * ArrayList<>(); try {
	 * 
	 * pstmt = con.prepareStatement(sql); pstmt.setString(1, employee_id);
	 * pstmt.setString(2, employee_id); pstmt.setString(3, employee_id);
	 * rs=pstmt.executeQuery();
	 * 
	 * while(rs.next()) { Document d=new Document(); Employee emp=new Employee();
	 * DocumentType dt= new DocumentType(); Approval a = new Approval();
	 * ApprovalStatus ap = new ApprovalStatus();
	 * 
	 * d.setDocument_no(rs.getString("document_no"));
	 * d.setDocument_title(rs.getString("document_title"));
	 * emp.setEmployee_id(rs.getString("employee_id"));
	 * emp.setName(rs.getString("name")); d.setEmployee(emp);
	 * d.setDraft_date(rs.getDate("dt"));
	 * dt.setDocument_type(rs.getString("employee_id")); d.setDocument_type(dt);
	 * String s=rs.getString("ap_type");
	 * ap.setApStatus_type(rs.getString("ap_type")); a.setAp_type(ap);
	 * d.setApproval(a);
	 * 
	 * list.add(d);
	 * 
	 * }
	 * 
	 * if(list.size()==0) { throw new FindException("목록이 존재하지 않습니다."); }
	 * 
	 * return list; }catch(SQLException e) { e.printStackTrace(); throw new
	 * FindException(e.getMessage()); }finally { MyConnection.close(con, pstmt, rs);
	 * }
	 * 
	 * }
	 * 
	 * 
	 * //(대기)사용자는 확인or미확인 문서를 선택해서 볼 수 있다.
	 * 
	 * 
	 * //(대기/승인/반려)사용자는 확인or미확인 문서를 선택해서 볼 수 있다.
	 * 
	 * @Override public List<Document> selectByCheckStatus(String employee_id,String
	 * document_status,String check) throws FindException { Connection con = null;
	 * try { con = MyConnection.getConnection(); }catch(SQLException e) {
	 * e.printStackTrace(); System.out.println("db연동 실패"); throw new
	 * FindException(e.getMessage()); } String
	 * sql="SELECT j.document_no, j.document_title, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type, j.ap_type\r\n"
	 * + "from employee e join (\r\n" + "SELECT * FROM (select a.*\r\n" +
	 * "FROM ((SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" +
	 * "WHERE  a.ap_type='대기' AND a.employee_id=?)\r\n" + "UNION ALL\r\n" +
	 * "(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" +
	 * "WHERE r.ap_type='대기' AND r.employee_id=?)\r\n" + "UNION ALL\r\n" +
	 * "(SELECT d.document_no,d.document_title,d.employee_id,draft_date,d.document_type,ap_type\r\n"
	 * + "FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" +
	 * "WHERE ag.ap_type='대기' AND ag.employee_id=?))a\r\n" +
	 * "JOIN document d ON a.document_no= d.document_no  WHERE document_status= 대기)) j on e.employee_id = j.employee_id\r\n"
	 * + "ORDER BY draft_date ASC";
	 * 
	 * 
	 * PreparedStatement pstmt=null; ResultSet rs= null; List list = new
	 * ArrayList<>();
	 * 
	 * try { pstmt = con.prepareStatement(sql); pstmt.setString(1, employee_id);
	 * pstmt.setString(2, employee_id); pstmt.setString(3, employee_id);
	 * rs=pstmt.executeQuery();
	 * 
	 * while(rs.next()) { Document d=new Document(); Employee emp=new Employee();
	 * DocumentType dt= new DocumentType(); Approval a = new Approval();
	 * ApprovalStatus ap = new ApprovalStatus();
	 * d.setDocument_no(rs.getString("document_no"));
	 * d.setDocument_title(rs.getString("document_title"));
	 * emp.setEmployee_id(rs.getString("employee_id"));
	 * emp.setName(rs.getString("name")); d.setEmployee(emp);
	 * d.setDraft_date(rs.getDate("dt"));
	 * dt.setDocument_type(rs.getString("employee_id")); d.setDocument_type(dt);
	 * String s=rs.getString("ap_type");
	 * ap.setApStatus_type(rs.getString("ap_type")); a.setAp_type(ap);
	 * d.setApproval(a);
	 * 
	 * list.add(d);
	 * 
	 * }
	 * 
	 * if(list.size()==0) { throw new FindException("목록이 존재하지 않습니다."); }
	 * 
	 * return list; }catch(SQLException e) { e.printStackTrace(); throw new
	 * FindException(e.getMessage()); }finally { MyConnection.close(con, pstmt, rs);
	 * } }
	 */





	//사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
	@Override
	public List<String> selectByMyClick(String employee_id, String document_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		//? : 사번,문서번호 x3 = 6
		String sql= 
				"SELECT e.employee_id,a,j.ap_step\r\n" + 
				"from employee e join ( \r\n" + 
				"SELECT * FROM (select a.*\r\n" + 
				"FROM ((SELECT a.employee_id,a.document_no,'결재' a,a.ap_step\r\n" + 
				"FROM approval a JOIN document d ON a.document_no=d.document_no\r\n" + 
				"WHERE a.employee_id=? AND a.document_no=?)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT r.employee_id,r.document_no,'참조' a,-1\r\n" + 
				"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
				"WHERE r.employee_id=? AND r.document_no=?)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT ag.employee_id,ag.document_no,'합의' a,-1\r\n" + 
				"FROM agreement ag JOIN document d ON ag.document_no=d.document_no\r\n" + 
				"WHERE ag.employee_id=? AND ag.document_no=?))a\r\n" + 
				"JOIN document d ON a.document_no= d.document_no )) j on e.employee_id = j.employee_id";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List<String> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, document_no);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, document_no);
			pstmt.setString(5, employee_id);
			pstmt.setString(6, document_no);
			rs=pstmt.executeQuery();
			
		    if(rs.next()) {		  
		    	list.add(0,rs.getString("employee_id"));
				list.add(1,rs.getString("a"));
				list.add(2,rs.getString("ap_step"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
		 return list;
	}

	//사용자는 결재 문서를 선택했을 때, 해당 문서의 상세 내용정보를 확인할 수 있다. (내용+결재선)
	@Override
	public List<Document> selectByDocsDetail(String document_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		//?=사원번호
		String sql="SELECT d.document_title, d.document_type, d.document_no, e.department_id, e.name,  to_char(d.draft_date,'yyyy-mm-dd') dt, d.document_content,\r\n" + 
				"(SELECT name FROM employee\r\n" + 
				"WHERE employee_id=(SELECT employee_id FROM approval WHERE document_no=? AND ap_step=0)\r\n" + 
				") ap0,\r\n" + 
				"(SELECT to_char(ap_date,'yyyy-mm-dd') FROM approval WHERE document_no=? AND ap_step=0)\r\n" + 
				" apDt0,\r\n" + 
				" (SELECT ap_type FROM approval WHERE document_no='?' AND ap_step=0)\r\n" + 
				" apOk0 ,\r\n" + 
				"(SELECT name FROM employee\r\n" + 
				"WHERE employee_id=(SELECT employee_id FROM approval WHERE document_no=? AND ap_step=1)\r\n" + 
				") ap1,\r\n" + 
				"(SELECT  to_char(ap_date,'yyyy-mm-dd') FROM approval WHERE document_no=? AND ap_step=1)\r\n" + 
				" apDt1 ,\r\n" + 
				" (SELECT ap_type FROM approval WHERE document_no=? AND ap_step=1)\r\n" + 
				" apOk1 ,\r\n" + 
				"(SELECT name FROM employee\r\n" + 
				"WHERE employee_id=(SELECT employee_id FROM approval WHERE document_no=? AND ap_step=2)\r\n" + 
				") ap2,\r\n" + 
				"(SELECT  to_char(ap_date,'yyyy-mm-dd') FROM approval WHERE document_no=? AND ap_step=2)\r\n" + 
				" apDt2 ,\r\n" + 
				"  (SELECT ap_type FROM approval WHERE document_no=? AND ap_step=2)\r\n" + 
				" apOk2 ,\r\n" + 
				"(SELECT name FROM employee\r\n" + 
				"WHERE employee_id=(SELECT employee_id FROM approval WHERE document_no=? AND ap_step=3)\r\n" + 
				") ap3,\r\n" + 
				"(SELECT  to_char(ap_date,'yyyy-mm-dd') FROM approval WHERE document_no=? AND ap_step=3)\r\n" + 
				" apDt3 ,\r\n" + 
				"  (SELECT ap_type FROM approval WHERE document_no=? AND ap_step=3)\r\n" + 
				" apOk3 ,\r\n" + 
				"(SELECT name FROM employee\r\n" + 
				"WHERE employee_id=(SELECT employee_id FROM agreement WHERE document_no=?)) ag\r\n" + 
				", \r\n" + 
				"(SELECT ap_type FROM agreement WHERE document_no=?)\r\n" + 
				" agOk ,\r\n" + 
				"(SELECT name FROM employee\r\n" + 
				"WHERE employee_id=(SELECT employee_id FROM reference WHERE document_no=?)) re,\r\n" + 
				"(SELECT ap_type FROM reference WHERE document_no=?) reOk\r\n" + 
				" FROM document d \r\n" + 
				"JOIN employee e ON (d.employee_id = e.employee_id)\r\n" + 
				"WHERE document_no=?";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List<Document> list =new ArrayList<>();
		try {
			int cnt=0;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, document_no);
			pstmt.setString(2, document_no);
			pstmt.setString(3, document_no);
			pstmt.setString(4, document_no);
			pstmt.setString(5, document_no);
			pstmt.setString(6, document_no);
			pstmt.setString(7, document_no);
			pstmt.setString(8, document_no);
			pstmt.setString(9, document_no);
			pstmt.setString(10, document_no);
			pstmt.setString(11, document_no);
			pstmt.setString(12, document_no);
			pstmt.setString(13, document_no);
			pstmt.setString(14, document_no);
			pstmt.setString(15, document_no);
			pstmt.setString(16, document_no);
//			pstmt.setString(17, document_no);
//			pstmt.setString(18, document_no);
//		
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
			
				Document d=new Document();
		    	Employee emp=new Employee();
		       	Employee emp0=new Employee();
		    	Employee emp1=new Employee();
		    	Employee emp2=new Employee();
		    	Employee emp3=new Employee();
		    	Employee emp4=new Employee();
		    	Employee emp5=new Employee();
		    	DocumentType dt= new DocumentType();
		    	Approval a0 = new Approval();
		     	Approval a1 = new Approval();
		     	Approval a2 = new Approval();
		     	Approval a3 = new Approval();
		    	Reference r = new Reference();
		    	Agreement ag = new Agreement();
		    	ApprovalStatus ap0 = new ApprovalStatus();
		    	ApprovalStatus ap1 = new ApprovalStatus();
		    	ApprovalStatus ap2 = new ApprovalStatus();
		    	ApprovalStatus ap3 = new ApprovalStatus();
		    	ApprovalStatus ap4 = new ApprovalStatus();
		    	ApprovalStatus ap5 = new ApprovalStatus();
		    	Department dep =new Department();
		    
		    	//문서내용 받아오기
		      	d.setDocument_title(rs.getString("document_title"));//document_title
		    	dt.setDocument_type(rs.getString("document_type"));
		    	d.setDocument_type(dt);//document_type
		    	d.setDocument_no(rs.getString("document_no"));//document_no
		    	//emp.setEmployee_id(rs.getString("employee_id"));
		    	String depp = rs.getString("department_id");
		    	String depReal="";
		    	if(dep.equals("CEO")) {
		    		depReal="대표이사";
		    	}else if(depp.equals("MSD")) {
		    		depReal="경영지원실";
		    	}else if(depp.equals("DEV")) {
		    		depReal="기획개발실";
		    	}else if(depp.equals("SVC")) {
		    		depReal="서비스운영실";
		    	}else if(depp.equals("IFR")) {
		    		depReal="인프라운영실";
		    	}else if(depp.equals("SAL")) {
		    		depReal="영업부";
		    	}else if(depp.equals("BIN")) {
		    		depReal="사업부";
		    	}else if(depp.equals("SEC")) {
		    		depReal="정보보안실";
		    	}
		    	
		    	
		    	dep.setDepartment_title(depReal);
		    	emp.setDepartment(dep);
		    	emp.setName(rs.getString("name"));
		    	d.setEmployee(emp);//name
		    	d.setDraft_date(rs.getDate("dt"));//draft_date
		    	d.setDocument_content(rs.getString("document_content"));//document_content
		    	
		    	//결재자
		    	List<Approval> approvals = new ArrayList<>();
		    	emp0.setName(rs.getString("ap0"));
		    	a0.setEmployee_id(emp0);
		    	a0.setAp_ap_date(rs.getDate("apDt0"));
		    	ap0.setApStatus_type(rs.getString("apOk0"));
		    	a0.setAp_type(ap0);
		    	approvals.add(0,a0);
		    	
		    	emp1.setName(rs.getString("ap1"));
		    	a1.setEmployee_id(emp1);
		    	a1.setAp_ap_date(rs.getDate("apDt1"));
		     	ap1.setApStatus_type(rs.getString("apOk1"));
		    	a1.setAp_type(ap1);
		    	approvals.add(1, a1);
		    	
		    	emp2.setName(rs.getString("ap2"));
		    	a2.setEmployee_id(emp2);
		    	a2.setAp_ap_date(rs.getDate("apDt2"));
		    	ap2.setApStatus_type(rs.getString("apOk2"));
		    	a2.setAp_type(ap2);
		    	approvals.add(2, a2);
		    	
		    	emp3.setName(rs.getString("ap3"));
		    	a3.setEmployee_id(emp3);
		    	a3.setAp_ap_date(rs.getDate("apDt3"));
		    	approvals.add(3, a3);
		    	ap3.setApStatus_type(rs.getString("apOk3"));
		    	a3.setAp_type(ap3);
		    	d.setApprovals(approvals);
		    	//합의자
		    	emp4.setName(rs.getString("ag"));
		    	ag.setEmployee_id(emp4);
		      	//ag.setAg_ap_date(rs.getDate("agDt"));
		    	ap4.setApStatus_type(rs.getString("agOk"));
		    	ag.setAg_ap_type(ap4);
		      	d.setAgreement(ag);
		    	//참조자
		       	emp5.setName(rs.getString("re"));
		    	r.setEmployee_id(emp5);
		    	ap5.setApStatus_type(rs.getString("reOk"));
		    	r.setRe_ap_type(ap5);
		      	d.setReference(r);
		    	
		  		list.add(d);
			}
			return list;
			//throw new FindException("목록이 존재하지 않습니다.");				
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	//문서에 대해 제목으로 검색할 수 있다. 
	@Override
	public List<Document> selectBySearchTitle(String employee_id,String title) throws FindException,SearchException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		int check=0;

		String sql="";
		sql+="SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n" + 
				"from employee e join ( \r\n" + 
				"SELECT * FROM (select a.*\r\n" + 
				"FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n" + 
				"FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + 
				"WHERE a.employee_id=?)\r\n" + 
				"UNION \r\n" + 
				"(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n" + 
				"FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n" + 
				"WHERE ag.employee_id=?)\r\n" + 
				"UNION \r\n" + 
				"(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n" + 
				"FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + 
				"WHERE r.employee_id=?)\r\n" + 
				"UNION\r\n" + 
				"(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n" + 
				"FROM document d  \r\n" + 
				"WHERE employee_id=?))a\r\n" + 
				"JOIN document d ON a.document_no= d.document_no)) j ON e.employee_id = j.employee_id where document_title like ? ORDER BY draft_date ASC";
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			int cnt=0;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
			pstmt.setString(5, "%"+title+"%");
	
			rs=pstmt.executeQuery();
			while(rs.next()) {
		    	Document d=new Document();
		    	Employee emp=new Employee();
		    	DocumentType dt= new DocumentType();
		    	Approval a = new Approval();
		    	ApprovalStatus ap = new ApprovalStatus();
		    	
		    	d.setState(rs.getString("state"));
			    d.setDocument_no(rs.getString("document_no"));
		    	d.setDocument_title(rs.getString("document_title"));
		    	emp.setEmployee_id(rs.getString("employee_id"));
		    	emp.setName(rs.getString("name"));
		    	d.setEmployee(emp);
		    	d.setDraft_date(rs.getDate("dt"));
		    	dt.setDocument_type(rs.getString("employee_id"));
		    	d.setDocument_type(dt);
		    	String s=rs.getString("ap_type");
		    	ap.setApStatus_type(rs.getString("ap_type"));
		    	a.setAp_type(ap);
		    	d.setApproval(a);
	
		  		list.add(d);
		  		
			}
		    
		    if(list.size()==0) {
			throw new FindException("검색 목록이 존재하지 않습니다.");
		    }
		   
			return list;			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	//문서에 대해 내용으로 검색할 수 있다. 
	@Override
	public List<Document> selectBySearchContent(String employee_id,String content) throws FindException,SearchException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		int check=0;

		String sql="";
		sql+="SELECT state,j.document_no, j.document_title,j.document_content, j.employee_id, e.name,to_char(j.draft_date, 'yyyy-mm-dd') dt, j.document_type,ap_type\r\n" + 
				"from employee e join ( \r\n" + 
				"SELECT * FROM (select a.*\r\n" + 
				"FROM ((SELECT '결재서류' state, d.document_title,d.document_content, d.document_no, draft_date, d.employee_id,d.document_type,a.ap_type\r\n" + 
				"FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + 
				"WHERE a.employee_id=?)\r\n" + 
				"UNION \r\n" + 
				"(SELECT '결재서류', d.document_title,d.document_content, d.document_no, draft_date, d.employee_id, d.document_type,ag.ap_type\r\n" + 
				"FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n" + 
				"WHERE ag.employee_id=?)\r\n" + 
				"UNION \r\n" + 
				"(SELECT '결재서류',d.document_title,d.document_content,d.document_no, draft_date, d.employee_id,d.document_type,r.ap_type\r\n" + 
				"FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + 
				"WHERE r.employee_id=?)\r\n" + 
				"UNION\r\n" + 
				"(SELECT '기안서류', document_title,d.document_content,document_no, draft_date, employee_id,document_type,'확인'\r\n" + 
				"FROM document d  \r\n" + 
				"WHERE employee_id=?))a\r\n" + 
				"JOIN document d ON a.document_no= d.document_no)) j ON e.employee_id = j.employee_id where document_content like ? ORDER BY draft_date ASC";
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			int cnt=0;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
			pstmt.setString(5, "%"+content+"%");	
		
			rs=pstmt.executeQuery();
			while(rs.next()) {
		    	Document d=new Document();
		    	Employee emp=new Employee();
		    	DocumentType dt= new DocumentType();
		    	Approval a = new Approval();
		    	ApprovalStatus ap = new ApprovalStatus();
		    	
		    	d.setState(rs.getString("state"));
			    d.setDocument_no(rs.getString("document_no"));
		    	d.setDocument_title(rs.getString("document_title"));
		    	emp.setEmployee_id(rs.getString("employee_id"));
		    	emp.setName(rs.getString("name"));
		    	d.setEmployee(emp);
		    	d.setDraft_date(rs.getDate("dt"));
		    	dt.setDocument_type(rs.getString("employee_id"));
		    	d.setDocument_type(dt);
		    	String s=rs.getString("ap_type");
		    	ap.setApStatus_type(rs.getString("ap_type"));
		    	a.setAp_type(ap);
		    	d.setApproval(a);
	
		  		list.add(d);
		  		
			}
		    
		    if(list.size()==0) {
			throw new FindException("검색 목록이 존재하지 않습니다.");
		    }
		   
			return list;			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	@Override
	public List<Approval> selectByComments(String document_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}

		String sql="";
		sql+="select a.* from(\r\n" + 
				"(select employee_id,ap_date,ap_comment from approval where document_no=? and ap_comment is not null) \r\n" + 
				"union all \r\n" + 
				"(select employee_id,ap_date,ap_comment from agreement where document_no=? and ap_comment is not null))a ORDER BY ap_date ASC";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			int cnt=0;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, document_no);
			pstmt.setString(2, document_no);
		
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Employee e = new Employee();
		    	Approval a = new Approval();
		    	e.setEmployee_id(rs.getString("employee_id"));
		    	a.setEmployee_id(e);
		    	a.setAp_ap_date(rs.getDate("ap_date"));
		    	a.setAp_ap_comment(rs.getString("ap_comment"));
	
		  		list.add(a);
		  		
			}
		    
		    if(list.size()==0) {
			throw new FindException("검색 목록이 존재하지 않습니다.");
		    }
		   
			return list;			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	public static void main(String[] args) {
		ConfirmDocsDAOOracle dao; 

		try {
			dao = new ConfirmDocsDAOOracle();
			
			String id="DEV001";
			String documentState="반려";
			String check="확인";
		
			// (전체)사용자는 확인or미확인 문서를 선택해서 볼 수 있다. 
//			List<Document> selectCheckList = new ArrayList<>();
//			System.out.println(id+"사원이 받은 문서들의 "+check+"값의 전체 목록");
//			selectCheckList=dao.selectByCheckAll(id,check);
//			for(Document d: selectCheckList) {	
//				System.out.println(d.getDocument_no()+" "+
//						d.getDocument_title()+" "+
//						d.getEmployee().getEmployee_id()+" "+
//						d.getEmployee().getName()+" "+
//						d.getDraft_date()+" "+
//						d.getDocument_type().getDocument_type()+" "+
//						d.getApproval().getAp_type().getApStatus_type());
//			}
//			System.out.println();
//			
//			// (진행/승인/반려)사용자는 확인or미확인 문서를 선택해서 볼 수 있다. 
//			List<Document> selectCheckList1 = new ArrayList<>();
//			System.out.println(id+"사원이 받은 문서들의 "+check+"값의 "+documentState+" 목록");
//			selectCheckList1=dao.selectByCheckStatus(id,documentState,check);
//			for(Document d: selectCheckList1) {	
//				System.out.println(d.getDocument_no()+" "+
//						d.getDocument_title()+" "+
//						d.getEmployee().getEmployee_id()+" "+
//						d.getEmployee().getName()+" "+
//						d.getDraft_date()+" "+
//						d.getDocument_type().getDocument_type()+" "+
//						d.getApproval().getAp_type().getApStatus_type());
//			}
//			System.out.println();
//			
			//사용자는 문서를 선택하면,해당 문서에서 자신이 승인해야하는 부분을 확인할 수 있다.
			List<String> list =dao.selectByMyClick(id,"CR-회람-20210621-0001");
			System.out.println("자신의 승인 부분 : "+list.get(0).toString()+" "+list.get(1).toString()+" "+list.get(2).toString());
			
			System.out.println();
			//문서 상세내용 확인
			String docsNum="LE-휴가-20210624-0001";
			List<Document> detailList = new ArrayList<>();
			System.out.println(docsNum+" 상세 내용");
			detailList=dao.selectByDocsDetail(docsNum);
			for(Document d: detailList) {	
				System.out.println("문서내용 ="+
						d.getDocument_title()+" "+
						d.getDocument_type().getDocument_type()+" "+
						d.getDocument_no()+" "+
						d.getEmployee().getDepartment().getDepartment_title()+" "+
						d.getEmployee().getName()+" "+
						d.getEmployee().getEmployee_id()+" "+
						d.getDraft_date()+" "+
						d.getDocument_content()+
						"\n결재자0="+
						d.getApprovals().get(0).getEmployee_id().getName()+
						d.getApprovals().get(0).getAp_ap_date()+
				        d.getApprovals().get(0).getAp_type().getApStatus_type()+
						"\n결재자1="+
						d.getApprovals().get(1).getEmployee_id().getName()+
						d.getApprovals().get(1).getAp_ap_date()+
						d.getApprovals().get(1).getAp_type().getApStatus_type()+
						"\n결재자2="+
						d.getApprovals().get(2).getEmployee_id().getName()+
						d.getApprovals().get(2).getAp_ap_date()+
						d.getApprovals().get(2).getAp_type().getApStatus_type()+
						"\n결재자3="+
						d.getApprovals().get(3).getEmployee_id().getName()+
						d.getApprovals().get(3).getAp_ap_date()+
						d.getApprovals().get(3).getAp_type().getApStatus_type()+
						"\n합의자 ="+
						d.getAgreement().getEmployee_id().getName()+" "+
						d.getAgreement().getAg_ap_type().getApStatus_type()+
						"\n참조자="+
						d.getReference().getEmployee_id().getName()+
						d.getReference().getRe_ap_type().getApStatus_type()
						);
			}
			System.out.println();
			
			//문서에 대해 제목,내용으로 검색할 수 있다.
//			String title="";
//			String content="연락";
//			List<Document> searchList = new ArrayList<>();
//			System.out.println("제목 검색값 :"+title);
//			System.out.println("내용 검색값 :"+content);
//			searchList=dao.selectBySearch("MSD003",title,content);
//			for(Document d: searchList) {
//				System.out.println(
//						d.getState()+" "+
//						d.getDocument_no()+" "+
//						d.getDocument_title()+" "+
//						d.getEmployee().getEmployee_id()+" "+
//						d.getEmployee().getName()+" "+
//						d.getDraft_date()+" "+
//						d.getDocument_type().getDocument_type()+" "+
//						d.getApproval().getAp_type().getApStatus_type());
//			}
	} catch (Exception e) {
			e.printStackTrace();
	}

	}


	}



