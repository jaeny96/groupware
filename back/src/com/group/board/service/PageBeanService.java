package com.group.board.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.group.board.dao.BoardDAO;
import com.group.board.dto.Board;
import com.group.board.dto.PageBean;
import com.group.exception.FindException;

public class PageBeanService {
	private BoardDAO dao;
	private static PageBeanService service;
	public static String envProp;

	private PageBeanService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
			String className = env.getProperty("boardDAO");
			/*
			 * 리플랙션 기법 이용하여 객체 생성 소스코드를 재컴파일하지 않기 위해 리플랙션 기법 이용하는 것임!
			 */
			Class c = Class.forName(className); // JVM에 로드
			dao = (BoardDAO) c.newInstance(); // 객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static PageBeanService getInstance() {
		if (service == null) {
			service = new PageBeanService();
		}
		return service;
	}

	public int selectTotalPage() {
		int totalPage=0;
		int temp=PageBean.CNT_PER_PAGE;
		try { 
			List<Board> bdList = dao.selectAll();
			if((bdList.size()%temp)!=0) {
				totalPage=(bdList.size()/temp)+1;
			}else {
				totalPage=(bdList.size()/temp);
			}
			
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		return totalPage;
	}
	public List<Integer> selectPageGroup(int PageGroup) {
		int totalPage=0;
		int totalGroupNum=0;
		int temp=PageBean.CNT_PER_PAGE;
		int group=PageBean.CNT_PER_PAGE_GROUP;
		List<Integer> list = new ArrayList<Integer>();
		int index=0;
		try { 
			List<Board> bdList = dao.selectAll();
			if((bdList.size()%temp)!=0) {
				totalPage=(bdList.size()/temp)+1;
			}else {
				totalPage=(bdList.size()/temp);
			}
			
			if((totalPage%group)!=0) {
				totalGroupNum=(totalPage/group)+1;	
			}else {
				totalGroupNum=(totalPage/group);
			}
			
			if(PageGroup==totalGroupNum) {
				if((totalPage%group)!=0) {
					index=(totalPage%group);
				}else {
					index=4;					
				}
			}else {
				index=4;
			}

			for(int i=0; i<index; i++) {
				list.add(((PageGroup-1)*group)+i+1);
			}

		} catch (FindException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
