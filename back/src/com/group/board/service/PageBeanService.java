package com.group.board.service;

import java.io.FileInputStream;
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
//			env.load(new FileInputStream("classes.prop"));
			String className = env.getProperty("boardDAO");
			System.out.println(className);
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

	public int[] createPageGroup(int endIndex) {
		int[] pageGroup = new int[PageBean.CNT_PER_PAGE_GROUP];
		for (int i = 0; i < pageGroup.length; i++) {
			pageGroup[i] = i + 1 + endIndex;
		}
		return pageGroup;

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
			System.out.println(bdList.size());
		} catch (FindException e) {
			// TODO Auto-generated catch blo ck
			e.printStackTrace();
		}
		
		return totalPage;
	}
	public static void main(String[] args) {
//		PageBeanService service = new PageBeanService(); 
//		int[] group = service.createPageGroup(4);
//		for(int i : group) {
//			System.out.println(i);
//		}
//		int cnt = service.selectTotalPage();
//		System.out.println(cnt);
	}
}
