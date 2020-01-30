package com.b2en.sms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.CmmnDetailCdDto;
import com.b2en.sms.model.CmmnDetailCd;
import com.b2en.sms.repo.CmmnDetailCdRepository;

@RestController
@RequestMapping("/api/cmmncd")
public class CmmnCdController {
	
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	
	@GetMapping(value = "/{cmmncd}")
	public ResponseEntity<List<CmmnDetailCdDto>> findByCmmdCd(@PathVariable("cmmncd") String cmmnCd) {
		
		List<CmmnDetailCd> list = repositoryCDC.findByCmmnDetailCdPKCmmnCd(cmmnCd);
		if(list == null) {
			List<CmmnDetailCdDto> nothing = new ArrayList<CmmnDetailCdDto>();
			return new ResponseEntity<List<CmmnDetailCdDto>>(nothing, HttpStatus.OK);
		}
		List<CmmnDetailCdDto> dtoList = new ArrayList<CmmnDetailCdDto>();
		for(int i = 0; i < list.size(); i++) {
			CmmnDetailCdDto dto = new CmmnDetailCdDto();
			dto.setCmmnCd(list.get(i).getCmmnDetailCdPK().getCmmnCd());
			dto.setCmmnDetailCd(list.get(i).getCmmnDetailCdPK().getCmmnDetailCd());
			dto.setCmmnDetailCdNm(list.get(i).getCmmnDetailCdNm());
			dto.setCmmnDetailCdDesc(list.get(i).getCmmnDetailCdDesc());
			dtoList.add(dto);
		}

		return new ResponseEntity<List<CmmnDetailCdDto>>(dtoList, HttpStatus.OK);

	}
	
}
