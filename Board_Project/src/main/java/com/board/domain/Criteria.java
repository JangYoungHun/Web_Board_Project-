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
	
	// ���� ������ ��ȣ
	long pageNum;
	// �������� ����� ����
	long amount;
	
	//DataBase�� ��ü ��� ����
	long total;
	
	/* ������ ������
	 * ex) 1~10 �������� �����ٰ�� startPage = 1 endPage = 10
	 * 11�������� �Ѿ ���  startPage = 11 endPage = 20 
	 */
	long endPage;
	long startPage;
	
	// ���� ��ư ����
	boolean prev;
	// ���� ��ư ����
	boolean next;
		
	// �ʱ�ȭ 
	// ����¡�� �ʿ��� �����͸� ����Ѵ�.
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
