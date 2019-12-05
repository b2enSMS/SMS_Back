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

import com.b2en.sms.dto.MeetAttendCustDto;
import com.b2en.sms.dto.MeetAttendEmpDto;
import com.b2en.sms.dto.MeetDto;
import com.b2en.sms.dto.MeetDtoToClient;
import com.b2en.sms.dto.OrgDtoToClient;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.entity.B2en;
import com.b2en.sms.entity.Cust;
import com.b2en.sms.entity.Meet;
import com.b2en.sms.entity.MeetAttendCust;
import com.b2en.sms.entity.MeetAttendEmp;
import com.b2en.sms.entity.Org;
import com.b2en.sms.entity.pk.MeetAttendCustPK;
import com.b2en.sms.entity.pk.MeetAttendEmpPK;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.CustRepository;
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
	private CustRepository repositoryC;
	
	@Autowired
	private B2enRepository repositoryB;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetDtoToClient>> showAll() {

		List<Meet> entityList = repositoryM.findAll();
		List<MeetDtoToClient> list;
		OrgDtoToClient org;

		list = modelMapper.map(entityList, new TypeToken<List<MeetDtoToClient>>() {
		}.getType());

		for(int i = 0; i < entityList.size(); i++) {
			org = modelMapper.map(entityList.get(i).getOrg(), OrgDtoToClient.class);
			list.get(i).setOrg(org);
		}
		
		return new ResponseEntity<List<MeetDtoToClient>>(list, HttpStatus.OK);

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
		
		String orgId = meet.getOrgId(); // 해당 id를 갖는 Org 찾아서 저장
		Org org = repositoryO.findByOrgId(orgId);
		
		meetEntity.setOrg(org);
		
		repositoryM.save(meetEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {

		repositoryM.deleteByMeetId(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") String id, @Valid @RequestBody MeetDto meet, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Meet toUpdate = repositoryM.findByMeetId(id);

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
	
	@PostMapping(value = "/attend/cust/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> createAttendCust(@RequestBody MeetAttendCustDto meetAttendCust) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		int custSeq = meetAttendCust.getCustSeq();

		String meetId = meetAttendCust.getMeetId();
		Meet meet = repositoryM.findByMeetId(meetId);

		String custId = meetAttendCust.getCustId();
		Cust cust = repositoryC.findByCustId(custId);

		MeetAttendCustPK meetAttendCustPK = new MeetAttendCustPK();
		meetAttendCustPK.setCustSeq(custSeq);
		meetAttendCustPK.setMeetId(meetId);
		
		MeetAttendCust meetAttendCustEntity = new MeetAttendCust();

		meetAttendCustEntity.setMeetAttendCustPK(meetAttendCustPK);
		meetAttendCustEntity.setMeet(meet);
		meetAttendCustEntity.setCust(cust);
		
		repositoryMAC.save(meetAttendCustEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/attend/cust/{id}")
	public ResponseEntity<Void> deleteAttendCust(@PathVariable("id") int id) {

		repositoryMAC.deleteByMeetAttendCustPKCustSeq(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/attend/cust/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> updateAttendCust(@PathVariable("id") int id, @RequestBody MeetAttendCustDto meetAttendCust, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		MeetAttendCust toUpdate = repositoryMAC.findByMeetAttendCustPKCustSeq(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}

		repositoryMAC.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/attend/b2en/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetAttendEmp>> showAllAttendEmp() {

		List<MeetAttendEmp> entityList = repositoryMAE.findAll();
		
		return new ResponseEntity<List<MeetAttendEmp>>(entityList, HttpStatus.OK);

	}
	
	@PostMapping(value = "/attend/b2en/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> createAttendEmp(@RequestBody MeetAttendEmpDto meetAttendEmp) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		int empSeq = meetAttendEmp.getEmpSeq();

		String meetId = meetAttendEmp.getMeetId();
		Meet meet = repositoryM.findByMeetId(meetId);

		String empId = meetAttendEmp.getEmpId();
		B2en b2en = repositoryB.findByEmpId(empId);

		MeetAttendEmpPK meetAttendEmpPK = new MeetAttendEmpPK();
		meetAttendEmpPK.setEmpSeq(empSeq);
		meetAttendEmpPK.setMeetId(meetId);
		
		MeetAttendEmp meetAttendEmpEntity = new MeetAttendEmp();

		meetAttendEmpEntity.setMeetAttendEmpPK(meetAttendEmpPK);
		meetAttendEmpEntity.setMeet(meet);
		meetAttendEmpEntity.setB2en(b2en);
		
		repositoryMAE.save(meetAttendEmpEntity);

		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/attend/b2en/{id}")
	public ResponseEntity<Void> deleteAttendEmp(@PathVariable("id") int id) {

		repositoryMAE.deleteByMeetAttendEmpPKEmpSeq(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/attend/b2en/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> updateAttendEmp(@PathVariable("id") int id, @RequestBody MeetAttendEmpDto meetAttendEmp, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		MeetAttendEmp toUpdate = repositoryMAE.findByMeetAttendEmpPKEmpSeq(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}

		repositoryMAE.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
