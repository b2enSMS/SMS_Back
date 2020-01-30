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
import com.b2en.sms.dto.OrgDto;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.autocompleteinfo.OrgAC;
import com.b2en.sms.model.Org;
import com.b2en.sms.repo.ContRepository;
import com.b2en.sms.repo.CustRepository;
import com.b2en.sms.repo.OrgRepository;

@RestController
@RequestMapping("/api/org")
public class OrgController {

	@Autowired
	private OrgRepository repositoryOrg;
	
	@Autowired
	private CustRepository repositoryCust;
	
	@Autowired
	private ContRepository repositoryCont;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<OrgDto.Response>> showAll() {

		List<Org> entityList = repositoryOrg.findAllOrderByName();
		if(entityList.size()==0) {
			return new ResponseEntity<List<OrgDto.Response>>(new ArrayList<OrgDto.Response>(), HttpStatus.OK);
		}
		List<OrgDto.Response> list = modelMapper.map(entityList, new TypeToken<List<OrgDto.Response>>() {}.getType());

		return new ResponseEntity<List<OrgDto.Response>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value = "/aclist")
	public ResponseEntity<List<OrgAC>> acList() {

		List<Org> list = repositoryOrg.findAllOrderByName();
		if(list == null) {
			return new ResponseEntity<List<OrgAC>>(new ArrayList<OrgAC>(), HttpStatus.OK);
		}
		List<OrgAC> acList = new ArrayList<OrgAC>();
		
		for(int i = 0; i < list.size(); i++) {
			OrgAC orgAC = new OrgAC();
			orgAC.setOrgId(list.get(i).getOrgId());
			orgAC.setOrgNm(list.get(i).getOrgNm());
			acList.add(orgAC);
		}

		return new ResponseEntity<List<OrgAC>>(acList, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<OrgDto.Response> findById(@PathVariable("id") int id) {
		
		Org org = repositoryOrg.findById(id).orElse(null);
		if(org==null) {
			OrgDto.Response nothing = null;
			return new ResponseEntity<OrgDto.Response>(nothing, HttpStatus.OK);
		}
		
		OrgDto.Response orgDtoToClient = modelMapper.map(org, OrgDto.Response.class);
		
		return new ResponseEntity<OrgDto.Response>(orgDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody OrgDto.Request orgDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Org orgEntity = modelMapper.map(orgDto, Org.class);
		
		repositoryOrg.save(orgEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<List<ResponseInfo>> delete(@RequestBody DeleteDto id) {
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();

		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			if(!repositoryOrg.existsById(idx[i])) {
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(idx[i]+"의 id를 가지는 row가 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryCust.countByOrgId(idx[i])>0) {
				String orgNm = repositoryOrg.findById(idx[i]).orElse(null).getOrgNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(orgNm+"을(를) 참조하는 고객사담당자가 있습니다. 그 고객사담당자를 먼저 삭제해야 합니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryCont.countByOrgId(idx[i])>0) {
				String orgNm = repositoryOrg.findById(idx[i]).orElse(null).getOrgNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(orgNm+"을(를) 참조하는 계약이 있어 삭제할 수 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
		}
		for(int i = 0; i < idx.length; i++) {
			repositoryOrg.deleteById(idx[i]);
		}
		res.add(new ResponseInfo("삭제에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody OrgDto.Request orgDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Org toUpdate = repositoryOrg.findById(id).orElse(null);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		LocalDateTime createdDate = toUpdate.getCreatedDate();
		toUpdate = modelMapper.map(orgDto, Org.class);
		toUpdate.setOrgId(id);
		toUpdate.setCreatedDate(createdDate);

		repositoryOrg.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
