package com.group.board.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.board.dto.Board;
import com.group.board.service.BoardService;
import com.group.exception.FindException;

/**
 * Servlet implementation class SearchBoardServlet
 */
public class SearchBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//게시글 검색 내용 반환
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bdSearchCategory = request.getParameter("bdSearchCategory");
		String bdSearchWord = request.getParameter("bdSearchWord");
		
		BoardService service;
		ServletContext sc = getServletContext();
		BoardService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		service = BoardService.getInstance();

		try {
			List<Board> bdList = service.searchBd(bdSearchCategory, bdSearchWord);
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			String jsonStr = mapper.writeValueAsString(bdList);

			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

}
