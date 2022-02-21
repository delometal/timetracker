package com.perigea.tracker.timesheet.approval.flow;

import com.perigea.tracker.timesheet.entity.Richiesta;
import com.perigea.tracker.timesheet.entity.RichiestaHistory;

public interface IApprovalFlow {
	
	void nextStep(Richiesta approvalRequest, RichiestaHistory history);

}