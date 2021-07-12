package com.group.board.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.group.board.dao.BoardDAO;
import com.group.board.dto.Board;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;
import com.group.main.service.MainService;

public class BoardService {
	private BoardDAO dao;
	private static BoardService service;
	public static String envProp;

	private BoardService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream(envProp));
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

	public static BoardService getInstance() {
		if (service == null) {
			service = new BoardService();
		}
		return service;
	}

	/**
	 * 현재 페이지의 게시글 목록을 조회한다
	 * 
	 * @param currentPage 현재 페이지
	 * @return 게시글 목록
	 * @throws FindException
	 */
	public List<Board> showBdAll(int currentPage) throws FindException {
		return dao.selectAll(currentPage);
	}

	/**
	 * 게시글(제목, 작성자)을 검색한다
	 * 
	 * @param category 제목으로 검색할지 작성자로 검색할지 결정한 카테고리
	 * @param word     검색할 단어
	 * @return 단어에 대한 게시글 목록
	 * @throws FindException
	 */
	public List<Board> searchBd(String category, String word) throws FindException {
		return dao.selectByWord(category, word);
	}

	/**
	 * 특정 게시글 상세정보를 조회한다
	 * 
	 * @param bd_no 특정 게시글 번호
	 * @return 게시글의 상세 정보
	 * @throws FindException
	 */
	public Board showBdDetail(String bd_no) throws FindException {
		return dao.selectBdInfo(bd_no);
	}

	/**
	 * 게시글을 등록한다
	 * 
	 * @param bd 등록할 내용 담은 객체
	 * @throws AddException
	 */
	public void addBd(Board bd) throws AddException {
		if (bd.getBd_title() != null) {
			dao.insert(bd);
		} else {
			System.out.println("제목이 입력되지 않았습니다");
		}
	}

	/**
	 * 게시글을 수정한다
	 * 
	 * @param bd 변경할 내용 담고 있는 객체
	 * @throws ModifyException 로그인한 사용자가 글을 작성한 사원인지 비교하는 조건 필요 - 어디서? 서비스에서 할지 후에
	 *                         할지 고민중
	 */
	public void modifyBd(Board bd) throws ModifyException {
		if (!"".equals(bd.getBd_title()) && bd.getBd_title() != null) {
			dao.update(bd);
		} else {
			System.out.println("제목이 입력되지 않았습니다");
		}
	}

	/**
	 * 게시글을 삭제한다
	 * 
	 * @param bd 삭제할 게시글 정보 담은 객체
	 * @throws RemoveException 로그인한 사용자가 글을 작성한 사원인지 비교하는 조건 필요 - 어디서? 서비스에서 할지 후에
	 *                         할지 고민중
	 */
	public void removeBd(Board bd) throws RemoveException {
		dao.delete(bd);
	}

	public static void main(String[] args) {
//		int currentPage=2;
//		try {
//			List<Board> bdList = service.showBdAll(currentPage);
//			for(Board bd : bdList) {
//				System.out.println(bd.getBd_no()+"/"+bd.getBd_title()+
//						"/" + bd.getWriter().getEmployee_id() + "/" + bd.getWriter().getName() + "/" +bd.getBd_date());
//			}
//		} catch (FindException e) {
//			e.printStackTrace();
//		}

//		String category="name";
//		String word = "권";
//		String category="bd_title";
//		String word = "안녕";
//		try {
//			List<Board> bdList= service.searchBd(category, word);
//			for (Board bd : bdList) {
//				System.out.println(bd.getBd_no() + "/" + bd.getBd_title() + "/" + bd.getWriter().getEmployee_id() + "/"
//						+ bd.getWriter().getName() + "/" + bd.getBd_date());
//			}
//		} catch (FindException e) {
//			e.printStackTrace();
//		}

//		String bd_no = "BD3";
//		try {
//			Board bd = service.showBdDetail(bd_no);
//			System.out.println(bd.getBd_no() + "/" + bd.getWriter().getEmployee_id() + "/" + bd.getWriter().getName()
//					+ "/" + bd.getBd_title() + "/" + bd.getBd_content() + "/" + bd.getBd_date());
//		} catch (FindException e) {
//			e.printStackTrace();
//		}

//		Employee emp = new Employee();
//		Board bd = new Board();
//		emp.setEmployee_id("DEV001");
//		bd.setWriter(emp);
//		bd.setBd_title("책임을 맡게된 임창균입니다.");
//		bd.setBd_content("앞으로 잘 부탁드립니다 여러분");
//		try {
//			service.addBd(bd);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}

//		int currentPage = 1;
//		List<Board> bdList;
//		try {
//			bdList = service.showBdAll(currentPage);
//			Board bd = bdList.get(0);
////			System.out.println(bd);
//			bd.setBd_title("하하");
//			bd.setBd_content("힘들군여");
//			service.modifyBd(bd);
//		} catch (FindException | ModifyException e) {
//			e.printStackTrace();
//		}

//		int currentPage = 1;
//		List<Board> bdList;
//		try {
//			bdList = service.showBdAll(currentPage);
//			Board bd = bdList.get(0);
////			System.out.println(bd);
//			service.removeBd(bd);
//		} catch (FindException | RemoveException e) {
//			e.printStackTrace();
//		}

	}
}
