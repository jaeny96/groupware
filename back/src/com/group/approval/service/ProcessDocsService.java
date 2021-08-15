package com.group.approval.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.group.approval.dao.ConfirmDocsDAO;
import com.group.approval.dao.ProcessDocsDAO;
import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Reference;
import com.group.approval.exception.UpdateException;
import com.group.exception.ModifyException;


public class ProcessDocsService {

	private ProcessDocsDAO dao;
	private static ProcessDocsService service;
	public static String envProp;
	
	private ProcessDocsService(){
		Properties env =new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("ProcessDocsDAO");
			System.out.println(className);
			Class c = Class.forName(className);
			dao = (ProcessDocsDAO)c.newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static ProcessDocsService getInstance() {
		if(service == null) {
			service = new ProcessDocsService();
		}
		return service;
	}
	
	/**
	 * 승인, 반려를 선택하고 원하면 코멘트도 추가(Approval-결재용)
	 * @param ap
	 * @throws UpdateException
	 */
	public void decisionAp(Approval ap) throws UpdateException{
		//dao.updateApproval(ap);
	}
	/**
	 * 승인, 반려를 선택하고 원하면 코멘트도 추가(Agreement-합의용)
	 * @param ag
	 * @throws UpdateException
	 */
	public void decisionAg(Agreement ag) throws UpdateException{
		//dao.updateAgreement(ag);
	}
	/**
	 * 참조자의 참조 승인
	 * @param R
	 * @throws UpdateException
	 */
	public void decisionRe(Reference R) throws UpdateException{
		dao.updateReference(R);
	}
	/**
	 * 결재자 전원이 승인 처리 시, 문서 상태 승인으로
	 * @param document_no, id
	 * @throws ModifyException
	 */
	public void FinalAudmit(String document_no,String id) throws ModifyException{
		//dao.documentAudmit(document_no, id);
	}
	/**
	 * 결재자 중 한명이라도 반려 처리 시, 문서 상태 반려로
	 * @param document_no, id
	 * @throws ModifyException
	 */
	public void FinalRefuse(String document_no,String id) throws ModifyException{
		//dao.documentRefuse(document_no, id);
	}
}
