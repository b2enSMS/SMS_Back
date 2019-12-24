package com.b2en.sms.entity;

import java.io.Serializable;

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

//@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TempVerHist extends TimeEntity implements Serializable  {

	private static final long serialVersionUID = 8974431318427591632L;
	
	@Id
	@Column(name="temp_ver_hist_seq")
	private int TempVerHistSeq;
	
	// 고객ID (FK)
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "cust_id")
	private Cust cust;

	// 라이센스ID (FK)
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "lcns_id")
	private Lcns lcns;

	// 담당자ID (FK)
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "emp_id")
	private B2en b2en;

	// MAC주소
	@Column(name = "mac_addr")
	private String macAddr;
}
