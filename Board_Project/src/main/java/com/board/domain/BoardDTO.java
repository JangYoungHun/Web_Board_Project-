package com.board.domain;

import lombok.Data;

@Data
public class BoardDTO {
	
	private long bno;
	private String title;
	private String writer;
	private String content;
	private String regdate;
	private String updatedate;
	
}
