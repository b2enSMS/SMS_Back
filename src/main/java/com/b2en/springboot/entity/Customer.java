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
public class Customer extends TimeEntity implements Serializable {

	private static final long serialVersionUID = -1020705806990305070L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cust_id")
	private long custId;
	
	@Column(name="COMP_NM")
	private String compNm;
	
	@Column(name="MGR_NM")
	private String mgrNm;
	
	@Column(name="MGR_POSITION")
	private String mgrPosition;
	
	@Column(name="CUST_TP")
	private String custTp;
	
	@Column(name="CUST_EMAIL")
	private String custEmail;
	
	@Column(name="USR_NM")
	private String usrNm;
	
	@Column(name="MAC_ADR")
	private String macAdr;
	
	@Column(name="TEL_NO")
	private String telNo;
	
}
