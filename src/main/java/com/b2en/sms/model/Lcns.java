package com.b2en.sms.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.BatchSize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@BatchSize(size=10)
public class Lcns extends TimeEntity implements Serializable{

	// 라이센스
	private static final long serialVersionUID = -3132322132330034950L;

	// 라이센스ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="lcns_id")
	private int lcnsId;
	
	// 제품ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="prdt_id")
	private Prdt prdt;
	
	// 라이센스번호
	@Column(name="lcns_no")
	private String lcnsNo;
	
	// 발행일
	@Column(name="lcns_issu_dt")
	private Date lcnsIssuDt;
	
	// 라이센스유형코드
	@Column(name="lcns_tp_cd")
	private String lcnsTpCd;
	
	// 증명번호
	@Column(name="cert_no")
	private String certNo;
	
	// 라이센스개시일자
	@Column(name="lcns_start_dt")
	private Date lcnsStartDt;
	
	// 라이센스종료일자
	@Column(name="lcns_end_dt")
	private Date lcnsEndDt;
	
	// 스캔본
	@Column(name="scan")
	private String scan;
	
	// 삭제여부
	@Column(name="del_yn")
	private String delYn;
}
