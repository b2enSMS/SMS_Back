package com.b2en.sms.entity;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cont extends TimeEntity implements Serializable {

	// 계약
	private static final long serialVersionUID = -5171381164879310748L;

	// 계약ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cont_id")
	private int contId;
	
	// 고객사담당자ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="cust_id")
	private Cust cust;
	
	// 고객사ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="org_id")
	private Org org;
	
	// 담당자ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="emp_id")
	private B2en b2en;
	
	// 모계약ID
	@Column(name="head_cont_id")
	private int headContId;
	
	// 사업명
	@Column(name="cont_nm")
	private String contNm;
	
	// 계약일자
	@Column(name="cont_dt")
	private Date contDt;
	
	// 총계약금액
	@Column(name="cont_tot_amt")
	private String contTotAmt;
	
	// 삭제여부
	@Column(name="del_yn")
	private String delYn;
	
	// 수주보고서번호
	@Column(name="cont_report_no")
	private String contReportNo;
	
	// 계약유형코드
	@Column(name="cont_tp_cd")
	private String contTpCd;
	
	// 설치일자
	@Column(name="install_dt")
	private Date installDt;
	
	// 검수일자
	@Column(name="check_dt")
	private Date checkDt;
	
	// 유지보수개시일
	@Column(name="mtnc_start_dt")
	private Date mtncStartDt;
	
	// 유지보수종료일
	@Column(name="mtnc_end_dt")
	private Date mtncEndDt;

}
