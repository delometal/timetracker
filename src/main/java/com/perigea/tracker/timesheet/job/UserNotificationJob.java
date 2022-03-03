package com.perigea.tracker.timesheet.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.perigea.tracker.commons.dto.ScheduleDto.Tipo;
import com.perigea.tracker.commons.model.Email;
import com.perigea.tracker.timesheet.rest.RestClient;
import com.perigea.tracker.timesheet.service.SchedulerService;

@Component
public class UserNotificationJob implements Job {
	
	@Autowired
	private SchedulerService schedulerService;
	
	@Autowired
	private RestClient restclient;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Email email = (Email)context.getJobDetail().getJobDataMap().get("email");
		restclient.send(email);
		
		String tipo = (String)context.getJobDetail().getJobDataMap().get("type");
		if (tipo.equals(Tipo.ISTANTANEA.toString())) {
			schedulerService.disactiveNotification(email.getEventID());
		}
		
	}

}
