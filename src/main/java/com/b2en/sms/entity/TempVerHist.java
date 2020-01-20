package com.b2en.sms.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.BatchSize;

import com.b2en.sms.entity.pk.TempVerHistPK;

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
public class TempVerHist extends TimeEntity implements Serializable  {

	private static final long serialVersionUID = 8974431318427591632L;
	
	@EmbeddedId
	private TempVerHistPK tempVerHistPK;
	
	@MapsId("tempVerId")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="temp_ver_id")
	private TempVer tempVer;
	
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
	
	// 사용자
	@Column(name="user")
	private String user;

	// MAC주소
	@Column(name = "mac_addr")
	private String macAddr;
	
	// 비고
	@Column(name = "issue_reason")
	private String issueReason;
	
	// 요청일
	@Column(name = "request_date")
	private Date requestDate;
}
