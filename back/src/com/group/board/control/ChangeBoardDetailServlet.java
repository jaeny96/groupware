package com.group.board.control;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group.board.dto.Board;
import com.group.board.service.BoardService;
import com.group.employee.dto.Employee;
import com.group.exception.ModifyException;

/**
 * Servlet implementation class ModifyBoardDetailServlet
 */
public class ChangeBoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//게시글 수정 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String targetWriterid = session.getAttribute("id").toString();
		String modiTargetBdNo = request.getParameter("modiBdTargetNo");
		String modiTargetBdTitle = request.getParameter("modiBdTitle");
		String modiTargetBdContent = request.getParameter("modiBdContent");
		BoardService service;
		ServletContext sc = getServletContext();
		BoardService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = BoardService.getInstance();

		try {
			Employee emp = new Employee();
			emp.setEmployee_id(targetWriterid);
			Board bd = new Board();
			bd.setBd_no(modiTargetBdNo);
			bd.setBd_title(modiTargetBdTitle);
			bd.setBd_content(modiTargetBdContent);
			bd.setWriter(emp);
			service.modifyBd(bd);
		} catch (ModifyException e) {
			e.printStackTrace();
		}
	}

}
