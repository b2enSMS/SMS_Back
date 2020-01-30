package com.b2en.sms.controller;

import java.time.LocalDateTime;
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
import com.b2en.sms.model.Prdt;
import com.b2en.sms.repo.CmmnDetailCdRepository;
import com.b2en.sms.repo.LcnsRepository;
import com.b2en.sms.repo.PrdtRepository;

@RestController
@RequestMapping("/api/prdt")
public class PrdtController {
	
	@Autowired
	private PrdtRepository repository;
	
	@Autowired
	private LcnsRepository repositoryL;
	
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<PrdtDto.Response>> showAll() {

		List<Prdt> entityList = repository.findAll();
		if(entityList.size()==0) {
			return new ResponseEntity<List<PrdtDto.Response>>(new ArrayList<PrdtDto.Response>(), HttpStatus.OK);
		}
		List<PrdtDto.Response> list;
		
		list = modelMapper.map(entityList, new TypeToken<List<PrdtDto.Response>>() {
		}.getType());

		return new ResponseEntity<List<PrdtDto.Response>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value = "/aclist")
	public ResponseEntity<List<PrdtACInterface>> acList() {

		List<PrdtACInterface> list = repository.findAllBy();
		if(list == null) {
			return new ResponseEntity<List<PrdtACInterface>>(new ArrayList<PrdtACInterface>(), HttpStatus.OK);
		}

		return new ResponseEntity<List<PrdtACInterface>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<PrdtDto.Response> findById(@PathVariable("id") int id) {
		
		Prdt prdt = repository.findById(id).orElse(null);
		if(prdt==null) {
			PrdtDto.Response nothing = null;
			return new ResponseEntity<PrdtDto.Response>(nothing, HttpStatus.OK);
		}
		
		PrdtDto.Response prdtDtoToClient = modelMapper.map(prdt, PrdtDto.Response.class);
		prdtDtoToClient.setPrdtTpCdNm(repositoryCDC.findByCmmnDetailCdPKCmmnDetailCd(prdtDtoToClient.getPrdtTpCd()).getCmmnDetailCdNm());
		
		return new ResponseEntity<PrdtDto.Response>(prdtDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody PrdtDto.Request prdt, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
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
	
	@DeleteMapping
	public ResponseEntity<List<ResponseInfo>> delete(@RequestBody DeleteDto id) {
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			if(!repository.existsById(idx[i])) {
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(idx[i]+"의 id를 가지는 row가 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryL.countByPrdtId(idx[i])>0) {
				String prdtNm = repository.findById(idx[i]).orElse(null).getPrdtNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(prdtNm+"을(를) 참조하는 라이센스가 있습니다. 그 라이센스를 먼저 삭제해야 합니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
		}
		
		for(int i = 0; i < idx.length; i++) {
			repository.deleteById(idx[i]);
		}
		res.add(new ResponseInfo("삭제에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody PrdtDto.Request prdtDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
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
		
		LocalDateTime createdDate = toUpdate.getCreatedDate();
		toUpdate = modelMapper.map(prdtDto, Prdt.class);
		toUpdate.setPrdtId(id);
		toUpdate.setCreatedDate(createdDate);
		
		repository.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
