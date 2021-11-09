package com.board.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.board.mapper.BoardMapper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
@Setter
@Getter
@ToString
public class Criteria {
	
	// 현재 페이지 번호
	long pageNum;
	// 페이지별 요소의 갯수
	long amount;
	
	//DataBase의 전체 요소 갯수
	long total;
	
	/* 마지막 페이지
	 * ex) 1~10 페이지를 보여줄경우 startPage = 1 endPage = 10
	 * 11페이지로 넘어갈 경우  startPage = 11 endPage = 20 
	 */
	long endPage;
	long startPage;
	
	// 이전 버튼 여부
	boolean prev;
	// 다음 버튼 여부
	boolean next;
		
	// 초기화 
	// 페이징에 필요한 데이터를 계산한다.
	public void init() {

		long realEnd = (long)(Math.ceil(total/((amount)*(1f))));
		endPage = (long)(Math.ceil(pageNum/10.0))*10;	

		startPage = endPage - 9;
		
		if(realEnd < endPage) {
			endPage = realEnd;
		}	
		prev = startPage > 1;
		next = endPage < realEnd;
	}
	
}
