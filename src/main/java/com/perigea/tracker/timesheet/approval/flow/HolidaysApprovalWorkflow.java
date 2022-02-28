package com.perigea.tracker.timesheet.approval.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.HolidayEventDto;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.rest.CalendarRestClient;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class HolidaysApprovalWorkflow implements IApprovalFlow {
	
	@Autowired
	private CalendarRestClient restClient;
	
	public void holidaysRequest(HolidayEventDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, "holiday/add");
	}
	
	public void approveHolidaysRequest(HolidayEventDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		restClient.sendNotifica(event, "holiday/approve");
	}

	@Override
	public void nextStep(Richiesta approvalRequest, RichiestaHistory history) {
		approvalRequest.addRichiestaHistory(history);
	}

}
