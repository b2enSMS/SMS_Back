package com.b2en.sms.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Meet extends TimeEntity implements Serializable {

	// 미팅
	private static final long serialVersionUID = 5246350661882469030L;
	
	// 미팅ID
	@Id
	@Column(name="meet_id")
	private String meetId;
	
	// 고객사ID (FK)
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="org_id")
	private Org org;
	
	// 미팅일시
	@Column(name="meet_dt")
	private Date meetDt;
	
	// 미팅내용
	@Column(name="meet_cnt")
	private String meetCnt;
	
	// 미팅시작시간
	@Column(name="meet_start_time")
	private Time meetStartTime;
	
	// 미팅전체시간
	@Column(name="meet_tot_time")
	private String meetTotTime;
	
	// 미팅유형코드
	@Column(name="meet_tp_cd")
	private String meetTpCd;
}
