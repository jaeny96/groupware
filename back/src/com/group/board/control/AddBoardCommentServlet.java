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
import com.group.exception.AddException;

/**
 * Servlet implementation class AddBoardCommentServlet
 */
public class AddBoardCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//댓글 등록
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String addTargetNo = request.getParameter("addTargetBdNo");
		String addCmWriter = request.getParameter("addCmWriter");
		String addCmWriterId = request.getParameter("addCmWriterId");
		String addCmContent = request.getParameter("addCmContent");
		BoardCommentService service;
		ServletContext sc = getServletContext();
		BoardCommentService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = BoardCommentService.getInstance();

		try {
			Employee emp = new Employee();
			emp.setEmployee_id(addCmWriterId);
			emp.setName(addCmWriter);
			BoardComment cm = new BoardComment();
			cm.setBd_no(addTargetNo);
			cm.setCm_content(addCmContent);
			cm.setCm_writer(emp);
			service.addCm(cm);
		} catch (AddException e) {
			e.printStackTrace();
		}
	}

}
