package com.perigea.tracker.timesheet.approval.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;
import com.perigea.tracker.timesheet.entity.Timesheet;
import com.perigea.tracker.timesheet.repository.TimesheetRepository;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class TimesheetApprovalWorkflow implements IApprovalFlow {

	@Autowired
	private TimesheetRepository timesheetRepository;

//	@Autowired
//	private RichiestaRepository richiestaRepository;
	
	
	/**
	 * TODO notification
	 * @param timesheet
	 * @param approvalRequest
	 * @param history
	 */
	public void nextStep(Timesheet timesheet, Richiesta approvalRequest, RichiestaHistory history) {
		nextStep(approvalRequest, history);
		timesheet.setRichiesta(approvalRequest);		
		timesheetRepository.save(timesheet);
		
		//notification service

	}

	@Override
	public void nextStep(Richiesta approvalRequest, RichiestaHistory history) {
		approvalRequest.addRichiestaHistory(history);
	}

}