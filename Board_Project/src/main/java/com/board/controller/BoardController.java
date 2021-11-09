package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.BoardDTO;
import com.board.domain.Criteria;
import com.board.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping(value = "/board/")
public class BoardController {
	
	@Setter(onMethod_ = {@Autowired})
	BoardMapper boardMapper;

	// 리스트 화면
	@GetMapping("list")
	public String listPage(Criteria criteria, Model model){		
		criteria.setTotal(boardMapper.getCount());
		criteria.init();
		model.addAttribute("criteria", criteria);
		return "list";
	}
	
	// DataBase에서 데이터 리스트를 받아와 return 하는 페이지 
	@GetMapping(value = "getList", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<List<BoardDTO>> getList(Criteria criteria){
		criteria.setTotal(boardMapper.getCount());
		criteria.init();
		return new ResponseEntity<List<BoardDTO>>(boardMapper.getListWithPaging(criteria), HttpStatus.OK);
	}
	
	//등록 화면
	@GetMapping("registerPage")
	public String registerPage(Criteria criteria, RedirectAttributes attr) {
		return "register";
	}
	
	//등록 화면
	@PostMapping(value = "register")
	public String register(BoardDTO board, Criteria criteria, RedirectAttributes attr) {
		criteria.setPageNum(1);
		attr.addFlashAttribute("criteria", criteria);
		boardMapper.add(board);
		return "redirect:list";
	}
	
	//조회화면
	@GetMapping(value = "get/{bno}")
	public String get(@PathVariable("bno") long bno, Criteria criteria, Model model) {
		BoardDTO board = boardMapper.get(bno);
	
		model.addAttribute("board", board);
		model.addAttribute("criteria", criteria);
		return "get";
	}
	
	@GetMapping(value = "remove/{bno}")
	public String remove(Criteria criteria, @PathVariable("bno")long bno, RedirectAttributes attr) {
		log.info(bno + "번 Board가 삭제되었습니다.");
		attr.addFlashAttribute("criteria", criteria);
		return "redirect:/board/list";
	}
	
	@PostMapping("modifyPage")
	public String modifyPage(BoardDTO board,Criteria criteria, Model model) {
		
		model.addAttribute("board", board);
		model.addAttribute("criteria", criteria);
	
		return "modify";
	}
	
	@PostMapping("modify")
	public String modify(BoardDTO board, Criteria criteria, RedirectAttributes attr) {
		boardMapper.update(board);
		log.info(board.getBno() + "번 Board가 변경되었습니다.");
		attr.addFlashAttribute("criteria", criteria);
		return "redirect:/board/list";
	}
}
