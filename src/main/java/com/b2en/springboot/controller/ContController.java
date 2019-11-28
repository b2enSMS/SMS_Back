package com.b2en.springboot.controller;

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

import com.b2en.springboot.dto.B2enDtoToClient;
import com.b2en.springboot.dto.ContDetailDto;
import com.b2en.springboot.dto.ContDto;
import com.b2en.springboot.dto.ContDtoToClient;
import com.b2en.springboot.dto.OrgDtoToClient;
import com.b2en.springboot.dto.ResponseInfo;
import com.b2en.springboot.entity.B2en;
import com.b2en.springboot.entity.Cont;
import com.b2en.springboot.entity.ContDetail;
import com.b2en.springboot.entity.Org;
import com.b2en.springboot.entity.Prdt;
import com.b2en.springboot.entity.pk.ContDetailPK;
import com.b2en.springboot.repo.B2enRepository;
import com.b2en.springboot.repo.ContDetailRepository;
import com.b2en.springboot.repo.ContRepository;
import com.b2en.springboot.repo.OrgRepository;
import com.b2en.springboot.repo.PrdtRepository;

@RestController
@RequestMapping("/api/cont")
public class ContController {
	
	@Autowired
	private ContRepository repositoryC;
	
	@Autowired
	private ContDetailRepository repositoryCD;
	
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
		OrgDtoToClient org;
		B2enDtoToClient b2en;

		list = modelMapper.map(entityList, new TypeToken<List<ContDtoToClient>>() {
		}.getType());
		
		for(int i = 0; i < entityList.size(); i++) {
			org = modelMapper.map(entityList.get(i).getOrg(), OrgDtoToClient.class);
			b2en = modelMapper.map(entityList.get(i).getB2en(), B2enDtoToClient.class);
			list.get(i).setOrg(org);
			list.get(i).setB2en(b2en);
		}

		return new ResponseEntity<List<ContDtoToClient>>(list, HttpStatus.OK);

	}

	@PostMapping(value = "/create")
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody ContDto cont, BindingResult result) {
		
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
		
		String orgId = cont.getOrgId();
		Org org = repositoryO.findByOrgId(orgId);
		
		String empId = cont.getEmpId();
		B2en b2en = repositoryB.findByEmpId(empId);
		
		contEntity.setOrg(org);
		contEntity.setB2en(b2en);
		
		repositoryC.save(contEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detail/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDetail>> getAllDetail() {

		List<ContDetail> entityList = repositoryCD.findAll();

		return new ResponseEntity<List<ContDetail>>(entityList, HttpStatus.OK);

	}
	
	@PostMapping(value = "/detail/create")
	public ResponseEntity<List<ResponseInfo>> createDetail(@RequestBody ContDetailDto contDetail) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		ContDetail contDetailEntity = modelMapper.map(contDetail, ContDetail.class);
		
		int contSeq = contDetail.getContSeq();
		
		String contId = contDetail.getContId();
		Cont cont = repositoryC.findByContId(contId);
		
		String prdtId = contDetail.getPrdtId();
		Prdt prdt = repositoryP.findByPrdtId(prdtId);
		
		ContDetailPK contDetailPK = new ContDetailPK();
		contDetailPK.setContSeq(contSeq);
		contDetailPK.setCont(cont);
		
		contDetailEntity.setContDetailPK(contDetailPK);
		contDetailEntity.setPrdt(prdt);
		
		repositoryCD.save(contDetailEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
