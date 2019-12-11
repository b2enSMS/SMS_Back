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
import com.b2en.sms.dto.ContDto;
import com.b2en.sms.dto.ContDtoToClient;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.entity.B2en;
import com.b2en.sms.entity.Cont;
import com.b2en.sms.entity.ContChngHist;
import com.b2en.sms.entity.ContDetail;
import com.b2en.sms.entity.ContDetailHist;
import com.b2en.sms.entity.Lcns;
import com.b2en.sms.entity.Org;
import com.b2en.sms.entity.pk.ContChngHistPK;
import com.b2en.sms.entity.pk.ContDetailHistPK;
import com.b2en.sms.entity.pk.ContDetailPK;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.ContChngHistRepository;
import com.b2en.sms.repo.ContDetailHistRepository;
import com.b2en.sms.repo.ContDetailRepository;
import com.b2en.sms.repo.ContRepository;
import com.b2en.sms.repo.LcnsRepository;
import com.b2en.sms.repo.OrgRepository;

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
	private LcnsRepository repositoryL;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showincludedel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDtoToClient>> getAll() {
		// delYn의 값에 상관없이 모두 불러옴
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
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDtoToClient>> getAllNotDeleted() {
		// delYn의 값이 "N"인 경우(삭제된걸로 처리되지 않은 경우)만 불러옴
		List<Cont> entityList = repositoryC.findByDelYn("N");
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
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody ContDto contDto, BindingResult result) {
		
		log.debug("cont:{}", contDto);
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Cont contEntity = modelMapper.map(contDto, Cont.class);
		
		int orgId = contDto.getOrgId();
		Org org = repositoryO.findByOrgId(orgId);
		int empId = contDto.getEmpId();
		B2en b2en = repositoryB.findByEmpId(empId);
		
		contEntity.setOrg(org);
		contEntity.setB2en(b2en);
		contEntity.setDelYn("N");
		
		repositoryC.save(contEntity);
		
		// ContDetail 생성하는 부분
		int contId = repositoryC.findMaxContId(); // 가장 마지막에 생성된 Cont의 cont_id가 가장 크다
		Cont contInContDetail = repositoryC.findByContId(contId);
		int[] lcnsId = contDto.getLcnsId();
		String[] contAmt = contDto.getContAmt();
		
		int maxSeq; // contSeq를 현존하는 가장 큰 contSeq값+1로 직접 할당하기 위한 변수
		if(repositoryCD.findMaxContSeq()==null) {
			maxSeq = 0;
		} else {
			maxSeq = repositoryCD.findMaxContSeq();
		}
		
		for (int i = 0; i < lcnsId.length; i++) {
			ContDetailPK contDetailPK = new ContDetailPK();
			contDetailPK.setContSeq(maxSeq+i+1); // contSeq 직접 할당
			contDetailPK.setContId(contId);
			ContDetail contDetail = new ContDetail();
			Lcns lcns = repositoryL.findByLcnsId(lcnsId[i]);

			contDetail.setContDetailPK(contDetailPK);
			contDetail.setCont(contInContDetail);
			contDetail.setLcns(lcns);
			contDetail.setContAmt(contAmt[i]);
			contDetail.setDelYn("N");

			repositoryCD.save(contDetail);
		}

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		// Cont는 delete시 실제로 DB에서 삭제하지 않고 delYn이 "N"에서 "Y"로 변경되게 함
		Cont cont = repositoryC.findByContId(id);
		cont.setDelYn("Y");
		repositoryC.save(cont);
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
		
		toUpdate.setContReportNo(cont.getContReportNo());

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
	
	@DeleteMapping(value = "/detail/{id}")
	public ResponseEntity<Void> deleteDetail(@PathVariable("id") int id) {
		// ContDetail은 delete시 실제로 DB에서 삭제하지 않고 delYn이 "N"에서 "Y"로 변경되게 함
		ContDetail contDetail = repositoryCD.findByContDetailPKContSeq(id);
		contDetail.setDelYn("Y");
		repositoryCD.save(contDetail);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> updateDetail(@PathVariable("id") int id, @Valid @RequestBody ContDetailDto contDetailDto, BindingResult result) {
		
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
		
		// ContDetailHist가 자동 생성되는 부분
		ContDetailHist contDetailHist = new ContDetailHist();
		ContDetailHistPK contDetailHistPK = new ContDetailHistPK();
		ContDetailPK contDetailPK = toUpdate.getContDetailPK();
		contDetailHistPK.setContDetailPK(contDetailPK);
		contDetailHist.setContDetailHistPK(contDetailHistPK);
		contDetailHist.setContDetail(toUpdate);
		contDetailHist.setContAmt(toUpdate.getContAmt());
		contDetailHist.setLcns(toUpdate.getLcns());
		
		toUpdate.setContAmt(contDetailDto.getContAmt());
		
		repositoryCD.save(toUpdate);
		repositoryCDH.save(contDetailHist);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detail/hist/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDetailHist>> getAllDetailHist() {

		List<ContDetailHist> entityList = repositoryCDH.findAll();

		return new ResponseEntity<List<ContDetailHist>>(entityList, HttpStatus.OK);

	}
	
}
