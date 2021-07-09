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
import com.group.exception.AddException;

/**
 * Servlet implementation class AddBoardServlet
 */
public class AddBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String addBdWriter = request.getParameter("addBdWriter");
		String addBdWriterId = request.getParameter("addBdWriterId");
		String addBdTitle = request.getParameter("addBdTitle");
		String addBdContent = request.getParameter("addBdContent");
		System.out.println(addBdWriter + "/" + addBdWriterId + "/" + addBdTitle + "/" + addBdContent);
		BoardService service;
		ServletContext sc = getServletContext();
		BoardService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = BoardService.getInstance();

		try {
			Employee emp = new Employee();
			emp.setEmployee_id(addBdWriterId);
			emp.setName(addBdWriter);
			Board bd = new Board();
			bd.setBd_title(addBdTitle);
			bd.setBd_content(addBdContent);
			bd.setWriter(emp);
			service.addBd(bd);
		} catch (AddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
