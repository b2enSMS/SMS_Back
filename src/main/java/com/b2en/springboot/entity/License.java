package com.b2en.springboot.entity;

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
public class License extends TimeEntity implements Serializable {
	
	private static final long serialVersionUID = 9044317811497949704L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="lcns_id")
	private long lcnsId;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="prdt_id")
	private Solution solution;
	
	@Column(name="LCNS_KEY")
	private String lcnsKey;
	
	@Column(name="LCNS_NM")
	private String lcnsNm;
	
	@Column(name="LCNS_TP")
	private String lcnsTp;
	
	@Column(name="LCNS_ISSU_DTM")
	private Date lcnsIssuDtm;
	
	@Column(name="LCNS_EXDT")
	private Date lcnsExdt;
}