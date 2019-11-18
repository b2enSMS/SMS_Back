package com.b2en.springboot.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Solution extends TimeEntity implements Serializable {

	private static final long serialVersionUID = 6383716841868473289L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="prdt_id")
	private long prdtId;
	
	@Column(name="PRDT_NM")
	private String prdtNm;
	
	@Column(name="PRDT_VER")
	private String prdtVer;
	
	@Column(name="PRDT_TP")
	private String prdt_tp;
}
