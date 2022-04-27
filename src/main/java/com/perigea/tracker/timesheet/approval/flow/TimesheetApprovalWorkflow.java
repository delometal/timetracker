package com.perigea.tracker.timesheet.approval.flow;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.commons.dto.TimesheetEventDto;
import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.kafka.sender.KafkaSender;
import com.perigea.tracker.timesheet.repository.TimesheetRepository;
import com.perigea.tracker.timesheet.rest.NotificationRestClient;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class TimesheetApprovalWorkflow implements IApprovalFlow {

	@Autowired
	private Logger logger;
	
	@Autowired
	private TimesheetRepository timesheetRepository;

	@Autowired
	private NotificationRestClient restClient;

	@Autowired
	private KafkaSender kafkaSender;
	
	public static final String TIMESHEET_REQUEST_ENDPOINT = "timesheet/create";
	public static final String APPROVE_TIMESHEET_ENDPOINT = "timesheet/approve";

	public void approveTimesheet(Timesheet timesheet, Richiesta approvalRequest, RichiestaHistory history,
			TimesheetEventDto event) {
		nextStep(approvalRequest, history);
		timesheet.setRichiesta(approvalRequest);
		timesheetRepository.save(timesheet);
		restClient.sendNotificaApprovazione(event, APPROVE_TIMESHEET_ENDPOINT);

	}

	public void richiestaTimesheet(TimesheetEventDto event, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		
		if (kafkaSender.isBrokerOn()) {
			kafkaSender.send(event);
		} else {
			logger.error("Unable to send {}, broker may be offline. Falling back to REST", event);
			//restClient.sendNotifica(event, TIMESHEET_REQUEST_ENDPOINT);
		}
	}

	@Override
	public void nextStep(Richiesta approvalRequest, RichiestaHistory history) {
		approvalRequest.addRichiestaHistory(history);
	}

}