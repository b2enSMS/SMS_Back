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

import com.b2en.springboot.entity.pk.LcnsChngHistPK;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LcnsChngHist extends TimeEntity implements Serializable {

	// 라이센스이력
	private static final long serialVersionUID = 4820335186883041696L;

	// 라이센스이력 PK
	@EmbeddedId
	private LcnsChngHistPK lcnsChngHistPK;
	
	@MapsId("lcnsId")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="lcns_id")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Lcns lcns;

	// 라이센스번호
	@Column(name = "lcns_no")
	private String lcnsNo;

	// 발행일
	@Column(name = "lcns_issu_dt")
	private Date lcnsIssuDt;

	// 라이센스유형코드
	@Column(name = "lcns_tp_cd")
	private String lcnsTpCd;

	// 증명번호
	@Column(name = "cert_no")
	private String certNo;

	// 라이센스개시일자
	@Column(name = "lcns_start_dt")
	private Date lcnsStartDt;

	// 라이센스종료일자
	@Column(name = "lcns_end_dt")
	private Date lcnsEndDt;
}
