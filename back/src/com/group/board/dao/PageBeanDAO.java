package com.group.board.dao;

public interface PageBeanDAO {
	/**
	 * 페이지 그룹을 생성한다
	 * @return 그룹의 int 배열
	 */
	public int[] createPageGroup(int endIndex);
}
