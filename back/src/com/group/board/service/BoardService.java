package com.group.board.service;

import java.util.List;

import com.group.board.dto.Board;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.exception.ModifyException;
import com.group.exception.RemoveException;

public class BoardService {
	public List<Board> showBdAll(int currentPage) throws FindException{
		return null;
	}
	
	public List<Board> searchBd(String word) throws FindException{
		return null;
	}
	public Board showBdDetail(String bd_no) throws FindException{
		return null;
	}
	public void addBd(Board bd) throws AddException{
		
	}
	public void modifyBd(Board bd) throws ModifyException{
		
	}
	public void removeBd(Board bd) throws RemoveException{
		
	}
}
