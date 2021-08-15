package com.group.board.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group.board.dto.Board;
import com.group.board.service.BoardService;
import com.group.employee.dto.Employee;
import com.group.exception.RemoveException;

/**
 * Servlet implementation class RemoveBoardServlet
 */
public class RemoveBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//게시글 삭제
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String removeTargetBdNo = request.getParameter("removeTargetBdNo");
		String removeBdWriterId = request.getParameter("removeWriterId");
		BoardService service;
		ServletContext sc = getServletContext();
		BoardService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = BoardService.getInstance();

		try {
			Board bd = new Board();
			bd.setBd_no(removeTargetBdNo);
			Employee emp = new Employee();
			emp.setEmployee_id(removeBdWriterId);
			bd.setWriter(emp);
			service.removeBd(bd);
		} catch (RemoveException e) {
			e.printStackTrace();
		}

	}

}
