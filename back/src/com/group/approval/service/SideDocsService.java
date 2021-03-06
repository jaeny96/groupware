package com.group.approval.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import com.group.approval.dao.SideDocsDAO;
import com.group.approval.dto.Document;
import com.group.approval.exception.FindException;

public class SideDocsService {

	private SideDocsDAO dao;
	private static SideDocsService service;
	//private static String envProp="classes.prop";
	public static String envProp;
	
	private SideDocsService(){
		Properties env =new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("SideDocsDAO");
			System.out.println(className);
			Class c = Class.forName(className);
			dao = (SideDocsDAO)c.newInstance();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static SideDocsService getInstance() {
		if(service == null) {
			service = new SideDocsService();
		}
		return service;
	}
	
	/**
	 * (전체)좌측 사이드 바를 통해 모든 목록의 각각의 개수를 확인할 수 있다.
	 * @param id 로그인한 사용자 id
	 * @return 기안올린+결재 해야하는 총합 개수
	 * @throws FindException
	 * 
	 */
	public int findCntAll(String id) throws FindException{
		return dao.selectByCountAll(id);
	}
	/**
	 * (진행)좌측 사이드 바를 통해 대기 목록의 각각의 개수를 확인할 수 있다.
	 * @param id 로그인한 사용자 id
	 * @return 기안올린+결재 해야하는대기인 개수
	 * @throws FindException
	 * 
	 */
	public int findCntWait(String id) throws FindException{
		return dao.selectByCountWait(id);
	}
	/**
	 * (승인)좌측 사이드 바를 통해 승인 목록의 각각의 개수를 확인할 수 있다.
	 * @param id 로그인한 사용자 id
	 * @return 기안올린+결재 해야하는 승인된 개수
	 * @throws FindException
	 * 
	 */
	public int findCntOk(String id) throws FindException{
		return dao.selectByCountOk(id);
	}
	/**
	 * (반려)좌측 사이드 바를 통해 반려 목록의 각각의 개수를 확인할 수 있다.
	 * @param id 로그인한 사용자 id
	 * @return 기안올린+결재 해야하는 반려된 개수
	 * @throws FindException
	 * 
	 */
	public int findCntNo(String id) throws FindException{
		return dao.selectByCountNo(id);
	}
	
	/**
	 * (전체)를 누르면 해당 페이지에 자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	 * @param id 로그인한 사용자 id
	 * @return 기안올린+결재 해야하는 전체 문서 목록
	 * @throws FindException
	 */
	public List<Document> findDocsAll(String id) throws FindException{
		
		return dao.selectByListAll(id);
	}
	
	/**
	 * (진행/승인/반려)를 누르면 해당 페이지에  자신이 기안을 올린 문서와 결재해야하는 문서를 모두 가지고온다.
	 * @param id 로그인한 사용자 id
	 * @param state 사이드바에서 사용자가 선택한 부분
	 * @return 사용자가 선택한 상태에 해당하는 기안올린 + 결재 해야하는 문서 목록
	 * @throws FindException
	 */
	public List<Document> findDocsStatus(String id,String state) throws FindException{
		List<Document> lists=null;
		if(state.equals("대기")) {
			lists=dao.selectByListWait(id);
		}else if(state.equals("승인")){
			lists=dao.selectByListOk(id);
		}else if(state.equals("반려")) {
			lists=dao.selectByListNo(id);
			
		}
		return lists;
	}

	
	public static void main(String[] args) {
		service = SideDocsService.getInstance();
		//사이드바 목록 확인 
		try {
			String id="DEV001";
			int result = 0;
			result=service.findCntAll(id);
			System.out.println(id+"의 전체 목록개수 : "+result);
			result=service.findCntWait(id);
			System.out.println(id+"의 진행 목록개수 : "+result);
			result=service.findCntOk(id);
			System.out.println(id+"의 승인 목록개수 : "+result);
			result=service.findCntNo(id);
			System.out.println(id+"의 반려 목록개수 : "+result);
			
			List<Integer> arrlist =new ArrayList<Integer>();
			arrlist.add(0, service.findCntAll(id));
			System.out.println(arrlist.get(0));
			System.out.println(arrlist);
			
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		//사이드바 클릭시 목록 불러오기
		try {
			String id="DEV001";
			String state="승인";
			
			List<Document> lists0 = new ArrayList<>();
			System.out.println(id+"사원의 최종문서  전체값의 목록");
			lists0=service.findDocsAll(id);
			for(Document d: lists0) {
				System.out.println(d.getDocument_no()+" "+
						d.getDocument_title()+" "+
						d.getEmployee().getEmployee_id()+" "+
						d.getEmployee().getName()+" "+
						d.getDraft_date()+" "+
						d.getDocument_type().getDocument_type()+" "+
						d.getApproval().getAp_type().getApStatus_type());
			}
			System.out.println();
			
				List<Document> lists = new ArrayList<>();
			System.out.println(id+"사원의 최종문서 "+state+"값의 목록");
			lists=service.findDocsStatus(id,state);
			for(Document d: lists) {	
				System.out.println(d.getDocument_no()+" "+
						d.getDocument_title()+" "+
						d.getEmployee().getEmployee_id()+" "+
						d.getEmployee().getName()+" "+
						d.getDraft_date()+" "+
						d.getDocument_type().getDocument_type()+" "+
						d.getApproval().getAp_type().getApStatus_type());
			}
		
			
		} catch (FindException e) {
			e.printStackTrace();
		}


	}
	
	
}
