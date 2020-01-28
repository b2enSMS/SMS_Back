package com.b2en.sms.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Embeddable
public class TempVerHistPK implements Serializable{
	
	private static final long serialVersionUID = -6699089240310649834L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="temp_ver_hist_seq")
	private int tempVerHistSeq;
	
	private int tempVerId;
}
