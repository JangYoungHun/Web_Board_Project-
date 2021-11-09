package com.board.mapper;

import java.util.List;

import com.board.domain.BoardDTO;
import com.board.domain.Criteria;

public interface BoardMapper {

	public BoardDTO get(long bno);

	public List<BoardDTO> getList();
	
	public List<BoardDTO> getListWithPaging(Criteria criteria);
	
	public long getCount();
	
	public int add(BoardDTO board);
	
	public int update(BoardDTO board);
	
	public int delete(long bno); 
}
