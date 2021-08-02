package com.group.approval.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.group.approval.dto.Approval;
import com.group.approval.dto.ApprovalStatus;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.exception.FindException;
import com.group.approval.exception.ModifyException;
import com.group.employee.dto.Employee;
import com.group.sql.MyConnection;

public class SideDocsDAOOracle implements SideDocsDAO{
	public SideDocsDAOOracle() throws Exception{
		try {
		//JDBC드라이버로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공 ");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//(전체)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	@Override
	public int selectByCountAll(String employee_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		//?=사원번호
		String sql="SELECT count(*) AS total FROM (select a.* "+
				"FROM ((SELECT a.document_no FROM approval a WHERE a.employee_id=? and a.ap_step!=0) "+
						"UNION ALL "+
						"(SELECT ag.document_no FROM agreement ag WHERE ag.employee_id=?) "+
						"UNION ALL "+
						"(SELECT re.document_no FROM reference re WHERE re.employee_id=?) "+
						"UNION ALL "+
						"(SELECT dd.document_no FROM document dd WHERE dd.employee_id=?))a "+
						"JOIN document d ON a.document_no= d.document_no)";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		try {
			int cnt=0;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				cnt=rs.getInt("total");
				return cnt;
			}
			throw new FindException("목록이 존재하지 않습니다.");				
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
		
	}

	//(진행)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.	
	@Override
	public int selectByCountWait(String employee_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		//?=사원번호
		int cnt=0;
		String sql="SELECT count(*) AS total FROM (select a.* "+
				"FROM ((SELECT a.document_no FROM approval a WHERE a.employee_id=? and a.ap_step!=0) "+
						"UNION ALL "+
						"(SELECT ag.document_no FROM agreement ag WHERE ag.employee_id=?) "+
						"UNION ALL "+
						"(SELECT re.document_no FROM reference re WHERE re.employee_id=?) "+
						"UNION ALL "+
						"(SELECT dd.document_no FROM document dd WHERE dd.employee_id=?))a "+
						"JOIN document d ON a.document_no= d.document_no WHERE d.document_status='대기')";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		try {
		
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
			rs=pstmt.executeQuery();
		    if(rs.next()) {
				cnt=rs.getInt("total");
				return cnt;	
			}
			throw new FindException("목록이 존재하지 않습니다.");
	
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	//(승인)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.	
	@Override
	public int selectByCountOk(String employee_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		//?=사원번호
		int cnt=0;
		String sql="SELECT count(*) AS total FROM (select a.* "+
				"FROM ((SELECT a.document_no FROM approval a WHERE a.employee_id=? and a.ap_step!=0) "+
						"UNION ALL "+
						"(SELECT ag.document_no FROM agreement ag WHERE ag.employee_id=?) "+
						"UNION ALL "+
						"(SELECT re.document_no FROM reference re WHERE re.employee_id=?) "+
						"UNION ALL "+
						"(SELECT dd.document_no FROM document dd WHERE dd.employee_id=?))a "+
						"JOIN document d ON a.document_no= d.document_no WHERE d.document_status='승인')";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		try {
		
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
			rs=pstmt.executeQuery();
		    if(rs.next()) {
				cnt=rs.getInt("total");
				return cnt;	
			}
			throw new FindException("목록이 존재하지 않습니다.");
	
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	//(반려)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.
	@Override
	public int selectByCountNo(String employee_id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("db연동 실패");
			throw new FindException(e.getMessage());
		}
		//?=사원번호
		int cnt=0;
		String sql="SELECT count(*) AS total FROM (select a.* "+
				"FROM ((SELECT a.document_no FROM approval a WHERE a.employee_id=? and a.ap_step!=0) "+
						"UNION ALL "+
						"(SELECT ag.document_no FROM agreement ag WHERE ag.employee_id=?) "+
						"UNION ALL "+
						"(SELECT re.document_no FROM reference re WHERE re.employee_id=?) "+
						"UNION ALL "+
						"(SELECT dd.document_no FROM document dd WHERE dd.employee_id=?))a "+
						"JOIN document d ON a.document_no= d.document_no WHERE d.document_status='반려')";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		try {
		
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
			rs=pstmt.executeQuery();
		    if(rs.next()) {
				cnt=rs.getInt("total");
				return cnt;	
			}
			throw new FindException("목록이 존재하지 않습니다.");
	
		}catch(SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	//(전체)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	@Override
	public List<Document> selectByListAll(String employee_id) throws FindException {

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
				"FROM ((SELECT d.document_title, d.document_no, draft_date, d.employee_id,d.document_type, ap_type \r\n" + 
				"FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + 
				"WHERE a.employee_id=? and a.ap_step!=0)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_title, d.document_no, draft_date, d.employee_id, d.document_type,ap_type \r\n" + 
				"FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n" + 
				"WHERE ag.employee_id=?)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_title,d.document_no, draft_date, d.employee_id,d.document_type,ap_type \r\n" + 
				"FROM reference r JOIN document d ON r.document_no=d.document_no \r\n" + 
				"WHERE r.employee_id=?)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT document_title,document_no, draft_date, employee_id,document_type,'확인'\r\n" + 
				"FROM document d\r\n" + 
				"WHERE employee_id=?))a\r\n" + 
				"JOIN document d ON a.document_no= d.document_no)) j ON e.employee_id = j.employee_id \r\n" + 
				"ORDER BY draft_date ASC";
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
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
		    	dt.setDocument_type(rs.getString("document_type"));
		    	d.setDocument_status(dt);
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

	//승인
	@Override
	public List<Document> selectByListOk(String employee_id) throws FindException {
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
				"SELECT *FROM (select a.*\r\n" + 
				"FROM ((SELECT d.document_title, d.document_no, draft_date, d.employee_id,d.document_type, ap_type \r\n" + 
				"FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + 
				"WHERE a.employee_id=? and a.ap_step!=0)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_title, d.document_no, draft_date, d.employee_id, d.document_type,ap_type\r\n" + 
				"FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n" + 
				"WHERE ag.employee_id=?)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT d.document_title,d.document_no, draft_date, d.employee_id,d.document_type,ap_type \r\n" + 
				"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
				"WHERE r.employee_id=?)\r\n" + 
				"UNION ALL\r\n" + 
				"(SELECT document_title,document_no, draft_date, employee_id,document_type,'확인'\r\n" + 
				"FROM document d   \r\n" + 
				"WHERE employee_id=?))a \r\n" + 
				"JOIN document d ON a.document_no= d.document_no WHERE document_status='승인')) j ON e.employee_id = j.employee_id\r\n" + 
				"ORDER BY draft_date ASC";
		
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List list = new ArrayList<>();
		try {
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee_id);
			pstmt.setString(2, employee_id);
			pstmt.setString(3, employee_id);
			pstmt.setString(4, employee_id);
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
		    	dt.setDocument_type(rs.getString("document_type"));
		    	d.setDocument_status(dt);
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

	//진행
		@Override
		public List<Document> selectByListWait(String employee_id) throws FindException {
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
					"SELECT *FROM (select a.*\r\n" + 
					"FROM ((SELECT d.document_title, d.document_no, draft_date, d.employee_id,d.document_type, ap_type \r\n" + 
					"FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + 
					"WHERE a.employee_id=? and a.ap_step!=0)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_title, d.document_no, draft_date, d.employee_id, d.document_type,ap_type\r\n" + 
					"FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n" + 
					"WHERE ag.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_title,d.document_no, draft_date, d.employee_id,d.document_type,null \r\n" + 
					"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
					"WHERE r.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT document_title,document_no, draft_date, employee_id,document_type,'확인'\r\n" + 
					"FROM document d   \r\n" + 
					"WHERE employee_id=?))a \r\n" + 
					"JOIN document d ON a.document_no= d.document_no WHERE document_status='대기')) j ON e.employee_id = j.employee_id\r\n" + 
					"ORDER BY draft_date ASC";
			
			PreparedStatement pstmt=null;
			ResultSet rs= null;
			List list = new ArrayList<>();
			try {
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, employee_id);
				pstmt.setString(2, employee_id);
				pstmt.setString(3, employee_id);
				pstmt.setString(4, employee_id);
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
			    	dt.setDocument_type(rs.getString("document_type"));
			    	d.setDocument_status(dt);
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
		
		
		//반려
		@Override
		public List<Document> selectByListNo(String employee_id) throws FindException {
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
					"SELECT *FROM (select a.*\r\n" + 
					"FROM ((SELECT d.document_title, d.document_no, draft_date, d.employee_id,d.document_type, ap_type \r\n" + 
					"FROM approval a JOIN document d ON a.document_no=d.document_no \r\n" + 
					"WHERE a.employee_id=? and a.ap_step!=0)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_title, d.document_no, draft_date, d.employee_id, d.document_type,ap_type\r\n" + 
					"FROM agreement ag JOIN document d ON ag.document_no=d.document_no \r\n" + 
					"WHERE ag.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT d.document_title,d.document_no, draft_date, d.employee_id,d.document_type,null \r\n" + 
					"FROM reference r JOIN document d ON r.document_no=d.document_no\r\n" + 
					"WHERE r.employee_id=?)\r\n" + 
					"UNION ALL\r\n" + 
					"(SELECT document_title,document_no, draft_date, employee_id,document_type,'확인'\r\n" + 
					"FROM document d   \r\n" + 
					"WHERE employee_id=?))a \r\n" + 
					"JOIN document d ON a.document_no= d.document_no WHERE document_status='반려')) j ON e.employee_id = j.employee_id\r\n" + 
					"ORDER BY draft_date ASC";
			
			PreparedStatement pstmt=null;
			ResultSet rs= null;
			List list = new ArrayList<>();
			try {
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, employee_id);
				pstmt.setString(2, employee_id);
				pstmt.setString(3, employee_id);
				pstmt.setString(4, employee_id);
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
			    	dt.setDocument_type(rs.getString("document_type"));
			    	d.setDocument_status(dt);
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
	
	public static void main(String[] args) {
		int result=0;	
		SideDocsDAOOracle dao; 
		try {
			dao = new SideDocsDAOOracle();
			
			//프로시저 
//			Document d = new Document();
//			Employee em = new Employee();
//			
//			em.setEmployee_id("IFR001");
//			d.setEmployee(em);
//			d.setDocument_no("CR-회람-20210621-0001");
//			dao.documentAudmit(d);
//			
			String str="DEV001";
			//(전체)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.	
			result = dao.selectByCountAll(str);
			System.out.println(str+"의 전체 목록개수 : "+result);
			//(진행)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.	
			result = dao.selectByCountWait(str);
			System.out.println(str+"의 진행 목록개수 : "+result);
			//(승인)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.	
			result = dao.selectByCountOk(str);
			System.out.println(str+"의 승인 목록개수 : "+result);
			//(반려)좌측 사이드 바를 통해 목록의 각각의 개수를 확인할 수 있다.	
			result = dao.selectByCountNo(str);
			System.out.println(str+"의 반려 목록개수 : "+result);
			System.out.println();
			//(전체)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
			List<Document> lists0 = new ArrayList<>();
			System.out.println(str+"사원의 최종문서  전체값의 목록");
			lists0=dao.selectByListAll(str);
			for(Document d: lists0) {
				System.out.println(d.getDocument_no()+" "+
						d.getDocument_title()+" "+
						d.getEmployee().getEmployee_id()+" "+
						d.getEmployee().getName()+" "+
						d.getDraft_date()+" "+
						d.getDocument_status().getDocument_type()+" "+
						d.getApproval().getAp_type().getApStatus_type());
			}
			System.out.println();
			//(진행/승인/반려)자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
		
	}
}
