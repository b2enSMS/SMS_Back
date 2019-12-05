package com.b2en.sms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class B2en extends TimeEntity implements Serializable {
	
	// 비투엔담당자
	private static final long serialVersionUID = -7519824014185634060L;
	
	// 담당자ID
	@Id
	@Column(name="emp_id")
	private String empId;
	
	// 담당자명
	@Column(name="emp_nm")
	private String empNm;
	
	// 사번
	@Column(name="emp_no")
	private String empNo;
	
	// 이메일
	@Column(name="email")
	private String email;
	
	// 전화번호
	@Column(name="tel_no")
	private String telNo;
	
	// 역할구분코드
	@Column(name="emp_tp_cd")
	private String empTpCd;
}
