package com.group.board.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.group.board.dao.BoardCommentDAO;
import com.group.board.dto.BoardComment;
import com.group.employee.dto.Employee;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.RemoveException;

public class BoardCommentService {
	private BoardCommentDAO dao;
	private static BoardCommentService service = new BoardCommentService();

	private BoardCommentService() {
		Properties env = new Properties();
		try {
			env.load(new FileInputStream("classes.prop"));
			String className = env.getProperty("boardCommentDAO");
			System.out.println(className);
			/*
			 * 리플랙션 기법 이용하여 객체 생성 소스코드를 재컴파일하지 않기 위해 리플랙션 기법 이용하는 것임!
			 */
			Class c = Class.forName(className); // JVM에 로드
			dao = (BoardCommentDAO) c.newInstance(); // 객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 특정 게시글에 대한 댓글목록을 조회한다
	 * @param bd_no 특정 게시글 번호
	 * @return 댓글 목록
	 * @throws FindException
	 */
	public List<BoardComment> showCm(String bd_no) throws FindException {
		return dao.selectAll(bd_no);
	}

	/**
	 * 특정 게시글에 대한 댓글을 작성한다
	 * @param cm 댓글의 내용을 담은 객체
	 * @throws AddException
	 */
	public void addCm(BoardComment cm) throws AddException {
		boolean flag = false;
		if (cm.getCm_writer().getEmployee_id() != null && !"".equals(cm.getCm_writer().getEmployee_id())) {
			if (cm.getCm_content() != null && !"".equals(cm.getCm_content())) {
				flag = true;
			} else {
				System.out.println("입력한 내용이 없습니다");
			}
		} else {
			System.out.println("로그인 되지 않았습니다");
		}
		if (flag) {
			dao.insert(cm);
		}
	}

	/**
	 * 댓글을 삭제한다
	 * @param cm 삭제할 댓글을 담고 있는 객체
	 * @throws RemoveException
	 * 로그인한 사용자가 댓글을 작성한 작성자인지 비교하는 조건 추가함
	 */
	public void removeCm(BoardComment cm) throws RemoveException {
		List<BoardComment> cmList;
		try {
			cmList = service.showCm(cm.getBd_no());
			BoardComment compare = cmList.get(cmList.size()-cm.getCm_no());
			if(compare.getCm_writer().employee_id.equals(cm.getCm_writer().employee_id)) {
				dao.delete(cm);
			}else {
				System.out.println("댓글을 작성한 작성자가 아닙니다");
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String bd_no = "BD2";
		List<BoardComment> cmList;
		try {
			cmList = service.showCm(bd_no);
			for (BoardComment cm : cmList) {
				System.out.println(cm.getBd_no() + "/" + cm.getCm_no() + "/" + cm.getCm_writer().getEmployee_id() + "/"
						+ cm.getCm_writer().getName() + "/" + cm.getCm_content() + "/" + cm.getCm_date());
			}
		} catch (FindException e) {
			e.printStackTrace();
		}

//		try {
//			Employee emp = new Employee();
//			BoardComment cm = new BoardComment();
//			emp.setEmployee_id("DEV003");
//			cm.setCm_writer(emp);
//			cm.setBd_no("BD2");
//			cm.setCm_content("hello everybody");
//			service.addCm(cm);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}

//		int cm_no=3;
//		String id = "DEV003";
//		BoardComment cm = new BoardComment();
//		cm.setBd_no(bd_no);
//		cm.setCm_no(cm_no);
//		Employee emp = new Employee();
//		emp.setEmployee_id(id);
//		cm.setCm_writer(emp);
//		
//		try {
//			service.removeCm(cm);
//		} catch (RemoveException e) {
//			e.printStackTrace();
//		}

	}

}
