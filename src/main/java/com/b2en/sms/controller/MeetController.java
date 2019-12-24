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

import com.b2en.sms.dto.MeetDto;
import com.b2en.sms.dto.MeetDtoToClient;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.entity.Meet;
import com.b2en.sms.entity.MeetAttendCust;
import com.b2en.sms.entity.MeetAttendEmp;
import com.b2en.sms.entity.Org;
import com.b2en.sms.repo.MeetAttendCustRepository;
import com.b2en.sms.repo.MeetAttendEmpRepository;
import com.b2en.sms.repo.MeetRepository;
import com.b2en.sms.repo.OrgRepository;

@RestController
@RequestMapping("/api/meet")
public class MeetController {
	
	@Autowired
	private MeetRepository repositoryM;
	
	@Autowired
	private MeetAttendCustRepository repositoryMAC;
	
	@Autowired
	private MeetAttendEmpRepository repositoryMAE;
	
	@Autowired
	private OrgRepository repositoryO;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetDtoToClient>> showAll() {

		List<Meet> entityList = repositoryM.findAll();
		List<MeetDtoToClient> list;
		int orgId;
		String orgNm;

		list = modelMapper.map(entityList, new TypeToken<List<MeetDtoToClient>>() {
		}.getType());

		for(int i = 0; i < entityList.size(); i++) {
			orgId = entityList.get(i).getOrg().getOrgId();
			orgNm = entityList.get(i).getOrg().getOrgNm();
			list.get(i).setOrgId(orgId);
			list.get(i).setOrgNm(orgNm);
		}
		
		return new ResponseEntity<List<MeetDtoToClient>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MeetDtoToClient> findById(@PathVariable("id") int id) {
		
		Meet meet = repositoryM.getOne(id);
		
		MeetDtoToClient meetDtoToClient = modelMapper.map(meet, MeetDtoToClient.class);
		meetDtoToClient.setOrgId(meet.getOrg().getOrgId());
		meetDtoToClient.setOrgNm(meet.getOrg().getOrgNm());
		
		return new ResponseEntity<MeetDtoToClient>(meetDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody MeetDto meet, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		 
		
		Meet meetEntity = modelMapper.map(meet, Meet.class);
		
		int orgId = meet.getOrgId(); // 해당 id를 갖는 Org 찾아서 저장
		Org org = repositoryO.getOne(orgId);
		
		meetEntity.setOrg(org);
		
		repositoryM.save(meetEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {

		repositoryM.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody MeetDto meet, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Meet toUpdate = repositoryM.getOne(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		String meetCnt = meet.getMeetCnt();

		toUpdate.setMeetCnt(meetCnt);

		repositoryM.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/attend/cust/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetAttendCust>> showAllAttendCust() {

		List<MeetAttendCust> entityList = repositoryMAC.findAll();
		
		return new ResponseEntity<List<MeetAttendCust>>(entityList, HttpStatus.OK);

	}
	
	@GetMapping(value = "/attend/b2en/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetAttendEmp>> showAllAttendEmp() {

		List<MeetAttendEmp> entityList = repositoryMAE.findAll();
		
		return new ResponseEntity<List<MeetAttendEmp>>(entityList, HttpStatus.OK);

	}
}
