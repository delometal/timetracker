package com.perigea.tracker.timesheet.approval.flow;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class HolidaysApprovalWorkflow implements IApprovalFlow{

	@Override
	public void nextStep(Richiesta approvalRequest, RichiestaHistory history) {
		approvalRequest.addRichiestaHistory(history);
	}

}
