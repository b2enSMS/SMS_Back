package com.b2en.sms.entity;

import java.io.Serializable;

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
public class TempVer extends TimeEntity implements Serializable {

	// 임시배포항목
	private static final long serialVersionUID = 2808514435270171033L;

	// 임시배포내역ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="temp_ver_id")
	private int tempVerId;
	
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
	@Column(name="mac_addr")
	private String macAddr;
}
