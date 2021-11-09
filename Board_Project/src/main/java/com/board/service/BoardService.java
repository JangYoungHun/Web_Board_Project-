package com.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardService {
	
	@Setter(onMethod_ = {@Autowired} )
	BoardMapper mapper;
	
	public BoardDTO get(long bno) {
		return mapper.get(bno);
		}
	
}
