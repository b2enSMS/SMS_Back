package com.b2en.springboot.dto;

import lombok.Data;

@Data
public class ContDetailHistDto {

	private int contSeq;

	private String contId;
	
	private int detailSeq;
	
	private String contAmt;
	
	private String prdtId;
}
