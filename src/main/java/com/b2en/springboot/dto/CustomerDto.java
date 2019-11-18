package com.b2en.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerDto {
	
	//@NotNull
	//@NotBlank
	private String compNm;
	
	//@NotNull
	//@NotBlank
	//@Size(min=2, max=6)
	private String mgrNm;
	
	private String mgrPosition;
	
	private String custTp;
	
	private String custEmail;
	
	private String usrNm;
	
	private String macAdr;
	
	private String telNo;
}
