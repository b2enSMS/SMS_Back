package com.b2en.sms.controller;

import java.util.ArrayList;
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
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.TempVerDto;
import com.b2en.sms.dto.toclient.LcnsDtoToClientTempVer;
import com.b2en.sms.dto.toclient.TempVerAndLcnsDtoToClient;
import com.b2en.sms.dto.toclient.TempVerDtoToClient;
import com.b2en.sms.dto.toclient.TempVerHistDtoToClient;
import com.b2en.sms.entity.TempVer;
import com.b2en.sms.entity.TempVerHist;
import com.b2en.sms.entity.pk.TempVerHistPK;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.CustRepository;
import com.b2en.sms.repo.LcnsRepository;
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
	private B2enRepository repositoryB2en;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TempVerDtoToClient>> showAll() {

		List<TempVer> entityList = repositoryTemp.findAll();
		List<TempVerDtoToClient> list = new ArrayList<TempVerDtoToClient>();
		
		for(int i = 0; i < entityList.size(); i++) {
			TempVerDtoToClient tempVerDtoToClient = new TempVerDtoToClient();
			TempVer tempVer = entityList.get(i);
			tempVerDtoToClient.setTempVerId(tempVer.getTempVerId());
			tempVerDtoToClient.setCustId(tempVer.getCust().getCustId());
			tempVerDtoToClient.setCustNm(tempVer.getCust().getCustNm());
			tempVerDtoToClient.setLcnsId(tempVer.getLcns().getLcnsId());
			tempVerDtoToClient.setLcnsNo(tempVer.getLcns().getLcnsNo());
			tempVerDtoToClient.setEmpId(tempVer.getB2en().getEmpId());
			tempVerDtoToClient.setEmpNm(tempVer.getB2en().getEmpNm());
			tempVerDtoToClient.setMacAddr(tempVer.getMacAddr());
			list.add(tempVerDtoToClient);
		}

		return new ResponseEntity<List<TempVerDtoToClient>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TempVerAndLcnsDtoToClient> findById(@PathVariable("id") int id) {
		
		TempVer tempVer = repositoryTemp.getOne(id);
		
		TempVerAndLcnsDtoToClient tempVerAndLcnsDtoToClient = new TempVerAndLcnsDtoToClient();
		tempVerAndLcnsDtoToClient.setTempVerId(tempVer.getTempVerId());
		tempVerAndLcnsDtoToClient.setCustId(tempVer.getCust().getCustId());
		tempVerAndLcnsDtoToClient.setCustNm(tempVer.getCust().getCustNm());
		LcnsDtoToClientTempVer lcnsDto = new LcnsDtoToClientTempVer();
		lcnsDto = modelMapper.map(tempVer.getLcns(), LcnsDtoToClientTempVer.class);
		lcnsDto.setPrdtId(tempVer.getLcns().getPrdt().getPrdtId());
		lcnsDto.setPrdtNm(tempVer.getLcns().getPrdt().getPrdtNm());
		tempVerAndLcnsDtoToClient.setLcns(lcnsDto);
		tempVerAndLcnsDtoToClient.setEmpId(tempVer.getB2en().getEmpId());
		tempVerAndLcnsDtoToClient.setEmpNm(tempVer.getB2en().getEmpNm());
		tempVerAndLcnsDtoToClient.setMacAddr(tempVer.getMacAddr());
		
		return new ResponseEntity<TempVerAndLcnsDtoToClient>(tempVerAndLcnsDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody TempVerDto tempVerDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		TempVer tempEntity = new TempVer();
		tempEntity.setCust(repositoryCust.getOne(tempVerDto.getCustId()));
		tempEntity.setLcns(repositoryLcns.getOne(tempVerDto.getLcnsId()));
		tempEntity.setB2en(repositoryB2en.getOne(tempVerDto.getEmpId()));
		tempEntity.setMacAddr(tempVerDto.getMacAddr());
		
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
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody TempVerDto tempVerDto, BindingResult result) {
		
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
		
		TempVerHist tempVerHist = new TempVerHist();
		TempVerHistPK tempVerHistPK = new TempVerHistPK();
		tempVerHistPK.setTempVerId(toUpdate.getTempVerId());
		tempVerHist.setTempVerHistPK(tempVerHistPK);
		tempVerHist.setTempVer(toUpdate);
		tempVerHist.setCust(toUpdate.getCust());
		tempVerHist.setLcns(toUpdate.getLcns());
		tempVerHist.setB2en(toUpdate.getB2en());
		tempVerHist.setMacAddr(toUpdate.getMacAddr());
		
		repositoryTempHist.save(tempVerHist);
		
		toUpdate.setCust(repositoryCust.getOne(tempVerDto.getCustId()));
		toUpdate.setLcns(repositoryLcns.getOne(tempVerDto.getLcnsId()));
		toUpdate.setB2en(repositoryB2en.getOne(tempVerDto.getEmpId()));
		toUpdate.setMacAddr(tempVerDto.getMacAddr());

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
