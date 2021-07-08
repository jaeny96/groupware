package com.group.board.service;

import com.group.board.dao.PageBeanDAO;
import com.group.board.dto.PageBean;

public class PageBeanService implements PageBeanDAO{
	@Override
	public int[] createPageGroup(int endIndex) {
		PageBean pb = new PageBean<>();
		int[] pageGroup = new int[pb.CNT_PER_PAGE_GROUP];
		for(int i=0; i<pageGroup.length; i++) {
			pageGroup[i]= i+1+endIndex;
		}
		return pageGroup;
	}
	
	public static void main(String[] args) {
		PageBeanService service = new PageBeanService(); 
		int[] group = service.createPageGroup(4);
		for(int i : group) {
			System.out.println(i);
		}
	}
}
