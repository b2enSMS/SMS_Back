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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.DeleteDto;
import com.b2en.sms.dto.PrdtDto;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.autocompleteinfo.PrdtACInterface;
import com.b2en.sms.dto.toclient.PrdtDtoToClient;
import com.b2en.sms.entity.Prdt;
import com.b2en.sms.repo.CmmnDetailCdRepository;
import com.b2en.sms.repo.PrdtRepository;

@RestController
@RequestMapping("/api/prdt")
public class PrdtController {
	
	@Autowired
	private PrdtRepository repository;
	
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PrdtDtoToClient>> showAll() {

		List<Prdt> entityList = repository.findAll();
		if(entityList.size()==0) {
			return new ResponseEntity<List<PrdtDtoToClient>>(new ArrayList<PrdtDtoToClient>(), HttpStatus.OK);
		}
		List<PrdtDtoToClient> list;
		
		list = modelMapper.map(entityList, new TypeToken<List<PrdtDtoToClient>>() {
		}.getType());

		return new ResponseEntity<List<PrdtDtoToClient>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value = "/aclist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PrdtACInterface>> acList() {

		List<PrdtACInterface> list = repository.findAllBy();
		if(list == null) {
			return new ResponseEntity<List<PrdtACInterface>>(new ArrayList<PrdtACInterface>(), HttpStatus.OK);
		}

		return new ResponseEntity<List<PrdtACInterface>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PrdtDtoToClient> findById(@PathVariable("id") int id) {
		
		Prdt prdt = repository.findById(id).orElse(null);
		if(prdt==null) {
			PrdtDtoToClient nothing = null;
			return new ResponseEntity<PrdtDtoToClient>(nothing, HttpStatus.OK);
		}
		
		PrdtDtoToClient prdtDtoToClient = modelMapper.map(prdt, PrdtDtoToClient.class);
		prdtDtoToClient.setPrdtTpCdNm(repositoryCDC.findByCmmnDetailCdPKCmmnDetailCd(prdtDtoToClient.getPrdtTpCd()).getCmmnDetailCdNm());
		
		return new ResponseEntity<PrdtDtoToClient>(prdtDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody PrdtDto prdt, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Prdt prdtEntity = modelMapper.map(prdt, Prdt.class);
		
		repository.save(prdtEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "")
	public ResponseEntity<List<ResponseInfo>> delete(@RequestBody DeleteDto id) {
		boolean deleteFlag = true;
		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			if(!repository.existsById(idx[i])) {
				deleteFlag = false;
				continue;
			}
			repository.deleteById(idx[i]);
		}
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		if(deleteFlag) {
			res.add(new ResponseInfo("삭제에 성공했습니다."));
		} else {
			res.add(new ResponseInfo("삭제 도중 문제가 발생했습니다."));
		}
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody PrdtDto prdtDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		 
		
		Prdt toUpdate = repository.findById(id).orElse(null);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		toUpdate.setPrdtNm(prdtDto.getPrdtNm());
		toUpdate.setPrdtDesc(prdtDto.getPrdtDesc());
		toUpdate.setPrdtAmt(prdtDto.getPrdtAmt());
		toUpdate.setPrdtTpCd(prdtDto.getPrdtTpCd());
		
		repository.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
