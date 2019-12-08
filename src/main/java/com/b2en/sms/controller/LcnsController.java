package com.b2en.sms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.LcnsDto;
import com.b2en.sms.dto.LcnsDtoToClient;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.entity.Lcns;
import com.b2en.sms.entity.Prdt;
import com.b2en.sms.repo.LcnsRepository;
import com.b2en.sms.repo.PrdtRepository;

@RestController
@RequestMapping("/api/lcns")
public class LcnsController {
	
	@Autowired
	private LcnsRepository repositoryL;
	
	@Autowired
	private PrdtRepository repositoryP;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LcnsDtoToClient>> showAll() {

		List<Lcns> entityList = repositoryL.findAll();
		List<LcnsDtoToClient> list;
		int prdtId;
		String prdtNm;

		list = modelMapper.map(entityList, new TypeToken<List<LcnsDtoToClient>>() {
		}.getType());

		for(int i = 0; i < entityList.size(); i++) {
			prdtId = entityList.get(i).getPrdt().getPrdtId();
			prdtNm = entityList.get(i).getPrdt().getPrdtNm();
			list.get(i).setPrdtId(prdtId);
			list.get(i).setPrdtNm(prdtNm);
		}
		
		return new ResponseEntity<List<LcnsDtoToClient>>(list, HttpStatus.OK);

	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody LcnsDto lcns, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		 
		
		Lcns lcnsEntity = modelMapper.map(lcns, Lcns.class);
		
		int prdtId = lcns.getPrdtId();
		Prdt prdt = repositoryP.findByPrdtId(prdtId);
		
		lcnsEntity.setPrdt(prdt);
		
		repositoryL.save(lcnsEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
