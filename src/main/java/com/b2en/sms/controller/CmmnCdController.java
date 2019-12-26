package com.b2en.sms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.toclient.CmmnDetailCdDtoToClient;
import com.b2en.sms.entity.CmmnDetailCd;
import com.b2en.sms.repo.CmmnDetailCdRepository;

@RestController
@RequestMapping("/api/cmmncd")
public class CmmnCdController {
	
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	
	@GetMapping(value = "/{cmmncd}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CmmnDetailCdDtoToClient>> findByCmmdCd(@PathVariable("cmmncd") String cmmnCd) {
		
		List<CmmnDetailCd> list = repositoryCDC.findByCmmnDetailCdPKCmmnCd(cmmnCd);
		List<CmmnDetailCdDtoToClient> dtoList = new ArrayList<CmmnDetailCdDtoToClient>();
		for(int i = 0; i < list.size(); i++) {
			CmmnDetailCdDtoToClient dto = new CmmnDetailCdDtoToClient();
			dto.setCmmnCd(list.get(i).getCmmnDetailCdPK().getCmmnCd());
			dto.setCmmnDetailCd(list.get(i).getCmmnDetailCdPK().getCmmnDetailCd());
			dto.setCmmnDetailCdNm(list.get(i).getCmmnDetailCdNm());
			dto.setCmmnDetailCdDesc(list.get(i).getCmmnDetailCdDesc());
			dtoList.add(dto);
		}

		return new ResponseEntity<List<CmmnDetailCdDtoToClient>>(dtoList, HttpStatus.OK);

	}
	
}
