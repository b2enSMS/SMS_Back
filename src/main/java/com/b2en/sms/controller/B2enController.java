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

import com.b2en.sms.dto.B2enDto;
import com.b2en.sms.dto.DeleteDto;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.autocompleteinfo.B2enAC;
import com.b2en.sms.model.B2en;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.CmmnDetailCdRepository;
import com.b2en.sms.repo.ContRepository;
import com.b2en.sms.repo.MeetAttendEmpRepository;
import com.b2en.sms.repo.TempVerRepository;

@RestController
@RequestMapping("/api/b2en")
public class B2enController {
	
	@Autowired
	private B2enRepository repository;
	@Autowired
	private ContRepository repositoryC;
	@Autowired
	private TempVerRepository repositoryT;
	@Autowired
	private MeetAttendEmpRepository repositoryMAE;
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<B2enDto.Response>> showAll() {

		List<B2en> entityList = repository.findAllOrderByName();
		if(entityList.size()==0) {
			return new ResponseEntity<List<B2enDto.Response>>(new ArrayList<B2enDto.Response>(), HttpStatus.OK);
		}
		List<B2enDto.Response> list = modelMapper.map(entityList, new TypeToken<List<B2enDto.Response>>() {}.getType());

		return new ResponseEntity<List<B2enDto.Response>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value = "/aclist")
	public ResponseEntity<List<B2enAC>> acList() {

		List<B2en> list = repository.findAllOrderByName();
		if(list == null) {
			return new ResponseEntity<List<B2enAC>>(new ArrayList<B2enAC>(), HttpStatus.OK);
		}
		List<B2enAC> acList = new ArrayList<B2enAC>();
		
		for(int i = 0; i < list.size(); i++) {
			B2enAC b2enAC = new B2enAC();
			b2enAC.setEmpId(list.get(i).getEmpId());
			b2enAC.setEmpNm(list.get(i).getEmpNm());
			acList.add(b2enAC);
		}
		
		return new ResponseEntity<List<B2enAC>>(acList, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<B2enDto.Response> findById(@PathVariable("id") int id) {
		
		B2en b2en = repository.findById(id).orElse(null);
		if(b2en==null) {
			B2enDto.Response nothing = null;
			return new ResponseEntity<B2enDto.Response>(nothing, HttpStatus.OK);
		}
		
		B2enDto.Response b2enDtoToClient = modelMapper.map(b2en, B2enDto.Response.class);
		String empTpCdNm = repositoryCDC.findByCmmnDetailCdPKCmmnDetailCd(b2enDtoToClient.getEmpTpCd()).getCmmnDetailCdNm();
		b2enDtoToClient.setEmpTpCdNm(empTpCdNm);
		
		return new ResponseEntity<B2enDto.Response>(b2enDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody B2enDto.Request b2enDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		B2en b2enEntity = modelMapper.map(b2enDto, B2en.class);
		
		repository.save(b2enEntity);
		
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
			if(repositoryC.countByEmpId(idx[i])>0) {
				String empNm = repository.findById(idx[i]).orElse(null).getEmpNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(empNm+"을(를) 참조하는 계약이 있어 삭제할 수 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryT.countByEmpId(idx[i])>0) {
				String empNm = repository.findById(idx[i]).orElse(null).getEmpNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(empNm+"을(를) 참조하는 임시계약이 있어 삭제할 수 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryMAE.countByEmpId(idx[i])>0) {
				String empNm = repository.findById(idx[i]).orElse(null).getEmpNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(empNm+"을(를) 참조하는 미팅참가자목록이 있어 삭제할 수 없습니다."));
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
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody B2enDto.Request b2enDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		B2en toUpdate = repository.findById(id).orElse(null);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		LocalDateTime createdDate = toUpdate.getCreatedDate();
		toUpdate = modelMapper.map(b2enDto, B2en.class);
		toUpdate.setEmpId(id);
		toUpdate.setCreatedDate(createdDate);

		repository.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
