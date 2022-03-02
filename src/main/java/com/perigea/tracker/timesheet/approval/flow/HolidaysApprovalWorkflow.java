package com.perigea.tracker.timesheet.approval.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.HolidayEventDto;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.rest.RestClient;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class HolidaysApprovalWorkflow implements IApprovalFlow {

	@Autowired
	private RestClient restClient;

	public static final String HOLIDAYS_REQUEST_ENDPOINT = "holiday/add";
	public static final String HOLIDAYS_APPROVE_ENDPOINT = "holiday/approve";

	public void holidaysRequest(HolidayEventDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, HOLIDAYS_REQUEST_ENDPOINT);
	}

	public void approveHolidaysRequest(HolidayEventDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, HOLIDAYS_APPROVE_ENDPOINT);
	}

	@Override
	public void nextStep(Richiesta approvalRequest, RichiestaHistory history) {
		approvalRequest.addRichiestaHistory(history);
	}

}
