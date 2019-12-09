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

import com.b2en.sms.dto.ContDto;
import com.b2en.sms.dto.ContDtoToClient;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.entity.B2en;
import com.b2en.sms.entity.Cont;
import com.b2en.sms.entity.ContChngHist;
import com.b2en.sms.entity.ContDetail;
import com.b2en.sms.entity.ContDetailHist;
import com.b2en.sms.entity.Org;
import com.b2en.sms.entity.Prdt;
import com.b2en.sms.entity.pk.ContChngHistPK;
import com.b2en.sms.entity.pk.ContDetailHistPK;
import com.b2en.sms.entity.pk.ContDetailPK;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.ContChngHistRepository;
import com.b2en.sms.repo.ContDetailHistRepository;
import com.b2en.sms.repo.ContDetailRepository;
import com.b2en.sms.repo.ContRepository;
import com.b2en.sms.repo.OrgRepository;
import com.b2en.sms.repo.PrdtRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cont")
@Slf4j
public class ContController {
	
	@Autowired
	private ContRepository repositoryC;
	@Autowired
	private ContChngHistRepository repositoryCCH;
	@Autowired
	private ContDetailRepository repositoryCD;
	@Autowired
	private ContDetailHistRepository repositoryCDH;
	@Autowired
	private OrgRepository repositoryO;
	@Autowired
	private B2enRepository repositoryB;
	@Autowired
	private PrdtRepository repositoryP;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDtoToClient>> getAll() {

		List<Cont> entityList = repositoryC.findAll();
		List<ContDtoToClient> list;
		int orgId;
		String orgNm;
		int empId;
		String empNm;

		list = modelMapper.map(entityList, new TypeToken<List<ContDtoToClient>>() {
		}.getType());
		
		for(int i = 0; i < entityList.size(); i++) {
			orgId = entityList.get(i).getOrg().getOrgId();
			orgNm = entityList.get(i).getOrg().getOrgNm();
			empId = entityList.get(i).getB2en().getEmpId();
			empNm = entityList.get(i).getB2en().getEmpNm();
			list.get(i).setOrgId(orgId);
			list.get(i).setOrgNm(orgNm);
			list.get(i).setEmpId(empId);
			list.get(i).setEmpNm(empNm);
		}

		return new ResponseEntity<List<ContDtoToClient>>(list, HttpStatus.OK);

	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody ContDto cont, BindingResult result) {
		
		log.debug("cont:{}", cont);
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Cont contEntity = modelMapper.map(cont, Cont.class);
		
		int orgId = cont.getOrgId();
		Org org = repositoryO.findByOrgId(orgId);
		
		int empId = cont.getEmpId();
		B2en b2en = repositoryB.findByEmpId(empId);
		
		contEntity.setOrg(org);
		contEntity.setB2en(b2en);
		
		repositoryC.save(contEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/chng/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContChngHist>> getAllChng() {

		List<ContChngHist> entityList = repositoryCCH.findAll();

		return new ResponseEntity<List<ContChngHist>>(entityList, HttpStatus.OK);

	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {

		repositoryC.deleteByContId(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody ContDto cont, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Cont toUpdate = repositoryC.findByContId(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		// ContChngHist가 자동 생성되는 부분
		ContChngHist contChngHist = modelMapper.map(toUpdate, ContChngHist.class);
		ContChngHistPK contChngHistPK = new ContChngHistPK();
		contChngHistPK.setContId(id);
		contChngHist.setContChngHistPK(contChngHistPK);
		contChngHist.setCont(toUpdate);

		repositoryC.save(toUpdate);
		repositoryCCH.save(contChngHist);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detail/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDetail>> getAllDetail() {

		List<ContDetail> entityList = repositoryCD.findAll();

		return new ResponseEntity<List<ContDetail>>(entityList, HttpStatus.OK);

	}
	
	@GetMapping(value = "/detail/hist/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDetailHist>> getAllDetailHist() {

		List<ContDetailHist> entityList = repositoryCDH.findAll();

		return new ResponseEntity<List<ContDetailHist>>(entityList, HttpStatus.OK);

	}
	
}
