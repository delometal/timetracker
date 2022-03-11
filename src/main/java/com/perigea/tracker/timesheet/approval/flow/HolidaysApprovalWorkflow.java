package com.perigea.tracker.timesheet.approval.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.HolidayEventRequestDto;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.rest.RestClient;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class HolidaysApprovalWorkflow implements IApprovalFlow {

	@Autowired
	private RestClient restClient;

	public static final String HOLIDAYS_REQUEST_ENDPOINT = "holiday/add";
	public static final String ALL_HOLIDAYS_APPROVE_ENDPOINT = "holiday/approve-all";
	public static final String SINGLE_HOLIDAY_APPROVE_ENDPOINT = "holiday/approve-single-event";
	public static final String CANCEL_HOLIDAYS_ENDPOINT = "holiday/cancel-holidays";
	public static final String CANCEL_HOLIDAY_APPROVE_ENDPOINT = "holiday/approve-cancel-holidays";
	
	

	public void holidaysRequest(HolidayEventRequestDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, HOLIDAYS_REQUEST_ENDPOINT);
	}

	public void approveAllHolidaysRequest(HolidayEventRequestDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, ALL_HOLIDAYS_APPROVE_ENDPOINT);
	}
	
	public void approveSingleHolidaysRequest(HolidayEventRequestDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, SINGLE_HOLIDAY_APPROVE_ENDPOINT);
	}
	
	public void cancelHolidays(HolidayEventRequestDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, CANCEL_HOLIDAYS_ENDPOINT);
	}
	
	public void approveCancelHolidays(HolidayEventRequestDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, CANCEL_HOLIDAY_APPROVE_ENDPOINT);
	}
	

	@Override
	public void nextStep(Richiesta approvalRequest, RichiestaHistory history) {
		approvalRequest.addRichiestaHistory(history);
	}

}
