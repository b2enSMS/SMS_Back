package com.b2en.sms.entity;

import java.util.Calendar;
import java.util.List;

public class AddOneDay {
	public static List<Cont> addOneDayInCont(List<Cont> cont) {
		Calendar calContDt;
		Calendar calInstallDt;
		Calendar calCheckDt;
		Calendar calMtncStartDt;
		Calendar calMtncEndDt;
		for(int i = 0; i < cont.size(); i++) {
			calContDt = Calendar.getInstance();
			calInstallDt = Calendar.getInstance();
			calCheckDt = Calendar.getInstance();
			calMtncStartDt = Calendar.getInstance();
			calMtncEndDt = Calendar.getInstance();
			
			calContDt.setTime(cont.get(i).getContDt());
			calInstallDt.setTime(cont.get(i).getInstallDt());
			calCheckDt.setTime(cont.get(i).getCheckDt());
			calMtncStartDt.setTime(cont.get(i).getMtncStartDt());
			calMtncEndDt.setTime(cont.get(i).getMtncEndDt());
			
			calContDt.add(Calendar.DATE, 1);
			calInstallDt.add(Calendar.DATE, 1);
			calCheckDt.add(Calendar.DATE, 1);
			calMtncStartDt.add(Calendar.DATE, 1);
			calMtncEndDt.add(Calendar.DATE, 1);
			
			cont.get(i).setContDt(new java.sql.Date(calContDt.getTimeInMillis()));
			cont.get(i).setInstallDt(new java.sql.Date(calInstallDt.getTimeInMillis()));
			cont.get(i).setCheckDt(new java.sql.Date(calCheckDt.getTimeInMillis()));
			cont.get(i).setMtncStartDt(new java.sql.Date(calMtncStartDt.getTimeInMillis()));
			cont.get(i).setMtncEndDt(new java.sql.Date(calMtncEndDt.getTimeInMillis()));
		}
		return cont;
	}
	
	public static List<ContDetail> addOneDayInLcnsInContDetail(List<ContDetail> contDetail) {
		Calendar calLcnsIssuDt;
		Calendar calLcnsStartDt;
		Calendar calLcnsEndDt;
		for(int i = 0; i < contDetail.size(); i++) {
			calLcnsIssuDt = Calendar.getInstance();
			calLcnsStartDt = Calendar.getInstance();
			calLcnsEndDt = Calendar.getInstance();
			
			calLcnsIssuDt.setTime(contDetail.get(i).getLcns().getLcnsIssuDt());
			calLcnsStartDt.setTime(contDetail.get(i).getLcns().getLcnsStartDt());
			calLcnsEndDt.setTime(contDetail.get(i).getLcns().getLcnsEndDt());
			
			calLcnsIssuDt.add(Calendar.DATE, 1);
			calLcnsStartDt.add(Calendar.DATE, 1);
			calLcnsEndDt.add(Calendar.DATE, 1);
			
			contDetail.get(i).getLcns().setLcnsIssuDt(new java.sql.Date(calLcnsIssuDt.getTimeInMillis()));
			contDetail.get(i).getLcns().setLcnsStartDt(new java.sql.Date(calLcnsStartDt.getTimeInMillis()));
			contDetail.get(i).getLcns().setLcnsEndDt(new java.sql.Date(calLcnsEndDt.getTimeInMillis()));
		}
		return contDetail;
	}
	
	public static List<ContChngHist> addOneDayInContHist(List<ContChngHist> contChngHist) {
		Calendar calContDt;
		Calendar calInstallDt;
		Calendar calCheckDt;
		Calendar calMtncStartDt;
		Calendar calMtncEndDt;
		for(int i = 0; i < contChngHist.size(); i++) {
			calContDt = Calendar.getInstance();
			calInstallDt = Calendar.getInstance();
			calCheckDt = Calendar.getInstance();
			calMtncStartDt = Calendar.getInstance();
			calMtncEndDt = Calendar.getInstance();
			
			calContDt.setTime(contChngHist.get(i).getContDt());
			calInstallDt.setTime(contChngHist.get(i).getInstallDt());
			calCheckDt.setTime(contChngHist.get(i).getCheckDt());
			calMtncStartDt.setTime(contChngHist.get(i).getMtncStartDt());
			calMtncEndDt.setTime(contChngHist.get(i).getMtncEndDt());
			
			calContDt.add(Calendar.DATE, 1);
			calInstallDt.add(Calendar.DATE, 1);
			calCheckDt.add(Calendar.DATE, 1);
			calMtncStartDt.add(Calendar.DATE, 1);
			calMtncEndDt.add(Calendar.DATE, 1);
			
			contChngHist.get(i).setContDt(new java.sql.Date(calContDt.getTimeInMillis()));
			contChngHist.get(i).setInstallDt(new java.sql.Date(calInstallDt.getTimeInMillis()));
			contChngHist.get(i).setCheckDt(new java.sql.Date(calCheckDt.getTimeInMillis()));
			contChngHist.get(i).setMtncStartDt(new java.sql.Date(calMtncStartDt.getTimeInMillis()));
			contChngHist.get(i).setMtncEndDt(new java.sql.Date(calMtncEndDt.getTimeInMillis()));
		}
		return contChngHist;
	}
	
	public static List<TempVer> addOneDayInTempVer(List<TempVer> temp) {
		Calendar calRequestDate;
		Calendar calLcnsIssuDt;
		Calendar calLcnsStartDt;
		Calendar calLcnsEndDt;
		for(int i = 0; i < temp.size(); i++) {
			calRequestDate = Calendar.getInstance();
			calLcnsIssuDt = Calendar.getInstance();
			calLcnsStartDt = Calendar.getInstance();
			calLcnsEndDt = Calendar.getInstance();
			
			calRequestDate.setTime(temp.get(i).getRequestDate());
			calLcnsIssuDt.setTime(temp.get(i).getLcns().getLcnsIssuDt());
			calLcnsStartDt.setTime(temp.get(i).getLcns().getLcnsStartDt());
			calLcnsEndDt.setTime(temp.get(i).getLcns().getLcnsEndDt());
			
			calRequestDate.add(Calendar.DATE, 1);
			calLcnsIssuDt.add(Calendar.DATE, 1);
			calLcnsStartDt.add(Calendar.DATE, 1);
			calLcnsEndDt.add(Calendar.DATE, 1);
			
			temp.get(i).setRequestDate(new java.sql.Date(calRequestDate.getTimeInMillis()));
			temp.get(i).getLcns().setLcnsIssuDt(new java.sql.Date(calLcnsIssuDt.getTimeInMillis()));
			temp.get(i).getLcns().setLcnsStartDt(new java.sql.Date(calLcnsStartDt.getTimeInMillis()));
			temp.get(i).getLcns().setLcnsEndDt(new java.sql.Date(calLcnsEndDt.getTimeInMillis()));
		}
		return temp;
	}
	
	public static List<TempVerHist> addOneDayInTempVerHist(List<TempVerHist> tempHist) {
		Calendar calRequestDate;
		//Calendar calLcnsIssuDt;
		//Calendar calLcnsStartDt;
		//Calendar calLcnsEndDt;
		for(int i = 0; i < tempHist.size(); i++) {
			calRequestDate = Calendar.getInstance();
			//calLcnsIssuDt = Calendar.getInstance();
			//calLcnsStartDt = Calendar.getInstance();
			//calLcnsEndDt = Calendar.getInstance();
			
			calRequestDate.setTime(tempHist.get(i).getRequestDate());
			//calLcnsIssuDt.setTime(tempHist.get(i).getLcns().getLcnsIssuDt());
			//calLcnsStartDt.setTime(tempHist.get(i).getLcns().getLcnsStartDt());
			//calLcnsEndDt.setTime(tempHist.get(i).getLcns().getLcnsEndDt());
			
			calRequestDate.add(Calendar.DATE, 1);
			//calLcnsIssuDt.add(Calendar.DATE, 1);
			//calLcnsStartDt.add(Calendar.DATE, 1);
			//calLcnsEndDt.add(Calendar.DATE, 1);
			
			tempHist.get(i).setRequestDate(new java.sql.Date(calRequestDate.getTimeInMillis()));
			//tempHist.get(i).getLcns().setLcnsIssuDt(new java.sql.Date(calLcnsIssuDt.getTimeInMillis()));
			//tempHist.get(i).getLcns().setLcnsStartDt(new java.sql.Date(calLcnsStartDt.getTimeInMillis()));
			//tempHist.get(i).getLcns().setLcnsEndDt(new java.sql.Date(calLcnsEndDt.getTimeInMillis()));
		}
		return tempHist;
	}
	
	public static List<Meet> addOneDayInMeet(List<Meet> meet) {
		Calendar calMeetDt;

		for(int i = 0; i < meet.size(); i++) {
			calMeetDt = Calendar.getInstance();
			
			calMeetDt.setTime(meet.get(i).getMeetDt());
			
			calMeetDt.add(Calendar.DATE, 1);
			
			meet.get(i).setMeetDt(new java.sql.Date(calMeetDt.getTimeInMillis()));
		}
		return meet;
	}
}
