package com.board.mapper;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.domain.BoardDTO;
import com.board.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

@Log4j
public class BoardMapperTest {
	
	@Setter(onMethod_ = {@Autowired} )
	BoardMapper mapper;
	
//	@Test
//	public void insertTest() {
//		
//		BoardDTO dto = new BoardDTO();
//		dto.setTitle("his story");
//		dto.setWriter("him");
//		dto.setContent("a long long time ago");
//		mapper.add(dto);
//	}
	
//	@Test
//	public void getTest() {
//		log.info("getTest-----------------------------");
//		BoardDTO dto = mapper.get(3);
//		log.info(dto);
//	}
//		
//	@Test
//	public void updateTest() {
//		log.info("getTest-----------------------------");
//		int bno = 3;
//		
//		BoardDTO dto = new BoardDTO();
//		dto.setBno(bno);
//		dto.setTitle("her story");
//		dto.setWriter("her");
//		dto.setContent("a short short time after");
//		
//		log.info("before update");
//		log.info(mapper.get(bno));
//		
//		mapper.update(dto);
//		
//		log.info("after update");
//		log.info(mapper.get(bno));
//
//	}
	
//	@Test
//	public void getListTest() {
//		
//		List<BoardDTO> list = mapper.getList();
//		list.stream().forEach(n -> log.info(n));
//	}
	
//	@Test
//	public void deleteTest() {
//		log.info("before delete -------------------------");
//		mapper.getList().stream().forEach(n -> log.info(n));
//		mapper.delete(3);
//		log.info("after delete -------------------------");
//		mapper.getList().stream().forEach(n -> log.info(n));
//	}
	
	@Test
	public void getListWithParingTest() {
		Criteria cri = new Criteria();
		cri.setAmount(2);
		cri.setPageNum(2);
		cri.init();
		
		mapper.getListWithPaging(cri).forEach(it -> log.info(it));
		
	}
}
