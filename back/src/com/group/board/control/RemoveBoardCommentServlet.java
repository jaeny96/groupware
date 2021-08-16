package com.group.board.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.board.dto.BoardComment;
import com.group.board.service.BoardCommentService;
import com.group.employee.dto.Employee;
import com.group.exception.RemoveException;

/**
 * Servlet implementation class RemoveBoardCommentServlet
 */
public class RemoveBoardCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//게시글의 댓글 삭제
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int removeTargetCmNo = Integer.parseInt(request.getParameter("removeTargetCmNo"));
		String removeTargetBdNo = request.getParameter("removeTargetBdNo");
		String removeCmWriterId = request.getParameter("removeCmWriterId");
		BoardCommentService service;
		ServletContext sc = getServletContext();
		BoardCommentService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = BoardCommentService.getInstance();

		try {
			Employee emp = new Employee();
			emp.setEmployee_id(removeCmWriterId);
			BoardComment cm = new BoardComment();
			cm.setBd_no(removeTargetBdNo);
			cm.setCm_no(removeTargetCmNo);
			cm.setCm_writer(emp);
			service.removeCm(cm);
		} catch (RemoveException e) {
			e.printStackTrace();
		}
	}

}
