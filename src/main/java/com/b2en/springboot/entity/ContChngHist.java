package com.b2en.springboot.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.b2en.springboot.entity.pk.ContChngHistPK;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContChngHist extends TimeEntity implements Serializable {

	// 계약이력
	private static final long serialVersionUID = 4870948412599295269L;

	// 계약이력 복합키
	@EmbeddedId
	private ContChngHistPK contChngHistPK;
	
	@MapsId("contId")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cont_id")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Cont cont;
	
	// 고객사ID (FK)
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="org_id")
	private Org org;
	
	// 담당자ID (FK)
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="emp_id")
	private B2en b2en;
	
	// 계약일자
	@Column(name="cont_dt")
	private Date contDt;
	
	// 총계약금액
	@Column(name="cont_tot_amt")
	private String contTotAmt;
	
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
