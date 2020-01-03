package com.b2en.sms.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.DeleteDto;
import com.b2en.sms.dto.LcnsDtoTempVer;
import com.b2en.sms.dto.LcnsDtoTempVerForUpdate;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.TempVerAndLcnsDto;
import com.b2en.sms.dto.TempVerAndLcnsDtoForUpdate;
import com.b2en.sms.dto.toclient.TempVerAndLcnsDtoToClient;
import com.b2en.sms.dto.toclient.TempVerDtoToClient;
import com.b2en.sms.dto.toclient.TempVerHistDtoToClient;
import com.b2en.sms.entity.Lcns;
import com.b2en.sms.entity.LcnsChngHist;
import com.b2en.sms.entity.Prdt;
import com.b2en.sms.entity.TempVer;
import com.b2en.sms.entity.TempVerHist;
import com.b2en.sms.entity.pk.LcnsChngHistPK;
import com.b2en.sms.entity.pk.TempVerHistPK;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.CustRepository;
import com.b2en.sms.repo.LcnsChngHistRepository;
import com.b2en.sms.repo.LcnsRepository;
import com.b2en.sms.repo.PrdtRepository;
import com.b2en.sms.repo.TempVerHistRepository;
import com.b2en.sms.repo.TempVerRepository;

@RestController
@RequestMapping("/api/temp")
public class TempVerController {

	@Autowired
	private TempVerRepository repositoryTemp;
	@Autowired
	private TempVerHistRepository repositoryTempHist;
	@Autowired
	private CustRepository repositoryCust;
	@Autowired
	private LcnsRepository repositoryLcns;
	@Autowired
	private LcnsChngHistRepository repositoryLCH;
	@Autowired
	private B2enRepository repositoryB2en;
	@Autowired
	private PrdtRepository repositoryPrdt;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TempVerDtoToClient>> showAll() {

		List<TempVer> entityList = repositoryTemp.findAll();
		List<TempVerDtoToClient> list = new ArrayList<TempVerDtoToClient>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0; i < entityList.size(); i++) {
			TempVerDtoToClient tempVerDtoToClient = new TempVerDtoToClient();
			TempVer tempVer = entityList.get(i);
			tempVerDtoToClient.setTempVerId(tempVer.getTempVerId());
			tempVerDtoToClient.setOrgNm(tempVer.getCust().getOrg().getOrgNm());
			tempVerDtoToClient.setCustNm(tempVer.getCust().getCustNm());
			tempVerDtoToClient.setEmpNm(tempVer.getB2en().getEmpNm());
			tempVerDtoToClient.setMacAddr(tempVer.getMacAddr());
			tempVerDtoToClient.setRequestDate(sdf.format(tempVer.getRequestDate()));
			tempVerDtoToClient.setIssueReason(tempVer.getIssueReason());
			list.add(tempVerDtoToClient);
		}

		return new ResponseEntity<List<TempVerDtoToClient>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TempVerAndLcnsDtoToClient> findById(@PathVariable("id") int id) {
		
		TempVer tempVer = repositoryTemp.getOne(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		TempVerAndLcnsDtoToClient tempVerAndLcnsDtoToClient = new TempVerAndLcnsDtoToClient();
		tempVerAndLcnsDtoToClient.setTempVerId(tempVer.getTempVerId());
		tempVerAndLcnsDtoToClient.setCustId(tempVer.getCust().getCustId());
		tempVerAndLcnsDtoToClient.setEmpId(tempVer.getB2en().getEmpId());
		tempVerAndLcnsDtoToClient.setMacAddr(tempVer.getMacAddr());
		tempVerAndLcnsDtoToClient.setRequestDate(sdf.format(tempVer.getRequestDate()));
		tempVerAndLcnsDtoToClient.setIssueReason(tempVer.getIssueReason());
		
		return new ResponseEntity<TempVerAndLcnsDtoToClient>(tempVerAndLcnsDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody TempVerAndLcnsDto tempVerDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		// ========================== lcns 생성 =====================
		LcnsDtoTempVer[] lcnsDto = tempVerDto.getLcns();
		int lcnsNum = lcnsDto.length;
		Lcns[] lcnsEntity = new Lcns[lcnsNum];
		
		HashMap<Integer, Prdt> prdtMap = new HashMap<Integer, Prdt>();
		List<Prdt> prdtList = repositoryPrdt.findAll();
		for(int i = 0; i < prdtList.size(); i++) {
			prdtMap.put(prdtList.get(i).getPrdtId(), prdtList.get(i));
		}
		
		for(int i = 0; i < lcnsNum; i++) {
			lcnsEntity[i] = modelMapper.map(lcnsDto[i], Lcns.class);
			int prdtId = lcnsDto[i].getPrdtId();
			lcnsEntity[i].setPrdt(prdtMap.get(prdtId));
			
			lcnsEntity[i] = repositoryLcns.save(lcnsEntity[i]);
		}
		
		// ========================== temp 생성 ========================
		TempVer tempEntity = new TempVer();
		tempEntity.setCust(repositoryCust.getOne(tempVerDto.getCustId()));
		tempEntity.setB2en(repositoryB2en.getOne(tempVerDto.getEmpId()));
		tempEntity.setLcns(lcnsEntity[0]);
		tempEntity.setMacAddr(tempVerDto.getMacAddr());
		tempEntity.setRequestDate(Date.valueOf(tempVerDto.getRequestDate()));
		tempEntity.setIssueReason(tempVerDto.getIssueReason());
		
		repositoryTemp.save(tempEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "")
	public ResponseEntity<Void> delete(@RequestBody DeleteDto id) {
		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			repositoryTemp.deleteById(idx[i]);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody TempVerAndLcnsDtoForUpdate tempVerDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		TempVer toUpdate = repositoryTemp.getOne(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		// ================= tempVerHist 생성 =================
		TempVerHist tempVerHist = new TempVerHist();
		TempVerHistPK tempVerHistPK = new TempVerHistPK();
		Integer findMaxTempVerHistSeq = repositoryTempHist.findMaxTempVerHistSeq();
		int maxTempVerHistSeq = (findMaxTempVerHistSeq==null) ? 0 : findMaxTempVerHistSeq;// histSeq를 현존하는 가장 큰 histSeq값+1로 직접 할당하기 위한 변수
		tempVerHistPK.setTempVerId(toUpdate.getTempVerId());
		tempVerHistPK.setTempVerHistSeq(maxTempVerHistSeq+1);
		tempVerHist.setTempVerHistPK(tempVerHistPK);
		tempVerHist.setTempVer(toUpdate);
		tempVerHist.setCust(toUpdate.getCust());
		tempVerHist.setLcns(toUpdate.getLcns());
		tempVerHist.setB2en(toUpdate.getB2en());
		tempVerHist.setMacAddr(toUpdate.getMacAddr());
		
		repositoryTempHist.save(tempVerHist);
		
		// ================== lcns 수정 =====================
		LcnsDtoTempVerForUpdate[] lcnsDto = tempVerDto.getLcns();
		
		HashMap<Integer, Prdt> prdtMap = new HashMap<Integer, Prdt>();
		List<Prdt> prdtList = repositoryPrdt.findAll();
		for(int i = 0; i < prdtList.size(); i++) {
			prdtMap.put(prdtList.get(i).getPrdtId(), prdtList.get(i));
		}
		
		Lcns lcnsCheck = null;
		Lcns[] lcns = new Lcns[lcnsDto.length];
		for(int i = 0; i < lcnsDto.length; i++) {
			try {
				lcnsCheck = repositoryLcns.getOne(lcnsDto[i].getLcnsId());
			} catch(Exception e) {
				lcnsCheck = null;
			}
			if(lcnsCheck == null) { // 새로 생김
				lcns[i] = modelMapper.map(lcnsDto[i], Lcns.class);
				int prdtId = lcnsDto[i].getPrdtId();
				lcns[i].setPrdt(prdtMap.get(prdtId));
				lcns[i].setDelYn("N");
				lcns[i] = repositoryLcns.save(lcns[i]);
				
			} else { // 기존에 있던거 수정
				// 1. LcnsChngHist 생성
				lcns[i] = lcnsCheck;
				LcnsChngHist lcnsChngHist = modelMapper.map(lcns[i], LcnsChngHist.class);
				LcnsChngHistPK lcnsChngHistPK = new LcnsChngHistPK();
				Integer findMaxHistSeq = repositoryLCH.findMaxHistSeq();
				int maxHistSeq = (findMaxHistSeq==null) ? 0 : findMaxHistSeq;// histSeq를 현존하는 가장 큰 histSeq값+1로 직접 할당하기 위한 변수
				lcnsChngHistPK.setHistSeq(maxHistSeq+1);
				lcnsChngHistPK.setLcnsId(lcns[i].getLcnsId());
				lcnsChngHist.setLcnsChngHistPK(lcnsChngHistPK);
				lcnsChngHist.setLcns(lcns[i]);
				lcnsChngHist.setPrdt(lcns[i].getPrdt());
				repositoryLCH.save(lcnsChngHist);
				
				// 3. Lcns 수정
				int prdtId = lcnsDto[i].getPrdtId();
				Prdt prdt = repositoryPrdt.getOne(prdtId);
				lcns[i].setPrdt(prdt);
				lcns[i].setLcnsIssuDt(Date.valueOf(lcnsDto[i].getLcnsIssuDt()));
				lcns[i].setLcnsTpCd(lcnsDto[i].getLcnsTpCd());
				lcns[i].setLcnsStartDt(Date.valueOf(lcnsDto[i].getLcnsStartDt()));
				lcns[i].setLcnsEndDt(Date.valueOf(lcnsDto[i].getLcnsEndDt()));
				lcns[i] = repositoryLcns.save(lcns[i]);
			}
		}
		
		// ================== temp 수정 ====================
		toUpdate.setCust(repositoryCust.getOne(tempVerDto.getCustId()));
		toUpdate.setB2en(repositoryB2en.getOne(tempVerDto.getEmpId()));
		toUpdate.setLcns(lcns[0]);
		toUpdate.setMacAddr(tempVerDto.getMacAddr());
		toUpdate.setRequestDate(Date.valueOf(tempVerDto.getRequestDate()));
		toUpdate.setIssueReason(tempVerDto.getIssueReason());

		repositoryTemp.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value="/hist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TempVerHistDtoToClient>> findHistByTempVerId(@PathVariable("id") int id) {
		
		List<TempVerHist> tempVerHistList = repositoryTempHist.findByTempVerHistPKTempVerId(id);
		List<TempVerHistDtoToClient> list = new ArrayList<TempVerHistDtoToClient>();

		for(int i = 0; i < tempVerHistList.size(); i++) {
			TempVerHistDtoToClient tempVerHistDtoToClient = new TempVerHistDtoToClient();
			TempVerHist tempVerHist = tempVerHistList.get(i);
			tempVerHistDtoToClient.setCustNm(tempVerHist.getCust().getCustNm());
			tempVerHistDtoToClient.setLcnsNo(tempVerHist.getLcns().getLcnsNo());
			tempVerHistDtoToClient.setEmpNm(tempVerHist.getB2en().getEmpNm());
			tempVerHistDtoToClient.setMacAddr(tempVerHist.getMacAddr());
			
			list.add(tempVerHistDtoToClient);
		}
		
		return new ResponseEntity<List<TempVerHistDtoToClient>>(list, HttpStatus.OK);
	}
}
