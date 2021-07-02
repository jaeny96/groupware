package com.group.approval.dao;

import java.util.List;

import com.group.approval.dto.Document;
import com.group.approval.exception.AddException;
import com.group.approval.exception.FindException;

public class DocsWriteDAOOracle implements DocsWriteDAO {	
	public DocsWriteDAOOracle() throws Exception {
	// JDBC드라이버로드
	Class.forName("oracle.jdbc.driver.OracleDriver");

}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void draft(Document d) throws AddException {
		// TODO Auto-generated method stub
		
	}

	public void selectDocsType(String document_type) throws FindException {
		// TODO Auto-generated method stub
		
	}

	public List<String> searchByName(String name) throws FindException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> searchByDep(String department_title) throws FindException {
		// TODO Auto-generated method stub
		return null;
	}

}
