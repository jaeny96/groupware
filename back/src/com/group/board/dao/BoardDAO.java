package com.group.board.dao;

import java.util.List;

import com.group.board.dto.Board;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

public interface BoardDAO {
	/**
	 * 게시글 전체 목록을 조회한다
	 * @return 게시글 전체 목록
	 */
	public List<Board> selectAll() throws FindException; //나중에 없어질 수도 있음
	/**
	 * 페이지 별 게시글 목록을 조회한다
	 * @param currentPage 현재 페이지
	 * @return 페이지 별 게시글 목록
	 */
	public List<Board> selectAll(int currentPage) throws FindException;
	/**
	 * 제목과 작성자를 통해 게시글 목록을 검색한다
	 * @param word 제목과 작성자가 가지고 있는 문자
	 * @return 해당 문자를 제목과 작성자가 가지고 있는 게시글 목록
	 */
	public List<Board> selectByWord(String category,String word) throws FindException;
	/**
	 * 게시글의 상세 내용을 조회한다
	 * @param bd_no 게시글 번호
	 * @return 게시글 상세 내용
	 */
	public Board selectBdInfo(String bd_no) throws FindException;
	/**
	 * 게시글을 등록한다
	 * @param bd 게시글 내용들을 담고 있는 객체
	 */
	public void insert(Board bd) throws AddException;
	/**
	 * 게시글을 수정한다 (제목, 내용)
	 * @param bd 게시글 수정할 내용 담고 있는 객체
	 */
	public void update(Board bd) throws ModifyException;
	/**
	 * 게시글을 삭제한다 
	 * @param bd_no 삭제할 게시글 번호
	 */
	public void delete(Board bd) throws RemoveException;
	
}
