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
import com.b2en.sms.dto.MeetDto;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.toclient.MeetAttendCustDtoToClient;
import com.b2en.sms.dto.toclient.MeetAttendEmpDtoToClient;
import com.b2en.sms.dto.toclient.MeetDtoToClient;
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
		List<MeetAttendCust> meetAttendCust = repositoryMAC.findByMeetAttendCustPKMeetId(id);
		List<MeetAttendEmp> meetAttendEmp = repositoryMAE.findByMeetAttendEmpPKMeetId(id);
		MeetAttendCustDtoToClient[] meetAttendCustDtoToClientList = new MeetAttendCustDtoToClient[meetAttendCust.size()];
		MeetAttendEmpDtoToClient[] meetAttendEmpDtoToClientList = new MeetAttendEmpDtoToClient[meetAttendEmp.size()];
		
		for(int i = 0; i < meetAttendCust.size(); i++) {
			MeetAttendCustDtoToClient meetAttendCustDtoToClient = new MeetAttendCustDtoToClient();
			meetAttendCustDtoToClient.setCustId(meetAttendCust.get(i).getCust().getCustId());
			meetAttendCustDtoToClient.setCustNm(meetAttendCust.get(i).getCust().getCustNm());
			meetAttendCustDtoToClientList[i] = meetAttendCustDtoToClient;
		}
		for(int i = 0; i < meetAttendEmp.size(); i++) {
			MeetAttendEmpDtoToClient meetAttendEmpDtoToClient = new MeetAttendEmpDtoToClient();
			meetAttendEmpDtoToClient.setEmpId(meetAttendEmp.get(i).getB2en().getEmpId());
			meetAttendEmpDtoToClient.setEmpNm(meetAttendEmp.get(i).getB2en().getEmpNm());
			meetAttendEmpDtoToClientList[i] = meetAttendEmpDtoToClient;
		}
		
		meetDtoToClient.setMeetAttendCust(meetAttendCustDtoToClientList);
		meetDtoToClient.setMeetAttendEmp(meetAttendEmpDtoToClientList);
		
		return new ResponseEntity<MeetDtoToClient>(meetDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody MeetDto meetDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		 
		
		Meet meetEntity = modelMapper.map(meetDto, Meet.class);
		
		int orgId = meetDto.getOrgId();
		Org org = repositoryO.getOne(orgId);
		
		meetEntity.setOrg(org);
		
		meetEntity = repositoryM.save(meetEntity);
		
		int[] custId = meetDto.getCustId();
		int[] empId = meetDto.getEmpId();
		int custSeq = (repositoryMAC.findMaxCustSeq()==null) ? 0 : repositoryMAC.findMaxCustSeq();// custSeq를 현존하는 가장 큰 custSeq값+1로 직접 할당하기 위한 변수
		int empSeq = (repositoryMAE.findMaxEmpSeq()==null) ? 0 : repositoryMAE.findMaxEmpSeq();// empSeq를 현존하는 가장 큰 empSeq값+1로 직접 할당하기 위한 변수
		for(int i = 0; i < custId.length; i++) {
			MeetAttendCust meetAttendCust = new MeetAttendCust();
			MeetAttendCustPK meetAttendCustPK = new MeetAttendCustPK();
			Cust cust = repositoryC.getOne(custId[i]);
			meetAttendCustPK.setCustSeq(custSeq+i+1);
			meetAttendCustPK.setMeetId(meetEntity.getMeetId());
			meetAttendCust.setMeetAttendCustPK(meetAttendCustPK);
			meetAttendCust.setMeet(meetEntity);
			meetAttendCust.setCust(cust);
			
			repositoryMAC.save(meetAttendCust);
		}
		
		for(int i = 0; i < empId.length; i++) {
			MeetAttendEmp meetAttendEmp = new MeetAttendEmp();
			MeetAttendEmpPK meetAttendEmpPK = new MeetAttendEmpPK();
			B2en b2en = repositoryB.getOne(empId[i]);
			meetAttendEmpPK.setEmpSeq(empSeq+i+1);
			meetAttendEmpPK.setMeetId(meetEntity.getMeetId());
			meetAttendEmp.setMeetAttendEmpPK(meetAttendEmpPK);
			meetAttendEmp.setMeet(meetEntity);
			meetAttendEmp.setB2en(b2en);
			
			repositoryMAE.save(meetAttendEmp);
		}
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "")
	public ResponseEntity<Void> delete(@RequestBody DeleteDto id) {
		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			repositoryMAC.deleteByMeetAttendCustPKMeetId(idx[i]);
			repositoryMAE.deleteByMeetAttendEmpPKMeetId(idx[i]);
			repositoryM.deleteById(idx[i]);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody MeetDto meetDto, BindingResult result) {
		
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
		
		toUpdate.setOrg(repositoryO.getOne(meetDto.getOrgId()));
		toUpdate.setMeetDt(java.sql.Date.valueOf(meetDto.getMeetDt()));
		toUpdate.setMeetCnt(meetDto.getMeetCnt());
		toUpdate.setMeetStartTime(java.sql.Time.valueOf(meetDto.getMeetStartTime()));
		toUpdate.setMeetTotTime(meetDto.getMeetTotTime());
		toUpdate.setMeetTpCd(meetDto.getMeetTpCd());

		toUpdate = repositoryM.save(toUpdate);
		
		repositoryMAC.deleteByMeetAttendCustPKMeetId(id);
		repositoryMAE.deleteByMeetAttendEmpPKMeetId(id);
		
		int[] custId = meetDto.getCustId();
		int[] empId = meetDto.getEmpId();
		int custSeq = (repositoryMAC.findMaxCustSeq()==null) ? 0 : repositoryMAC.findMaxCustSeq();// custSeq를 현존하는 가장 큰 custSeq값+1로 직접 할당하기 위한 변수
		int empSeq = (repositoryMAE.findMaxEmpSeq()==null) ? 0 : repositoryMAE.findMaxEmpSeq();// empSeq를 현존하는 가장 큰 empSeq값+1로 직접 할당하기 위한 변수
		for(int i = 0; i < custId.length; i++) {
			MeetAttendCust meetAttendCust = new MeetAttendCust();
			MeetAttendCustPK meetAttendCustPK = new MeetAttendCustPK();
			Cust cust = repositoryC.getOne(custId[i]);
			meetAttendCustPK.setCustSeq(custSeq+i+1);
			meetAttendCustPK.setMeetId(id);
			meetAttendCust.setMeetAttendCustPK(meetAttendCustPK);
			meetAttendCust.setMeet(toUpdate);
			meetAttendCust.setCust(cust);
			
			repositoryMAC.save(meetAttendCust);
		}
			
		for(int i = 0; i < empId.length; i++) {
			MeetAttendEmp meetAttendEmp = new MeetAttendEmp();
			MeetAttendEmpPK meetAttendEmpPK = new MeetAttendEmpPK();
			B2en b2en = repositoryB.getOne(empId[i]);
			meetAttendEmpPK.setEmpSeq(empSeq+i+1);
			meetAttendEmpPK.setMeetId(id);
			meetAttendEmp.setMeetAttendEmpPK(meetAttendEmpPK);
			meetAttendEmp.setMeet(toUpdate);
			meetAttendEmp.setB2en(b2en);
			
			repositoryMAE.save(meetAttendEmp);
		}
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/attend/cust/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetAttendCust>> showAttendCust(@PathVariable int id) {

		List<MeetAttendCust> entityList = repositoryMAC.findByMeetAttendCustPKMeetId(id);
		
		return new ResponseEntity<List<MeetAttendCust>>(entityList, HttpStatus.OK);

	}
	
	@GetMapping(value = "/attend/b2en/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetAttendEmp>> showAttendEmp(@PathVariable int id) {

		List<MeetAttendEmp> entityList = repositoryMAE.findByMeetAttendEmpPKMeetId(id);
		
		return new ResponseEntity<List<MeetAttendEmp>>(entityList, HttpStatus.OK);

	}
}
