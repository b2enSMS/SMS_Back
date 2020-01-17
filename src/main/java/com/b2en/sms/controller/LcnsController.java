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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.GeneratingLcnsNo;
import com.b2en.sms.dto.autocompleteinfo.LcnsAC;
import com.b2en.sms.dto.toclient.GeneratedLcnsNo;
import com.b2en.sms.dto.toclient.LcnsDtoToClient;
import com.b2en.sms.entity.Lcns;
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
		if(entityList.size()==0) {
			return new ResponseEntity<List<LcnsDtoToClient>>(new ArrayList<LcnsDtoToClient>(), HttpStatus.OK);
		}
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
	
	@GetMapping(value = "/newest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LcnsAC> getNewest() {
		Lcns lcns = repositoryL.findNewest();
		if(lcns==null) {
			LcnsAC nothing = null;
			return new ResponseEntity<LcnsAC>(nothing, HttpStatus.OK);
		}
		LcnsAC lcnsAC = new LcnsAC();
		
		lcnsAC.setLcnsId(lcns.getLcnsId());
		lcnsAC.setPrdtNm(lcns.getPrdt().getPrdtNm());
		
		return new ResponseEntity<LcnsAC>(lcnsAC, HttpStatus.OK);
	}
	
	@PostMapping(value = "/generate")
	public GeneratedLcnsNo generateLcnsNo(@Valid @RequestBody GeneratingLcnsNo generatingLcnsNo, BindingResult result) {
		
		GeneratedLcnsNo generatedLcnsNo = new GeneratedLcnsNo();
		String prdtNm = generatingLcnsNo.getPrdtNm();
		String installDt = generatingLcnsNo.getInstallDt();
		if(result.hasErrors()) {
			generatedLcnsNo.setLcnsNo("FAILED");
			return generatedLcnsNo;
		}
		StringBuilder sb = new StringBuilder();
		String[] splitDate = installDt.split("-");
		if(prdtNm.contains("SDQ")) {
			sb.append("Q");
		} else if(prdtNm.contains("SMETA")) {
			sb.append("M");
		}
		sb.append(splitDate[1]);
		sb.append(splitDate[2]);
		sb.append(splitDate[0].substring(2, 4));
		sb.append("P");
		int prdtId = repositoryP.findPrdtIdByPrdtNm(prdtNm);
		String count = Integer.toString(repositoryL.countByPrdtId(prdtId)+1);
		if(count.length() > 3) {
			generatedLcnsNo.setLcnsNo("FAILED");
			return generatedLcnsNo;
		}
		for(int i = count.length()-1; i < 2; i++) {
			count = "0" + count;
		}
		sb.append(count);
		sb.append("0");

		generatedLcnsNo.setLcnsNo(sb.toString());
		return generatedLcnsNo;
	}
	
	/*
	 * @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<LcnsDtoToClient> findById(@PathVariable("id") int id) {
	 * 
	 * Lcns lcns = repositoryL.findById(id).orElse(null); if(lcns==null) {
	 * LcnsDtoToClient nothing = null; return new
	 * ResponseEntity<LcnsDtoToClient>(nothing, HttpStatus.OK); }
	 * 
	 * LcnsDtoToClient lcnsDtoToClient = modelMapper.map(lcns,
	 * LcnsDtoToClient.class);
	 * lcnsDtoToClient.setPrdtId(lcns.getPrdt().getPrdtId());
	 * lcnsDtoToClient.setPrdtNm(lcns.getPrdt().getPrdtNm());
	 * 
	 * return new ResponseEntity<LcnsDtoToClient>(lcnsDtoToClient, HttpStatus.OK); }
	 * 
	 * @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody LcnsDto
	 * lcns, BindingResult result) {
	 * 
	 * List<ResponseInfo> res = new ArrayList<ResponseInfo>();
	 * 
	 * if (result.hasErrors()) { res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
	 * List<ObjectError> errors = result.getAllErrors(); for (int i = 0; i <
	 * errors.size(); i++) { res.add(new
	 * ResponseInfo(errors.get(i).getDefaultMessage())); } return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * 
	 * Lcns lcnsEntity = modelMapper.map(lcns, Lcns.class);
	 * 
	 * int prdtId = lcns.getPrdtId(); Prdt prdt = repositoryP.getOne(prdtId);
	 * 
	 * lcnsEntity.setPrdt(prdt);
	 * 
	 * repositoryL.save(lcnsEntity);
	 * 
	 * res.add(new ResponseInfo("등록에 성공했습니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK); }
	 * 
	 * @DeleteMapping(value = "/{id}") public ResponseEntity<Void>
	 * delete(@PathVariable("id") int id) {
	 * 
	 * repositoryL.deleteById(id); return new
	 * ResponseEntity<Void>(HttpStatus.NO_CONTENT); }
	 * 
	 * @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int
	 * id, @Valid @RequestBody LcnsDto lcns, BindingResult result) {
	 * 
	 * List<ResponseInfo> res = new ArrayList<ResponseInfo>();
	 * 
	 * if(result.hasErrors()) { res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
	 * List<FieldError> errors = result.getFieldErrors(); for(int i = 0; i <
	 * errors.size(); i++) { res.add(new
	 * ResponseInfo(errors.get(i).getDefaultMessage())); } return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * Lcns toUpdate = repositoryL.findById(id).orElse(null);
	 * 
	 * if (toUpdate == null) { res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
	 * res.add(new ResponseInfo("해당 id를 가진 row가 없습니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * // LcnsChngHist가 자동 생성되는 부분 LcnsChngHist lcnsChngHist =
	 * modelMapper.map(toUpdate, LcnsChngHist.class); LcnsChngHistPK lcnsChngHistPK
	 * = new LcnsChngHistPK(); lcnsChngHistPK.setLcnsId(id);
	 * lcnsChngHist.setLcnsChngHistPK(lcnsChngHistPK);
	 * lcnsChngHist.setLcns(toUpdate); lcnsChngHist.setPrdt(toUpdate.getPrdt());
	 * 
	 * toUpdate.setLcnsNo(lcns.getLcnsNo());
	 * 
	 * repositoryL.save(toUpdate); repositoryLCH.save(lcnsChngHist);
	 * 
	 * res.add(new ResponseInfo("수정에 성공했습니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK); }
	 */
}
