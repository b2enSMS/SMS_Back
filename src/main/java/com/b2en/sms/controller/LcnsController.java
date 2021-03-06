package com.b2en.sms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.GeneratingLcnsNo;
import com.b2en.sms.dto.LcnsDtoNew;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.autocompleteinfo.LcnsAC;
import com.b2en.sms.model.Lcns;
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
	
	@GetMapping
	public ResponseEntity<List<LcnsDtoNew.Response>> showAll() {

		List<Lcns> entityList = repositoryL.findAll();
		if(entityList.size()==0) {
			return new ResponseEntity<List<LcnsDtoNew.Response>>(new ArrayList<LcnsDtoNew.Response>(), HttpStatus.OK);
		}
		List<LcnsDtoNew.Response> list;
		int prdtId;
		String prdtNm;

		list = modelMapper.map(entityList, new TypeToken<List<LcnsDtoNew.Response>>() {}.getType());

		for(int i = 0; i < entityList.size(); i++) {
			prdtId = entityList.get(i).getPrdt().getPrdtId();
			prdtNm = entityList.get(i).getPrdt().getPrdtNm();
			list.get(i).setPrdtId(prdtId);
			list.get(i).setPrdtNm(prdtNm);
		}
		
		return new ResponseEntity<List<LcnsDtoNew.Response>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value = "/newest")
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
	public ResponseEntity<List<ResponseInfo>> generateLcnsNo(@Valid @RequestBody GeneratingLcnsNo generatingLcnsNo, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		String prdtNm = generatingLcnsNo.getPrdtNm();
		String installDt = generatingLcnsNo.getInstallDt();
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 라이센스번호 생성에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Integer prdtId = repositoryP.findPrdtIdByPrdtNm(prdtNm);
		if(prdtId==null) {
			res.add(new ResponseInfo("다음의 문제로 라이센스번호 생성에 실패했습니다: "));
			res.add(new ResponseInfo("해당하는 제품명을 가진 제품이 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		StringBuilder sb = new StringBuilder();
		if(prdtNm.contains("SDQ")) {
			sb.append("Q");
		} else if(prdtNm.contains("SMETA")) {
			sb.append("M");
		}
		
		String[] splitDate = installDt.split("-");
		if(splitDate[1].length()==1) {
			splitDate[1] = "0" + splitDate[1];
		}
		sb.append(splitDate[1]); // 월
		if(splitDate[2].length()==1) {
			splitDate[2] = "0" + splitDate[2];
		}
		sb.append(splitDate[2]); // 일
		sb.append(splitDate[0].substring(2, 4)); // 년
		
		sb.append("P");

		String count = Integer.toString(repositoryL.countByPrdtId(prdtId)+1);
		if(count.length() > 3) {
			res.add(new ResponseInfo("다음의 문제로 라이센스번호 생성에 실패했습니다: "));
			res.add(new ResponseInfo("발행순서번호가 3자리를 초과하게 되었습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		for(int i = count.length()-1; i < 2; i++) {
			count = "0" + count;
		}
		sb.append(count);
		sb.append("0");

		res.add(new ResponseInfo(sb.toString()));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
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
