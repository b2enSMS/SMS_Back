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

import com.b2en.sms.dto.ContDetailDto;
import com.b2en.sms.dto.ContDetailHistDto;
import com.b2en.sms.dto.ContDto;
import com.b2en.sms.dto.ContDtoToClient;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.entity.B2en;
import com.b2en.sms.entity.Cont;
import com.b2en.sms.entity.ContDetail;
import com.b2en.sms.entity.ContDetailHist;
import com.b2en.sms.entity.Org;
import com.b2en.sms.entity.Prdt;
import com.b2en.sms.entity.pk.ContDetailHistPK;
import com.b2en.sms.entity.pk.ContDetailPK;
import com.b2en.sms.repo.B2enRepository;
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
		String orgId;
		String orgNm;
		String empId;
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
		
		log.info("cont:{}", cont);
		
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
		
		String orgNm = cont.getOrgNm();
		Org org = repositoryO.findByOrgNm(orgNm);
		
		String empNm = cont.getEmpNm();
		B2en b2en = repositoryB.findByEmpId(empNm);
		
		contEntity.setOrg(org);
		contEntity.setB2en(b2en);
		
		repositoryC.save(contEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {

		repositoryC.deleteByContId(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") String id, @Valid @RequestBody ContDto cont, BindingResult result) {
		
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

		repositoryC.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detail/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDetail>> getAllDetail() {

		List<ContDetail> entityList = repositoryCD.findAll();

		return new ResponseEntity<List<ContDetail>>(entityList, HttpStatus.OK);

	}
	
	@PostMapping(value = "/detail/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> createDetail(@RequestBody ContDetailDto contDetail) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		ContDetail contDetailEntity = modelMapper.map(contDetail, ContDetail.class);;
		
		
		int contSeq = contDetail.getContSeq();

		String contId = contDetail.getContId();
		Cont cont = repositoryC.findByContId(contId);

		String prdtId = contDetail.getPrdtId();
		Prdt prdt = repositoryP.findByPrdtId(prdtId);

		ContDetailPK contDetailPK = new ContDetailPK();
		contDetailPK.setContSeq(contSeq);
		contDetailPK.setContId(contId);

		contDetailEntity.setContDetailPK(contDetailPK);
		contDetailEntity.setCont(cont);
		contDetailEntity.setPrdt(prdt);
		
		repositoryCD.save(contDetailEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/detail/{id}")
	public ResponseEntity<Void> deleteDetail(@PathVariable("id") int id) {

		repositoryCD.deleteByContDetailPKContSeq(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> updateDetail(@PathVariable("id") int id, @Valid @RequestBody ContDetailDto contDetail, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		ContDetail toUpdate = repositoryCD.findByContDetailPKContSeq(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}

		repositoryCD.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detail/hist/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDetailHist>> getAllDetailHist() {

		List<ContDetailHist> entityList = repositoryCDH.findAll();

		return new ResponseEntity<List<ContDetailHist>>(entityList, HttpStatus.OK);

	}
	
	@PostMapping(value = "/detail/hist/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> createDetailHist(@RequestBody ContDetailHistDto contDetailHist) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		ContDetailHist contDetailHistEntity = modelMapper.map(contDetailHist, ContDetailHist.class);;
		
		int contSeq = contDetailHist.getContSeq();
		
		int detailSeq = contDetailHist.getDetailSeq();

		String contId = contDetailHist.getContId();

		String prdtId = contDetailHist.getPrdtId();
		Prdt prdt = repositoryP.findByPrdtId(prdtId);

		ContDetailPK contDetailPK = new ContDetailPK();
		contDetailPK.setContId(contId);
		contDetailPK.setContSeq(contSeq);
		
		ContDetailHistPK contDetailHistPK = new ContDetailHistPK();
		contDetailHistPK.setDetailSeq(detailSeq);
		contDetailHistPK.setContDetailPK(contDetailPK);

		ContDetail contDetail = repositoryCD.findByContDetailPKContSeq(contSeq);
		contDetailHistEntity.setContDetailHistPK(contDetailHistPK);
		contDetailHistEntity.setContDetail(contDetail);
		contDetailHistEntity.setPrdt(prdt);
		
		repositoryCDH.save(contDetailHistEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/detail/hist/{id}")
	public ResponseEntity<Void> deleteDetailHist(@PathVariable("id") int id) {

		repositoryCDH.deleteByContDetailHistPKDetailSeq(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/detail/hist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> updateDetailHist(@PathVariable("id") int id, @Valid @RequestBody ContDetailHistDto contDetailHist, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		ContDetailHist toUpdate = repositoryCDH.findByContDetailHistPKDetailSeq(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}

		repositoryCDH.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
}
