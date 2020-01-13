package com.b2en.sms.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.b2en.sms.dto.toclient.MeetAndAttendDtoToClient;
import com.b2en.sms.dto.toclient.MeetAttendCustDto;
import com.b2en.sms.dto.toclient.MeetAttendEmpDto;
import com.b2en.sms.dto.toclient.MeetDtoToClient;
import com.b2en.sms.entity.B2en;
import com.b2en.sms.entity.CmmnDetailCd;
import com.b2en.sms.entity.Cust;
import com.b2en.sms.entity.Meet;
import com.b2en.sms.entity.MeetAttendCust;
import com.b2en.sms.entity.MeetAttendEmp;
import com.b2en.sms.entity.pk.MeetAttendCustPK;
import com.b2en.sms.entity.pk.MeetAttendEmpPK;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.CmmnDetailCdRepository;
import com.b2en.sms.repo.CustRepository;
import com.b2en.sms.repo.MeetAttendCustRepository;
import com.b2en.sms.repo.MeetAttendEmpRepository;
import com.b2en.sms.repo.MeetRepository;

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
	private CustRepository repositoryC;
	@Autowired
	private B2enRepository repositoryB;
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetDtoToClient>> showAll() {

		List<Meet> entityList = repositoryM.findAllByOrderByMeetIdDesc();
		
		if(entityList.size()==0) { // 결과가 없을 경우의 문제 예방
			return new ResponseEntity<List<MeetDtoToClient>>(new ArrayList<MeetDtoToClient>(), HttpStatus.OK);
		}
		
		List<MeetDtoToClient> list;

		list = modelMapper.map(entityList, new TypeToken<List<MeetDtoToClient>>() {
		}.getType());
		
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setMeetStartTime(getTimeString(list.get(i).getMeetStartTime()));
		}
		
		HashMap<String, String> cmmnDetailCdMap = new HashMap<String, String>();
		List<CmmnDetailCd> cmmnDetailCdList = repositoryCDC.findByCmmnDetailCdPKCmmnCd("meet_tp_cd");
		for(int i = 0; i < cmmnDetailCdList.size(); i++) {
			cmmnDetailCdMap.put(cmmnDetailCdList.get(i).getCmmnDetailCdPK().getCmmnDetailCd(), cmmnDetailCdList.get(i).getCmmnDetailCdNm());
		}
		
		List<List<MeetAttendCust>> meetAttendCustList = new ArrayList<List<MeetAttendCust>>();
		List<List<MeetAttendEmp>> meetAttendEmpList = new ArrayList<List<MeetAttendEmp>>();
		List<List<String>> orgList = new ArrayList<List<String>>();
		for(int i = 0; i < entityList.size(); i++) {
			orgList.add(repositoryMAC.findOrg(entityList.get(i).getMeetId()));
			meetAttendCustList.add(repositoryMAC.findByMeetAttendCustPKMeetId(entityList.get(i).getMeetId()));
			meetAttendEmpList.add(repositoryMAE.findByMeetAttendEmpPKMeetId(entityList.get(i).getMeetId()));
		}
		
		for(int i = 0; i < entityList.size(); i++) {
			list.get(i).setOrgNm(orgList.get(i).get(0)+" 외 "+(orgList.get(i).size()-1)+"개 기관");
			list.get(i).setCustNm(meetAttendCustList.get(i).get(0).getCust().getCustNm()+ " 외 " + (meetAttendCustList.get(i).size()-1) + "명");
			list.get(i).setEmpNm(meetAttendEmpList.get(i).get(0).getB2en().getEmpNm()+ " 외 " + (meetAttendEmpList.get(i).size()-1) + "명");
			if(list.get(i).getMeetCnt()==null) {
				list.get(i).setMeetCnt("");
			}
			list.get(i).setMeetTpCdNm(cmmnDetailCdMap.get(entityList.get(i).getMeetTpCd()));
		}
		
		return new ResponseEntity<List<MeetDtoToClient>>(list, HttpStatus.OK);

	}
	
	private String getTimeString(String time) {
		
		String[] split = time.split(":");
		String hour = split[0];
		String minute = split[1];
		String ampm = "";
		int hourInt;
		
		if(hour.charAt(0)=='0') {
			hourInt = hour.charAt(1) - '0';
		} else {
			hourInt = Integer.parseInt(hour);
		}
		
		if(hourInt<12) {
			ampm = "오전";
		} else if(hourInt==12) {
			ampm = "오후";
		} else if(hourInt==24) {
			hourInt -= 12;
			ampm = "오전";
		} else {
			hourInt -= 12;
			ampm = "오후";
		}
		
		hour = Integer.toString(hourInt);
		
		return ampm + " " + hour + "시 " + minute + "분";
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MeetAndAttendDtoToClient> findById(@PathVariable("id") int id) {
		
		Meet meet = repositoryM.findById(id).orElse(null);
		if(meet==null) {
			MeetAndAttendDtoToClient nothing = null;
			return new ResponseEntity<MeetAndAttendDtoToClient>(nothing, HttpStatus.OK);
		}
		
		MeetAndAttendDtoToClient meetAndAttendDtoToClient = modelMapper.map(meet, MeetAndAttendDtoToClient.class);
		String meetTpCdNm = repositoryCDC.findByCmmnDetailCdPKCmmnDetailCd(meet.getMeetTpCd()).getCmmnDetailCdNm();
		meetAndAttendDtoToClient.setMeetTpCdNm(meetTpCdNm);
		if(meetAndAttendDtoToClient.getMeetCnt()==null) {
			meetAndAttendDtoToClient.setMeetCnt("");
		}
		
		List<MeetAttendCust> meetAttendCust = repositoryMAC.findByMeetAttendCustPKMeetId(id);
		List<MeetAttendEmp> meetAttendEmp = repositoryMAE.findByMeetAttendEmpPKMeetId(id);
		MeetAttendCustDto[] meetAttendCustDtoToClientList = new MeetAttendCustDto[meetAttendCust.size()];
		MeetAttendEmpDto[] meetAttendEmpDtoToClientList = new MeetAttendEmpDto[meetAttendEmp.size()];
		
		for(int i = 0; i < meetAttendCust.size(); i++) {
			MeetAttendCustDto meetAttendCustDtoToClient = new MeetAttendCustDto();
			meetAttendCustDtoToClient.setKey(Integer.toString(meetAttendCust.get(i).getCust().getCustId()));
			meetAttendCustDtoToClient.setLabel(meetAttendCust.get(i).getCust().getCustNm());
			meetAttendCustDtoToClientList[i] = meetAttendCustDtoToClient;
		}
		for(int i = 0; i < meetAttendEmp.size(); i++) {
			MeetAttendEmpDto meetAttendEmpDtoToClient = new MeetAttendEmpDto();
			meetAttendEmpDtoToClient.setKey(Integer.toString(meetAttendEmp.get(i).getB2en().getEmpId()));
			meetAttendEmpDtoToClient.setLabel(meetAttendEmp.get(i).getB2en().getEmpNm());
			meetAttendEmpDtoToClientList[i] = meetAttendEmpDtoToClient;
		}
		
		meetAndAttendDtoToClient.setCusts(meetAttendCustDtoToClientList);
		meetAndAttendDtoToClient.setEmps(meetAttendEmpDtoToClientList);
		
		return new ResponseEntity<MeetAndAttendDtoToClient>(meetAndAttendDtoToClient, HttpStatus.OK);
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
		 
		meetDto.setMeetStartTime(meetDto.getMeetStartTime()+":00");
		Meet meetEntity = modelMapper.map(meetDto, Meet.class);
		
		meetEntity = repositoryM.save(meetEntity);
		
		MeetAttendCustDto[] custs = meetDto.getCusts();
		MeetAttendEmpDto[] emps = meetDto.getEmps();
		int custSeq = (repositoryMAC.findMaxCustSeq()==null) ? 0 : repositoryMAC.findMaxCustSeq();// custSeq를 현존하는 가장 큰 custSeq값+1로 직접 할당하기 위한 변수
		int empSeq = (repositoryMAE.findMaxEmpSeq()==null) ? 0 : repositoryMAE.findMaxEmpSeq();// empSeq를 현존하는 가장 큰 empSeq값+1로 직접 할당하기 위한 변수
		for(int i = 0; i < custs.length; i++) {
			MeetAttendCust meetAttendCust = new MeetAttendCust();
			MeetAttendCustPK meetAttendCustPK = new MeetAttendCustPK();
			Cust cust = repositoryC.getOne(Integer.parseInt(custs[i].getKey()));
			meetAttendCustPK.setCustSeq(custSeq+i+1);
			meetAttendCustPK.setMeetId(meetEntity.getMeetId());
			meetAttendCust.setMeetAttendCustPK(meetAttendCustPK);
			meetAttendCust.setMeet(meetEntity);
			meetAttendCust.setCust(cust);
			
			repositoryMAC.save(meetAttendCust);
		}
		
		for(int i = 0; i < emps.length; i++) {
			MeetAttendEmp meetAttendEmp = new MeetAttendEmp();
			MeetAttendEmpPK meetAttendEmpPK = new MeetAttendEmpPK();
			B2en b2en = repositoryB.getOne(Integer.parseInt(emps[i].getKey()));
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
		
		Meet toUpdate = repositoryM.findById(id).orElse(null);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		toUpdate.setMeetDt(java.sql.Date.valueOf(meetDto.getMeetDt()));
		toUpdate.setMeetCnt(meetDto.getMeetCnt());
		toUpdate.setMeetStartTime(java.sql.Time.valueOf(meetDto.getMeetStartTime()+":00"));
		toUpdate.setMeetTotTime(meetDto.getMeetTotTime());
		toUpdate.setMeetTpCd(meetDto.getMeetTpCd());

		toUpdate = repositoryM.save(toUpdate);
		
		repositoryMAC.deleteByMeetAttendCustPKMeetId(id);
		repositoryMAE.deleteByMeetAttendEmpPKMeetId(id);
		
		MeetAttendCustDto[] custs = meetDto.getCusts();
		MeetAttendEmpDto[] emps = meetDto.getEmps();
		int custSeq = (repositoryMAC.findMaxCustSeq()==null) ? 0 : repositoryMAC.findMaxCustSeq();// custSeq를 현존하는 가장 큰 custSeq값+1로 직접 할당하기 위한 변수
		int empSeq = (repositoryMAE.findMaxEmpSeq()==null) ? 0 : repositoryMAE.findMaxEmpSeq();// empSeq를 현존하는 가장 큰 empSeq값+1로 직접 할당하기 위한 변수
		for(int i = 0; i < custs.length; i++) {
			MeetAttendCust meetAttendCust = new MeetAttendCust();
			MeetAttendCustPK meetAttendCustPK = new MeetAttendCustPK();
			Cust cust = repositoryC.getOne(Integer.parseInt(custs[i].getKey()));
			meetAttendCustPK.setCustSeq(custSeq+i+1);
			meetAttendCustPK.setMeetId(id);
			meetAttendCust.setMeetAttendCustPK(meetAttendCustPK);
			meetAttendCust.setMeet(toUpdate);
			meetAttendCust.setCust(cust);
			
			repositoryMAC.save(meetAttendCust);
		}
			
		for(int i = 0; i < emps.length; i++) {
			MeetAttendEmp meetAttendEmp = new MeetAttendEmp();
			MeetAttendEmpPK meetAttendEmpPK = new MeetAttendEmpPK();
			B2en b2en = repositoryB.getOne(Integer.parseInt(emps[i].getKey()));
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
